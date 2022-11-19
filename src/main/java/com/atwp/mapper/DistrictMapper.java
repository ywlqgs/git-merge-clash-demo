package com.atwp.mapper;

import com.atwp.entity.District;

import java.util.List;

public interface DistrictMapper {

    /**
     * 根据父代号查询区域信息
     * @param parent
     * @return
     */
    List<District> findByParent(String parent);

    String findNameByCode(String code);
}
