package com.atwp.mapper;

import com.atwp.entity.Order;
import com.atwp.entity.OrderItem;

/**订单的持久层接口*/
public interface OrderMapper {

    /**
     * 插入订单数据
     * @param order
     * @return
     */
    Integer insertOrder(Order order);

    /**
     * 插入订单项的数据
     * @param orderItem
     * @return
     */
    Integer insertOrderItem(OrderItem orderItem);
}
