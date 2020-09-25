package com.account.manager.controller;

import com.account.manager.dto.AccountInDto;
import com.account.manager.dto.AccountOutDto;
import com.account.manager.dto.mapper.AccountMapper;
import com.account.manager.model.Account;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = {"classpath:application.properties"})

class AccountControllerTest {

    @Autowired
    private AccountTestUtil accountTestUtil;

    @Autowired
    private AccountMapper accountMapper;

    @AfterEach
    void cleanUpEach() {
        accountTestUtil.cleanUp();
    }

    @Test
    void givenAValidId_whenRetrievingAnAccount_thenItReturnsOk() {
        // given
        Account account = accountTestUtil.createAccount();
        Long accountId = account.getId();

        // when
        ResponseEntity<AccountOutDto> response = accountTestUtil.getAccountRequest(accountId);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accountMapper.toOutDto(account), response.getBody());
    }

    @Test
    void givenAnInvalidId_whenRetrievingAnAccount_thenItReturnsNotFound() {
        // given
        long accountId = 2L;

        // when
        ResponseEntity<AccountOutDto> response = accountTestUtil.getAccountRequest(accountId);

        // then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void givenAValidAccountData_whenCreatingAnAccount_thenItIsOk() {
        // given
        AccountInDto accountInDto = accountTestUtil.buildCompleteAccountInDto();

        // when
        ResponseEntity<AccountOutDto> response = accountTestUtil.postAccountRequest(accountInDto);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
    }

    @Test
    void givenAnInvalidAccountData_whenCreatingAnAccount_thenItReturnsBadRequest() {
        // given
        AccountInDto accountInDto = accountTestUtil.buildIncompleteAccountInDto();

        // when
        ResponseEntity<AccountOutDto> response = accountTestUtil.postAccountRequest(accountInDto);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void givenAValidAccountDataAndId_whenUpdatingTheAccount_thenItReturnsOk() {
        // given
        Account account = accountTestUtil.createAccount();
        long accountId = account.getId();
        AccountInDto accountInDto = accountTestUtil.buildCompleteAccountInDto();

        // when
        ResponseEntity<AccountOutDto> response = accountTestUtil.putAccountRequest(accountId, accountInDto);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(accountInDto.getName(), response.getBody().getName());
        assertEquals(accountInDto.getEmailAddress(), response.getBody().getEmailAddress());
        assertEquals(accountInDto.getPrimaryContactName(), response.getBody().getPrimaryContactName());
    }

    @Test
    void givenAValidAccountDataAndInvalidId_whenUpdatingTheAccount_thenItReturnsNotFound() {
        // given
        long accountId = 2L;
        AccountInDto accountInDto = accountTestUtil.buildIncompleteAccountInDto();

        // when
        ResponseEntity<AccountOutDto> response = accountTestUtil.putAccountRequest(accountId, accountInDto);

        // then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void givenAValidAccountDataAndId_whenPatchingTheAccount_thenItReturnsOk() throws JSONException {
        // given
        Account account = accountTestUtil.createAccount();
        long accountId = account.getId();
        JSONObject patchBody = new JSONObject();
        patchBody.put("name", "updated");

        // when
        ResponseEntity<AccountOutDto> response = accountTestUtil.patchAccountRequest(accountId, patchBody);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(patchBody.getString("name"), response.getBody().getName());
    }

    @Test
    void givenAValidAccountDataAndInvalidId_whenPatchingTheAccount_thenItReturnsNotFound() throws JSONException {
        // given
        long accountId = 2L;
        JSONObject patchBody = new JSONObject();
        patchBody.put("name", "updated");

        // when
        ResponseEntity<AccountOutDto> response = accountTestUtil.patchAccountRequest(accountId, patchBody);

        // then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void givenAValidId_whenDeletingTheAccount_thenItReturnsOk() {
        // given
        Account account = accountTestUtil.createAccount();
        long accountId = account.getId();

        // when
        ResponseEntity<Void> response = accountTestUtil.deleteAccountRequest(accountId);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void givenAnInvalidId_whenDeletingTheAccount_thenItReturnsNotFound() {
        // given
        long accountId = 2L;

        // when
        ResponseEntity<Void> response = accountTestUtil.deleteAccountRequest(accountId);

        // then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
