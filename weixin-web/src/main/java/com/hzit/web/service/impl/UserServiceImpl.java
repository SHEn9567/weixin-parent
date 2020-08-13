package com.hzit.web.service.impl;

import com.hzit.proxy.proxy.UserProxy;
import com.hzit.web.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements IUserService {
    private UserProxy userProxy = new UserProxy(); //没有交给sping管理，需要自己创建对象

    @Override
    public Map<String, Object> userListInfo() {

        //获取所有的ID及相关信息
        Map<String, Object> openIds = userProxy.getOpenIds(null);
        //{
        //    "total":2,
        //    "count":2,
        //    "data":{
        //    "openid":["OPENID1","OPENID2"]},
        //    "next_openid":"NEXT_OPENID"
        //}

        int total = (int) openIds.get("total");
        int count = (int) openIds.get("count");
        Map<String, List<String>> data = (Map<String, List<String>>) openIds.get("data");
        List<String> myOpenIds = data.get("openid");

        Map<String, Object> userListInfo = userProxy.getUsetListInfo(myOpenIds, null);

        //补充统计信息
        userListInfo.put("total",total);
        userListInfo.put("count",count);

        //{
        //   "user_info_list": [
        //       {
        //           "subscribe": 1,
        //           "openid": "otvxTs4dckWG7imySrJd6jSi0CWE",
        //           "nickname": "iWithery",
        //           "sex": 1,
        //           "language": "zh_CN",
        //           "city": "揭阳",
        //           "province": "广东",
        //           "country": "中国",
        //
        //           "headimgurl": "http://thirdwx.qlogo.cn/mmopen/xbIQx1GRqdvyqkMMhEaGOX802l1CyqMJNgUzKP8MeAeHFicRDSnZH7FY4XB7p8XHXIf6uJA2SCunTPicGKezDC4saKISzRj3nz/0",
        //
        //          "subscribe_time": 1434093047,
        //           "unionid": "oR5GjjgEhCMJFyzaVZdrxZ2zRRF4",
        //           "remark": "",
        //
        //           "groupid": 0,
        //           "tagid_list":[128,2],
        //           "subscribe_scene": "ADD_SCENE_QR_CODE",
        //           "qr_scene": 98765,
        //           "qr_scene_str": ""
        //
        //      },
        //       {
        //           "subscribe": 0,
        //           "openid": "otvxTs_JZ6SEiP0imdhpi50fuSZg"
        //       }
        //   ]
        //}

        return userListInfo;
    }

    @Override
    public String qrcode() {
        return userProxy.getQrCode();
    }
}
