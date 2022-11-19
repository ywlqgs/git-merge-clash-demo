package com.atwp.entity;

import com.atwp.controller.BaseController;
import lombok.Data;

import java.io.Serializable;

/**省市区的数据实体类*/
@Data
public class District extends BaseController implements Serializable {

    private Integer id;
    private String parent;
    private String code;
    private String name;

}
