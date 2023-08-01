package io.miranum.integration.s3.service;

import io.miranum.integration.s3.client.exception.PropertyNotSetException;
import io.miranum.integration.s3.client.service.ApiClientFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class ApiClientFactoryTest {

    @Test
    void getDefaultDocumentStorageUrl() throws PropertyNotSetException {
        var apiClientFactory = new ApiClientFactory(null, null);
        Assertions.assertThrows(PropertyNotSetException.class, apiClientFactory::getDefaultDocumentStorageUrl);

        apiClientFactory = new ApiClientFactory("url", null);
        assertThat(apiClientFactory.getDefaultDocumentStorageUrl(), is("url"));
    }

}
