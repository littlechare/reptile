package com.advance.reptile.wx.catalog.service;

import com.advance.reptile.wx.catalog.entity.Catalog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhouh
 * @since 2019-09-04
 */
public interface ICatalogService extends IService<Catalog> {

    Map<String, Object> getCatalogHomeData();

}
