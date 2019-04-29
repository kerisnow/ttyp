package com.ttyp.tiantao.ttmb.util;

import android.util.Log;

import org.json.JSONObject;

import java.util.Map;

public class ConnectLinkService {
    ConnectHttps connectHttps;
    private ConnectLinkService(){
        connectHttps = new ConnectHttps();
    }

    public static ConnectLinkService getInstance(){
        return  new ConnectLinkService();
    }

    public String ConnectHttpsPost(Map<String,String> params, String url){
        String res = connectHttps.getRequest(params,ConnectHttps.POST,url,0);
        if(res==null){
            JSONObject jo = new JSONObject();
            try {
                jo.put("res", 0);
                jo.put("msg", "网络请求异常");
            }catch (Exception e){
                Log.e("Connect_setJSON",e.toString());
            }
            res = jo.toString();
        }
        return res;
    }

    public String ConnectHttpsGET(Map<String,String> params,String url){
        String res = connectHttps.getRequest(params,ConnectHttps.GET,url,0);
        if(res==null){
            JSONObject jo = new JSONObject();
            try {
                jo.put("res", "0");
                jo.put("msg", "网络请求异常");
            }catch (Exception e){
                Log.e("Connect_setJSON",e.toString());
            }
            res = jo.toString();
        }
        return res;
    }

    public String ConnectHttpsPost1(Map<String,String> params, String url){
        String res = connectHttps.getRequest(params,ConnectHttps.POST,url,1);
        if(res==null){
            JSONObject jo = new JSONObject();
            try {
                jo.put("res", "0");
                jo.put("msg", "网络请求异常");
            }catch (Exception e){
                Log.e("Connect_setJSON",e.toString());
            }
            res = jo.toString();
        }
        return res;
    }

    public String ConnectHttpsGET1(Map<String,String> params,String url){
        String res = connectHttps.getRequest(params,ConnectHttps.GET,url,1);
        if(res==null){
            JSONObject jo = new JSONObject();
            try {
                jo.put("res", "0");
                jo.put("msg", "网络请求异常");
            }catch (Exception e){
                Log.e("Connect_setJSON",e.toString());
            }
            res = jo.toString();
        }
        return res;
    }

    public String UploadFilePost(Map<String,Object> params,String url){
        String res = connectHttps.getRequestForUpload(params,ConnectHttps.POST,url,0);
        if(res==null){
            JSONObject jo = new JSONObject();
            try {
                jo.put("res", "0");
                jo.put("msg", "网络请求异常");
            }catch (Exception e){
                Log.e("Connect_setJSON",e.toString());
            }
            res = jo.toString();
        }
        return res;
    }
    public String UploadFilePost1(Map<String,Object> params,String url){
        String res = connectHttps.getRequestForUpload(params,ConnectHttps.POST,url,1);
        if(res==null){
            JSONObject jo = new JSONObject();
            try {
                jo.put("res", "0");
                jo.put("msg", "网络请求异常");
            }catch (Exception e){
                Log.e("Connect_setJSON",e.toString());
            }
            res = jo.toString();
        }
        return res;

    }

    public String UploadFileGET(Map<String,Object> params,String url){
        String res = connectHttps.getRequestForUpload(params,ConnectHttps.GET,url,0);
        if(res==null){
            JSONObject jo = new JSONObject();
            try {
                jo.put("res", "0");
                jo.put("msg", "网络请求异常");
            }catch (Exception e){
                Log.e("Connect_setJSON",e.toString());
            }
            res = jo.toString();
        }
        return res;
    }
    public String UploadFileGET1(Map<String,Object> params,String url){
        String res = connectHttps.getRequestForUpload(params,ConnectHttps.GET,url,1);
        if(res==null){
            JSONObject jo = new JSONObject();
            try {
                jo.put("res", "0");
                jo.put("msg", "网络请求异常");
            }catch (Exception e){
                Log.e("Connect_setJSON",e.toString());
            }
            res = jo.toString();
        }
        return res;

    }
}
