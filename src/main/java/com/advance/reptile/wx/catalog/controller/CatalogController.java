package com.advance.reptile.wx.catalog.controller;


import com.advance.reptile.common.CommonUtils;
import com.advance.reptile.common.Response;
import com.advance.reptile.common.SysConstant;
import com.advance.reptile.wx.catalog.entity.Catalog;
import com.advance.reptile.wx.catalog.entity.Topic;
import com.advance.reptile.wx.catalog.service.ICatalogService;
import com.advance.reptile.wx.catalog.service.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhouh
 * @since 2019-09-04
 */
@RestController
@RequestMapping("/api/v1.0/wx/catalog")
public class CatalogController {

    @Autowired
    private ICatalogService catalogService;
    @Autowired
    private ITopicService topicService;

    @RequestMapping("/home")
    public Response catalogHome(){
        return Response.success(catalogService.getCatalogHomeData());
    }

    @RequestMapping("/add/topic")
    public Response addTopic(String name){
        Topic topic = new Topic();
        topic.setId(CommonUtils.getUuid());
        topic.setName(name);
        topic.setStatus(SysConstant.DATA_STATUS_VALID);
        topicService.save(topic);
        return Response.success();
    }

    @RequestMapping("/add/catalog")
    public Response addCatalog(String name){
        Catalog catalog = new Catalog();
        catalog.setId(CommonUtils.getUuid());
        catalog.setName(name);
        catalog.setStatus(SysConstant.DATA_STATUS_VALID);
        catalogService.save(catalog);
        return Response.success();
    }

}
