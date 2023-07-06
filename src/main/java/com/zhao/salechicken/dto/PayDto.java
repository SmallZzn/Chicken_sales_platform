package com.zhao.salechicken.dto;


import lombok.Data;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Data
@Repository
public class PayDto implements Serializable {

    /**
     * 订单详情Id
     */
    private List<Integer> cartdetailIds;

    /**
     * 地址Id
     */
    private Integer addressId;
}
