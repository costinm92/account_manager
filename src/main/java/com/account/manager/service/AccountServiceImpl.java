package com.account.manager.service;

import com.account.manager.exception.NotFoundException;
import com.account.manager.model.Account;
import com.account.manager.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    public AccountServiceImpl(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account findById(Long id) {
        log.info("Retrieving account with id " + id);
        return accountRepository.findById(id)
                .orElseThrow(() -> logAndBuildAccountNotFoundException(id, null));
    }

    @Override
    public Account createAccount(Account account) {
        log.info("Creating account with " + account);
        if (account.getId() != null) {
            log.warn("Cannot create new account. It already has an id");
            throw new IllegalArgumentException("Id should be null.");
        }
        try {
            return accountRepository.save(account);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public Account updateAccount(Long id, Account account) {
        log.info("Updating account with id " + id + " with " + account);
        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() ->
                        logAndBuildAccountNotFoundException(id,
                                "Cannot create update account. It was not found in the database.")
                );

        updateAccountWithNewData(existingAccount, account);
        return accountRepository.save(existingAccount);
    }

    @Override
    public Account updateAccountName(Long id, String name) {
        log.info("Updating account name with id " + id);
        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() ->
                        logAndBuildAccountNotFoundException(id,
                                "Cannot create update account. It was not found in the database.")
                );

        existingAccount.setName(name);
        return accountRepository.save(existingAccount);
    }

    private void updateAccountWithNewData(Account existingAccount, Account account) {
        existingAccount.setName(account.getName());
        existingAccount.setPrimaryContactName(account.getPrimaryContactName());
        existingAccount.setEmailAddress(account.getEmailAddress());
        if (existingAccount.getAddress() != null && account.getAddress() != null) {
            account.getAddress().setId(existingAccount.getAddress().getId());
        }
        existingAccount.setAddress(account.getAddress());
    }

    @Override
    public void deleteById(Long id) {
        log.info("Removing account with id " + id);
        try {
            accountRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw logAndBuildAccountNotFoundException(id, "Cannot delete account. It was not found in the database.");
        }
    }

    private NotFoundException logAndBuildAccountNotFoundException(long id, String logMessage) {
        String errorMessage = String.format("Account with id %s was not found", id);
        log.warn(logMessage != null ? logMessage : errorMessage);
        return new NotFoundException(errorMessage);
    }
}
