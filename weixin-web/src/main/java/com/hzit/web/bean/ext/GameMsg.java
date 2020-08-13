package com.hzit.web.bean.ext;

import com.hzit.web.bean.BaseMsg;
import lombok.Data;

@Data
public class GameMsg extends BaseMsg {
    private String Event;
    private String EventKey;
}
