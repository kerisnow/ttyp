package com.ttyp.tiantao.ttmb.util;


import android.util.Log;

import com.ttyp.tiantao.R;
import com.ttyp.tiantao.ttmb.template.URLValue;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import static com.ttyp.tiantao.ttmb.template.URLValue.DEF_URL;

public final class ConnectHttps {
    public static String PATH = "";
    private static final String encode = "urf-8";
    private static String msg="";
    public static String GET = "GET";
    public static String POST = "POST";
//    private final OkHttpClient client;

    public ConnectHttps(){
//        X509TrustManager trustManager;
//        SSLSocketFactory sslSocketFactory;

//        try {
//            SSLContext sslContext = SSLContext.getInstance("TLS");
//            sslSocketFactory = sslContext.getSocketFactory();
//        } catch (GeneralSecurityException e) {
//            throw new RuntimeException(e);
//        }
//        client = new OkHttpClient.Builder()
////                .sslSocketFactory(sslSocketFactory, trustManager)
//                .build();
    }

    public String getRequest(Map<String,String> params, String connectType, String path,int type){
        try {
//            URL url = new URL("http://blog.csdn.net/yanzhenjie1003");
            String content = "";
            if (null != params && params.size() > 0) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    content += "&" + entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "utf-8");
                }
            }
            URL url;
            if(connectType.equals("POST")) {
                url = new URL(DEF_URL + path);
            }else {
                url = new URL(DEF_URL + path + "?" + content.replaceFirst("&",""));
            }
            Log.i("Connect_url",url.getHost()+url.getPath());
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod(connectType);
            // 设置属性
            conn.setConnectTimeout(8 * 1000);
            conn.setReadTimeout(8 * 1000);
            conn.setDoInput(true);
            if(connectType.equals("POST")) {
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
//            conn.setHostnameVerifier(new HostnameVerifier() {
//                @Override
//                public boolean verify(String hostname, SSLSession session) {
//                    return true;
//                }
//            });
            InputStream inputStream = MyApplication.getContext().getResources().openRawResource(R.raw.fullchain);
//            CertificateFactory fa = CertificateFactory.getInstance("Certificate");
            //keystore添加证书内容和密码
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            //证书工厂类，生成证书
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            //生成证书，添加别名
            keyStore.setCertificateEntry("test1", certificateFactory.generateCertificate(inputStream));
            inputStream.close();
            //信任管理器工厂
//            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//            trustManagerFactory.init(keyStore);
            TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[] {};
                }

                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }
            } };
            //构建一个ssl上下文，加入ca证书格式，与后台保持一致
            SSLContext sslContext = SSLContext.getInstance("TLSv1","AndroidOpenSSL");
            //参数，添加受信任证书和生成随机数
            sslContext.init(null, trustAllCerts, new SecureRandom());
            //获得scoket工厂
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            conn.setSSLSocketFactory(sslSocketFactory);
            conn.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            if(connectType.equals("POST")) {
                DataOutputStream osp = new DataOutputStream(conn.getOutputStream());

                osp.writeBytes(content.replaceFirst("&", ""));
            }
            conn.connect();
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) { // 请求成功
                InputStream in = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                StringBuffer result = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                return result.toString();
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("conn",e.toString());
        }
        return null;
    }


    public String getRequestForUpload(Map<String, Object> params, String connectType, String path, int type){
        try {
//            URL url = new URL("http://blog.csdn.net/yanzhenjie1003");
            URL url;
            String PREFIX ="--", LINE_END ="\r\n";
            String BOUNDARY = UUID.randomUUID().toString();

            if(type == 0) {
                url = new URL(DEF_URL + path);
            }else{
                url = new URL(URLValue.DEF_URL1 + path);
            }
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod(connectType);
            // 设置属性
            conn.setConnectTimeout(8 * 1000);
            conn.setReadTimeout(8 * 1000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/form-data;charset=UTF-8");
//            conn.setHostnameVerifier(new HostnameVerifier() {
//                @Override
//                public boolean verify(String hostname, SSLSession session) {
//                    return true;
//                }
//            });
            InputStream inputStream = MyApplication.getContext().getResources().openRawResource(R.raw.fullchain);
//            CertificateFactory fa = CertificateFactory.getInstance("Certificate");
            //keystore添加证书内容和密码
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            //证书工厂类，生成证书
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            //生成证书，添加别名
            keyStore.setCertificateEntry("test1", certificateFactory.generateCertificate(inputStream));
            inputStream.close();
            //信任管理器工厂
//            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//            trustManagerFactory.init(keyStore);
            TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[] {};
                }

                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }
            } };
            //构建一个ssl上下文，加入ca证书格式，与后台保持一致
            SSLContext sslContext = SSLContext.getInstance("TLSv1","AndroidOpenSSL");
            //参数，添加受信任证书和生成随机数
            sslContext.init(null, trustAllCerts, new SecureRandom());
            //获得scoket工厂
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            conn.setSSLSocketFactory(sslSocketFactory);
            conn.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            DataOutputStream osp = new DataOutputStream(conn.getOutputStream());
            String content = "";
            if(null != params && params.size()>0) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    if(entry.getKey().equals("image")){
                        File file = (File)entry.getValue();
                        StringBuffer sb =new StringBuffer();
                        sb.append(PREFIX);
                        sb.append(BOUNDARY);
                        sb.append(LINE_END);
                        /**
                         * 这里重点注意： name里面的值为服务端需要key 只有这个key 才可以得到对应的文件
                         * filename是文件的名字，包含后缀名的 比如:abc.png
                         */
                        sb.append("Content-Disposition: form-data; name=\""+entry.getKey()+"\"; filename=\""
                                + file.getName() +"\"" + LINE_END);
                        sb.append("Content-Type: application/octet-stream; charset=utf-8"  + LINE_END);
                        sb.append(LINE_END);
                        content += "&" + sb.toString();
                    }else {
                        content += "&" + entry.getKey() + "=" + URLEncoder.encode((String)entry.getValue(), "utf-8");
                    }
                }
            }
            osp.writeBytes(content.replaceFirst("&",""));
            conn.connect();
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) { // 请求成功
                InputStream in = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                StringBuffer result = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                return result.toString();
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("conn",e.toString());
        }
        return null;
    }

}
