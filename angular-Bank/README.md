# Digital Banking Web Application

A modern web application for digital banking built with Angular and Spring Boot, featuring secure authentication, customer management, and account operations.

## Table of Contents
- [Features](#features)
- [Project Structure](#project-structure)
- [Key Components](#key-components)
- [Authentication & Authorization](#authentication--authorization)
- [Models](#models)
- [Services](#services)
- [Routing](#routing)
- [Getting Started](#getting-started)

## Features
- 🔐 Secure JWT-based authentication
- 👥 Customer management (search, create, delete)
- 💰 Account management (Current and Savings accounts)
- 💳 Banking operations (debit, credit, transfer)
- 👮‍♂️ Role-based access control (ADMIN and USER roles)
- 📱 Responsive design with Bootstrap

## Project Structure
```
src/app/
├── components/
│   ├── accounts/
│   ├── customers/
│   ├── customer-accounts/
│   ├── login/
│   ├── navbar/
│   └── new-customer/
├── models/
├── services/
├── guards/
└── interceptors/
```

## Key Components

### Customer Management
```typescript
// customers.component.ts
export class CustomersComponent implements OnInit {
  customers: any;
  listCustomers$!: Observable<Array<Customer> | null>;
  
  handleSearchCustomers() {
    let keyword = this.searchFormGroup?.value.keyword;
    this.listCustomers$ = this.customerService.searchCustomers(keyword);
  }
  
  handleDeleteCustomer(customer: Customer) {
    this.customerService.deleteCustomer(customer.id).subscribe();
  }
}
```

### Account Operations
```typescript
// accounts.component.ts
export class AccountsComponent implements OnInit {
  handleAccountOperation() {
    // Supports DEBIT, CREDIT, and TRANSFER operations
    if (operationType === 'DEBIT') {
      this.accountService.debit(accountId, amount, description);
    }
    else if (operationType === 'CREDIT') {
      this.accountService.credit(accountId, amount, description);
    }
    else if (operationType === 'TRANSFER') {
      this.accountService.transfer(accountId, accountDestination, amount, description);
    }
  }
}
```

## Authentication & Authorization

### JWT Authentication
```typescript
// auth.service.ts
export class AuthService {
  isAuthenticated: boolean = false;
  roles: any;
  username: any;
  accessToken!: any;

  public login(username: string, password: string) {
    return this.http.post("http://localhost:8080/auth/login", params, options);
  }

  loadProfile(data: any) {
    this.isAuthenticated = true;
    this.accessToken = data['access-token'];
    let decodedJwt: any = jwtDecode(this.accessToken);
    this.username = decodedJwt.sub;
    this.roles = decodedJwt.scope;
  }
}
```

### Route Guards
```typescript
// authentication.guard.ts
export const authenticationGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
) => {
  const authService = inject(AuthService);
  if (authService.isAuthenticated) {
    return true;
  } else {
    router.navigateByUrl('/login');
    return false;
  }
};
```

## Models

### Account Model
```typescript
// account.model.ts
export interface Account {
  type: 'CurrentAccount' | 'SavingAccount';
  id: string;
  balance: number;
  createdDate: string;
  status: string;
  interestRate?: number;  // Only for SavingAccount
  overDraft?: number;     // Only for CurrentAccount
}

export interface AccountDetails {
  accountId: string;
  balance: number;
  currentPage: number;
  totalPages: number;
  pageSize: number;
  accountOperationDTOList: AccountOperation[];
}
```

## Services

### Customer Service
```typescript
// customer.service.ts
@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  backendHost = "http://localhost:8080";
  
  public getCustomers(): Observable<Array<Customer>> {
    return this.http.get<Array<Customer>>(this.backendHost + "/customers");
  }
  
  public searchCustomers(keyword: string): Observable<Array<Customer>> {
    return this.http.get<Array<Customer>>(this.backendHost + "/customers/search?keyword=" + keyword);
  }
}
```

### Accounts Service
```typescript
// accounts.service.ts
@Injectable({
  providedIn: 'root'
})
export class AccountsService {
  public getAccount(accountId: string, page: number, size: number): Observable<AccountDetails> {
    return this.http.get<AccountDetails>(`${this.backendHost}/accounts/${accountId}/pageOperations?page=${page}&size=${size}`);
  }
  
  public debit(accountId: string, amount: number, description: string) {
    return this.http.post(this.backendHost + "/accounts/debit", {accountId, amount, description});
  }
}
```

## Routing
```typescript
// app.routes.ts
export const routes: Routes = [
  {path: "login", component: LoginComponent},
  {path: "", redirectTo: "/login", pathMatch: "full"},
  {
    path: "admin", 
    component: AdminTemplateComponent, 
    canActivate: [authenticationGuard],
    children: [
      {path: "customers", component: CustomersComponent},
      {path: "accounts", component: AccountsComponent},
      {path: "new-customer", component: NewCustomerComponent, canActivate: [authorizationGuard], data: {roles: ["ADMIN"]}},
      {path: "customer-accounts/:id", component: CustomerAccountsComponent}
    ]
  }
];
```

## Getting Started

1. Clone the repository
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the development server:
   ```bash
   ng serve
   ```
4. Navigate to `http://localhost:4200`

### Prerequisites
- Node.js (v14 or higher)
- Angular CLI
- Spring Boot backend running on `http://localhost:8080`

### Environment Setup
The application connects to a Spring Boot backend running on `http://localhost:8080`. Make sure the backend service is running before starting the Angular application.

## Security
- JWT-based authentication
- Role-based authorization (ADMIN and USER roles)
- HTTP Interceptors for token management
- Secure password handling
- Protected routes with guards

## Contributing
1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request
