package com.zhao.salechicken.service.impl;


import com.zhao.salechicken.common.BaseContext;
import com.zhao.salechicken.mapper.AddressMapper;
import com.zhao.salechicken.pojo.Address;
import com.zhao.salechicken.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author 86180
* @description 针对表【address】的数据库操作Service实现
* @createDate 2023-04-08 15:12:11
*/
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public void addAddress(Address address) {
        //获取当前登录用户的id
        Integer loginUser = BaseContext.getCurrentId();

        //设置当前登录用户的id
        address.setUserId(loginUser);

        //默认不是默认地址
        address.setIsdefault(0);

        addressMapper.addAddress(address);
    }

    @Override
    public void deleteAddress(Integer addressId) {
        addressMapper.deleteAddress(addressId);
    }

    @Override
    public List<Address> selectAllAddress(Integer userId) {
        return addressMapper.selectAllAddress(userId);
    }

    @Override
    public void updateAddress(Address address){
        addressMapper.updateAddress(address);
    }

    @Override
    @Transactional
    public void isDefaultAddress(Address address) {
        //设置为默认地址
        address.setIsdefault(1);

        //取消原来的默认地址
        addressMapper.isDefaultAddress(address);

        //修改默认地址
        addressMapper.updateAddress(address);
    }
}




