package com.ttyp.tiantao.ttmb.entity;

import java.util.List;

public class RecommendListEntry {
    int id;
    int favorites_id;
    int type;
    int created;
    int changed;
    String favorites_title;
    List<RecommendList> recommendList;

    public RecommendListEntry(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public RecommendListEntry setId(int id) {
        this.id = id;
        return this;
    }

    public int getFavorites_id() {
        return favorites_id;
    }

    public RecommendListEntry setFavorites_id(int favorites_id) {
        this.favorites_id = favorites_id;
        return this;
    }

    public int getType() {
        return type;
    }

    public RecommendListEntry setType(int type) {
        this.type = type;
        return this;
    }

    public int getCreated() {
        return created;
    }

    public RecommendListEntry setCreated(int created) {
        this.created = created;
        return this;
    }

    public int getChanged() {
        return changed;
    }

    public RecommendListEntry setChanged(int changed) {
        this.changed = changed;
        return this;
    }

    public String getFavorites_title() {
        return favorites_title;
    }

    public RecommendListEntry setFavorites_title(String favorites_title) {
        this.favorites_title = favorites_title;
        return this;
    }

    public List<RecommendList> getRecommendList() {
        return recommendList;
    }

    public RecommendListEntry setRecommendList(List<RecommendList> recommendList) {
        this.recommendList = recommendList;
        return this;
    }
}
