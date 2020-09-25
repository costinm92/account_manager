package com.account.manager.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class AccountInDto {

    @NotEmpty
    private String name;

    @NotEmpty
    private String primaryContactName;

    @Email(message = "Email should be valid")
    private String emailAddress;

    @NotNull
    private AddressInDto address;
}
