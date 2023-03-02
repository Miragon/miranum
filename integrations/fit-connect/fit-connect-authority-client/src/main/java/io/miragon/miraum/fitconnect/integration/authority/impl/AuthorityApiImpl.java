package io.miragon.miraum.fitconnect.integration.authority.impl;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.miragon.miranum.connect.process.api.ProcessApi;
import io.miragon.miraum.fitconnect.integration.authority.AuthorityProperties;
import io.miragon.miraum.fitconnect.integration.authority.api.AuthorityApi;
import io.miragon.miraum.fitconnect.integration.gen.api.EinreichungsempfangApi;
import io.miragon.miraum.fitconnect.integration.gen.model.SubmissionForPickup;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
@Log
@AllArgsConstructor
@EnableConfigurationProperties(AuthorityProperties.class)
public class AuthorityApiImpl implements AuthorityApi {

    private final EinreichungsempfangApi apiClient;
    private final AuthorityProperties authorityProperties;
    private final ProcessApi processApi;

    @Override
    public void pollAndAcceptPickupReadySubmissions() throws ParseException, IOException {
        log.info("Fetch submissions...");
        var submissionsForPickupResponse = apiClient.getSubmissionsForPickup(UUID.fromString(authorityProperties.getDestinationId()), 100, 0).block();

        // TODO: Verify submission, see https://docs.fitko.de/fit-connect/docs/receiving/verification
        for (var submission : submissionsForPickupResponse.getSubmissions()) {
            var submissionData = apiClient.getSubmission(submission.getSubmissionId()).block();

            for (var attachmentId : submissionData.getAttachments()) {
                var attachment = apiClient.getSubmissionAttachment(submission.getSubmissionId(), attachmentId).block();

                log.info("Encrypted attachment payload: " + attachment);

                var rsaService = new RSAService(Paths.get(authorityProperties.getPrivateKeyDecryptionPath()));
                rsaService.validateRsaKey();
                var decryptedPayload = rsaService.decrypt(attachment);

                log.info("Decrypted attachment payload: " + decryptedPayload);

                var startProcessCommand = ProcessCommandFactory.create(decryptedPayload, authorityProperties.getProcessKey());
                processApi.startProcess(startProcessCommand);
            }

            var signedAndSerializedSET = createSignedAndSerializedAcceptSet(authorityProperties.getDestinationId(), submission);

            apiClient.sendCaseEvent(submission.getCaseId(), signedAndSerializedSET).block();
        }
    }

    private String createSignedAndSerializedAcceptSet(String destinationIdStr, SubmissionForPickup submission) throws IOException, ParseException {
        JWK signaturePrivateKey = RSAKey.parse(Files.readString(Paths.get(authorityProperties.getPrivateKeySigningPath())));
        String subject = "submission:" + submission.getSubmissionId();
        String event = "https://schema.fitko.de/fit-connect/events/accept-submission";
        String transactionId = "case:" + submission.getCaseId();

        try {
            JWSSigner signer = new RSASSASigner(signaturePrivateKey.toRSAKey());
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .issuer(destinationIdStr)
                    .issueTime(new Date())
                    .jwtID(UUID.randomUUID().toString())
                    .subject(subject)
                    .claim("events", Map.of(event, Map.of()))
                    .claim("txn", transactionId)
                    .build();

            JWSHeader header = JWSHeader.parse(Map.of(
                    "typ", "secevent+jwt",
                    "kid", signaturePrivateKey.getKeyID(),
                    "alg", "PS512"
            ));

            SignedJWT signedJWT = new SignedJWT(
                    header,
                    claimsSet);

            signedJWT.sign(signer);

            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException("Could not generate SET");
        }
    }
}