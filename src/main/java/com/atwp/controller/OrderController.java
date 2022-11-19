package com.atwp.controller;

import com.atwp.common.JsonResult;
import com.atwp.entity.Order;
import com.atwp.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("orders")
public class OrderController extends BaseController{

    @Autowired
    private IOrderService orderService;

    @GetMapping("create")
    public JsonResult<Order> create(Integer aid,Integer[] cids,HttpSession session){

        Integer uid = getUidFromSession(session);
        String username = getUserNameFromSession(session);
        Order data = orderService.create(aid, uid, username, cids);

        return new JsonResult<>(OK,data);
    }
}
