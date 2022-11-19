package com.atwp.controller;

import com.atwp.common.JsonResult;
import com.atwp.entity.District;
import com.atwp.service.IDistrictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("districts")
public class DistrictController extends BaseController{

    @Autowired
    private IDistrictService districtService;

    //district开头的请求都被拦截到getByParent()方法
    @GetMapping({"/",""})
    public JsonResult<List<District>> getByParent(String parent){

        log.info("parent={}",parent);
        List<District> data = districtService.getByParent(parent);

        return new JsonResult<>(OK,data);
    }
}
