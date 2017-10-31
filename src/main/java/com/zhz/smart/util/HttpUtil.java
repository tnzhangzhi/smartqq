package com.zhz.smart.util;


import net.sf.json.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.*;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zz1987 on 17/10/30.
 */
public class HttpUtil {

    static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static CloseableHttpClient httpClient = null;
    public static HttpClientContext context = null;
    public static CookieStore cookieStore = null;
    public static RequestConfig requestConfig = null;
    public static Map<String,String> map = new HashMap<>();

    static {
        init();
    }

    private static void init(){
        context = HttpClientContext.create();
        cookieStore = new BasicCookieStore();
        requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.DEFAULT).setConnectTimeout(120000).setSocketTimeout(60000)
                .setConnectionRequestTimeout(60000).build();
        context.setCookieStore(cookieStore);
        httpClient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                .setDefaultRequestConfig(requestConfig)
                .build();
    }

    public static InputStream streamMethod(String url,String refer){
        CloseableHttpResponse response = excute(url,refer);
        if(response!=null){
            try {
                return response.getEntity().getContent();
            }catch (Exception e){
                logger.error("获取请求响应内容失败! {}",e.getMessage());
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String  stringMethod(String url,String refer){
        CloseableHttpResponse response = excute(url,refer);
        if(response!=null){
            try {
                return EntityUtils.toString(response.getEntity(),"utf-8");
            }catch (Exception e){
                logger.error("获取请求响应内容失败!! {}",e.getMessage());
                e.printStackTrace();
            }
        }
        return null;
    }

    public static CloseableHttpResponse excute(String url,String refer) {
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("user-agent", Constant.AGENT);
        if(refer!=null){
            httpGet.addHeader("referer",refer);
        }
        try {
            logger.info("Execute request: "+httpGet.getURI());
            CloseableHttpResponse response = httpClient.execute(httpGet, context);
            for (Cookie c : context.getCookieStore().getCookies()) {
                map.put(c.getName(),c.getValue());
            }
            return response;
        }catch (Exception e){
            logger.error("网络请求失败 {}",e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static String post(String url, String referer, String origin, JSONObject object){
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("user-agent",Constant.AGENT);
        httpPost.addHeader("referer",referer);
        httpPost.addHeader("origin",origin);
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("r", object.toString()));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse response = httpClient.execute(httpPost, context);
            for (Cookie c : context.getCookieStore().getCookies()) {
                map.put(c.getName(),c.getValue());
            }
            if(response.getStatusLine().getStatusCode()==200){
                return EntityUtils.toString(response.getEntity(),"utf-8");
            }else{
                logger.error("请求返回状态码:"+response.getStatusLine().getStatusCode());
            }
        }catch (Exception e){
            logger.error("网络请求失败 {}",e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
