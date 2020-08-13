package com.hzit.proxy.proxy;


import com.alibaba.fastjson.JSONObject;
import com.hzit.net.WXHttpUtil;
import com.hzit.proxy.base.AccessTokenUtil;

public class MsgProxy {
    String ACCESS_TOKEN = AccessTokenUtil.getWXToken();

//    http请求方式: POST https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN
    public void sendMessage(String requestBody){
        String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+ACCESS_TOKEN;

        String body = WXHttpUtil.post(url, requestBody);
        JSONObject jsonObject = JSONObject.parseObject(body);
        if (jsonObject.containsValue("ok")){
            System.out.println("消息发送成功!");
        }else {
            System.out.println("消息发送失败!");
        }
    }

}
