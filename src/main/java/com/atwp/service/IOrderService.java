package com.atwp.service;

import com.atwp.entity.Address;
import com.atwp.entity.Order;

public interface IOrderService {

    Order create(Integer aid, Integer uid, String username, Integer[] cids);
}
