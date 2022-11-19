package com.atwp.service;

import com.atwp.entity.Address;

import java.util.List;

public interface IAddressService {

    /**
     * 新增收货地址
     * @param uid
     * @param username
     * @param address
     */
    void addNewAddress(Integer uid,String username,Address address);

    List<Address> getByUid(Integer uid);

    /**
     * 修改默认收货地址
     * @param aid
     * @param uid
     * @param username
     */
    void setDefault(Integer aid,Integer uid,String username);

    /**
     * 删除用户选中的收货地址数据
     * @param aid
     * @param uid
     * @param username
     */
    void delete(Integer aid ,Integer uid ,String username);

    Address getByAid(Integer aid,Integer uid);
}
