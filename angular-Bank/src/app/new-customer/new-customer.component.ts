import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Customer} from '../models/customer.model';
import {CustomerService} from '../services/customer.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-new-customer',
  imports: [
    ReactiveFormsModule
  ],
  standalone: true,
  templateUrl: './new-customer.component.html',
  styleUrl: './new-customer.component.css'
})
export class NewCustomerComponent implements OnInit{
  newCustomerFormGroup!: FormGroup ;
  constructor(private fb: FormBuilder,private customerService: CustomerService, private router: Router) {
    this.newCustomerFormGroup = this.fb.group({
      name: this.fb.control(null, [Validators.required,Validators.minLength(3)]),
      email: this.fb.control(null, [Validators.required, Validators.email]),

    })
  }

  ngOnInit(): void {
  }

  handleSaveCustomer() {
    let customer: Customer = this.newCustomerFormGroup.value;
    this.customerService.saveCustomer(customer).subscribe({
      next: (data) => {
        alert("Customer Created Successfully");
        //this.newCustomerFormGroup.reset();
        this.router.navigateByUrl("/customers");
      },
      error: (err) => {
        console.log(err);
      }
    });
  }
}
