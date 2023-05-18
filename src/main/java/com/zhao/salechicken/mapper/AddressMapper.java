package com.zhao.salechicken.mapper;

import com.zhao.salechicken.pojo.Address;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
* @author 86180
* @description 针对表【address】的数据库操作Mapper
* @createDate 2023-04-08 15:12:11
* @Entity com.zhao.salechicken.pojo.Address
*/
@Mapper
public interface AddressMapper{
    /**
     * 新增地址
     * @param address
     */
    void addAddress(@Param("address") Address address);

    /**
     * 删除地址
     * @param addressId
     */
    void deleteAddress(@Param("addressId") Integer addressId);

    /**
     * 查看所有地址
     * @param userId
     * @return
     */
    List<Address> selectAllAddress(@Param("userId") Integer userId);

    /**
     * 更新地址
     * @param address
     */
    void updateAddress(@Param("address") Address address);

    /**
     * 取消默认地址
     * @param address
     */
    void isDefaultAddress(@Param("address") Address address);
}




