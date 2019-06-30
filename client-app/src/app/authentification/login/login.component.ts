
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { AuthService } from '@ikubinfo/core/services/auth.service';
import { LoggerService } from '@ikubinfo/core/utilities/logger.service';
import { User } from '@ikubinfo/core/models/user';
import { Role } from '@ikubinfo/core/models/role';
import { RoleEnum } from '@ikubinfo/core/models/role.enum';


@Component({
  selector: 'ikubinfo-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  user: User;
  role: Role;
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
    this.user={};
    this.role={
      id: RoleEnum.ADMIN,
      roleName: "ADMIN",
      roleDescription: "Menaxhon_postet_dhe_kategorite"
      
    }
    
    
  }

  login(): void {
 this.authService.login(this.loginForm.value).subscribe(
 (res: any)=>{
  this.authService.setData(res);
  console.log(this.authService.user.role);
  console.log(this.role);
  if (JSON.stringify(this.authService.user.role) === JSON.stringify(this.role)){
    this.router.navigate(['suggestion/categories']);
  } else {
    this.router.navigate(['suggestion/dashboard']);
  }
  this.logger.success('Success', 'You logged in successfully!');
 },
 err => {
  this.logger.error('Error', 'Invalid username or password');
});
}

}