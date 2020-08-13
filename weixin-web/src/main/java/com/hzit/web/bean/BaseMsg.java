package com.hzit.web.bean;

import lombok.Data;

import java.util.Date;

@Data
public class BaseMsg {
    protected String MsgId;
    protected String ToUserName;
    protected String FromUserName;
    protected Long CreateTime;
    protected String MsgType;
}
