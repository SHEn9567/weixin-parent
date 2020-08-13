
package com.hzit.proxy.proxy;

import com.alibaba.fastjson.JSONObject;
import com.hzit.net.WXHttpUtil;
import com.hzit.proxy.base.AccessTokenUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户相关操作
 * https://developers.weixin.qq.com/doc/offiaccount/User_Management/Get_users_basic_information_UnionID.html#UinonId
 */
public class UserProxy {

    public static void main(String[] args) {
        Map<String, Object> map = new UserProxy().getOpenIds(null);
        System.out.println(map);

        Map<String, Object> xx = (Map<String, Object>) map.get("data");
        List<String> list = (List<String>) xx.get("openid");

        Map<String, Object> listInfo = new UserProxy().getUsetListInfo(list, null);
        System.out.println(listInfo);

    }

    private String token = AccessTokenUtil.getWXToken();

    /**
     * 获取用户的openId
     *
     * @param nextOpenId
     * @return 格式：
     * 成功
     * {
     * "total":2,
     * "count":2,
     * "data":{
     * "openid":["OPENID1","OPENID2"]},
     * "next_openid":"NEXT_OPENID"
     * }
     */
    public Map<String, Object> getOpenIds(String nextOpenId) {

        String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + token;
        if (nextOpenId != null) {
            url += "&next_openid=" + nextOpenId; //如果传入nextOpenid从指定的获取
        }
        String body = WXHttpUtil.get(url);

        //{
        //    "total":2,
        //    "count":2,
        //    "data":{
        //    "openid":["OPENID1","OPENID2"]},
        //    "next_openid":"NEXT_OPENID"
        //}

        //{"errcode":40013,"errmsg":"invalid appid"}

        JSONObject jsonObject = JSONObject.parseObject(body);
        if (!jsonObject.containsKey("data")) {
            System.err.println("获取用户信息失败:" + body);
            return null;
        }

        Map<String, Object> map = JSONObject.toJavaObject(jsonObject, Map.class);

        return map;
    }


    /**
     * 获取单个用户信息
     *
     * @param openId
     * @return
     */
    public Map<String, Object> getUserInfoById(String openId) {
        String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + token + "&openid=" + openId + "&lang=zh_CN";
        String body = WXHttpUtil.get(url);

        //{
        //    "subscribe": 1,
        //    "openid": "o6_bmjrPTlm6_2sgVt7hMZOPfL2M",
        //    "nickname": "Band",
        //    "sex": 1,
        //    "language": "zh_CN",
        //    "city": "广州",
        //    "province": "广东",
        //    "country": "中国",
        //    "headimgurl":"http://thirdwx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
        //    "subscribe_time": 1382694957,
        //    "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
        //    "remark": "",
        //    "groupid": 0,
        //    "tagid_list":[128,2],
        //    "subscribe_scene": "ADD_SCENE_QR_CODE",
        //    "qr_scene": 98765,
        //    "qr_scene_str": ""
        //}

        //{"errcode":40013,"errmsg":"invalid appid"}

        JSONObject jsonObject = JSONObject.parseObject(body);
        if (jsonObject.containsKey("errcode")) {
            //失败
            System.err.println("获取用户信息失败:" + jsonObject);
            return null;
        }

        Map<String, Object> map = JSONObject.toJavaObject(jsonObject, Map.class);

        return map;
    }


    /**
     * 批量获取数据
     *
     * @param openIds
     * @param lang
     * @return
     */
    public Map<String, Object> getUsetListInfo(List<String> openIds, String lang) {


        String url = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=" + token;
        String requestParam = this.userInfoParam(openIds, lang);

        String body = WXHttpUtil.post(url, requestParam);

        JSONObject jsonObject = JSONObject.parseObject(body);
        if (jsonObject.containsKey("errcode")) {
            //失败
            System.err.println("批量获取用户信息失败:" + jsonObject);
            return null;
        }

        Map<String, Object> map = JSONObject.toJavaObject(jsonObject, Map.class);

        return map;
    }


    /**
     * 封装参数
     *
     * @param openIds
     * @return 返回值格式:
     * {
     * "user_list": [
     * {
     * "openid": "otvxTs4dckWG7imySrJd6jSi0CWE",
     * "lang": "zh_CN"
     * },
     * {
     * "openid": "otvxTs_JZ6SEiP0imdhpi50fuSZg",
     * "lang": "zh_CN"
     * }
     * ]
     * }
     */
    private String userInfoParam(List<String> openIds, String lang) {
        if (lang == null) {
            lang = "zh_CN";
        }
        //根据返回值 封装的集合
        Map<String, List<Map<String, String>>> map = new HashMap<String, List<Map<String, String>>>();

        List<Map<String, String>> userList = new ArrayList<Map<String, String>>();
        for (String openId : openIds) {
            Map<String, String> openIdMap = new HashMap<String, String>();
            openIdMap.put("openid", openId);
            openIdMap.put("lang", lang);

            userList.add(openIdMap);
        }

        map.put("user_list", userList);

        String jsonString = JSONObject.toJSONString(map);

        return jsonString;
    }


    /**
     * 获取二维码
     *
     * @return
     */
    public String getQrCode() {

        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + token;
        String requestParam = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": 123}}}";

        String body = WXHttpUtil.post(url, requestParam);
        JSONObject jsonObject = JSONObject.parseObject(body);

        //{"ticket":"gQH47joAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2taZ2Z3TVRtNzJXV1Brb3ZhYmJJAAIEZ23sUwMEmm
        //3sUw==","expire_seconds":60,"url":"http://weixin.qq.com/q/kZgfwMTm72WWPkovabbI"}

        if (!jsonObject.containsKey("ticket")) {
            //失败
            System.err.println("获取图像失败:" + jsonObject);
            return null;
        }
        String ticket = jsonObject.getString("ticket");

        String qrcode = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket;
        return qrcode;
    }
}
