# Digital Banking Backend Application

This is a Spring Boot backend application for a digital banking system that provides RESTful APIs for managing bank accounts, customers, and banking operations.

## Project Structure

The project follows a layered architecture with the following main components:

### 1. Entities
The core domain models representing the banking system's data structure:

```java
@Entity
@Table(name = "customers")
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    // ... other fields and relationships
}

@Entity
@Table(name = "bank_accounts")
public class BankAccount {
    @Id
    private String id;
    private double balance;
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @ManyToOne
    private Customer customer;
    // ... other fields and relationships
}
```

### 2. DTOs (Data Transfer Objects)
Objects used to transfer data between layers:

```java
public class CustomerDTO {
    private Long id;
    private String name;
    private String email;
    private List<BankAccountDTO> bankAccounts;
    // ... getters, setters, and constructors
}

public class BankAccountDTO {
    private String id;
    private double balance;
    private String type;
    private String customerName;
    // ... getters, setters, and constructors
}
```

### 3. Mappers
Convert between entities and DTOs:

```java
@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDTO toDTO(Customer customer);
    Customer toEntity(CustomerDTO customerDTO);
    List<CustomerDTO> toDTOList(List<Customer> customers);
}

@Mapper(componentModel = "spring")
public interface BankAccountMapper {
    BankAccountDTO toDTO(BankAccount bankAccount);
    BankAccount toEntity(BankAccountDTO bankAccountDTO);
    List<BankAccountDTO> toDTOList(List<BankAccount> bankAccounts);
}
```

### 4. Controllers
REST endpoints for the banking operations:

```java
@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {
    @Autowired
    private CustomerService customerService;
    
    @GetMapping
    public List<CustomerDTO> customers() {
        return customerService.listCustomers();
    }
    
    @GetMapping("/{id}")
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) {
        return customerService.getCustomer(customerId);
    }
    
    @PostMapping
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.saveCustomer(customerDTO);
    }
    // ... other endpoints
}

@RestController
@RequestMapping("/api/accounts")
public class BankAccountRestController {
    @Autowired
    private BankAccountService bankAccountService;
    
    @GetMapping("/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) {
        return bankAccountService.getBankAccount(accountId);
    }
    
    @PostMapping("/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) {
        return bankAccountService.debit(debitDTO);
    }
    // ... other endpoints
}
```

### 5. Services
Business logic layer:

```java
@Service
@Transactional
public class BankAccountServiceImpl implements BankAccountService {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private CustomerRepository customerRepository;
    
    @Override
    public BankAccountDTO getBankAccount(String accountId) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
            .orElseThrow(() -> new RuntimeException("Bank Account not found"));
        return bankAccountMapper.toDTO(bankAccount);
    }
    
    @Override
    public DebitDTO debit(DebitDTO debitDTO) {
        // Implementation of debit operation
        // ... business logic
    }
    // ... other service methods
}
```

### 6. Repositories
Data access layer:

```java
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByNameContains(String keyword);
}

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
    List<BankAccount> findByCustomerId(Long customerId);
}
```

## Security
The application uses Spring Security with JWT authentication:

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/api/auth/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // ... JWT configuration
        return http.build();
    }
}
```

## Technologies Used
- Java 17
- Spring Boot 3.x
- Spring Data JPA
- Spring Security with JWT
- MySQL Database
- MapStruct for DTO mapping
- Lombok for reducing boilerplate code
- Maven for dependency management

## Getting Started

1. Clone the repository
2. Configure your database connection in `application.properties`
3. Run the application using Maven:
```bash
mvn spring-boot:run
```

## API Documentation
The API documentation is available at `/swagger-ui.html` when running the application.

## Features
- Customer management
- Bank account management (Current and Savings accounts)
- Account operations (debit, credit, transfer)
- JWT-based authentication
- Role-based authorization
- Account history tracking
