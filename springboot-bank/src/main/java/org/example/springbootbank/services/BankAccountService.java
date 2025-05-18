package org.example.springbootbank.services;



import org.example.springbootbank.dtos.BankAccountDTO;
import org.example.springbootbank.dtos.CurrentBankAccountDTO;
import org.example.springbootbank.dtos.CustomerDTO;
import org.example.springbootbank.dtos.SavingBankAccountDTO;
import org.example.springbootbank.entities.BankAccount;
import org.example.springbootbank.entities.CurrentAccount;
import org.example.springbootbank.entities.Customer;
import org.example.springbootbank.entities.SavingAccount;
import org.example.springbootbank.exceptions.BalanceNotSufficientException;
import org.example.springbootbank.exceptions.BankAccountNotFoundException;
import org.example.springbootbank.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
    List<CustomerDTO> listCustomers();

    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;

    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

    List<BankAccount> bankAccountList();
    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId);
}