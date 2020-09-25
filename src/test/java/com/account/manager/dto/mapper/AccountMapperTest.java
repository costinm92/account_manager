package com.account.manager.dto.mapper;

import com.account.manager.dto.AccountInDto;
import com.account.manager.dto.AccountOutDto;
import com.account.manager.dto.AddressInDto;
import com.account.manager.model.Account;
import com.account.manager.model.Address;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.Date;

class AccountMapperTest {
    private final AccountMapper mapper = Mappers.getMapper(AccountMapper.class);

    @Test
    void givenAccountInDto_whenMaps_thenCorrect() {
        // given
        AccountInDto accountInDto = AccountInDto.builder()
                .name("name")
                .emailAddress("a@a.a")
                .primaryContactName("test")
                .address(
                        AddressInDto.builder()
                                .street("Iasi")
                                .houseNumber("127")
                                .build()
                )
                .build();

        // when
        Account account = mapper.fromInDto(accountInDto);

        // then
        Assertions.assertEquals(accountInDto.getName(), account.getName());
        Assertions.assertEquals(accountInDto.getEmailAddress(), account.getEmailAddress());
        Assertions.assertEquals(accountInDto.getPrimaryContactName(), account.getPrimaryContactName());
        Assertions.assertEquals(accountInDto.getAddress().getStreet(), account.getAddress().getStreet());
        Assertions.assertEquals(accountInDto.getAddress().getHouseNumber(), account.getAddress().getHouseNumber());
    }

    @Test
    void givenAccount_whenMaps_thenCorrect() {
        // given
        Account account = Account.builder()
                .id(1L)
                .name("name")
                .emailAddress("a@a.a")
                .primaryContactName("test")
                .createdAt(Date.from(Instant.now()))
                .updatedAt(Date.from(Instant.now()))
                .address(
                        Address.builder()
                                .street("Iasi")
                                .houseNumber("127")
                                .build()
                )
                .build();

        // when
        AccountOutDto accountOutDto = mapper.toOutDto(account);

        // then
        Assertions.assertEquals(account.getId(), accountOutDto.getId());
        Assertions.assertEquals(account.getEmailAddress(), accountOutDto.getEmailAddress());
        Assertions.assertEquals(account.getPrimaryContactName(), accountOutDto.getPrimaryContactName());
        Assertions.assertEquals(account.getName(), accountOutDto.getName());
        Assertions.assertEquals(account.getCreatedAt(), accountOutDto.getCreatedAt());
        Assertions.assertEquals(account.getUpdatedAt(), accountOutDto.getUpdatedAt());
        Assertions.assertEquals(account.getAddress().getId(), accountOutDto.getAddress().getId());
        Assertions.assertEquals(account.getAddress().getStreet(), accountOutDto.getAddress().getStreet());
        Assertions.assertEquals(account.getAddress().getHouseNumber(), accountOutDto.getAddress().getHouseNumber());
    }
}
