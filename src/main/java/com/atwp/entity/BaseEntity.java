package com.atwp.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 作为实体类的基类
 *     created_user VARCHAR(20) COMMENT '日志-创建人',
 *     created_time DATETIME COMMENT '日志-创建时间',
 *     modified_user VARCHAR(20) COMMENT '日志-最后修改执行人',
 *     modified_time DATETIME COMMENT '日志-最后修改时间',
 */
@Data
public class BaseEntity  implements Serializable {

    private String createdUser;
    private Date createdTime;
    private String modifiedUser;
    private Date modifiedTime;
}
