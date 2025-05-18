package org.example.springbootbank.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springbootbank.dtos.*;
import org.example.springbootbank.exceptions.BalanceNotSufficientException;
import org.example.springbootbank.exceptions.BankAccountNotFoundException;
import org.example.springbootbank.services.BankAccountServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class BankAccountRestController {
    BankAccountServiceImpl bankAccountService;

    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }
    @GetMapping("/accounts")
    public List<BankAccountDTO> bankAccounts() {
        return bankAccountService.bankAccountList();
    }
    @GetMapping("/accounts/{accountId}/history")
    public List<AccountOperationDTO> getAccountHistory(@PathVariable String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(accountId);
    }

    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(
            @PathVariable String accountId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) throws BankAccountNotFoundException {
        log.info("Retrieving paginated operation history for account: " + accountId);
        return bankAccountService.getAccountHistory(accountId, page, size);
    }
    @PostMapping("/accounts/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        log.info("Debiting account: " + debitDTO.getAccountId());
        this.bankAccountService.debit(debitDTO.getAccountId(), debitDTO.getAmount(), debitDTO.getDescription());
        return debitDTO;
    }

    @PostMapping("/accounts/credit")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundException {
        log.info("Crediting account: " + creditDTO.getAccountId());
        this.bankAccountService.credit(creditDTO.getAccountId(), creditDTO.getAmount(), creditDTO.getDescription());
        return creditDTO;
    }

    @PostMapping("/accounts/transfer")
    public void transfer(@RequestBody TransferDTO transferDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        log.info("Transferring from account: " + transferDTO.getAccountSource() + " to account: " + transferDTO.getAccountDestination());
        this.bankAccountService.transfer(
                transferDTO.getAccountSource(),
                transferDTO.getAccountDestination(),
                transferDTO.getAmount());
    }

}
