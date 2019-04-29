package com.ttyp.tiantao.ttmb.template;

public enum PackageShop {
    TENGXUN(0,"应用宝","com.tencent.android.qqdownloader"),
    BAIDU(1,"百度手机助手","com.baidu.appsearch"),
    XIAOMI(2,"小米应用商店","com.xiaomi.market"),
    HUAWEI(3,"华为应用商店","com.huawei.appmarket"),
    OPPO(4,"OPPO应用商店","com.oppo.market"),
    SEC(5,"三星应用商店","com.sec.android.app.samsungapps"),
    BBK(6,"VIVO应用商店","com.bbk.appstore"),
    MEIZU(7,"魅族应用市场","com.meizu.mstore");

    private int index;
    private String packageURL;
    private String packageName;

    private PackageShop(int index,String packageURL,String packageName){
        this.index = index;
        this.packageURL = packageURL;
        this.packageName = packageName;
    }

    public static int getMaxIndex(){
        int index = 0;
        for (PackageShop ftype : PackageShop.values()) {
            index++;
        }
        return index;
    }

    public static String getURLByIndex(int index){
        return getByIndex(index).packageURL;
    }
    public static String getNAMEByIndex(int index){
        return getByIndex(index).packageName;
    }

    public static PackageShop getByIndex(int index){
        PackageShop defaultType = PackageShop.TENGXUN;
        for (PackageShop ftype : PackageShop.values()) {
            if (ftype.index == index) {
                return ftype;
            }
        }
        return defaultType;
    }
}
