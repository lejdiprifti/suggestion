
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { AuthService } from '@ikubinfo/core/services/auth.service';
import { LoggerService } from '@ikubinfo/core/utilities/logger.service';


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
    private authService: AuthService,
    private logger: LoggerService
  ) { }

  ngOnInit() {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required , Validators.minLength(4)]],
      password: ['', [Validators.required , Validators.minLength(4)]]
    });
  }

  login(): void {
 this.authService.login(this.loginForm.value).subscribe(
 (res: any)=>{
   console.log(res);
  this.authService.setData(res);
  this.router.navigate(['/suggestion']);
  this.logger.success('Success', 'Logined successfully');
 },
 err => {
  this.logger.error('Error', 'Invalid Username');
});
}

}