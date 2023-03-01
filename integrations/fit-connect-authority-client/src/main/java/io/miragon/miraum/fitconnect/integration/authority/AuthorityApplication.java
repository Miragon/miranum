package io.miragon.miraum.fitconnect.integration.authority;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.KeyOperation;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.miragon.miraum.fitconnect.integration.gen.api.EinreichungsempfangApi;
import io.miragon.miraum.fitconnect.integration.gen.model.SubmissionForPickup;
import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@SpringBootApplication
@Log
public class AuthorityApplication {

    public static void main(String[] args) throws IOException, ParseException, JOSEException {
        ConfigurableApplicationContext context = SpringApplication.run(AuthorityApplication.class, args);

        var destinationIdStr = context.getEnvironment().getProperty("fitconnect.destination-id");
        var destinationId = UUID.fromString(destinationIdStr);

        var apiClient = context.getBean(EinreichungsempfangApi.class);
        var submissionsForPickupResponse = apiClient.getSubmissionsForPickup(destinationId, 100, 0).block();

        // TODO: Verify submission, see https://docs.fitko.de/fit-connect/docs/receiving/verification
        for (var submission : submissionsForPickupResponse.getSubmissions()) {
            var submissionData = apiClient.getSubmission(submission.getSubmissionId()).block();

            for (var attachmentId : submissionData.getAttachments()) {
                var attachment = apiClient.getSubmissionAttachment(submission.getSubmissionId(), attachmentId).block();
                log.info("Encrypted attachment payload: " + attachment);

                var path = Paths.get("/path/to/privateKey_decryption.json");

                String keyStr = new String(Files.readAllBytes(path));
                RSAKey jwk = RSAKey.parse(keyStr);

                validateRSAKey(jwk, true);

                JWEObject jweObject = JWEObject.parse(attachment);

                jweObject.decrypt(new RSADecrypter(jwk));

                log.info("Decrypted attachment payload: " + jweObject.getPayload().toString());
            }

            var signedAndSerializedSET = createSignedAndSerializedAcceptSet(destinationIdStr, submission);
            apiClient.sendCaseEvent(submission.getCaseId(), signedAndSerializedSET).block();
        }
    }

    private static String createSignedAndSerializedAcceptSet(String destinationIdStr, SubmissionForPickup submission) throws ParseException, IOException {
        JWK signaturePrivateKey = RSAKey.parse(Files.readString(Paths.get("/path/to/privateKey_signing.json")));
        String issuer = destinationIdStr; // our own destinationId (MUST match destinationId of this submission)
        String subject = "submission:" + submission.getSubmissionId();
        String event ="https://schema.fitko.de/fit-connect/events/accept-submission";
        String transactionId = "case:" + submission.getCaseId();

        try {
            JWSSigner signer = new RSASSASigner(signaturePrivateKey.toRSAKey());
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .issuer(issuer)
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

    public static void validateRSAKey(RSAKey RSAKey, boolean isPrivate) {
        validateTrueOrElseThrow((RSAKey.getModulus().decodeToBigInteger().bitLength() >= 4096), "JWK has wrong key length.");
        if (isPrivate) {
            validateTrueOrElseThrow(RSAKey.getKeyOperations().size() == 1 &&
                            RSAKey.getKeyOperations().contains(KeyOperation.UNWRAP_KEY),
                    "The specified private key is not intended for 'unwrapKey' as specified through key operation.");
        } else {
            validateTrueOrElseThrow(RSAKey.getKeyOperations().size() == 1 &&
                            RSAKey.getKeyOperations().contains(KeyOperation.WRAP_KEY),
                    "The specified public key is not intended for 'wrapKey' as specified through key operation.");
        }
        validateTrueOrElseThrow(RSAKey.getAlgorithm().equals(JWEAlgorithm.RSA_OAEP_256), "Key algorithm must be RSA-OAEP-256!");
        validateTrueOrElseThrow(RSAKey.getPublicExponent().toString().equals("AQAB"), "Key must have e: \"AQAB\"");
    }

    private static void validateTrueOrElseThrow(boolean expression, String msg) {
        if (!expression) {
            throw new RuntimeException(msg);
        }
    }
}