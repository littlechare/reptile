package com.advance.reptile.jsoup.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * jsoup保存信息时的消息载体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsoupSaveDataVo {

    private String charpterId;

    private String pre;

    private String next;

    private String preTitle;

    private String nextTitle;

    private String bookId;

    private String baseUrl;

    private String startUrl;
}
