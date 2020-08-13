package com.hzit.web.service.impl;

import com.hzit.web.bean.ext.GameMsg;
import com.hzit.web.bean.ext.TextMsg;
import com.hzit.web.service.IAutoCallBackMsgService;
import com.hzit.web.util.Dom4jUtil;
import com.hzit.web.util.MsgType;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class AutoCallBackMsgServiceImpl implements IAutoCallBackMsgService {
    //剪刀"✌ " 石头"✊ " 布"✋ "
    //V1001_Scissors  V1001_Stone  V1001_Cloth

    @Override
    public String autoCallBackTextMsg(Document document) {
        String scissors ="✌ ";
        String stone ="✊ ";
        String cloth ="✋ ";
        String game[] ={"✌ ","✊ ","✋ "};

        XStream xStream = new XStream();
        xStream.alias("xml", TextMsg.class);

        //1.判断是否接收文本消息
        String msgType = Dom4jUtil.getElementValue(document, "MsgType");
        String fromUserName = Dom4jUtil.getElementValue(document, "FromUserName");
        String toUserName = Dom4jUtil.getElementValue(document, "ToUserName");

        //Document对象转为xml
        String xml = document.asXML();
        System.out.println("rev:" + xml);
        String str ="";
        if (msgType.equals("event")) {
            //xml转为java对象
            Random random = new Random();
            int index = random.nextInt(game.length);
            String eventKey = Dom4jUtil.getElementValue(document, "EventKey");
            TextMsg revTextMst = new TextMsg();
            revTextMst.setToUserName(fromUserName);
            revTextMst.setFromUserName(toUserName);
            revTextMst.setCreateTime(new Date().getTime());
            revTextMst.setMsgType(MsgType.TEXT_MSG_TYPE);
            if (eventKey.equals("V1001_Scissors") && game[index].equals(scissors)){
                str="在下出的"+scissors+",阁下出的"+scissors+",平局";
            }
            if (eventKey.equals("V1001_Scissors") && game[index].equals(cloth)){
                str="在下出的"+cloth+",阁下出的"+scissors+",阁下赢了";
            }
            if (eventKey.equals("V1001_Scissors") && game[index].equals(stone)){
                str="在下出的"+stone+",阁下出的"+scissors+",在下赢了";
            }
            if (eventKey.equals("V1001_Stone") && game[index].equals(scissors)){
                str="在下出的"+scissors+",阁下出的"+stone+",阁下赢了";
            }
            if (eventKey.equals("V1001_Stone") && game[index].equals(cloth)){
                str="在下出的"+cloth+",阁下出的"+stone+",在下赢了";
            }
            if (eventKey.equals("V1001_Stone") && game[index].equals(stone)){
                str="在下出的"+stone+",阁下出的"+stone+",平局";
            }
            if (eventKey.equals("V1001_Cloth") && game[index].equals(scissors)){
                str="在下出的"+scissors+",阁下出的"+cloth+",在下赢了";
            }
            if (eventKey.equals("V1001_Cloth") && game[index].equals(cloth)){
                str="在下出的"+cloth+",阁下出的"+cloth+",平局";
            }
            if (eventKey.equals("V1001_Cloth") && game[index].equals(stone)){
                str="在下出的"+stone+",阁下出的"+cloth+",阁下赢了";
            }
            revTextMst.setContent(str);

            String sendXml = xStream.toXML(revTextMst);
            return sendXml;
        }else if (msgType.equals("text")) {//2.如果是文本消息  获取关键字  回复 对应内容

            //用户发送过来的xml

            //xml转为java对象
            TextMsg revTextMst = (TextMsg) xStream.fromXML(xml);
            String key = revTextMst.getContent();//获取文本的内容

            //人工智能处理
            String value = key.replace("么", "").replace("吗", "")
                    .replace("?", "!").replace("？", "!");


            TextMsg sendTextMsg = new TextMsg();
            sendTextMsg.setToUserName(revTextMst.getFromUserName());
            sendTextMsg.setFromUserName(revTextMst.getToUserName());
            sendTextMsg.setCreateTime(new Date().getTime());
            sendTextMsg.setMsgType(MsgType.TEXT_MSG_TYPE);
            sendTextMsg.setContent(value);
            //将java对象转为xml
            String sendXml = xStream.toXML(sendTextMsg);
            System.out.println("send:" + sendXml);
            return sendXml;
        } else {
            //3.非文本消息，回复 暂不支持，正在开发中


            TextMsg sendTextMsg = new TextMsg();
            sendTextMsg.setToUserName(fromUserName);
            sendTextMsg.setFromUserName(toUserName);
            sendTextMsg.setCreateTime(new Date().getTime());
            sendTextMsg.setMsgType(MsgType.TEXT_MSG_TYPE);
            sendTextMsg.setContent("支持文本类型，其他正在开发中");
            String sendXml = xStream.toXML(sendTextMsg);
            return sendXml;
        }
    }
}
