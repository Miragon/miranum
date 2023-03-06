package io.miragon.miraum.fitconnect.integration.onlineservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.jwk.KeyOperation;
import com.nimbusds.jose.jwk.RSAKey;
import io.miragon.miraum.fitconnect.integration.gen.api.EinreichungsbermittlungApi;
import io.miragon.miraum.fitconnect.integration.gen.model.CreateSubmission;
import io.miragon.miraum.fitconnect.integration.gen.model.SubmitSubmission;
import io.miragon.miraum.fitconnect.integration.gen.model.Verwaltungsleistung;
import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.text.ParseException;
import java.util.UUID;

@SpringBootApplication
@Log
public class OnlineServiceApplication {

    public static void main(String[] args) throws ParseException, JOSEException, JsonProcessingException {
        ConfigurableApplicationContext context = SpringApplication.run(OnlineServiceApplication.class, args);

        var destinationIdStr = context.getEnvironment().getProperty("fitconnect.sender.destination-id");
        var destinationId = UUID.fromString(destinationIdStr);

        var apiClient = context.getBean(EinreichungsbermittlungApi.class);
        var info = apiClient.getDestinationInfo(destinationId).block();
        log.info(info.toString());

        var encryptionKeyId = info.getEncryptionKid();
        var publicKeyResponse = apiClient.getDestinationKey(destinationId, encryptionKeyId).block();
        log.info(publicKeyResponse.toString());

        var objMapper = new ObjectMapper();
        var publicKeyJsonString = objMapper.writeValueAsString(publicKeyResponse);

        var publicKey = RSAKey.parse(publicKeyJsonString);
        validateRSAKey(publicKey, false);

        var data = "{\"firstname\":\"test_firstname\", \"lastname\":\"test_lastname\", \"age\": 40}";

        // Create submission
        var createSubmission = new CreateSubmission();
        createSubmission.setDestinationId(destinationId);
        createSubmission.setServiceType(
                new Verwaltungsleistung()
                        .name("Test")
                        .identifier("urn:de:fim:leika:leistung:99008001012011")
        );
        var createSubmissionResponse = apiClient.createSubmission(createSubmission).block();
        log.info(createSubmissionResponse.toString());

        var submissionId = createSubmissionResponse.getSubmissionId();
        var caseId = createSubmissionResponse.getCaseId();

        // Encrypt attachments using the public key of the destination
        JWEHeader header = new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256GCM).build();
        Payload payload = new Payload(data);

        JWEObject jweObject = new JWEObject(header, payload);
        jweObject.encrypt(new RSAEncrypter(publicKey));
        String encryptedData = jweObject.serialize();

        // Submit submission
        var submitSubmission = new SubmitSubmission();
        submitSubmission.setEncryptedMetadata(encryptedData);
        submitSubmission.setEncryptedData(encryptedData);
        var submitSubmissionResponse = apiClient.submitSubmission(
                submissionId , submitSubmission).block();

        log.info(submitSubmissionResponse.toString());

        var caseEventResponse = apiClient.getCaseEvents(caseId, 100, 0).block();
        log.info(caseEventResponse.toString());
        log.info("Submission submitted successfully!");
    }

    public static void validateRSAKey(RSAKey RSAKey, boolean isPrivate){
        validateTrueOrElseThrow((RSAKey.getModulus().decodeToBigInteger().bitLength() >= 4096), "JWK has wrong key length.");
        if(isPrivate){
            validateTrueOrElseThrow(RSAKey.getKeyOperations().size() == 1 &&
                            RSAKey.getKeyOperations().contains(KeyOperation.UNWRAP_KEY),
                    "The specified private key is not intended for 'unwrapKey' as specified through key operation.");
        }
        else{
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