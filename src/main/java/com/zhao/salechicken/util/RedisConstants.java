package com.zhao.salechicken.util;

public class RedisConstants {
    //登录
    public static final String LOGIN_CODE_KEY = "login:code:";
    public static final Long LOGIN_CODE_TTL = 2L;
    public static final String LOGIN_USER_KEY = "login:token:";
    public static final Long LOGIN_USER_TTL = 30L;

    public static final Long CACHE_NULL_TTL = 2L;

    //商品
    public static final Long CACHE_SHOP_TTL = 30L;
    public static final String CACHE_SHOP_KEY = "cache:shop:";

    //产品信息
    public static final Long CACHE_SHOPINFO_TTL = 30L;
    public static final String CACHE_SHOPINFO_KEY = "cache:shopinfo:";

    //产品种类
    public static final String CACHE_CATEGORY_KEY = "cache:category";
    public static final Long CACHE_CATEGORY_TTL = 10L;

    public static final String LOCK_SHOP_KEY = "lock:shop:";
    public static final Long LOCK_SHOP_TTL = 10L;

    //订单提交分布式锁前缀
    public static final String LOCK_ORDER_KEY = "lock:order:";

    //产品库存扣减分布式锁前缀
//    public static final String LOCK_ORDER_DEDUCT_KEY = "lock:product:deductions:";

    public static final String SECKILL_STOCK_KEY = "seckill:stock:";
    public static final String BLOG_LIKED_KEY = "blog:liked:";
    public static final String FEED_KEY = "feed:";
    public static final String SHOP_GEO_KEY = "shop:geo:";
    public static final String USER_SIGN_KEY = "sign:";
}
