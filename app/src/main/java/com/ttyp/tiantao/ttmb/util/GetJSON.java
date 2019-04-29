package com.ttyp.tiantao.ttmb.util;

import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GetJSON {
    private static JSONObject jo;
    private static GetJSON getJSON = null;
    private GetJSON() {};

    public static GetJSON getInstance(){
        if (getJSON == null){
            getJSON = new GetJSON();
        }
        return getJSON;
    }

    public Map<String,Object> getJSON(String res){
        Map<String,Object> resMap = new HashMap<>();
        jo = new JSONObject();
        try {
            if(resMap!=null){
                jo = new JSONObject(res);
                Iterator<String> ite = jo.keys();
                while (ite.hasNext()){
                    String key = (String)ite.next();
                    resMap.put(key,jo.get(key));
                }
            }
        }catch (Exception e){

        }finally {
            jo = null;
            return resMap;
        }
    }

    public Map<String,Object> getMap(JSONObject jo,String name){
        Map<String,Object> res = new HashMap();
        try{
            JSONObject joa = jo.getJSONObject(name);
            Iterator<String> ite = joa.keys();
            while (ite.hasNext()){
                String key = (String)ite.next();
                res.put(key,joa.get(key));
            }
            Log.i("GetJSON",joa.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

}
