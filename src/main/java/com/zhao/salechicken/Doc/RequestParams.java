package com.zhao.salechicken.Doc;

import lombok.Data;

/**
 * @Author: 小赵
 * @DateTime: 2023/11/17 21:32
 * es优化，用于搜索框搜索
 */
@Data
public class RequestParams {
    /**
     * 关键字
     */
    private String key;

    /**
     * 种类
     */
    private Integer category;

    /**
     * 产地
     */
    private String origin;

    /**
     * 最小价格
     */
    private Integer minPrice;

    /**
     * 最大价格
     */
    private Integer maxPrice;
}
