package com.atwp.service.impl;

import com.atwp.entity.District;
import com.atwp.mapper.DistrictMapper;
import com.atwp.service.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictServiceImpl implements IDistrictService {

    @Autowired
    private DistrictMapper districtMapper;

    @Override
    public List<District> getByParent(String parent) {

        List<District> list = districtMapper.findByParent(parent);
        //把不需要的数据设置为null 节省流量，提升效率 （我在想可不可以只查后两个字段...）
        for(District district:list){
            district.setId(null);
            district.setParent(null);
        }

        return list;
    }

    @Override
    public String getNameByCode(String code) {

        return districtMapper.findNameByCode(code);
    }
}
