package com.account.manager.service;

import com.account.manager.model.Account;

public interface AccountService {

    Account findById(Long id);

    Account createAccount(Account account);

    Account updateAccount(Long id, Account account);

    Account updateAccountName(Long id, String name);

    void deleteById(Long id);
}
