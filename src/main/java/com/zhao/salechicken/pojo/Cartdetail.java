package com.zhao.salechicken.pojo;


import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName cartdetial
 */
@Data
public class Cartdetail implements Serializable {
    /**
     * 购物车详情id
     */
    private Integer cartdetailId;

    /**
     * 产品id
     */
    private Integer productId;

    /**
     * 产品数量
     */
    private Integer quantity;

    /**
     * 总价
     */
    private Double allprice;

    /**
     * 购物车id
     */
    private Integer cartId;

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
        Cartdetail other = (Cartdetail) that;
        return (this.getCartdetailId() == null ? other.getCartdetailId() == null : this.getCartdetailId().equals(other.getCartdetailId()))
            && (this.getProductId() == null ? other.getProductId() == null : this.getProductId().equals(other.getProductId()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getAllprice() == null ? other.getAllprice() == null : this.getAllprice().equals(other.getAllprice())
            && (this.getCartId() == null ? other.getCartId() == null : this.getCartId().equals(other.getCartId())));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCartdetailId() == null) ? 0 : getCartdetailId().hashCode());
        result = prime * result + ((getProductId() == null) ? 0 : getProductId().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getAllprice() == null) ? 0 : getAllprice().hashCode());
        result = prime * result + ((getCartId() == null) ? 0 : getCartId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", cartdetailId=").append(cartdetailId);
        sb.append(", productId=").append(productId);
        sb.append(", quantity=").append(quantity);
        sb.append(", allprice=").append(allprice);
        sb.append(", cartId=").append(cartId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}