package com.ttyp.tiantao.ttmb.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

public class SetJSON {
    private static JSONObject jo;
    private static JSONObject jo1;
    private static JSONObject jo2;
    private static JSONArray ja;
    private static JSONArray ja1;
    public static String setJSON(Map<String,String> value){
        String params = "";
        try{
            jo = new JSONObject();
            for(Map.Entry<String,String> entry : value.entrySet()){
                jo.put(entry.getKey(),entry.getValue());
            }
            ja.put(jo);
            params = ja.toString();
        }catch (Exception e){
            params = "";
        }finally {
            jo = null;
            return params;
        }


    }

}
