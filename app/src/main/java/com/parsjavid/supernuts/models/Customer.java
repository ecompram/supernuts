package com.parsjavid.supernuts.models;

public class Customer {
    private String name;
    private String family;
    private String mobile;
    private String nationalCode;
    private String address;
    private String telephone;
    private Long systemUserId;
    private boolean isProvider;
    private boolean isWholeSaler;
    private boolean isMajorBuyer;
    private String token;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Long getSystemUserId() {
        return systemUserId;
    }

    public void setSystemUserId(Long systemUserId) {
        this.systemUserId = systemUserId;
    }

    public boolean getIsProvider() {
        return isProvider;
    }

    public void setIsProvider(boolean provider) {
        isProvider = provider;
    }

    public boolean getIsWholeSaler() {
        return isWholeSaler;
    }

    public void setIsWholeSaler(boolean wholeSaler) {
        isWholeSaler = wholeSaler;
    }

    public boolean getIsMajorBuyer() {
        return isMajorBuyer;
    }

    public void setIsMajorBuyer(boolean majorBuyer) {
        isMajorBuyer = majorBuyer;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
