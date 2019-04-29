package com.ttyp.tiantao.ttmb.entity;


import java.io.Serializable;

public class DIXKBList implements Serializable {
    private int id;
    private String typeName;
    private String createTime;
    private String pice;

    public DIXKBList(int id, String typeName, String createTime, String pice) {
        this.id = id;
        this.typeName = typeName;
        this.createTime = createTime;
        this.pice = pice;
    }

    public int getId() {
        return id;
    }

    public DIXKBList setId(int id) {
        this.id = id;
        return this;
    }

    public String getTypeName() {
        return typeName;
    }

    public DIXKBList setTypeName(String typeName) {
        this.typeName = typeName;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public DIXKBList setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getPice() {
        return pice;
    }

    public DIXKBList setPice(String pice) {
        this.pice = pice;
        return this;
    }
}
