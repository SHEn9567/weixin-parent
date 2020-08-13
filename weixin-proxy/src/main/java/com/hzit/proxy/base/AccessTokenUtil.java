package com.hzit.proxy.base;

import com.alibaba.fastjson.JSONObject;
import com.hzit.net.WXHttpUtil;
import com.hzit.proxy.MyConst;
import com.hzit.proxy.utils.RedisUtil;


public class AccessTokenUtil {

    /**
     * 账户信息
     */
    final static String APPID = "wx2829726604e4f19b";
    final static String SECRET = "9e1ef62f27df7c52baa7dbaa1eeb855f";

    /**
     * 获取token
     * @return
     */
    public static String getWXToken(){
        //先从redis中获取token
        String token = RedisUtil.get(MyConst.WEIXIN_TOKEN_KEY);

        //判断是否存在,如果存在则直接返回
        if(token!=null){
            return token;
        }
        //如果不存在,则远程从公众号中获取
        token = remoteWXToken();
        //将新获取的token存储到redis中,设置过期时间小于两小时
        RedisUtil.set(MyConst.WEIXIN_TOKEN_KEY,token,7000);
        return token;
    }

    /**
     * 远程获取微信 token
     * GET https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
     * @return
     */

    public static String remoteWXToken(){
        System.out.println("---------------->远程获取token<-------------------------");
        //官网定义:
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APPID + "&secret=" + SECRET;

        //发起远程get请求，工具类封装好 （pom.xml中引入weixin-net包）
        String entity = WXHttpUtil.get(url);

        //返回值 参照接口文档
        //正常：{"access_token":"ACCESS_TOKEN","expires_in":7200}
        //异常: {"errcode":40013,"errmsg":"invalid appid"}

        // 返回结果是json格式  所以转为json 再来处理
        JSONObject jsonObject = JSONObject.parseObject(entity);

        if (jsonObject.containsKey("access_token")) {
            String accessToken = jsonObject.getString("access_token");
            return accessToken;
        } else {
            System.err.println("获取token失败:" + entity);
            return null;
        }
    }
}
