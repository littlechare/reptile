package com.advance.reptile.reader.mapper;

import com.advance.reptile.reader.entity.Bookmark;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2019-07-28
 */
public interface BookmarkMapper extends BaseMapper<Bookmark> {

    List<Map<String, Object>> selectBookMarks(@Param("bookName")String bookName, @Param("userId")String userId);

}
