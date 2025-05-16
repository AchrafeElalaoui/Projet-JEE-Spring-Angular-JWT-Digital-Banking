package org.example.springbootbank;

import org.example.springbootbank.entities.Customer;
import org.example.springbootbank.repository.AccountOperationRepository;
import org.example.springbootbank.repository.BankAccountRepository;
import org.example.springbootbank.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
			Stream.of("Hassan", "Yassine", "Omar").forEach(name -> {
				Customer customer = new Customer();
				customer.setId(name);
				customer.setName(name);
				customer.setEmail(name + "@gmail.com");
				customerRepository.save(customer);
			});
		};
	}
}
