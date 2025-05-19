import {Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {CustomerService} from '../services/customer.service';
import {Observable, catchError, of, startWith, throwError, map} from 'rxjs';
import {Customer} from '../models/customer.model';
import {FormBuilder, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {Router} from '@angular/router';

@Component({
  selector: 'app-customers',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './customers.component.html',
  standalone: true,
  styleUrl: './customers.component.css'
})
export class CustomersComponent implements OnInit {

  customers: any;
  errorMessage! : string;
  listCustomers$!: Observable<Array<Customer> | null>;
  searchFormGroup: FormGroup | undefined;

  constructor(private customerService : CustomerService, private fb : FormBuilder,private router:Router) {}

  ngOnInit(): void {
    this.searchFormGroup = this.fb.group({
      keyword: this.fb.control("")
    })

    this.listCustomers$ = this.customerService.getCustomers().pipe(
      startWith(null),
      catchError(err => {
        this.errorMessage = err.message;
        return of(null);
      })
    );
  }
  handleSearchCustomers(){
    let keyword = this.searchFormGroup?.value.keyword;
    this.listCustomers$ = this.customerService.searchCustomers(keyword).pipe(
      catchError(err => {
        this.errorMessage = err.message;
        return throwError(err);
      })
    );

  }

  handleDeleteCustomer(customer: Customer) {
    this.customerService.deleteCustomer(customer.id).subscribe({
      next: (resp)=> {
        this.customers=this.customers.pipe(
          map((data:any)=>{
            let index=data.indexOf(customer);
            data.slice(index,1);
            return data;
          })
        );
      },
      error: (err) => {
        console.log(err);
      }
    });
  }

  handleCustomerAccount(customer: Customer) {
    this.router.navigateByUrl("/customer-accounts/"+customer.id,{state: customer});


  }
}
