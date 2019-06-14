import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl, AbstractControl } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { RegisterService } from '@ikubinfo/core/services/register.service';
import { throwError, Subject } from 'rxjs';
import { Router } from '@angular/router';
import { Role } from '@ikubinfo/core/models/role';
import { User } from '@ikubinfo/core/models/user';
import { Register } from '@ikubinfo/core/models/register';
import { RoleEnum } from '@ikubinfo/core/models/role.enum';


@Component({
  selector: 'ikubinfo-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  usernameCtrl: FormControl;
  passwordCtrl: FormControl;
  birthdateCtrl: FormControl;
  addressCtrl: FormControl;
  emailCtrl:FormControl;

  registerForm: FormGroup;
  role:Role;
  registerUser: Register;
  static isOldEnough(control: AbstractControl):any {     
    const birthDatePlus18 = new Date(control.value);   
    birthDatePlus18.setFullYear(birthDatePlus18.getFullYear() + 18);   
    return birthDatePlus18 < new Date() ? null : { tooYoung: true };
   };

  
  constructor(private router: Router,private fb: FormBuilder ,private registerService: RegisterService) { 
    this.registerUser = {

    }
  }
  
   

  ngOnInit() {
    this.usernameCtrl=this.fb.control('', Validators.required,control => this.isUsernameAvailable(control));
    this.passwordCtrl=this.fb.control('', Validators.required);
    this.addressCtrl=this.fb.control('', Validators.required);
    this.emailCtrl=this.fb.control('', Validators.required );
    this.birthdateCtrl=this.fb.control('',Validators.required ,control => RegisterComponent.isOldEnough(control));
    this.registerForm = this.fb.group({
      username:  this.usernameCtrl,
      password: this.passwordCtrl,
      role: {
            "id": RoleEnum.USER,
            "roleName": "USER",
            "roleDescription": "Shikon_postimet_dhe_propozon_kategori"
      },
      birthdate: this.birthdateCtrl,
      email: this.emailCtrl,
      address: this.addressCtrl,
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
   
   isUsernameAvailable(control: AbstractControl): any {
     const username= control.value;
     return this.registerService.isUsernameAvailable(username)
     .subscribe(available => available ? null : {alreadyUsed: true});
   }

   

}
