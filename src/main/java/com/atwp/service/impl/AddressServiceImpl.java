package com.atwp.service.impl;

import com.atwp.common.JsonResult;
import com.atwp.entity.Address;
import com.atwp.mapper.AddressMapper;
import com.atwp.service.IAddressService;
import com.atwp.service.IDistrictService;
import com.atwp.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private IDistrictService districtService;

    @Value("${user.address.max-count}")
    private Integer maxCount;

    @Override
    public void addNewAddress(Integer uid, String username, Address address) {

        Integer count = addressMapper.countByUid(uid);
        if (count>=maxCount){
            throw new AddressCountLimitException("用户收货地址超出上限！");
        }

        //补全省市区
        String provinceName = districtService.getNameByCode(address.getProvinceCode());
        String cityName = districtService.getNameByCode(address.getCityCode());
        String areaName = districtService.getNameByCode(address.getAreaCode());
        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);

        //补全其他数据
        address.setUid(uid);
        Integer isDefault= count==0? 1 : 0; //1表示默认，0表示不是默认
        address.setIsDefault(isDefault);
        address.setCreatedUser(username);
        address.setModifiedUser(username);
        address.setCreatedTime(new Date());
        address.setModifiedTime(new Date());

        Integer rows = addressMapper.insert(address);
        if (rows!=1){
            throw new InsertException("插入用户的收货地址时产生未知的异常！");
        }
    }

    @Override
    public List<Address> getByUid(Integer uid) {
        List<Address> list = addressMapper.findByUid(uid);

        //只返回需要的信息
        for (Address address:list){
            //address.setAid(null);
            //address.setUid(null);
            address.setProvinceCode(null);
            address.setCityCode(null);
            address.setAreaCode(null);
            address.setTel(null);
            address.setIsDefault(null);
            address.setCreatedTime(null);
            address.setCreatedUser(null);
            address.setModifiedTime(null);
            address.setCreatedUser(null);
        }
        return list;
    }

    //TODO 后续优化时 要将更新操作用事务进行管理
    @Override
    public void setDefault(Integer aid, Integer uid, String username) {

        Address result = addressMapper.findByAid(aid);
        if (result==null){
            throw new AddressNotFoundException("收货地址不存在！");
        }

        //检测当前获取到的收货地址数据的归属
        if (!result.getUid().equals(uid)){
            throw new AccessDeniedException("非法数据访问！");
        }

        //先将所有的收获地址设置为非默认
        Integer rows = addressMapper.updateNonDefault(uid);
        if (rows<1){
            throw new UpdateException("更新数据时产生未知的异常！");
        }
        //将用户选中的某条地址设置为默认收货地址
          rows = addressMapper.updateDefaultByAid(aid, username, new Date());
        if (rows!=1){
            throw new UpdateException("更新数据时产生未知的异常！");
        }

    }

    //一个删除用户收货地址 查询了6次数据库！ 完全不符合实际开发要求！
    @Override
    public void delete(Integer aid, Integer uid, String username) {

        Address result = addressMapper.findByAid(aid);
        if (result==null){
            throw new AddressNotFoundException("用户地址数据不存在！");
        }
        if (!result.getUid().equals(uid)){
            throw new AccessDeniedException("非法数据访问！");
        }

        Integer rows = addressMapper.deleteByAid(aid);
        if (rows!=1){
            throw new DeleteException("删除数据时产生未知的异常！");
        }

        Integer count=addressMapper.countByUid(uid);
        if (count==0){
            // 直接种植程序
            return;
        }
        if (result.getIsDefault()==0){
            return;
        }

        Address address = addressMapper.findLastModified(uid);
        rows=addressMapper.updateDefaultByAid(
                address.getAid(),username,new Date());

        if (rows!=1){
            throw new UpdateException("更新时产生未知的异常！");
        }
    }

    @Override
    public Address getByAid(Integer aid, Integer uid) {

        Address address = addressMapper.findByAid(aid);
        if (address==null){
            throw new AddressNotFoundException("收货地址数据不存在！");
        }
        if (!address.getUid().equals(uid)){
            throw new AccessDeniedException("非法数据访问");
        }

        address.setProvinceCode(null);
        address.setCityCode(null);
        address.setAreaCode(null);
        address.setCreatedUser(null);
        address.setCreatedTime(null);
        address.setModifiedUser(null);
        address.setModifiedTime(null);

        return address;
    }

}
