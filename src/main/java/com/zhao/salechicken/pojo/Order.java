package com.zhao.salechicken.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName order
 */

@Data
@Repository
public class Order implements Serializable {
    /**
     * 订单id
     */
    private String orderId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 下单日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderdate;

    /**
     * 订单总价
     */
    private Double totalprice;

    /**
     * 订单状态（已下单、已付款、已发货、已完成、已取消等）
     */
    private String status;

    /**
     * 商品总数
     */
    private Integer num;

    /**
     * 地址id
     */
    private Integer addressId;

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
        Order other = (Order) that;
        return (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getOrderdate() == null ? other.getOrderdate() == null : this.getOrderdate().equals(other.getOrderdate()))
            && (this.getTotalprice() == null ? other.getTotalprice() == null : this.getTotalprice().equals(other.getTotalprice()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getNum() == null ? other.getNum() == null : this.getNum().equals(other.getNum()))
            && (this.getAddressId() == null ? other.getAddressId() == null : this.getAddressId().equals(other.getAddressId())
        );
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getOrderdate() == null) ? 0 : getOrderdate().hashCode());
        result = prime * result + ((getTotalprice() == null) ? 0 : getTotalprice().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getNum() == null) ? 0 : getNum().hashCode());
        result = prime * result + ((getAddressId() == null) ? 0 : getAddressId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", orderId=").append(orderId);
        sb.append(", userId=").append(userId);
        sb.append(", orderdate=").append(orderdate);
        sb.append(", totalprice=").append(totalprice);
        sb.append(", status=").append(status);
        sb.append(", num=").append(num);
        sb.append(", address_id=").append(addressId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}