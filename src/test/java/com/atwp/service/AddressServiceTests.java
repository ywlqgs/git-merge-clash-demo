package com.atwp.service;

import com.atwp.entity.Address;
import com.atwp.entity.User;
import com.atwp.service.ex.ServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

/**
 * 测试收货地址功能
 */
@SpringBootTest //表示当前的类是一个测试类，不会随同项目一块打包
public class AddressServiceTests {

    @Autowired
    private IAddressService addressService; //报错但不影响使用（看p2 1:30h老师的讲解）

    /**
     * 测试注册功能
     */
    @Test
    public void addNewAddress(){

        Address address = new Address();
        address.setName("miracle");
        addressService.addNewAddress(16,"管理员",address);
    }

    @Test
    public void setDefault(){
        addressService.setDefault(1,15,"管理员");
    }
}
