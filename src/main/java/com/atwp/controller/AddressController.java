package com.atwp.controller;

import com.atwp.common.JsonResult;
import com.atwp.entity.Address;
import com.atwp.service.IAddressService;
import com.atwp.service.IDistrictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("addresses")
public class AddressController extends BaseController{

    @Autowired
    private IAddressService addressService;


    @PostMapping("add_new_address")
    public JsonResult<Void> addNewAddress(HttpSession session, Address address){

        Integer uid = getUidFromSession(session);
        String username = getUserNameFromSession(session);
        addressService.addNewAddress(uid,username,address);

        return new JsonResult<>(OK);
    }

    @GetMapping({"","/"})
    public JsonResult<List<Address>> getByUid(HttpSession session){

        Integer uid = getUidFromSession(session);
        List<Address> address = addressService.getByUid(uid);

        return new JsonResult<>(OK,address);
    }

    //Restful风格的请求编写
    @PostMapping("{aid}/set_default")
    public JsonResult<Void> setDefault(@PathVariable("aid") Integer aid, HttpSession session){

        addressService.setDefault(aid,
                getUidFromSession(session),getUserNameFromSession(session));

        return new JsonResult<>(OK);
    }

    @PostMapping("{aid}/delete")
    public JsonResult<Void> delete(@PathVariable("aid") Integer aid, HttpSession session){

        addressService.delete(aid,getUidFromSession(session),getUserNameFromSession(session));

        return new JsonResult<>(OK);
    }
}
