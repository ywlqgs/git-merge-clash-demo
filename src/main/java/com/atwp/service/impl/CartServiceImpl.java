package com.atwp.service.impl;

import com.atwp.entity.Cart;
import com.atwp.entity.Product;
import com.atwp.mapper.CartMapper;
import com.atwp.mapper.ProductMapper;
import com.atwp.service.ICartService;
import com.atwp.service.ex.AccessDeniedException;
import com.atwp.service.ex.CartNotFoundException;
import com.atwp.service.ex.InsertException;
import com.atwp.service.ex.UpdateException;
import com.atwp.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;
    @Autowired
    //cart表中的多数数据是product数据中抽离出来的 可以查product表获得数据
    private ProductMapper productMapper;

    @Override
    public void addToCart(Integer uid, Integer pid, Integer amount, String username) {

        Cart result = cartMapper.findByUidandPid(uid, pid);
        Date date = new Date();
        if (result==null){ //商品没有被添加到购物车，进行新增
            Cart cart = new Cart();
            //补全数据:参数传递的数据
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(amount);
            //补全价格 ：来自于商品中的数据
            Product product = productMapper.findById(pid);
            cart.setPrice(product.getPrice());
            //补全日志
            cart.setCreatedUser(username);
            cart.setCreatedTime(date);
            cart.setModifiedUser(username);
            cart.setModifiedTime(date);

            //执行数据的插入操作
            Integer rows = cartMapper.insert(cart);
            if (rows!=1){
                throw new InsertException("购物车插入数据时产生未知的异常！");
            }

        }else { //商品已经添加到购物车中，更新这条数据的num值
            Integer num=result.getNum()+amount;
            Integer rows = cartMapper.update(result.getCid(), num, username, date);
            if (rows!=1){
                throw new UpdateException("购物车更新数据时产生未知的异常！");
            }
        }
    }

    @Override
    public List<CartVo> getVoByUid(Integer uid) {

        return cartMapper.findVoByUid(uid);
    }

    @Override
    public Integer addNum(Integer cid, Integer uid, String username) {

        Cart result = cartMapper.findByCid(cid);
        if (result==null){
            throw new CartNotFoundException("数据不存在！");
        }
        if (!result.getUid().equals(uid)){
            throw new AccessDeniedException("数据非法访问！");
        }
        Integer num=result.getNum()+1;
        Integer rows = cartMapper.update(cid, num, username, new Date());
        if (rows!=1){
            throw new UpdateException("更新数据失败！");
        }

        return num;
    }

    @Override
    public List<CartVo> getVoByCid(Integer uid, Integer[] cids) {

        List<CartVo> list = cartMapper.findVoByCid(cids);
        Iterator<CartVo> iterator = list.iterator();
        while (iterator.hasNext()){
            CartVo cartVo = iterator.next();
            if (!cartVo.getUid().equals(uid)){ //表示当前的数据不属于当前的用户
                //从集合中移除这个元素
                list.remove(cartVo);
            }
        }

        return list;
    }


}
