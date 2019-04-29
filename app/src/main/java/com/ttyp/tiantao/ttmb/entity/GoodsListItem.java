package com.ttyp.tiantao.ttmb.entity;

import java.io.Serializable;

public class GoodsListItem implements Serializable {
    int GoodsId;
    String goodsImage;
    String goodsTitle;
    String goodsTitleLogo;
    Integer goodsTitleLogo1;
    Boolean isGoodsoffset;
    String goodsoffset;
    Boolean isGoodsearn;
    String goodsearn;
    String price_now;
    String price_old;
    String sales;
    String shopname;
    String shopaddress;
    Boolean isShopaddress;

    public Integer getGoodsTitleLogo1() {
        return goodsTitleLogo1;
    }

    public GoodsListItem setGoodsTitleLogo1(Integer goodsTitleLogo1) {
        this.goodsTitleLogo1 = goodsTitleLogo1;
        return this;
    }

    public GoodsListItem(int goodsId) {
        GoodsId = goodsId;
    }

    public int getGoodsId() {
        return GoodsId;
    }

    public GoodsListItem setGoodsId(int goodsId) {
        GoodsId = goodsId;
        return this;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public GoodsListItem setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
        return this;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public GoodsListItem setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
        return this;
    }

    public String getGoodsTitleLogo() {
        return goodsTitleLogo;
    }

    public GoodsListItem setGoodsTitleLogo(String goodsTitleLogo) {
        this.goodsTitleLogo = goodsTitleLogo;
        return this;
    }

    public String getGoodsoffset() {
        return goodsoffset;
    }

    public GoodsListItem setGoodsoffset(String goodsoffset) {
        this.goodsoffset = goodsoffset;
        return this;
    }

    public String getGoodsearn() {
        return goodsearn;
    }

    public GoodsListItem setGoodsearn(String goodsearn) {
        this.goodsearn = goodsearn;
        return this;
    }

    public String getPrice_now() {
        return price_now;
    }

    public GoodsListItem setPrice_now(String price_now) {
        this.price_now = price_now;
        return this;
    }

    public String getPrice_old() {
        return price_old;
    }

    public GoodsListItem setPrice_old(String price_old) {
        this.price_old = price_old;
        return this;
    }

    public String getSales() {
        return sales;
    }

    public GoodsListItem setSales(String sales) {
        this.sales = sales;
        return this;
    }

    public String getShopname() {
        return shopname;
    }

    public GoodsListItem setShopname(String shopname) {
        this.shopname = shopname;
        return this;
    }

    public String getShopaddress() {
        return shopaddress;
    }

    public GoodsListItem setShopaddress(String shopaddress) {
        this.shopaddress = shopaddress;
        return this;
    }

    public GoodsListItem setIsGoodsoffset(Boolean isGoodsoffset) {
        this.isGoodsoffset = isGoodsoffset;
        return this;
    }

    public Boolean getIsGoodsoffset(){
        return isGoodsoffset;
    }

    public GoodsListItem setIsGoodsearn(Boolean isGoodsearn) {
        this.isGoodsearn = isGoodsearn;
        return this;
    }

    public Boolean getIsGoodsearn(){
        return isGoodsearn;
    }

    public Boolean getIsShopaddress(){
        return isShopaddress;
    }

    public GoodsListItem setIsShopaddress(Boolean isShopaddress){
        this.isShopaddress = isShopaddress;
        return this;
    }
}
