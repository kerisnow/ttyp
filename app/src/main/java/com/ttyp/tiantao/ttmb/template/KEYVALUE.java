package com.ttyp.tiantao.ttmb.template;

public enum KEYVALUE {
    FANS("粉丝",0),PARTNER("合伙人",1);
    private String identification;
    private int identificationKey;
    private KEYVALUE(String identification,int identificationKey){
        this.identification = identification;
        this.identificationKey = identificationKey;
    }

    @Override
    public String toString() {
        return this.identification;
    }

    public static String getByIdentificationKey(int identificationKey){
        return getIdentificationByKey(identificationKey).identification;
    }

    public static int getByIdentificationKey(String identification){
        return getIdentificationKey(identification).identificationKey;
    }

    public static KEYVALUE getIdentificationKey(String identification) {
        KEYVALUE defaultType = KEYVALUE.FANS;
        for (KEYVALUE ftype : KEYVALUE.values()) {
            if (ftype.identification == identification) {
                return ftype;
            }
        }
        return defaultType;
    }

    public static KEYVALUE getIdentificationByKey(Integer identificationKey) {
        KEYVALUE defaultType = KEYVALUE.FANS;
        for (KEYVALUE ftype : KEYVALUE.values()) {
            if (ftype.identificationKey == identificationKey) {
                return ftype;
            }
        }
        return defaultType;
    }

    public static KEYVALUE getIdentificationKeyByKey(Integer identificationKey) {
        KEYVALUE defaultType = KEYVALUE.FANS;
        for (KEYVALUE ftype : KEYVALUE.values()) {
            if (ftype.identificationKey == identificationKey) {
                return ftype;
            }
        }
        return defaultType;
    }

    public String getIdentification() {
        return identification;
    }

    public KEYVALUE setIdentification(String identification) {
        this.identification = identification;
        return this;
    }

    public int getIdentificationKey() {
        return identificationKey;
    }

    public KEYVALUE setIdentificationKey(int identificationKey) {
        this.identificationKey = identificationKey;
        return this;
    }
}
