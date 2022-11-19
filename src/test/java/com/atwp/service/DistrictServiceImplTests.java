package com.atwp.service;

import com.atwp.entity.Address;
import com.atwp.entity.District;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * 测试收货地址功能
 */
@SpringBootTest //表示当前的类是一个测试类，不会随同项目一块打包
public class DistrictServiceImplTests {

    @Autowired
    private IDistrictService districtService; //报错但不影响使用（看p2 1:30h老师的讲解）


    @Test
    public void getByParent(){

        List<District> list = districtService.getByParent("86");
        for (District district:list){
            System.err.println(district);
        }
    }


}
