package com.parsjavid.supernuts.models;

public class ProductGroup {
    public static final long Pistachio_Kernel=3;
    public static final long Pistachio=4;
    public static final long Walnut=5;
    public static final long Almond=6;

    private String name;
    private long id;
    private long UnitCode;
    private Long parentId;
    private String slug;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUnitCode() {
        return UnitCode;
    }

    public void setUnitCode(long unitCode) {
        UnitCode = unitCode;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
