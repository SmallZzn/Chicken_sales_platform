package com.zhao.salechicken.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName orderdetail
 */
@Data
public class Orderdetail implements Serializable {
    /**
     * 订单细节id
     */
    private Integer orderdetialId;

    /**
     * 产品数量
     */
    private Integer quantity;

    /**
     * 总价格
     */
    private Double totalMoney;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 商品id
     */
    private Integer productId;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Orderdetail other = (Orderdetail) that;
        return (this.getOrderdetialId() == null ? other.getOrderdetialId() == null : this.getOrderdetialId().equals(other.getOrderdetialId()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getTotalMoney() == null ? other.getTotalMoney() == null : this.getTotalMoney().equals(other.getTotalMoney()))
            && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId())
            && (this.getProductId() == null ? other.getProductId() == null : this.getProductId().equals(other.getProductId()))
        );
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOrderdetialId() == null) ? 0 : getOrderdetialId().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getTotalMoney() == null) ? 0 : getTotalMoney().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getProductId() == null) ? 0 : getProductId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", orderdetialId=").append(orderdetialId);
        sb.append(", quantity=").append(quantity);
        sb.append(", totalMoney=").append(totalMoney);
        sb.append(", orderId=").append(orderId);
        sb.append(", productId=").append(productId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}