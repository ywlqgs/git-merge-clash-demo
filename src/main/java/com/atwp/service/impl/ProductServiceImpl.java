package com.atwp.service.impl;

import com.atwp.entity.Product;
import com.atwp.mapper.ProductMapper;
import com.atwp.service.IProductService;
import com.atwp.service.ex.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> findHostList() {


        List<Product> list = productMapper.findHostList();
        for (Product product : list){
            product.setPriority(null);
            product.setCreatedUser(null);
            product.setCreatedTime(null);
            product.setModifiedUser(null);
            product.setModifiedTime(null);
        }

        return list;
    }

    @Override
    public Product findById(Integer id) {

        Product product = productMapper.findById(id);
        if (product==null){
            throw new ProductNotFoundException("尝试访问的商品数据不存在！");
        }
        product.setPriority(null);
        product.setCreatedUser(null);
        product.setCreatedTime(null);
        product.setModifiedUser(null);
        product.setModifiedTime(null);

        return product;
    }
}
