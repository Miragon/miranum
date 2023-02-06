package io.miragon.cmisadapter.client;

import org.apache.chemistry.opencmis.client.api.Folder;

public interface ICmisClient {

    void connect();

    Folder getRootFolder();

    void deleteFolder(Folder folder, String name);

    void listFolder(int depth, Folder folder);

    void deleteDocument(Folder folder, String name);

    void createDocument(Folder folder, String name, byte[] content);

    Folder createFolder(Folder folder, String name);

    byte[] getContent(Folder folder, String name);
}
