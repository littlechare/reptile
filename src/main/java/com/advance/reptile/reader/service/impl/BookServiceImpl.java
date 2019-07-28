package com.advance.reptile.reader.service.impl;

import com.advance.reptile.reader.entity.Book;
import com.advance.reptile.reader.mapper.BookMapper;
import com.advance.reptile.reader.service.IBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2019-07-28
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements IBookService {

}
