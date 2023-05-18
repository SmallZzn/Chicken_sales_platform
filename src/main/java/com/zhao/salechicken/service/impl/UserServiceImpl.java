package com.zhao.salechicken.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhao.salechicken.dto.UserDto;
import com.zhao.salechicken.mapper.UserMapper;
import com.zhao.salechicken.pojo.Address;
import com.zhao.salechicken.pojo.Permission;
import com.zhao.salechicken.pojo.Permissiondetail;
import com.zhao.salechicken.pojo.User;
import com.zhao.salechicken.service.AddressService;
import com.zhao.salechicken.service.PermissionService;
import com.zhao.salechicken.service.PermissiondetailService;
import com.zhao.salechicken.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 86180
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2023-03-13 22:06:28
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PermissiondetailService permissiondetailService;

    @Override
    public User getUserById(Integer userId) {
        return userMapper.getUserById(userId);
    }

    @Override
    public User getUserByName(String userName) {
        return userMapper.getUserByName(userName);
    }

    @Override
    public Integer addUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public void deleteUser(Integer userId) {
        userMapper.deleteUser(userId);
    }

    @Override
    public PageInfo selectUser(int page, int pageSize, Integer type, String keywords) {

        //1、开启分页功能
        PageHelper.startPage(page, pageSize);

        //2、查找用户/管理员
        List<User> users = userMapper.selectUser(type, keywords);

        //3、设置分页插件
        PageInfo<User> userPageInfo = new PageInfo<>(users);

        //4、设置要返回的分页插件
        PageInfo<UserDto> userDtoPageInfo = new PageInfo<>();

        //5、赋值信息到要返回的分页插件
        BeanUtils.copyProperties(userPageInfo, userDtoPageInfo);

        //6、创建集合reviewDtoList,并为其属性赋值
        List<UserDto> userDtoList = users.stream().map((item) -> {
            UserDto userDto = new UserDto();

            //复制item到userDto（item是遍历reviews得到的对象）
            BeanUtils.copyProperties(item, userDto);

            //为userDto的addressList赋值
            List<Address> addresses = addressService.selectAllAddress(item.getUserId());
            userDto.setAddressList(addresses);

            //为userDto的permissionList赋值
            List<Permission> permissions = permissiondetailService.selectProcessPermission(item.getUserId());
            userDto.setPermissionList(permissions);

            return userDto;

        }).collect(Collectors.toList());

        //7、为reviewDtoPageInfo赋值
        userDtoPageInfo.setList(userDtoList);
        return userDtoPageInfo;
    }

    @Override
    public String getUserNameById(Integer userId) {
        return userMapper.getUserNameById(userId);
    }

    @Override
    public UserDto selectUserInfo(Integer userId) {
        UserDto userDto = new UserDto();

        User user = userMapper.getUserById(userId);
        BeanUtils.copyProperties(user,userDto);

        List<Address> addresses = addressService.selectAllAddress(userId);

        userDto.setAddressList(addresses);

        return userDto;
    }
}




