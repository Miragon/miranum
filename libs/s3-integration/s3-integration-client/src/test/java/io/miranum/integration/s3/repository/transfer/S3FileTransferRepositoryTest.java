package io.miranum.integration.s3.repository.transfer;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.miranum.integration.s3.client.exception.DocumentStorageClientErrorException;
import io.miranum.integration.s3.client.exception.DocumentStorageException;
import io.miranum.integration.s3.client.exception.DocumentStorageServerErrorException;
import io.miranum.integration.s3.client.repository.transfer.S3FileTransferRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@WireMockTest
class S3FileTransferRepositoryTest {

    private final String PRESIGNED_URL = "/expected-presigned-url";

    private final S3FileTransferRepository s3FileTransferRepository =  new S3FileTransferRepository();

    @Test
    void getFile(final WireMockRuntimeInfo wmRuntimeInfo) throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        final String baseUrl = wmRuntimeInfo.getHttpBaseUrl();
        final byte[] file = new byte[]{ 1, 2, 3, 4, 5, 6, 7 };
        final String presignedUrl = baseUrl + this.PRESIGNED_URL;

        WireMock.stubFor(WireMock.get(this.PRESIGNED_URL).willReturn(WireMock.aResponse().withBody(file).withStatus(200)));
        final byte[] result = this.s3FileTransferRepository.getFile(presignedUrl);
        assertThat(result, is(file));

        WireMock.stubFor(WireMock.get(this.PRESIGNED_URL).willReturn(WireMock.aResponse().withStatus(400)));
        Assertions.assertThrows(DocumentStorageClientErrorException.class, () -> this.s3FileTransferRepository.getFile(presignedUrl));

        WireMock.stubFor(WireMock.get(this.PRESIGNED_URL).willReturn(WireMock.aResponse().withStatus(500)));
        Assertions.assertThrows(DocumentStorageServerErrorException.class, () -> this.s3FileTransferRepository.getFile(presignedUrl));

        Assertions.assertThrows(DocumentStorageException.class, () -> this.s3FileTransferRepository.getFile("http://localhost:" + (wmRuntimeInfo.getHttpPort() + 1) + "/invalid-url"));
    }

    @Test
    void saveFile(final WireMockRuntimeInfo wmRuntimeInfo) throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        final String baseUrl = wmRuntimeInfo.getHttpBaseUrl();
        final byte[] file = new byte[]{1, 2, 3, 4, 5, 6, 7};
        final String presignedUrl = baseUrl + this.PRESIGNED_URL;

        WireMock.stubFor(WireMock.put(this.PRESIGNED_URL).withRequestBody(WireMock.binaryEqualTo(file)).willReturn(WireMock.ok()));
        this.s3FileTransferRepository.saveFile(presignedUrl, file);

        WireMock.stubFor(WireMock.put(this.PRESIGNED_URL).willReturn(WireMock.aResponse().withStatus(400)));
        Assertions.assertThrows(DocumentStorageClientErrorException.class, () -> this.s3FileTransferRepository.saveFile(presignedUrl, file));

        WireMock.stubFor(WireMock.put(this.PRESIGNED_URL).willReturn(WireMock.aResponse().withStatus(500)));
        Assertions.assertThrows(DocumentStorageServerErrorException.class, () -> this.s3FileTransferRepository.saveFile(presignedUrl, file));

        Assertions.assertThrows(DocumentStorageException.class, () -> this.s3FileTransferRepository.saveFile("http://localhost:" + (wmRuntimeInfo.getHttpPort() + 1) + "/invalid-url", file));
    }

    @Test
    void updateFile(final WireMockRuntimeInfo wmRuntimeInfo) throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        final String baseUrl = wmRuntimeInfo.getHttpBaseUrl();
        final byte[] file = new byte[]{1, 2, 3, 4, 5, 6, 7};
        final String presignedUrl = baseUrl + this.PRESIGNED_URL;

        WireMock.stubFor(WireMock.put(this.PRESIGNED_URL).withRequestBody(WireMock.binaryEqualTo(file)).willReturn(WireMock.ok()));
        this.s3FileTransferRepository.updateFile(presignedUrl, file);

        WireMock.stubFor(WireMock.put(this.PRESIGNED_URL).willReturn(WireMock.aResponse().withStatus(400)));
        Assertions.assertThrows(DocumentStorageClientErrorException.class, () -> this.s3FileTransferRepository.updateFile(presignedUrl, file));

        WireMock.stubFor(WireMock.put(this.PRESIGNED_URL).willReturn(WireMock.aResponse().withStatus(500)));
        Assertions.assertThrows(DocumentStorageServerErrorException.class, () -> this.s3FileTransferRepository.updateFile(presignedUrl, file));

        Assertions.assertThrows(DocumentStorageException.class, () -> this.s3FileTransferRepository.updateFile("http://localhost:" + (wmRuntimeInfo.getHttpPort() + 1) + "/invalid-url", file));
    }

    @Test
    void deleteFile(final WireMockRuntimeInfo wmRuntimeInfo) throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        final String baseUrl = wmRuntimeInfo.getHttpBaseUrl();
        final String presignedUrl = baseUrl + this.PRESIGNED_URL;

        WireMock.stubFor(WireMock.delete(this.PRESIGNED_URL).willReturn(WireMock.aResponse().withStatus(204)));
        this.s3FileTransferRepository.deleteFile(presignedUrl);

        WireMock.stubFor(WireMock.delete(this.PRESIGNED_URL).willReturn(WireMock.aResponse().withStatus(400)));
        Assertions.assertThrows(DocumentStorageClientErrorException.class, () -> this.s3FileTransferRepository.deleteFile(presignedUrl));

        WireMock.stubFor(WireMock.delete(this.PRESIGNED_URL).willReturn(WireMock.aResponse().withStatus(500)));
        Assertions.assertThrows(DocumentStorageServerErrorException.class, () -> this.s3FileTransferRepository.deleteFile(presignedUrl));

        Assertions.assertThrows(DocumentStorageException.class, () -> this.s3FileTransferRepository.deleteFile("http://localhost:" + (wmRuntimeInfo.getHttpPort() + 1) + "/invalid-url"));

    }

}
