package com.ttyp.tiantao.ttmb.entity;

import java.io.Serializable;

public class SearchItem implements Serializable {
    String searchText;
    String searchAssociation;
    int index;

    public SearchItem( String searchAssociation, int index) {
        this.searchAssociation = searchAssociation;
        this.index = index;
    }

    public String getSearchText() {
        return searchText;
    }

    public SearchItem setSearchText(String searchText) {
        this.searchText = searchText;
        return this;
    }

    public String getSearchAssociation() {
        return searchAssociation;
    }

    public SearchItem setSearchAssociation(String searchAssociation) {
        this.searchAssociation = searchAssociation;
        return this;
    }

    public int getIndex() {
        return index;
    }

    public SearchItem setIndex(int index) {
        this.index = index;
        return this;
    }
}
