package io.miragon.miraum.fitconnect.integration.authority.api;

import com.nimbusds.jose.JOSEException;

import java.io.IOException;
import java.text.ParseException;

public interface AuthorityApi {

    void pollAndAcceptPickupReadySubmissions() throws JOSEException, ParseException, IOException;
}