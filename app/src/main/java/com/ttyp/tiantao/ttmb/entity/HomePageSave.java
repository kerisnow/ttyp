package com.ttyp.tiantao.ttmb.entity;

import java.util.Date;

public class HomePageSave {
    int pageid;
    String pagename;
    String data;
    Date lastSaveDate;

    public int getPageid() {
        return pageid;
    }

    public HomePageSave setPageid(int pageid) {
        this.pageid = pageid;
        return this;
    }

    public String getPagename() {
        return pagename;
    }

    public HomePageSave setPagename(String pagename) {
        this.pagename = pagename;
        return this;
    }

    public String getData() {
        return data;
    }

    public HomePageSave setData(String data) {
        this.data = data;
        return this;
    }

    public Date getLastSaveDate() {
        return lastSaveDate;
    }

    public HomePageSave setLastSaveDate(Date lastSaveDate) {
        this.lastSaveDate = lastSaveDate;
        return this;
    }
}
