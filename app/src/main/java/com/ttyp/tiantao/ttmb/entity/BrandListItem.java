package com.ttyp.tiantao.ttmb.entity;

import java.io.Serializable;

public class BrandListItem implements Serializable {
    private int id;
    private String titleImage;
    private String titleText;
    private String moreText;
    private int goodsOneID;
    private String goodsOneImage;
    private String goodsOneText;
    private double goodsOnePricNow;
    private double GoodsOnePricOld;
    private int goodsTwoID;
    private String goodsTwoImage;
    private String goodsTwoText;
    private double goodsTwoPricNow;
    private double GoodsTwoPricOld;
    private int goodsThreeID;
    private String goodsThreeImage;
    private String goodsThreeText;
    private double goodsThreePricNow;
    private double goodsThreePricOld;

    public BrandListItem(int id, String titleImage, String titleText) {
        this.id = id;
        this.titleImage = titleImage;
        this.titleText = titleText;
    }

    public int getId() {
        return id;
    }

    public BrandListItem setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitleImage() {
        return titleImage;
    }

    public BrandListItem setTitleImage(String titleImage) {
        this.titleImage = titleImage;
        return this;
    }

    public String getTitleText() {
        return titleText;
    }

    public BrandListItem setTitleText(String titleText) {
        this.titleText = titleText;
        return this;
    }

    public String getMoreText() {
        return moreText;
    }

    public BrandListItem setMoreText(String moreText) {
        this.moreText = moreText;
        return this;
    }

    public String getGoodsOneImage() {
        return goodsOneImage;
    }

    public BrandListItem setGoodsOneImage(String goodsOneImage) {
        this.goodsOneImage = goodsOneImage;
        return this;
    }

    public String getGoodsOneText() {
        return goodsOneText;
    }

    public BrandListItem setGoodsOneText(String goodsOneText) {
        this.goodsOneText = goodsOneText;
        return this;
    }

    public double getGoodsOnePricNow() {
        return goodsOnePricNow;
    }

    public BrandListItem setGoodsOnePricNow(double goodsOnePricNow) {
        this.goodsOnePricNow = goodsOnePricNow;
        return this;
    }

    public double getGoodsOnePricOld() {
        return GoodsOnePricOld;
    }

    public BrandListItem setGoodsOnePricOld(double goodsOnePricOld) {
        GoodsOnePricOld = goodsOnePricOld;
        return this;
    }

    public String getGoodsTwoImage() {
        return goodsTwoImage;
    }

    public BrandListItem setGoodsTwoImage(String goodsTwoImage) {
        this.goodsTwoImage = goodsTwoImage;
        return this;
    }

    public String getGoodsTwoText() {
        return goodsTwoText;
    }

    public BrandListItem setGoodsTwoText(String goodsTwoText) {
        this.goodsTwoText = goodsTwoText;
        return this;
    }

    public double getGoodsTwoPricNow() {
        return goodsTwoPricNow;
    }

    public BrandListItem setGoodsTwoPricNow(double goodsTwoPricNow) {
        this.goodsTwoPricNow = goodsTwoPricNow;
        return this;
    }

    public double getGoodsTwoPricOld() {
        return GoodsTwoPricOld;
    }

    public BrandListItem setGoodsTwoPricOld(double goodsTwoPricOld) {
        GoodsTwoPricOld = goodsTwoPricOld;
        return this;
    }

    public String getGoodsThreeImage() {
        return goodsThreeImage;
    }

    public BrandListItem setGoodsThreeImage(String goodsThreeImage) {
        this.goodsThreeImage = goodsThreeImage;
        return this;
    }

    public String getGoodsThreeText() {
        return goodsThreeText;
    }

    public BrandListItem setGoodsThreeText(String goodsThreeText) {
        this.goodsThreeText = goodsThreeText;
        return this;
    }

    public double getGoodsThreePricNow() {
        return goodsThreePricNow;
    }

    public BrandListItem setGoodsThreePricNow(double goodsThreePricNow) {
        this.goodsThreePricNow = goodsThreePricNow;
        return this;
    }

    public double getGoodsThreePricOld() {
        return goodsThreePricOld;
    }

    public BrandListItem setGoodsThreePricOld(double goodsThreePricOld) {
        this.goodsThreePricOld = goodsThreePricOld;
        return this;
    }

    public int getGoodsOneID() {
        return goodsOneID;
    }

    public BrandListItem setGoodsOneID(int goodsOneID) {
        this.goodsOneID = goodsOneID;
        return this;
    }

    public int getGoodsTwoID() {
        return goodsTwoID;
    }

    public BrandListItem setGoodsTwoID(int goodsTwoID) {
        this.goodsTwoID = goodsTwoID;
        return this;
    }

    public int getGoodsThreeID() {
        return goodsThreeID;
    }

    public BrandListItem setGoodsThreeID(int goodsThreeID) {
        this.goodsThreeID = goodsThreeID;
        return this;
    }
}
