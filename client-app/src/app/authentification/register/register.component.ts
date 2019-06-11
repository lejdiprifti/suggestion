import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { RegisterService } from '@ikubinfo/core/services/register.service';
import { throwError, Subject } from 'rxjs';
import { Router } from '@angular/router';
import { Role } from '@ikubinfo/core/models/role';
import { User } from '@ikubinfo/core/models/user';
import { Register } from '@ikubinfo/core/models/register';
import { RoleEnum } from '@ikubinfo/core/models/role.enum';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';


@Component({
  selector: 'ikubinfo-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup;
  role:Role;
  registerUser: Register;
  constructor(private router: Router,private fb: FormBuilder ,private registerService: RegisterService) { 
    this.registerUser = {

    }
  }


  ngOnInit() {
    this.registerForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      role: {
            "id": RoleEnum.USER,
            "roleName": "USER",
            "roleDescription": "Shikon_postimet_dhe_propozon_kategori"
      },
      birthdate: ['', Validators.required],
      email: ['', Validators.required],
      address: ['', Validators.required],
      repeatPassword: ['', Validators.required]
    });
  }

  register(): void {
    this.registerUser.username = this.registerForm.value.username;
    this.registerUser.password= this.registerForm.value.password;
    this.registerUser.role = this.registerForm.value.role;
    this.registerUser.birthdate = this.registerForm.value.birthdate;
    this.registerUser.email = this.registerForm.value.email;
    this.registerUser.address = this.registerForm.value.address;
    this.registerUser.flag = true;
    this.registerService.register(this.registerUser).subscribe(res=>{
      this.registerService.setData(res);
        this.router.navigate(['/suggestion']);
    },
    err=>{
      if (err.status === 400){
        return throwError("Username already exists");
      }
    });
  }

}
