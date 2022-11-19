package com.atwp.service;

import com.atwp.entity.Product;

import java.util.List;

public interface IProductService {

    List<Product> findHostList();

    /**
     * 根据商品id查询商品详情
     * @param id
     * @return
     */
    Product findById(Integer id);
}
