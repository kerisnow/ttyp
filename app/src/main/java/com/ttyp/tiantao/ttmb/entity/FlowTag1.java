package com.ttyp.tiantao.ttmb.entity;

import com.volley.library.flowtag.OptionCheck;

public class FlowTag1 implements OptionCheck {
    String keyword;
    String name;
    String title;
    String type;
    boolean isChecked = false;

    public String getKeyword() {
        return keyword;
    }

    public FlowTag1 setKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    public String getName() {
        return name;
    }

    public FlowTag1 setName(String name) {
        this.name = name;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public FlowTag1 setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getType() {
        return type;
    }

    public FlowTag1 setType(String type) {
        this.type = type;
        return this;
    }

    @Override
    public boolean isChecked() {
        return this.isChecked;
    }

    @Override
    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
