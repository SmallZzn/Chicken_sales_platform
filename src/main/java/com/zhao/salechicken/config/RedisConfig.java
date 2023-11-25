package com.zhao.salechicken.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 小赵
 * @DateTime: 2023/9/4 21:41
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedissonClient redissonClient() {
        //配置类
        Config config = new Config();
        //添加reds地址，这里添加了单点的地址，也可以使用config.useClusterServers()添加集群地址
        config.useSingleServer().setAddress("redis://192.168.200.131:6380").setPassword("zzn199636520");
        //创建客户端
        return Redisson.create(config);
    }
}
