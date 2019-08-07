package com.advance.reptile.jsoup.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Jsoup工具类
 */
public class JsoupUtil {

    /**
     * 解析路径获取HTML内容
     * @param url
     * @return
     */
    public static Document parseUrlHtml(String url) throws Exception {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("网址不正确！");
        }
    }

    /**
     * @param doc
     * @param cssQuery
     * @return
     */
    public static Elements parseDom(Document doc, String cssQuery){
        return doc.select(cssQuery);
    }

    /**
     * 获取节点属性
     * @param document
     * @param cssQuery
     * @param attrName
     * @return
     */
    public static String getElementAttr(Document document, String cssQuery, String attrName){
        return document.select(cssQuery).attr(attrName);
    }

    /**
     * 获取节点文本
     * @param document
     * @param cssQuery
     * @return
     */
    public static String getElementText(Document document, String cssQuery){
        return document.select(cssQuery).text();
    }
}
