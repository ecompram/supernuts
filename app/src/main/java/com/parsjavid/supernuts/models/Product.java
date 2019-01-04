package com.parsjavid.supernuts.models;

import java.math.BigDecimal;

public class Product {
    private String title, imageUrl, baseImageFilePath350,shortDescription;
    private BigDecimal price;

    public Product() {
    }

    public Product(String title, String imageUrl, BigDecimal price, String shortDescription,String baseImageFilePath350) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.price = price;
        this.baseImageFilePath350=baseImageFilePath350;
        this.shortDescription = shortDescription;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getBaseImageFilePath350() {
        return baseImageFilePath350;
    }

    public void setBaseImageFilePath350(String baseImageFilePath350) {
        this.baseImageFilePath350 = baseImageFilePath350;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
}
