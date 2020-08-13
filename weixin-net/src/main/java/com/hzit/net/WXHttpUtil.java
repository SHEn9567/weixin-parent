package com.hzit.net;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class WXHttpUtil {
    /**
     * 发起get请求
     * @param url  请求路径及参数
     * @return   返回内容
     */
    public static String get(String url){
        try {
            //创建HttpClient对象(浏览器)
            CloseableHttpClient ie = HttpClients.createDefault();

            //创建具体需要发起的请求,并且重置参数
            HttpGet httpGet = new HttpGet(url);

            //发起请求,获取响应
            HttpResponse httpResponse = ie.execute(httpGet);

            //关闭资源
            HttpEntity entity = httpResponse.getEntity();
            String string = EntityUtils.toString(entity, "utf-8");

            return string;
        } catch (IOException e) {
            System.out.println("wx-net:get方法出现异常:"+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发起post请求
     * @param url  请求参数及路径
     * @return   返回内容
     */
    public static String post(String url,String requestBody){
        try{
            //创建HttpClient对象(浏览器)
            CloseableHttpClient ie = HttpClients.createDefault();

            //创建具体需要发起的请求,并且重置参数
            HttpPost httpPost = new HttpPost(url);

            //设置请求体
            HttpEntity httpEntity = new StringEntity(requestBody, "utf-8");
            httpPost.setEntity(httpEntity);

            //发起请求,获取响应
            HttpResponse httpResponse = ie.execute(httpPost);

            //关闭资源
            HttpEntity entity = httpResponse.getEntity();
            String string = EntityUtils.toString(entity, "utf-8");

            return string;
        }catch (IOException e){
            System.out.println("wx-net:post方法出现异常:"+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
