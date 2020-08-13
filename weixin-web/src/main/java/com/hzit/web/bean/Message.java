package com.hzit.web.bean;

import com.hzit.web.bean.ext.Text;
import lombok.Data;

@Data
public class Message {
    private String touser;
    private String msgtype;
    private Text text;
}
