package com.zhao.salechicken.controller;

import com.zhao.salechicken.common.BaseContext;
import com.zhao.salechicken.common.R;
import com.zhao.salechicken.pojo.Address;
import com.zhao.salechicken.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    /**
     * 添加地址信息
     * @param address
     * @return
     */
    @PostMapping("/addAddress")
    public R<String> addAddress(@RequestBody Address address){
        addressService.addAddress(address);
        return R.success("添加成功");
    }

    /**
     * 删除地址
     * @param addressId
     * @return
     */
    @DeleteMapping("/deleteAddress")
    public R<String> deleteAddress(Integer addressId){
        addressService.deleteAddress(addressId);
        return R.success("删除成功");
    }

    /**
     * 查找所有地址(分页)
     * @param request
     * @return
     */
    @GetMapping("/selectAllAddress")
    public R<List<Address>> selectAllAddress(HttpServletRequest request){
        //获取当前登录用户的id
        Integer loginUser = BaseContext.getCurrentId();
        List<Address> addresses = addressService.selectAllAddress(loginUser);
        return R.success(addresses);
    }


    /**
     * 修改地址信息
     * @param address
     * @return
     */
    @PutMapping("/updateAddress")
    public R<String> updateAddress(@RequestBody Address address){
        addressService.updateAddress(address);
        return R.success("地址修改成功");
    }

    /**
     * 设置为默认地址
     * @param address
     * @return
     */
    @PostMapping("/defaultAddress")
    public R<String> defaultAddress(@RequestBody Address address){
        //设置为默认地址
        addressService.isDefaultAddress(address);
        return R.success("设置成功");
    }
}
