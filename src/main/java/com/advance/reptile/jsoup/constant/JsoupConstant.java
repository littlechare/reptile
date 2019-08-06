package com.advance.reptile.jsoup.constant;

/**
 *  jsoup常量类
 */
public class JsoupConstant {

    //下一章连接的样式选择器
    public final static String CSS_QUERY_OF_NEXT_CHAPTER_URL = ".bottem1 p:eq(1) a:eq(3)";
    //章节标题选择器
    public final static String CSS_QUERY_OF_CHAPTER_TITLE = ".bookname h1";
    //章节内容选择器
    public final static String CSS_QUERY_OF_CHAPTER_CONTENT = "#content";
    //书籍标题选择器
    public final static String CSS_QUERY_OF_BOOK_NAME = "#info h1";
    //书籍描述信息选择器
    public final static String CSS_QUERY_OF_BOOK_INFO = "#info p";
    //其实页面的获取路径
    public final static String CSS_QUERY_START_URL = "#list ._chapter li:eq(0)";

}
