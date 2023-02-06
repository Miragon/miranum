package io.miragon.cmisadapter;

public enum CmisObjectType {
    DOCUMENT("cmis:document"),
    FOLDER("cmis:folder");

    private final String type;

    CmisObjectType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
