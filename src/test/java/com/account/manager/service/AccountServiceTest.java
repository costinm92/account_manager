package com.account.manager.service;

import com.account.manager.exception.NotFoundException;
import com.account.manager.model.Account;
import com.account.manager.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void givenAValidId_whenRetrievingAnAccount_thenItIsSuccessfullyReturned() {
        // given
        Long id = 2L;
        Account accountFromRepository = Account.builder()
                .id(id)
                .build();

        // when
        when(accountRepository.findById(id)).thenReturn(Optional.of(accountFromRepository));

        // then
        Account account = accountService.findById(id);

        assertEquals(accountFromRepository, account);
    }

    @Test
    void givenAnInvalidId_whenRetrievingAnAccount_thenItThrowsNotFoundException() {
        // given
        Long id = 2L;

        // when
        when(accountRepository.findById(id)).thenReturn(Optional.empty());

        // then
        assertThrows(NotFoundException.class, () -> accountService.findById(id));
    }

    @Test
    void givenAValidAccountData_whenCreatingAnAccount_thenItIsSuccessfullyCreatedAndReturned() {
        // given
        Long id = 2L;
        String name = "name";
        Account accountPayload = Account.builder()
                .name(name)
                .build();

        // when
        Account createdAccount = Account.builder()
                .id(id)
                .name(name)
                .build();
        when(accountRepository.save(accountPayload)).thenReturn(createdAccount);

        // then
        Account account = accountService.createAccount(accountPayload);

        assertEquals(createdAccount, account);
    }

    @Test
    void givenAnInvalidAccountData_whenCreatingAnAccount_thenItThrowsIllegalArgumentException() {
        // given
        Long id = 2L;
        String name = "name";
        Account accountPayload = Account.builder()
                .id(id)
                .name(name)
                .build();

        // then
        assertThrows(IllegalArgumentException.class, () -> accountService.createAccount(accountPayload));
    }

    @Test
    void givenAValidAccountDataAndId_whenUpdatingTheAccount_thenItIsSuccessfullyUpdatedAndReturned() {
        // given
        Long id = 2L;
        String name = "name";
        Account accountPayload = Account.builder()
                .name(name)
                .build();

        // when
        Account updatedAccount = Account.builder()
                .id(id)
                .name(name)
                .build();
        Account accountFromRepository = Account.builder()
                .id(id)
                .build();
        when(accountRepository.findById(id)).thenReturn(Optional.of(accountFromRepository));
        when(accountRepository.save(updatedAccount)).thenReturn(updatedAccount);

        // then
        Account account = accountService.updateAccount(id, accountPayload);

        assertEquals(updatedAccount, account);
    }

    @Test
    void givenAValidAccountDataAndInvalidId_whenUpdatingTheAccount_thenItThrowsNotFoundException() {
        // given
        Long id = 2L;
        String name = "name";
        Account accountPayload = Account.builder()
                .name(name)
                .build();

        // when
        when(accountRepository.findById(id)).thenReturn(Optional.empty());

        // then
        assertThrows(NotFoundException.class, () -> accountService.updateAccount(id, accountPayload));
    }

    @Test
    void givenAValidId_whenDeletingTheAccount_thenItIsSuccessfullyDeleted() {
        // given
        Long id = 2L;

        // when
        doNothing().when(accountRepository).deleteById(id);

        // then
        accountService.deleteById(id);
        verify(accountRepository).deleteById(id);
    }

    @Test
    void givenAnInvalidId_whenDeletingTheAccount_thenItThrowsIllegalArgumentException() {
        // given
        Long id = 2L;

        // when
        doThrow(IllegalArgumentException.class).when(accountRepository).deleteById(id);

        // then
        assertThrows(IllegalArgumentException.class, () -> accountService.deleteById(id));
    }
}
