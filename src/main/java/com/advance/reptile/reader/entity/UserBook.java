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
 * @since 2019-08-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserBook implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String userId;

    private String bookId;

    private String status;

    private String chapterId;

    private Integer chapterNum;


}
