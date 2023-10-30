package com.zhao.salechicken.pojo;


import lombok.Data;

import java.io.Serializable;

/**
 * @TableName address
 */
@Data
public class Address implements Serializable {
    /**
     * 地址id9
     */
    private Integer addressId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 收货人姓名
     */
    private String receiver;

    /**
     * 性别(1代表男，0代表女)
     */
    private Integer sex;

    /**
     * 电话
     */
    private String phone;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String region;

    /**
     * 详细地址
     */
    private String detail;

    /**
     * 默认地址（1表示默认）
     */
    private Integer isdefault;

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
        Address other = (Address) that;
        return (this.getAddressId() == null ? other.getAddressId() == null : this.getAddressId().equals(other.getAddressId()))
                && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
                && (this.getReceiver() == null ? other.getReceiver() == null : this.getReceiver().equals(other.getReceiver()))
                && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
                && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
                && (this.getProvince() == null ? other.getProvince() == null : this.getProvince().equals(other.getProvince()))
                && (this.getCity() == null ? other.getCity() == null : this.getCity().equals(other.getCity()))
                && (this.getRegion() == null ? other.getRegion() == null : this.getRegion().equals(other.getRegion()))
                && (this.getDetail() == null ? other.getDetail() == null : this.getDetail().equals(other.getDetail()))
                && (this.getIsdefault() == null ? other.getIsdefault() == null : this.getIsdefault().equals(other.getIsdefault())
        );
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAddressId() == null) ? 0 : getAddressId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getReceiver() == null) ? 0 : getReceiver().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getProvince() == null) ? 0 : getProvince().hashCode());
        result = prime * result + ((getCity() == null) ? 0 : getCity().hashCode());
        result = prime * result + ((getRegion() == null) ? 0 : getRegion().hashCode());
        result = prime * result + ((getDetail() == null) ? 0 : getDetail().hashCode());
        result = prime * result + ((getIsdefault() == null) ? 0 : getIsdefault().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", addressId=").append(addressId);
        sb.append(", userId=").append(userId);
        sb.append(", receiver=").append(receiver);
        sb.append(", sex=").append(sex);
        sb.append(", phone=").append(phone);
        sb.append(", province=").append(province);
        sb.append(", city=").append(city);
        sb.append(", region=").append(region);
        sb.append(", detail=").append(detail);
        sb.append(", isdefault=").append(isdefault);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}