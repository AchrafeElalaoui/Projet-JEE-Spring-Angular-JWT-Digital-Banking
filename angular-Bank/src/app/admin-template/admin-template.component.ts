import {Component, OnInit} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {NavbarComponent} from '../navbar/navbar.component';

@Component({
  selector: 'app-admin-template',
  imports: [RouterOutlet, NavbarComponent],
  standalone: true,
  templateUrl: './admin-template.component.html',
  styleUrl: './admin-template.component.css'
})
export class AdminTemplateComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {
  }

}
