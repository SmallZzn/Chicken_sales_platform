package com.zhao.salechicken.service.impl;


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
        addressMapper.addAddress(address);
    }

    @Override
    public void deleteAddress(Integer addressId) {
        addressMapper.deleteAddress(addressId);
    }

    @Override
    public List<Address> selectAllAddress(Integer userId) {
//        //1、开启分页功能
//        PageHelper.startPage(page,pageSize);
//
//        //2、根据orderId查询订单详情
//        List<Address> addresses = addressMapper.selectAllAddress(userId);
//
//        //3、设置分页插件
//        PageInfo<Address> addressPageInfo = new PageInfo<>(addresses);
//
//        //4、设置要返回的分页插件
//        PageInfo<AddressDto> addressDtoPageInfo = new PageInfo<>();
//
//        //5、赋值信息到要返回的分页插件
//        BeanUtils.copyProperties(addressPageInfo,addressDtoPageInfo);
//
//        //6、创建集合addressDtos,并为其属性赋值
//        List<AddressDto> addressDtos = addresses.stream().map((item) -> {
//            AddressDto addressDto = new AddressDto();
//
//            //复制item到addressDto
//            BeanUtils.copyProperties(item, addressDto);
//
//            //为addressDto的userName赋值
//            String userName = userService.getUserNameById(userId);
//            addressDto.setUserName(userName);
//
//            return addressDto;
//        }).collect(Collectors.toList());
//
//        //7、为addressDtoPageInfo赋值
//        addressDtoPageInfo.setList(addressDtos);

        return addressMapper.selectAllAddress(userId);
    }

    @Override
    public void updateAddress(Address address){
        addressMapper.updateAddress(address);
    }

    @Override
    @Transactional
    public void isDefaultAddress(Address address) {

        //取消原来的默认地址
        addressMapper.isDefaultAddress(address);

        //修改默认地址
        addressMapper.updateAddress(address);
    }
}




