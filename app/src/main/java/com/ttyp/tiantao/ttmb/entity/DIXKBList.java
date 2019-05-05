package com.ttyp.tiantao.ttmb.entity;


import java.io.Serializable;
import java.util.Date;

public class DIXKBList implements Serializable {
//    private int id;
    private String typeName;
    private Date createTime;
    private String pice;

    public DIXKBList( String typeName, Date createTime, String pice) {
//        this.id = id;
        this.typeName = typeName;
        this.createTime = createTime;
        this.pice = pice;
    }


    public String getTypeName() {
        return typeName;
    }

    public DIXKBList setTypeName(String typeName) {
        this.typeName = typeName;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public DIXKBList setCreateTime(Date createTime) {
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
