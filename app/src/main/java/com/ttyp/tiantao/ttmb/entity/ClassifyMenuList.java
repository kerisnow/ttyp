package com.ttyp.tiantao.ttmb.entity;

import java.io.Serializable;

public class ClassifyMenuList implements Serializable {
    String text;
    String url;

    public ClassifyMenuList(String text, String url) {
        this.text = text;
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public ClassifyMenuList setText(String text) {
        this.text = text;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ClassifyMenuList setUrl(String url) {
        this.url = url;
        return this;
    }
}
