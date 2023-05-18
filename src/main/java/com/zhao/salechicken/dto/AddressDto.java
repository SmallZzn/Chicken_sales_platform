package com.zhao.salechicken.dto;

import com.zhao.salechicken.pojo.Address;
import lombok.Data;
import org.springframework.stereotype.Repository;

@Data
@Repository
public class AddressDto extends Address {
    private String userName;
}
