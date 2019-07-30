package com.advance.reptile.reader.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2019-07-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String id;

    private String name;

    private String url;

    private String author;

    private String tag;

    private String bookImg;

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
     * 书籍状态
     */
    private String status;

    private String intro;

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


}
