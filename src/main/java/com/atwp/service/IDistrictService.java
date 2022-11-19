package com.atwp.service;


import com.atwp.entity.District;

import java.util.List;

public interface IDistrictService {

    /**
     * 根据父代号来查询区域信息（省市区）
     * @param parent
     * @return
     */
    List<District> getByParent(String parent);

    String getNameByCode(String code);
}
