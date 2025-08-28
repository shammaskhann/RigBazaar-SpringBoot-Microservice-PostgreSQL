package com.example.rigbazaar.RigBazaar.constants;

public enum contentType {
    TEXT("text/plain"),
    IMAGE("image/jpeg"),
    VIDEO("video/mp4"),
    AUDIO("audio/mp3"),
    FILE("application/octet-stream");

    private final String type;

    contentType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
