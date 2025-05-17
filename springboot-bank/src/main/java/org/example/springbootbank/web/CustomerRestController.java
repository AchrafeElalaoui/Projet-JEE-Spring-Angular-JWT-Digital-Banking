package org.example.springbootbank.web;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springbootbank.dtos.CustomerDTO;
import org.example.springbootbank.services.BankAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class CustomerRestController {
    private BankAccountService bankAccountService;

    @GetMapping("/customers")
    public List<CustomerDTO> customers() {
        log.info("Retrieving all customers");
        return bankAccountService.listCustomers();
    }
}