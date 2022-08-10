package com.techelevator.model;

public class Image {
    private int imageId;
    private String imageName;
    private String imageType;

    private byte[] imageData;

    public Image(int imageId, String imageName, String imageType, byte[] imageData) {
        this.imageId = imageId;
        this.imageName = imageName;
        this.imageType = imageType;
        this.imageData = imageData;
    }

    public Image() {}

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}
