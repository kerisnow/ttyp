package com.ttyp.tiantao.ttmb.entity;

public class RecommendList {
    String pic;
    String title;
    String price;
    String couponPrice;
    String numIid;
    String view;

    public RecommendList(String pic, String title, String price, String couponPrice, String numIid, String view) {
        this.pic = pic;
        this.title = title;
        this.price = price;
        this.couponPrice = couponPrice;
        this.numIid = numIid;
        this.view = view;
    }

    public String getPic() {
        return pic;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getCouponPrice() {
        return couponPrice;
    }

    public String getNumIid() {
        return numIid;
    }

    public String getView() {
        return view;
    }
}
