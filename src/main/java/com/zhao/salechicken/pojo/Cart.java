package com.zhao.salechicken.pojo;


import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName cart
 */
@Data
public class Cart implements Serializable {
    /**
     * 购物车id
     */
    private Integer cartId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 购物车商品数量合计
     */
    private Integer num;

    /**
     * 购物车商品价格合计
     */
    private Double allprice;

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
        Cart other = (Cart) that;
        return (this.getCartId() == null ? other.getCartId() == null : this.getCartId().equals(other.getCartId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getNum() == null ? other.getNum() == null : this.getNum().equals(other.getNum())
            && (this.getAllprice() == null ? other.getAllprice() == null : this.getAllprice().equals(other.getAllprice())));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCartId() == null) ? 0 : getCartId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getNum() == null) ? 0 : getNum().hashCode());
        result = prime * result + ((getAllprice() == null) ? 0 : getAllprice().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", cartId=").append(cartId);
        sb.append(", userId=").append(userId);
        sb.append(", num=").append(num);
        sb.append(", allprice=").append(allprice);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}