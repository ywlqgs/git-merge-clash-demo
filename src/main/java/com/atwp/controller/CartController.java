package com.atwp.controller;

import com.atwp.common.JsonResult;
import com.atwp.service.ICartService;
import com.atwp.vo.CartVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("carts")
public class CartController extends BaseController{

    @Autowired
    private ICartService cartService;

    @PostMapping("add_to_cart")
    public JsonResult<Void> addCart(Integer pid, Integer amount, HttpSession session){

        cartService.addToCart(getUidFromSession(session),pid,amount,getUserNameFromSession(session));

        return new JsonResult<>(OK);
    }

    //@GetMapping({"","/"})
    @GetMapping
    public JsonResult<List<CartVo>> getVoByUid(HttpSession session){

        List<CartVo> list = cartService.getVoByUid(getUidFromSession(session));

        return new JsonResult<>(OK,list);
    }

    @PostMapping("{cid}/num/add")
    public JsonResult<Integer> addNum(@PathVariable("cid") Integer cid, HttpSession session){

        Integer data = cartService.addNum(cid, getUidFromSession(session), getUserNameFromSession(session));

        return new JsonResult<>(OK,data);
    }

    @GetMapping("list")
    public JsonResult<List<CartVo>> getVoByCid(Integer[] cids,HttpSession session){

        List<CartVo> data = cartService.getVoByCid(getUidFromSession(session), cids);

        return new JsonResult<>(OK,data);
    }
}
