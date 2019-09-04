package com.advance.reptile.wx.catalog.service.impl;

import com.advance.reptile.common.SysConstant;
import com.advance.reptile.wx.catalog.entity.Catalog;
import com.advance.reptile.wx.catalog.entity.Topic;
import com.advance.reptile.wx.catalog.mapper.CatalogMapper;
import com.advance.reptile.wx.catalog.service.ICatalogService;
import com.advance.reptile.wx.catalog.service.ITopicService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhouh
 * @since 2019-09-04
 */
@Service
public class CatalogServiceImpl extends ServiceImpl<CatalogMapper, Catalog> implements ICatalogService {

    @Autowired
    private ITopicService topicService;

    @Override
    public Map<String, Object> getCatalogHomeData() {
        Map<String, Object> resultData = new HashMap<>();
        QueryWrapper<Catalog> catalogQueryWrapper = new QueryWrapper<>();
        catalogQueryWrapper.eq("status", SysConstant.DATA_STATUS_VALID);
        resultData.put("catalog", super.list(catalogQueryWrapper));
        QueryWrapper<Topic> topicQueryWrapper = new QueryWrapper<>();
        topicQueryWrapper.eq("status", SysConstant.DATA_STATUS_VALID);
        resultData.put("topic", topicService.list(topicQueryWrapper));
        return resultData;
    }
}
