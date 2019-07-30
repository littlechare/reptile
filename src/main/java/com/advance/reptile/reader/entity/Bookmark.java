package com.advance.reptile.reader.entity;

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
public class Bookmark implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String id;

    private String bookId;

    private String userId;


}
