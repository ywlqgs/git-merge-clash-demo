package com.atwp.mapper;

import com.atwp.entity.Cart;
import com.atwp.vo.CartVo;

import java.util.Date;
import java.util.List;

public interface CartMapper {

    /**
     * 插入购物车的数据
     * @param cart
     * @return
     */
    Integer insert(Cart cart);

    /**
     * 更新购物车某件商品的数量
     * @param cid
     * @param num
     * @param modifiedUser
     * @param modifiedTime
     * @return
     */
    Integer update(Integer cid, Integer num, String modifiedUser, Date modifiedTime);

    /**
     * 根据用户的id和商品的id来查询购物车中的数据
     * @param uid
     * @param pid
     */
    Cart findByUidandPid(Integer uid, Integer pid);

    List<CartVo> findVoByUid(Integer uid);

    Cart findByCid(Integer cid);

    List<CartVo> findVoByCid(Integer[] cids);
}
