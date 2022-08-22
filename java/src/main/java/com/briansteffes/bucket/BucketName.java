package com.briansteffes.bucket;

public enum BucketName {
    PROFILE_IMAGE("spotholes-pothole-images");

    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }

}