package com.atwp.controller;

import com.atwp.common.JsonResult;
import com.atwp.entity.Product;
import com.atwp.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("products")
public class ProductController extends BaseController{

    @Autowired
    private IProductService productService;

    @GetMapping("hot_list")
    public JsonResult<List<Product>>  getHostList(){

        log.info("热销排行榜查询~");

        List<Product> list = productService.findHostList();

        return new JsonResult<>(OK,list);
    }

    @GetMapping("{id}/details")
    public JsonResult<Product> getById(@PathVariable("id") Integer id){

        Product product = productService.findById(id);

        return new JsonResult<>(OK,product);
    }
}
