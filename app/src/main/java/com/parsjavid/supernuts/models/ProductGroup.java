package com.parsjavid.supernuts.models;

public class ProductGroup extends EntityBase{
    public static final long Pistachio_Kernel=3;
    public static final long Pistachio=4;
    public static final long Walnut=5;
    public static final long Almond=6;
    private long UnitCode;
    private Long parentId;
    private String slug;
    public ProductGroup(){}
    public ProductGroup(long _id,String _name){
        id=_id;
        name=_name;
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
