package com.hzit.web.controller;

import com.hzit.web.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;


@Controller
public class UserController {

    @Autowired
    private IUserService userService;


    @RequestMapping("/main")
    public String main(Model model) {

        Map<String, Object> map = userService.userListInfo();

        model.addAttribute("count", map.get("count"));
        model.addAttribute("total", map.get("total"));
        List<Map<String, Object>> userList = (List<Map<String, Object>>) map.get("user_info_list");
        model.addAttribute("userList", userList);

        String qrcode = userService.qrcode();
        model.addAttribute("qrcode",qrcode);

        return "main";
    }
}
