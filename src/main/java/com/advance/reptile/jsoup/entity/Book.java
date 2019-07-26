package com.advance.reptile.jsoup.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2019-06-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String url;

    private String author;

    /**
     * 发布时间
     */
    private LocalDateTime publisherTime;

    /**
     * 字数
     */
    private Integer wordNum;

    private String type;

    /**
     * 最后更新时间
     */
    private String lastUpdateTime;

    /**
     * 章节数
     */
    private Integer praNum;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String dataStatus;

    private String remark;

    private String intro;

    private String status;

}
