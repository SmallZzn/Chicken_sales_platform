package com.zhao.salechicken.Doc;

import com.zhao.salechicken.pojo.Product;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: 小赵
 * @DateTime: 2023/11/25 15:49
 */
@Data
@Repository
@NoArgsConstructor
public class ProductDoc {
    private Integer productId;
    private String productName;
    private String description;
    private Double price;
    private Double weight;
    private String origin;
    private Integer category;
    private Integer inventory;
    private String image;
    private Integer sales;
    //用于自动补全
    private String suggestion;

    public ProductDoc(Product product) {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.weight = product.getWeight();
        this.origin = product.getOrigin();
        this.category = product.getCategory();
        this.inventory = product.getInventory();
        this.image = product.getImage();
        this.sales = product.getSales();
        this.suggestion = product.getProductName();
    }
}
