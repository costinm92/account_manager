package com.account.manager.controller;

import com.account.manager.dto.AccountInDto;
import com.account.manager.dto.AccountOutDto;
import com.account.manager.dto.mapper.AccountMapper;
import com.account.manager.exception.NotFoundException;
import com.account.manager.model.Account;
import com.account.manager.service.AccountService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("accounts")
public class AccountController {
    private final AccountService accountService;
    private final AccountMapper accountMapper;

    public AccountController(final AccountService accountService,
                             final AccountMapper accountMapper) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
    }

    @ApiOperation(value = "Find account by id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<AccountOutDto> findById(@PathVariable Long id) {
        try {
            Account account = accountService.findById(id);
            return ResponseEntity.ok(accountMapper.toOutDto(account));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value = "Create a new account")
    @PostMapping
    public ResponseEntity<AccountOutDto> createAccount(@Validated @RequestBody AccountInDto accountInDto) {
        try {
            Account account = accountMapper.fromInDto(accountInDto);
            Account createdAccount = accountService.createAccount(account);
            return ResponseEntity.ok(accountMapper.toOutDto(createdAccount));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @ApiOperation(value = "Update an existing account")
    @PutMapping(value = "/{id}")
    public ResponseEntity<AccountOutDto> updateAccount(@PathVariable Long id,
                                                       @Validated @RequestBody AccountInDto accountInDto) {
        try {
            Account account = accountMapper.fromInDto(accountInDto);
            Account updatedAccount = accountService.updateAccount(id, account);
            return ResponseEntity.ok(accountMapper.toOutDto(updatedAccount));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value = "Patch an existing account")
    @PatchMapping("/{id}")
    public ResponseEntity<AccountOutDto> patchAccount(@PathVariable("id") Long id,
                                                      @RequestBody AccountInDto accountInDto) {
        try {
            Account updatedAccount = accountService.updateAccountName(id, accountInDto.getName());
            return ResponseEntity.ok(accountMapper.toOutDto(updatedAccount));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value = "Delete an existing account")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        try {
            accountService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
