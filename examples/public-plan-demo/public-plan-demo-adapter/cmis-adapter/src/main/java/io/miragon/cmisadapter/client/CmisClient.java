package io.miragon.cmisadapter.client;

import io.miragon.cmisadapter.CmisObjectType;
import io.miragon.cmisadapter.configuration.CmisServerProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.chemistry.opencmis.client.api.*;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.BaseTypeId;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.UnfileObject;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class CmisClient implements ICmisClient {

    private final CmisServerProperties cmisServerProperties;

    private Session session;

    public CmisClient(final CmisServerProperties cmisServerProperties) {
        this.cmisServerProperties = cmisServerProperties;
    }

    @Override
    public Folder getRootFolder() {
        return this.session.getRootFolder();
    }

    @Override
    public void connect() {
        SessionFactory sessionFactory = SessionFactoryImpl.newInstance();
        Map<String, String> parameter = new HashMap<>();

        parameter.put(SessionParameter.USER, cmisServerProperties.getUser());
        parameter.put(SessionParameter.PASSWORD, cmisServerProperties.getPassword());

        parameter.put(SessionParameter.ATOMPUB_URL, this.cmisServerProperties.getAtompub());
        parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());

        Repository repository = sessionFactory.getRepositories(parameter).get(0);
        session = repository.createSession();
        log.info("Session created.");
    }

    @Override
    public void deleteFolder(Folder folder, String name) {
        try {
            var path = Paths.get(folder.getPath(), name);
            CmisObject object = session.getObjectByPath(path.toString());
            Folder delFolder = (Folder) object;
            delFolder.deleteTree(true, UnfileObject.DELETE, true);
            log.info("Deleted folder: {}.", name);
        } catch (CmisObjectNotFoundException e) {
            log.warn("Folder {} not found.", name);
        }
    }

    @Override
    public void listFolder(int depth, Folder folder) {
        String indent = StringUtils.repeat("\t", depth);
        for (CmisObject object : folder.getChildren()) {
            if (BaseTypeId.CMIS_DOCUMENT.equals(object.getBaseTypeId())) {
                log.info("{} [Document] {}", indent, object.getName());
            } else if (BaseTypeId.CMIS_FOLDER.equals(object.getBaseTypeId())) {
                log.info("{} [Folder] {}", indent, object.getName());
                listFolder(++depth, (Folder) object);
            }
        }
    }

    @Override
    public void deleteDocument(Folder folder, String name) {
        try {
            CmisObject object = session.getObjectByPath(folder.getPath() + name);
            Document delDoc = (Document) object;
            delDoc.delete(true);
            log.warn("Deleted document: {}.", name);
        } catch (CmisObjectNotFoundException e) {
            log.warn("Document not found: {}.", name);
        }
    }

    @Override
    public void createDocument(Folder folder, String name, byte[] content) {
        var properties = getObjectProperties(name, CmisObjectType.DOCUMENT);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(content);
        ContentStream contentStream = session.getObjectFactory()
                .createContentStream(name, content.length,
                        "application/octet-stream", inputStream);
        folder.createDocument(properties, contentStream, VersioningState.NONE);
        log.info("Created document: {}.", name);
    }

    @Override
    public Folder createFolder(Folder folder, String name) {
        var properties = getObjectProperties(name, CmisObjectType.FOLDER);
        var newFolder = folder.createFolder(properties);
        log.info("Created folder: {}.", name);
        return newFolder;
    }

    @Override
    public byte[] getContent(Folder folder, String name) {
        Path path = Paths.get(folder.getPath(), name);
        CmisObject object = session.getObjectByPath(path.toString());
        Document document = (Document) object;
        ContentStream contentStream = document.getContentStream();
        byte[] bytes;
        try {
            bytes = contentStream.getStream().readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("Content of file {}: {}.", path, new String(bytes, StandardCharsets.UTF_8));
        return bytes;
    }

    private Map<String, String> getObjectProperties(String name, CmisObjectType objectType) {
        Map<String, String> props = new HashMap<>();
        props.put(PropertyIds.OBJECT_TYPE_ID, objectType.getType());
        props.put(PropertyIds.NAME, name);
        return props;
    }
}