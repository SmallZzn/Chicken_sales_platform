package com.zhao.salechicken.service;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.zhao.salechicken.pojo.Address;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 86180
* @description 针对表【address】的数据库操作Service
* @createDate 2023-04-08 15:12:11
*/
@Service
public interface AddressService {
    /**
     * 新增地址
     * @param address
     */
    void addAddress(Address address);

    /**
     * 删除地址
     * @param addressId
     */
    void deleteAddress(Integer addressId);

    /**
     * 查看所有地址
     * @param userId
     * @return
     */
    List<Address> selectAllAddress(Integer userId);

    /**
     * 更新地址
     * @param address
     */
    void updateAddress(Address address);

    /**
     * 取消默认地址
     * @param address
     */
    void isDefaultAddress(Address address);
}
