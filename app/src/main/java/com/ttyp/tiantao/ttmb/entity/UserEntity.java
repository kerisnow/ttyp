package com.ttyp.tiantao.ttmb.entity;

import java.io.Serializable;

public class UserEntity implements Serializable {
    /**
     * 用户id
     */
    String uid ;
    /**
     * 用户姓名
     */
    String uname;
    /**
     * 用户头像
     */
    String headimage;
    /**
     * 用户昵称
     */
    String pickName;
    /**
     * 用户手机号
     */
    String phone;
    /**
     * 用户身份识别标识
     * #KEYVALUE 0
     * #KEYVALUE 1
     */
    int identification;
    /**
     * 积分
     */
    int integral;
    /**
     * 抵现卡余额
     */
    String rechargeMoney;
    /**
     * 阿里支付宝账号
     */
    String alipayAcount;
    /**
     * 阿里支付宝收款人真名
     */
    String alipayName;


    public UserEntity(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public UserEntity setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getUname() {
        return uname;
    }

    public UserEntity setUname(String uname) {
        this.uname = uname;
        return this;
    }

    public String getHeadimage() {
        return headimage;
    }

    public UserEntity setHeadimage(String headimage) {
        this.headimage = headimage;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public UserEntity setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public int getIdentification() {
        return identification;
    }

    public UserEntity setIdentification(int identification) {
        this.identification = identification;
        return this;
    }

    public String getPickName() {
        return pickName;
    }

    public UserEntity setPickName(String pickName) {
        this.pickName = pickName;
        return this;
    }

    public int getIntegral() {
        return integral;
    }

    public UserEntity setIntegral(int integral) {
        this.integral = integral;
        return this;
    }

    public String getRechargeMoney() {
        return rechargeMoney;
    }

    public UserEntity setRechargeMoney(String rechargeMoney) {
        this.rechargeMoney = rechargeMoney;
        return this;
    }

    public String getAlipayAcount() {
        return alipayAcount;
    }

    public UserEntity setAlipayAcount(String alipayAcount) {
        this.alipayAcount = alipayAcount;
        return this;
    }

    public String getAlipayName() {
        return alipayName;
    }

    public UserEntity setAlipayName(String alipayName) {
        this.alipayName = alipayName;
        return this;
    }
}
