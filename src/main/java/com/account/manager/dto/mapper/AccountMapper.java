package com.account.manager.dto.mapper;

import com.account.manager.dto.AccountInDto;
import com.account.manager.dto.AccountOutDto;
import com.account.manager.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target="id", source="entity.id")
    @Mapping(target="name", source="entity.name")
    @Mapping(target="primaryContactName", source="entity.primaryContactName")
    @Mapping(target="emailAddress", source="entity.emailAddress")
    @Mapping(target="createdAt", source="entity.createdAt")
    @Mapping(target="updatedAt", source="entity.updatedAt")
    @Mapping(target="address.id", source="entity.address.id")
    @Mapping(target="address.street", source="entity.address.street")
    @Mapping(target="address.houseNumber", source="entity.address.houseNumber")
    AccountOutDto toOutDto(Account entity);

    @Mapping(target="name", source="dto.name")
    @Mapping(target="primaryContactName", source="dto.primaryContactName")
    @Mapping(target="emailAddress", source="dto.emailAddress")
    @Mapping(target="address.street", source="dto.address.street")
    @Mapping(target="address.houseNumber", source="dto.address.houseNumber")
    Account fromInDto(AccountInDto dto);
}
