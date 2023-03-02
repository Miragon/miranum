package io.miragon.miraum.fitconnect.integration.destinationconnector.api;

import java.io.IOException;
import java.text.ParseException;

public interface AuthorityApi {

    void pollAndAcceptPickupReadySubmissions() throws ParseException, IOException;
}