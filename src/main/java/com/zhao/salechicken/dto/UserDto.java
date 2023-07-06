package com.zhao.salechicken.dto;

import com.zhao.salechicken.pojo.Address;
import com.zhao.salechicken.pojo.Permission;
import com.zhao.salechicken.pojo.User;
import lombok.Data;

import java.util.List;

@Data
public class UserDto extends User {

    /**
     * 地址集合
     */
    private List<Address> addressList;

    /**
     * 权限集合
     */
    private List<Integer> permissionIds;
}
