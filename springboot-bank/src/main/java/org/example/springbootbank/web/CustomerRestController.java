package org.example.springbootbank.web;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springbootbank.dtos.CustomerDTO;
import org.example.springbootbank.exceptions.CustomerNotFoundException;
import org.example.springbootbank.services.BankAccountService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CustomerRestController {
    private BankAccountService bankAccountService;
    @PostAuthorize("hasAuthority('SCOPE_USER')")
    @GetMapping("/customers")
    public List<CustomerDTO> customers() {
        log.info("Retrieving all customers");
        return bankAccountService.listCustomers();
    }
    @PostAuthorize("hasAuthority('SCOPE_USER')")
    @GetMapping("/customers/{customerId}")
    public CustomerDTO customer(@PathVariable Long customerId) throws CustomerNotFoundException {
        log.info("Retrieving the customer with id {}", customerId);
        return bankAccountService.getCustomer(customerId);
    }
    @PostAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping("/customers")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        log.info("Saving new customer");
        return bankAccountService.saveCustomer(customerDTO);
    }
    @PostAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping("/customers/{customerId}")
    public CustomerDTO updateCustomer(@PathVariable Long customerId,@RequestBody CustomerDTO customerDTO) {
        customerDTO.setId(customerId);
        return bankAccountService.updateCustomer(customerDTO);
    }
    @PostAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        bankAccountService.deleteCustomer(id);
    }
    @PostAuthorize("hasAuthority('SCOPE_USER')")
    @GetMapping("/customers/search")
    public List<CustomerDTO> searchCustomers(@RequestParam(defaultValue = "") String keyword) {
        log.info("Searching customers with keyword: " + keyword);
        return bankAccountService.searchCustomers(keyword);
    }
}