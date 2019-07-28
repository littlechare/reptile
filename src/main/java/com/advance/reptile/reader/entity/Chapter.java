package com.advance.reptile.reader.entity;

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
 * @since 2019-07-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Chapter implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 章节标题
     */
    private String title;

    private Integer num;

    /**
     * 链接
     */
    private String url;

    /**
     * 上一章id
     */
    private String pre;

    /**
     * 下一章id
     */
    private String next;


}
