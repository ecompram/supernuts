package com.parsjavid.supernuts.models;

import java.math.BigDecimal;
import java.util.List;

public class Product {
    private String title, imageUrl, baseImageFilePath350,shortDescription,
            providerFullName,baseImageFilePath200,baseImageFilePath600,review,description;
    private BigDecimal price,beforeDiscountPrice;
    private Long id,minimumOrderValue,stockValue;
    private List<ProductAttribute> productAttributes;


    public Product() {
    }

    public Product(Long id, String title, String imageUrl, BigDecimal price, String shortDescription,
                   String baseImageFilePath200,String providerFullName) {
        this.id=id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.price = price;
        this.baseImageFilePath200=baseImageFilePath200;
        this.shortDescription = shortDescription;
        this.providerFullName=providerFullName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getBaseImageFilePath600() {
        return baseImageFilePath600;
    }

    public void setBaseImageFilePath600(String baseImageFilePath600) {
        this.baseImageFilePath600 = baseImageFilePath600;
    }

    public String getBaseImageFilePath200() {
        return baseImageFilePath200;
    }

    public void setBaseImageFilePath200(String baseImageFilePath200) {
        this.baseImageFilePath200 = baseImageFilePath200;
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

    public String getProviderFullName() {
        return providerFullName;
    }

    public void setProviderFullName(String providerFullName) {
        this.providerFullName = providerFullName;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getBeforeDiscountPrice() {
        return beforeDiscountPrice;
    }

    public void setBeforeDiscountPrice(BigDecimal beforeDiscountPrice) {
        this.beforeDiscountPrice = beforeDiscountPrice;
    }

    public List<ProductAttribute> getProductAttributes() {
        return productAttributes;
    }

    public void setProductAttributes(List<ProductAttribute> productAttributes) {
        this.productAttributes = productAttributes;
    }

    public Long getMinimumOrderValue() {
        return minimumOrderValue;
    }

    public void setMinimumOrderValue(Long minimumOrderValue) {
        this.minimumOrderValue = minimumOrderValue;
    }

    public Long getStockValue() {
        return stockValue;
    }

    public void setStockValue(Long stockValue) {
        this.stockValue = stockValue;
    }
}
