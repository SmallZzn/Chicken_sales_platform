package com.zhao.salechicken.dto;

import com.zhao.salechicken.pojo.Orderdetail;
import lombok.Data;
import org.springframework.stereotype.Repository;

@Data
@Repository
public class OrderdetailDto extends Orderdetail{
    private String productName;
}
