package com.atwp.common;

import com.atwp.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;

@Data
public class User extends BaseEntity implements Serializable {

    private Integer uid;
    private String username;
    private String password;
    private String salt; //用于加密密码
    private String phone;
    private String email;
    private Integer gender;//'性别:0-女，1-男',
    private String avatar;
    //sdsa
    private Integer isDelete;
    //sss

}
