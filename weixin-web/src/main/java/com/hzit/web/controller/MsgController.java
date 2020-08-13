package com.hzit.web.controller;

import com.hzit.web.bean.Message;
import com.hzit.web.service.IMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/message")
public class MsgController {
    @Autowired
    private IMsgService msgService;
    @PutMapping("/send")
    public void sendMessage(Message message){
        msgService.sendMessage(message);
    }
    @PostMapping("/toSend/{openid}")
    public String toSend(@PathVariable("openid") String openid, Model model){
        model.addAttribute("openid",openid);
        return "sendMsg";
    }
}
