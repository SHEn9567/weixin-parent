package com.hzit.web;

import com.hzit.web.bean.Message;
import com.hzit.web.bean.ext.Text;
import com.hzit.web.service.IMsgService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageTest {
    @Autowired
    private IMsgService msgService;
    @Test
    public void test01(){
        Message message = new Message();
        Text text = new Text();
        message.setTouser("oQQST1HL76O9fD_y3qcFZ5bITwe8");
        message.setMsgtype("text");
        text.setContent("hello");
        message.setText(text);
        msgService.sendMessage(message);
    }
}
