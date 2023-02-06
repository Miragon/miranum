package io.miragon.cmisadapter;

import io.miragon.cmisadapter.client.ICmisClient;
import io.miragon.publicplandemocore.application.port.out.StorePDFPort;

public class CmisAdapter implements StorePDFPort {

    private final ICmisClient cmisClient;

    public CmisAdapter(ICmisClient cmisClient) {
        this.cmisClient = cmisClient;
        this.cmisClient.connect();
    }

    @Override
    public void storePDF(byte[] bytes) {
        var root = this.cmisClient.getRootFolder();
        this.cmisClient.createDocument(root, "name.pdf", bytes);
    }
}
