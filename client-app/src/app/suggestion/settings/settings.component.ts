import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RegisterComponent } from '@ikubinfo/authentification/register/register.component';
import { SettingsService } from '@ikubinfo/core/services/settings.service';
import { Register } from '@ikubinfo/core/models/register';
import { LoggerService } from '@ikubinfo/core/utilities/logger.service';
import { ConfirmationService } from 'primeng/components/common/confirmationservice';


@Component({
  selector: 'ikubinfo-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {
 settingsForm: FormGroup;
 updateUser: Register;
 passwordForm: FormGroup;
  constructor(private confirmationService: ConfirmationService, private logger: LoggerService,private router: Router , private fb:FormBuilder,private settingsService: SettingsService) { 
    this.updateUser={

    }
    
  }

  ngOnInit() {
    this.passwordForm=this.fb.group({
      password: [
        "",
        [
          Validators.pattern("(?=.*d)(?=.*[a-z])(?=.*[A-Z]).{8,}")
        ]
      ],
      repeatPassword: [
        "",
        [
          
        ]
      ]
    },
    {validators: RegisterComponent.passwordMatch});

    this.settingsForm=this.fb.group({
      username: [
        "",
        [
          Validators.minLength(4)
        ]
      ],
      birthdate: ['',
    [
      RegisterComponent.isOldEnough
    ]
  ],
    email: [
      "",
      [
        Validators.pattern("[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,}$")
      ]
    ],
    address: [
      "",
    ],
    passwordForm: this.passwordForm
    });
  }
update(): void {
    if (this.settingsForm.value.username !== ""){
      this.updateUser.username=this.settingsForm.value.username;
    }
    if (this.passwordForm.value.password !== ""){
      this.updateUser.password=this.passwordForm.value.password;
    }
    if (this.settingsForm.value.birthdate !== null){
      this.updateUser.birthdate=this.settingsForm.value.birthdate;
    }
    if (this.settingsForm.value.email !== ""){
      this.updateUser.email=this.settingsForm.value.email;
    }
    if (this.settingsForm.value.address !== ""){
      this.updateUser.address=this.settingsForm.value.address;
    }
    this.confirmationService.confirm({
      message: 'Do you want to save your data?',
      header: 'Save Confirmation',
      icon: 'pi pi-info-circle',
      accept: () => {
        this.settingsService.update(this.updateUser).subscribe(res=>{
          this.logger.success("Success", "Data saved successfully!");
      },
      err=>{
        this.logger.error("Error","Username is taken.");
        this.router.navigate(["/suggestion/settings"]);
      });
      }
    });
    
}
delete(): any{
  this.confirmationService.confirm({
    message: 'Do you want to delete your account?',
    header: 'Delete Confirmation',
    icon: 'pi pi-info-circle',
    accept: () => {
      this.settingsService.deleteAccount().subscribe(res=>{
        this.logger.info("Info", "Account was deleted.");
        this.router.navigate(["/login"]);
      },
      err => {
        this.logger.error('Error', 'An error accured');
      });
    }
  });

}
}
