package com.zhao.salechicken.dto;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.List;

@Data
@Repository
public class PermissionDto {
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 权限id集合
     */
    private List<Integer> permissionIds;
}
