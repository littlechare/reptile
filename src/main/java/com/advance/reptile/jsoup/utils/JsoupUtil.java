package com.advance.reptile.jsoup.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

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
    public static Document parseUrlHtml(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param doc
     * @param cssQuery
     * @return
     */
    public static Elements parseDom(Document doc, String cssQuery){
        return doc.select(cssQuery);
    }
}
