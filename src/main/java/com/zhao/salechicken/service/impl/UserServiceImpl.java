package com.zhao.salechicken.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.RandomUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhao.salechicken.common.R;
import com.zhao.salechicken.dto.LoginFormDTO;
import com.zhao.salechicken.dto.UserDto;
import com.zhao.salechicken.mapper.UserMapper;
import com.zhao.salechicken.pojo.Address;
import com.zhao.salechicken.pojo.User;
import com.zhao.salechicken.service.AddressService;
import com.zhao.salechicken.service.PermissiondetailService;
import com.zhao.salechicken.service.UserService;
import com.zhao.salechicken.util.RegexUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.zhao.salechicken.util.RedisConstants.*;

/**
 * @author 86180
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2023-03-13 22:06:28
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PermissiondetailService permissiondetailService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public R<String> sendCode(String phone, HttpSession session) {
        //1、校验手机号是否正确
        if (RegexUtils.isPhoneInvalid(phone)) {
            return R.error("号码格式有误");
        }
        //2、生成随机验证码
        String code = RandomUtil.randomNumbers(6);

        //3、保存到redis,以phone作为key
        stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY + phone, code, LOGIN_CODE_TTL, TimeUnit.MINUTES);

        //4、发送验证码（模拟）
        log.debug("发送验证码成功，验证码为:{}", code);

        return R.success("发送成功");
    }

    @Override
    public R loginByCode(LoginFormDTO loginUserDTO) {
        //1、校验手机号
        String phone = loginUserDTO.getPhone();
        if (RegexUtils.isPhoneInvalid(phone)) {
            return R.error("号码格式有误");
        }

        //2、查找用户
        User user = userMapper.getUserByPhone(phone);

        //3、判断用户是否存在，如果不存在则返回失败结果
        if (user == null) {
            return R.error("登录失败,用户不存在！！！");
        }

        //4、判断验证码是否正确
        String code = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY + phone);
        log.info("code:" + code);
        if (code == null || !code.equals(loginUserDTO.getCode())) {
            return R.error("验证码有误!!!");
        }


        //5、保存用户信息到redis中
        //5.1 生成随机token
//        String token = UUID.randomUUID().toString(true);//true表示是否不带中划线（不带）
        String token = "1";//由于不会修改前端代码，无法将token保存在前端，所以将token写死
        //5.2 将userId存储在redis
        stringRedisTemplate.opsForValue().set(LOGIN_USER_KEY + token, user.getUserId().toString());
        //5.3 设置token有效期
        stringRedisTemplate.expire(LOGIN_USER_KEY + token, LOGIN_USER_TTL, TimeUnit.MINUTES);

        //6、返回token给前端
        return R.success(user);
    }

    @Override
    public User getUserByPhone(String phone) {
        return userMapper.getUserByPhone(phone);
    }

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
            List<Integer> permissionIds = permissiondetailService.selectProcessPermission(item.getUserId());
            userDto.setPermissionIds(permissionIds);

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
        BeanUtils.copyProperties(user, userDto);

        List<Address> addresses = addressService.selectAllAddress(userId);

        userDto.setAddressList(addresses);

        return userDto;
    }
}




