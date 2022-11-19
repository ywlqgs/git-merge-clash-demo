package com.atwp.mapper;

import com.atwp.entity.Address;
import com.atwp.entity.District;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * 测试用户功能
 */
@SpringBootTest //表示当前的类是一个测试类，不会随同项目一块打包
public class DistrictMapperTests {

    @Autowired
    private DistrictMapper districtMapper; //报错但不影响使用（看p2 1:30h老师的讲解）


    @Test
    public void findByParent() {

        List<District> list = districtMapper.findByParent("410000");
        list.forEach(parent -> System.out.println("parent="+parent));
    }
}
