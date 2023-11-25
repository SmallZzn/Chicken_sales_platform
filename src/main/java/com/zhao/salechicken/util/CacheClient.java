package com.zhao.salechicken.util;


import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.zhao.salechicken.util.RedisConstants.LOCK_SHOP_KEY;
import static com.zhao.salechicken.util.RedisConstants.LOCK_SHOP_TTL;

/**
 * @Author: 小赵
 * @DateTime: 2023/9/3 10:50
 * Redis工具类
 */
@Slf4j
@Component
public class CacheClient {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    //线程池
    public static final ExecutorService CACHE_REBUILD_EXECUTOR = Executors.newFixedThreadPool(10);

    //方法1:将任意java对象序列化为json并存储在string类型的key中，并且可以设置TTL过期时间
    public void set(String key, Object value, Long time, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value), time, unit);
    }

    //方法2:将任意java对象序列化为son并存储在string类型的key中，并且可以设置逻辑过期时间，用于处理缓存击穿问题
    public void setWithLogicalExpire(String key, Object value, Long time, TimeUnit unit) {
        RedisData redisData = new RedisData();
        redisData.setData(value);
        //需将时间转化成以秒为单位
        redisData.setExpireTime(LocalDateTime.now().plusSeconds(unit.toSeconds(time)));
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(redisData));
    }

    //方法3:根据指定的key查询缓存，并反序列化为指定类型，利用缓存空值的方式解决缓存穿透问题
    public <R, ID> R queryWithPassThrough(String keyPrefix, ID id, Class<R> type, Function<ID, R> dbFallBack, Long time, TimeUnit unit) {
        String key = keyPrefix + id;

        //1、从redis中查询缓存
        String shopJson = stringRedisTemplate.opsForValue().get(key);

        //2、判断是否命中缓存
        if (StrUtil.isNotBlank(shopJson)) {
            //3、命中缓存，返回商铺信息
            R r = JSONUtil.toBean(shopJson, type);
            return r;
        }

        //判断是否命中空值
        if (shopJson != null) {
            return null;
        }

        //4、未命中缓存，查询数据库
        R r = dbFallBack.apply(id);

        //5、商铺不存在，向redis缓存空值（为了解决缓存穿透，不能完全解决）
        if (r == null) {
            stringRedisTemplate.opsForValue().set(key, "", time, unit);
            return null;
        }

        //6、商铺存在，写入redis
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(r), time, unit);

        return r;
    }

    //方法4:根据指定的key查询缓存，并反序列化为指定类型，需要利用逻辑过期解决缓存击穿问题
    public <R, ID> R queryWithLogicalExpire(String keyPrefix, ID id, Class<R> type, Function<ID, R> dbFallBack, Long time, TimeUnit unit) {
        String key = keyPrefix + id;

        //1、从redis中查询缓存
        String json = stringRedisTemplate.opsForValue().get(key);

        //2、判断是否命中缓存
        if (StrUtil.isBlank(json)) {
            //3、未命中缓存，返回null（因为缓存不会消失，且数据在后台添加的时候就会添加缓存，所以一定会命中）
            return null;
        }

        //4、命中缓存
        RedisData redisData = JSONUtil.toBean(json, RedisData.class);
        //由于redisData中的data是Object类型，需进行如下操作
        R r = JSONUtil.toBean((JSONObject) redisData.getData(), type);
        LocalDateTime expireTime = redisData.getExpireTime();
        //4.1 判断缓存是否过期
        if (expireTime.isAfter(LocalDateTime.now())) {
            //4.2 未过期，返回商铺数据
            return r;
        }

        //4.3 过期，缓存重建
        //5、缓存重建
        //5.1 是否获取锁
        String LockKey = LOCK_SHOP_KEY + id;
        boolean isLock = tryLock(LockKey);
        //5.2 获取成功，开启独立线程，更新缓存
        if (isLock) {
            CACHE_REBUILD_EXECUTOR.submit(() -> {
                //查询数据库
                R r1 = dbFallBack.apply(id);
                //写入redis
                setWithLogicalExpire(LockKey, r1, time, unit);
                //释放锁
                unlock(LockKey);
            });
        }
        //5.3 没有获取，返回旧数据
        return r;
    }

    //方法5:查询所有信息（不需要参数），并反序列化为指定类型，利用缓存空值的方式解决缓存穿透问题
    public <R> List<R> queryAllWithPassThrough(String key, Class<R> type, Supplier<List<R>> dbFallBack, Long time, TimeUnit unit) {
        //1、从redis中查询缓存
        String shopJson = stringRedisTemplate.opsForValue().get(key);

        //2、判断是否命中缓存
        if (StrUtil.isNotBlank(shopJson)) {
            //3、命中缓存，返回商铺信息
            List<R> r = JSONUtil.toList(shopJson, type);
            return r;
        }

        //判断是否命中空值
        if (shopJson != null) {
            return null;
        }

        //4、未命中缓存，查询数据库
        List<R> r = dbFallBack.get();

        //5、商铺不存在，向redis缓存空值（为了解决缓存穿透，不能完全解决）
        if (r == null) {
            stringRedisTemplate.opsForValue().set(key, "", time, unit);
            return null;
        }

        //6、商铺存在，写入redis
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(r), time, unit);

        return r;
    }

    //上锁
    private boolean tryLock(String key) {
        Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(key, "1", LOCK_SHOP_TTL, TimeUnit.MINUTES);
        return BooleanUtil.isTrue(flag);
    }

    //释放锁
    private void unlock(String key) {
        stringRedisTemplate.delete(key);
    }
}
