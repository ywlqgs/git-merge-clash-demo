package com.atwp.mapper;

import com.atwp.entity.Address;
import com.atwp.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

/**
 * 测试用户功能
 */
@SpringBootTest //表示当前的类是一个测试类，不会随同项目一块打包
public class AddressMapperTests {

    @Autowired
    private AddressMapper addressMapper; //报错但不影响使用（看p2 1:30h老师的讲解）

    /**
     * 单元测试方法：满足以下四个条件就可以独立运行，不用启动整个项目，可以做单元测试，提升了代码的测试效率
     * 1.必须用@Test注解
     * 2.返回值类型必须为void
     * 3.方法的参数列表不指定任何类型
     * 4.方法的访问修饰符必须是public
     */
    @Test
    public void insert(){
        Address address = new Address();
        address.setUid(15);
        address.setName("wp");

        addressMapper.insert(address);
    }

    @Test
    public void countByUid(){
        Integer count = addressMapper.countByUid(15);
        System.out.println("count="+count);
    }

    @Test
    public void findByUid(){

        List<Address> list = addressMapper.findByUid(15);
        System.err.println(list);
    }
}
