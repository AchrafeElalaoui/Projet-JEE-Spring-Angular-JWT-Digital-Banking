import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {AccountsService} from '../services/accounts.service';
import {Observable, catchError, throwError} from 'rxjs';
import {AccountDetails} from '../models/account.model';
import {AsyncPipe, CommonModule} from '@angular/common';

@Component({
  selector: 'app-accounts',
  imports: [
    ReactiveFormsModule,
    AsyncPipe,
    CommonModule
  ],
  templateUrl: './accounts.component.html',
  standalone: true,
  styleUrl: './accounts.component.css'
})
export class AccountsComponent implements OnInit {
  accountFormGroup! : FormGroup;
  currentPage: number = 0;
  pageSize: number = 5;
  accountObservable!: Observable<AccountDetails>;
  errorMessage!: string;
  operationFormGroup!: FormGroup;
  currentOperationType: string = '';

  constructor(private fb: FormBuilder, private accountService: AccountsService) { }

  ngOnInit(): void {
    this.accountFormGroup = this.fb.group({
      accountId: this.fb.control('')
    });
    this.operationFormGroup = this.fb.group({
      operationType: this.fb.control(null),
      amount: this.fb.control(0),
      description: this.fb.control(null),
      accountDestination: this.fb.control(null)
    });

    // Subscribe to operationType changes
    this.operationFormGroup.get('operationType')?.valueChanges.subscribe(value => {
      this.currentOperationType = value;
    });
  }

  handleSearchAccount() {
    let accountId: string = this.accountFormGroup.value.accountId;
    this.accountObservable = this.accountService.getAccount(accountId, this.currentPage, this.pageSize)
      .pipe(
        catchError(err => {
          this.errorMessage = err.message;
          return throwError(() => err);
        })
      );
  }

  gotToPage(page: number) {
    this.currentPage = page;
    this.handleSearchAccount();
  }

  handleAccountOperation() {
    let accountId: string = this.accountFormGroup.value.accountId;
    let operationType = this.operationFormGroup.value.operationType;
    let amount: number = this.operationFormGroup.value.amount;
    let description: string = this.operationFormGroup.value.description;
    let accountDestination: string = this.operationFormGroup.value.accountDestination;

    if (operationType === 'DEBIT') {
      this.accountService.debit(accountId, amount, description).subscribe(
        {
          next : (data) => {
            alert("Debit operation done successfully");
            this.operationFormGroup.reset();
            this.handleSearchAccount();
          },
          error: (err) => {
            console.log(err);
          }
        }
      );
    }
    else if (operationType === 'CREDIT') {
      this.accountService.credit(accountId, amount, description).subscribe(
        {
          next : (data) => {
            alert("Credit operation done successfully");
            this.operationFormGroup.reset();
            this.handleSearchAccount();
          },
          error: (err) => {
            console.log(err);
          }
        }
      );


    }
    else if (operationType === 'TRANSFER') {
      this.accountService.transfer(accountId, accountDestination, amount, description).subscribe(
        {
          next : (data) => {
            alert("Transfer operation done successfully");
            this.operationFormGroup.reset();
            this.handleSearchAccount();
          },
          error: (err) => {
            console.log(err);
          }
        }
      );
    }
  }
}
