
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { AuthService } from '@ikubinfo/core/services/auth.service';
import { throwError } from 'rxjs';


@Component({
  selector: 'ikubinfo-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  constructor(
    private router: Router,
    private fb: FormBuilder,
    private authService: AuthService
  ) { }

  ngOnInit() {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  login(): void {
 this.authService.login(this.loginForm.value).subscribe(
 (res: any)=>{
  this.authService.setData(res);
  this.router.navigate(['/suggestion']);
 },
 err=>{
   if (err.status === 400){
     console.log('Invalid username or password');
    return throwError('Invalid username or password');
   }else{
     console.log(err);
   }
 }
 );
}
}