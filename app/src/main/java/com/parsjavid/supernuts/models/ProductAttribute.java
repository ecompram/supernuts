package com.parsjavid.supernuts.models;

public class ProductAttribute {
    private Long productId,baseAttributeId;
    private String baseAttributeLabel;
    private short attributeOrder;
    private String attributeValues;
    private Boolean showAsVariable;
    private Boolean showInFront;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getBaseAttributeId() {
        return baseAttributeId;
    }

    public void setBaseAttributeId(Long baseAttributeId) {
        this.baseAttributeId = baseAttributeId;
    }

    public String getBaseAttributeLabel() {
        return baseAttributeLabel;
    }

    public void setBaseAttributeLabel(String baseAttributeLabel) {
        this.baseAttributeLabel = baseAttributeLabel;
    }

    public short getAttributeOrder() {
        return attributeOrder;
    }

    public void setAttributeOrder(short attributeOrder) {
        this.attributeOrder = attributeOrder;
    }

    public String getAttributeValues() {
        return attributeValues;
    }

    public void setAttributeValues(String attributeValues) {
        this.attributeValues = attributeValues;
    }

    public Boolean getShowAsVariable() {
        return showAsVariable;
    }

    public void setShowAsVariable(Boolean showAsVariable) {
        this.showAsVariable = showAsVariable;
    }

    public Boolean getShowInFront() {
        return showInFront;
    }

    public void setShowInFront(Boolean showInFront) {
        this.showInFront = showInFront;
    }
}
