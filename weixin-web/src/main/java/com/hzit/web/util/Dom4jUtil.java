package com.hzit.web.util;

import org.dom4j.Document;
import org.dom4j.Element;

public class Dom4jUtil {

    /**
     * 根据节点名称获取节点的value
     * @param document
     * @param name
     * @return
     */
    public static String getElementValue(Document document,String name){
        //获得根节点
        Element rootElement = document.getRootElement();
        //获取指定子节点
        Element element = rootElement.element(name);
        //获取子节点的value
        String value = element.getTextTrim();

        return value;
    }
}
