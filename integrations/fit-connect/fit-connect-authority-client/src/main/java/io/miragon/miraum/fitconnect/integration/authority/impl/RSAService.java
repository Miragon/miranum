package io.miragon.miraum.fitconnect.integration.authority.impl;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.jwk.KeyOperation;
import com.nimbusds.jose.jwk.RSAKey;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;

public class RSAService {
    private final RSAKey jwk;

    public RSAService(Path path) {
        String keyStr;
        try {
            keyStr = new String(Files.readAllBytes(path));
            jwk = RSAKey.parse(keyStr);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static void validateRSAKey(RSAKey RSAKey, boolean isPrivate) {
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

    public void validateRsaKey() {
        validateRSAKey(jwk, true);
    }

    public String decrypt(String attachment) {
        JWEObject jweObject;
        try {
            jweObject = JWEObject.parse(attachment);
            jweObject.decrypt(new RSADecrypter(jwk));
        } catch (ParseException | JOSEException e) {
            throw new RuntimeException(e);
        }
        return jweObject.getPayload().toString();
    }
}