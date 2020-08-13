package com.hzit.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.hzit.proxy.proxy.MsgProxy;
import com.hzit.web.bean.Message;
import com.hzit.web.service.IMsgService;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements IMsgService {
    private MsgProxy msgProxy = new MsgProxy(); //没有交给sping管理，需要自己创建对象
    @Override
    public void sendMessage(Message message) {
        String string = JSON.toJSONString(message);
        msgProxy.sendMessage(string);
    }
}
