package com.zhao.salechicken.dto;


import com.zhao.salechicken.pojo.Product;
import lombok.Data;
import org.springframework.stereotype.Repository;

@Data
@Repository
public class ProductDto extends Product {

    /**
     * 种类名称
     */
    private String categoryName;
}
