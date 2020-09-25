package com.account.manager.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AccountOutDto {
    private Long id;
    private String name;
    private String primaryContactName;
    private String emailAddress;
    private AddressOutDto address;
    private Date createdAt;
    private Date updatedAt;
}
