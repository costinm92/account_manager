package com.account.manager.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class AddressInDto {

    @NotEmpty
    private String street;

    @NotEmpty
    private String houseNumber;
}
