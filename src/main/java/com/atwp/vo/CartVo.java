package com.atwp.vo;

import lombok.Data;

import java.io.Serializable;

/**购物车数据的vo类(value object)*/
@Data
public class CartVo implements Serializable {

    private Integer cid;
    private Integer uid;
    private Integer pid;
    private Long price;
    private Integer num;
    private String title;
    private String image;
    private Long realPrice;
}
