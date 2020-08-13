package com.hzit.web.controller;

import com.hzit.web.service.IAutoCallBackMsgService;
import com.hzit.web.util.SecurityKit;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

@Controller
public class InitController {

    /**
     * 自定义随机值   网页中也需要配置
     */
    String token = "SHEn_token";

    //剪刀"✌ " 石头"✊ " 布"✋ "

    @Autowired
    private IAutoCallBackMsgService autoCallBackMsgService;

    /**
     * @param signature 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param echostr   随机字符串
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    @ResponseBody
    public String init(String signature, String timestamp, String nonce, String echostr) {

        //1.获取参数

        //2.校验规则
        //1）将token、timestamp、nonce三个参数进行字典序排序
        String strArr[] = {token, timestamp, nonce};
        Arrays.sort(strArr);

        // 2）将三个参数字符串拼接成一个字符串进行sha1加密
        String str = "";
        for (String s : strArr) {
            str += s;
        }
        System.out.println("字符串:" + str);
        String sha1 = SecurityKit.sha1(str);

        // 3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        if (sha1.equals(signature)) {
            // 4) 请原样返回echostr参数内容
            return echostr;
        }

        return null;
    }

    @RequestMapping(value = "/init", method = RequestMethod.POST)
    @ResponseBody
    public String msg(HttpServletRequest request) throws IOException, DocumentException {

        ServletInputStream inputStream = request.getInputStream();
        SAXReader saxReader = new SAXReader();
        //将xml转化为Document对象
        Document document = saxReader.read(inputStream);
        String backTextMsg = autoCallBackMsgService.autoCallBackTextMsg(document);
        return backTextMsg;
    }

}
