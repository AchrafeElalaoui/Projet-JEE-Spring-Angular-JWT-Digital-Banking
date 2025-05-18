import {Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {CustomerService} from '../services/customer.service';
import {Observable, catchError, of, startWith, throwError} from 'rxjs';
import {Customer} from '../models/customer.model';
import {FormBuilder, FormGroup, ReactiveFormsModule} from '@angular/forms';

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

  constructor(private customerService : CustomerService, private fb : FormBuilder) {}

  ngOnInit(): void {
    this.searchFormGroup = this.fb.group({
      keyword: this.fb.control("")
    })

    this.listCustomers$ = this.customerService.getCustomers().pipe(
      catchError(err => {
        this.errorMessage = err.message;
        return throwError(err);
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

}

