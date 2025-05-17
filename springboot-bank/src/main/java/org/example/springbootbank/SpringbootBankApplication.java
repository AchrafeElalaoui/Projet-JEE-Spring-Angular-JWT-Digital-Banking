package org.example.springbootbank;

import org.example.springbootbank.entities.*;
import org.example.springbootbank.repository.AccountOperationRepository;
import org.example.springbootbank.repository.BankAccountRepository;
import org.example.springbootbank.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class SpringbootBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBankApplication.class, args);
	}
	@Bean
	CommandLineRunner start(CustomerRepository customerRepository,
							BankAccountRepository bankAccountRepository,
							AccountOperationRepository accountOperationRepository) {
		return args -> {
			Stream.of("khadija", "yassir", "oma").forEach(name -> {
				Customer customer = new Customer();
				customer.setName(name);
				customer.setEmail(name + "@gmail.com");
				customerRepository.save(customer);
			});

			customerRepository.findAll().forEach(c -> {
				CurrentAccount currentAccount = new CurrentAccount();
				currentAccount.setId(UUID.randomUUID().toString());
				currentAccount.setBalance(Math.random() * 90000);
				currentAccount.setCreatedAt(new Date());
				currentAccount.setCustomer(c);
				currentAccount.setOverDraft(9000);
				currentAccount.setStatus(AccountStatus.CREATED);
				currentAccount.setCurrency("MAD");
				bankAccountRepository.save(currentAccount);
				SavingAccount savingAccount = new SavingAccount();
				savingAccount.setId(UUID.randomUUID().toString());
				savingAccount.setBalance(Math.random() * 90000);
				savingAccount.setCreatedAt(new Date());
				savingAccount.setCustomer(c);
				savingAccount.setInterestRate(5.5);
				savingAccount.setStatus(AccountStatus.CREATED);
				savingAccount.setCurrency("MAD");
				bankAccountRepository.save(savingAccount);
			});
			bankAccountRepository.findAll().forEach(b -> {
				for (int i = 0; i < 10; i++) {
					AccountOperation accountOperation = new AccountOperation();
					accountOperation.setAmount(230 + Math.random() * 9000);
					accountOperation.setDate(new Date());
					accountOperation.setType(Math.random() > 0.5 ? OperationType.DEBIT : OperationType.CREDIT);
					accountOperation.setBankAccount(b);
					accountOperationRepository.save(accountOperation);
				}
			});
		};
	}
}
