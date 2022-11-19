package com.atwp.mapper;

import com.atwp.entity.Address;

import java.util.Date;
import java.util.List;

/**
 * 收货地址持久层的接口
 */
public interface AddressMapper {

    /**
     * 插入用户的收货地址
     * @param address
     * @return
     */
    Integer insert(Address address);

    /**
     * 根据用户id统计收货地址数量（select count(*) from t_address where uid=?）
     * @param uid
     * @return
     */
    Integer countByUid(Integer uid);

    /**
     * 根据用户的id查询用户的收货地址数据
     * @param uid
     * @return
     */
    List<Address> findByUid(Integer uid);

    /**
     * 根据aid查询收货地址 是否存在
     * @param aid
     * @return
     */
    Address findByAid(Integer aid);

    /**
     * 根据用户的uid来修改用户的收货地址 设置为非默认
     * @param uid
     * @return
     */
    Integer updateNonDefault(Integer uid);

    Integer updateDefaultByAid(Integer aid, String modifiedUser, Date modifiedTime);

    /**
     * 根据收货地址id删除收货地址数据
     * @param aid
     * @return
     */
    Integer deleteByAid(Integer aid);

    /**
     * 根据uid查询当前用户最后一次被修改的收货地址数据
     * @param uid
     * @return
     */
    Address findLastModified(Integer uid);
}
