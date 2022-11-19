package com.atwp.mapper;

import com.atwp.entity.Product;

import java.util.List;

public interface ProductMapper {

    List<Product> findHostList();

    Product findById(Integer id);
}
