package com.account.manager.controller;

import com.account.manager.dto.AccountInDto;
import com.account.manager.dto.AccountOutDto;
import com.account.manager.dto.AddressInDto;
import com.account.manager.model.Account;
import com.account.manager.model.Address;
import com.account.manager.repository.AccountRepository;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;

@Component
public class AccountTestUtil {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    public void setup() {
        HttpClient client = HttpClients.createDefault();
        testRestTemplate.getRestTemplate()
                .setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
    }

    public void cleanUp() {
        accountRepository.deleteAll();
    }

    public Account createAccount() {
        Account account = Account.builder()
                .name("test")
                .address(
                        Address.builder()
                                .street("Iasi")
                                .houseNumber("127")
                                .build()
                )
                .build();
        return accountRepository.save(account);
    }

    public AccountInDto buildCompleteAccountInDto() {
        return AccountInDto.builder()
                .name("test")
                .address(
                        AddressInDto.builder()
                                .street("Iasi")
                                .houseNumber("127")
                                .build()
                )
                .build();
    }

    public AccountInDto buildIncompleteAccountInDto() {
        return AccountInDto.builder()
                .name("name")
                .build();
    }

    public ResponseEntity<AccountOutDto> getAccountRequest(long accountId) {
        RequestEntity<AccountOutDto> request = new RequestEntity<>(HttpMethod.GET, URI.create("/accounts/" + accountId));
        return testRestTemplate.exchange(request, AccountOutDto.class);
    }

    public ResponseEntity<AccountOutDto> postAccountRequest(AccountInDto account) {
        RequestEntity<AccountInDto> request = new RequestEntity<>(account, HttpMethod.POST, URI.create("/accounts"));
        return testRestTemplate.exchange(request, AccountOutDto.class);
    }

    public ResponseEntity<AccountOutDto> putAccountRequest(long accountId, AccountInDto account) {
        RequestEntity<AccountInDto> request = new RequestEntity<>(account, HttpMethod.PUT, URI.create("/accounts/" + accountId));
        return testRestTemplate.exchange(request, AccountOutDto.class);
    }

    public ResponseEntity<AccountOutDto> patchAccountRequest(long accountId, JSONObject body) {
        return testRestTemplate.getRestTemplate().exchange("/accounts/" + accountId, HttpMethod.PATCH, getPatchRequestHeaders(body.toString()), AccountOutDto.class);
    }

    public HttpEntity<String> getPatchRequestHeaders(String jsonPostBody) {
        List<MediaType> acceptTypes = List.of(MediaType.APPLICATION_JSON);

        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.setContentType(MediaType.APPLICATION_JSON);
        reqHeaders.setAccept(acceptTypes);

        return new HttpEntity<>(jsonPostBody, reqHeaders);
    }

    public ResponseEntity<Void> deleteAccountRequest(long accountId) {
        RequestEntity<Void> request = new RequestEntity<>(HttpMethod.DELETE, URI.create("/accounts/" + accountId));
        return testRestTemplate.exchange(request, Void.class);
    }
}
