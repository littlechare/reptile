package com.advance.reptile.reader.mapper;

import com.advance.reptile.reader.entity.UserBook;
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
 * @since 2019-08-01
 */
public interface UserBookMapper extends BaseMapper<UserBook> {

    List<Map<String, Object>> getBookShelfDatas(@Param("userId")String userId);

}
