package com.atwp.service.impl;

import com.atwp.entity.Address;
import com.atwp.entity.Order;
import com.atwp.entity.OrderItem;
import com.atwp.mapper.OrderMapper;
import com.atwp.service.IAddressService;
import com.atwp.service.ICartService;
import com.atwp.service.IOrderService;
import com.atwp.service.ex.InsertException;
import com.atwp.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private IAddressService addressService;
    @Autowired
    private ICartService cartService;

    @Override
    public Order create(Integer aid, Integer uid, String username, Integer[] cids) {

        //即将要下单的列表
        List<CartVo> list = cartService.getVoByCid(uid, cids);
        //计算产品的总价
        Long totalPrice=0L;
        //创建订单详细项的数据
        for (CartVo c:list){
            totalPrice+=c.getRealPrice()* c.getNum();
        }

        Address address = addressService.getByAid(aid, uid);
        Order order = new Order();
        order.setUid(uid);
        //收货地址数据
        order.setRecvName(address.getName());
        order.setRecvPhone(address.getPhone());
        order.setRecvProvince(address.getProvinceName());
        order.setRecvCity(address.getCityName());
        order.setRecvArea(address.getAreaName());
        order.setRecvAddress(address.getAddress());
        //支付、总价
        order.setStatus(0);
        order.setTotalPrice(totalPrice);
        order.setOrderTime(new Date());
        //日志
        order.setCreatedUser(username);
        order.setCreatedTime(new Date());
        order.setModifiedUser(username);
        order.setModifiedTime(new Date());

        //查询数据
        Integer rows = orderMapper.insertOrder(order);
        if (rows!=1){
            throw new InsertException("插入数据异常！");
        }

        //创建订单详细项的数据
        for (CartVo c:list){
            //创建一个订单项数据对象
            OrderItem orderItem = new OrderItem();
            orderItem.setOid(order.getOid());
            orderItem.setPid(c.getPid());
            orderItem.setTitle(c.getTitle());
            orderItem.setImage(c.getImage());
            orderItem.setPrice(c.getRealPrice());
            orderItem.setNum(c.getNum());
            //日志字段
            orderItem.setCreatedUser(username);
            orderItem.setCreatedTime(new Date());
            orderItem.setModifiedUser(username);
            orderItem.setModifiedTime(new Date());

            //插入数据操作
            Integer rowss = orderMapper.insertOrderItem(orderItem);
            if (rowss!=1){
                throw new InsertException("插入数据异常！");
            }
        }


        return order;
    }
}
