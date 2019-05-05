package com.ttyp.tiantao.ttmb.entity;

import java.util.Date;
import java.util.List;

public class GoodsEntity {
    long numIid;
    String title;
    String pic;
    String price;
    String couponPrice;
    int couponRate;
    String commission;
    int volume;
    String clickUrl;
    String couponAmount;
    String shopTitle;
    String content;
    Date begintime;
    Date endtime;
    List<String> goods_adv;

    public GoodsEntity(long numIid) {
        this.numIid = numIid;
    }

    public long getNumIid() {
        return numIid;
    }

    public GoodsEntity setNumIid(long numIid) {
        this.numIid = numIid;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public GoodsEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getPic() {
        return pic;
    }

    public GoodsEntity setPic(String pic) {
        this.pic = pic;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public GoodsEntity setPrice(String price) {
        this.price = price;
        return this;
    }

    public String getCouponPrice() {
        return couponPrice;
    }

    public GoodsEntity setCouponPrice(String couponPrice) {
        this.couponPrice = couponPrice;
        return this;
    }

    public int getCouponRate() {
        return couponRate;
    }

    public GoodsEntity setCouponRate(int couponRate) {
        this.couponRate = couponRate;
        return this;
    }

    public String getCommission() {
        return commission;
    }

    public GoodsEntity setCommission(String commission) {
        this.commission = commission;
        return this;
    }

    public int getVolume() {
        return volume;
    }

    public GoodsEntity setVolume(int volume) {
        this.volume = volume;
        return this;
    }

    public String getClickUrl() {
        return clickUrl;
    }

    public GoodsEntity setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
        return this;
    }

    public String getCouponAmount() {
        return couponAmount;
    }

    public GoodsEntity setCouponAmount(String couponAmount) {
        this.couponAmount = couponAmount;
        return this;
    }

    public String getShopTitle() {
        return shopTitle;
    }

    public GoodsEntity setShopTitle(String shopTitle) {
        this.shopTitle = shopTitle;
        return this;
    }

    public String getContent() {
        return content;
    }

    public GoodsEntity setContent(String content) {
        this.content = content;
        return this;
    }

    public List<String> getGoods_adv() {
        return goods_adv;
    }

    public GoodsEntity setGoods_adv(List<String> goods_adv) {
        this.goods_adv = goods_adv;
        return this;
    }

    public Date getBegintime() {
        return begintime;
    }

    public GoodsEntity setBegintime(Date begintime) {
        this.begintime = begintime;
        return this;
    }

    public Date getEndtime() {
        return endtime;
    }

    public GoodsEntity setEndtime(Date endtime) {
        this.endtime = endtime;
        return this;
    }
}
