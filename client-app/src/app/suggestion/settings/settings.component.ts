import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RegisterComponent } from '@ikubinfo/authentification/register/register.component';
import { SettingsService } from '@ikubinfo/core/services/settings.service';
import { Register } from '@ikubinfo/core/models/register';
import { ApiService } from '@ikubinfo/core/utilities/api.service';

@Component({
  selector: 'ikubinfo-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {
 settingsForm: FormGroup;
 updateUser: Register;
 
  constructor(private router: Router , private fb:FormBuilder,private settingsService: SettingsService) { 
    this.updateUser={

    }
    
  }

  ngOnInit() {
    this.settingsForm=this.fb.group({
      username: [
        "",
        [
          Validators.minLength(4)
        ]
      ],
      password: [
      "",
      [
        Validators.pattern("(?=.*d)(?=.*[a-z])(?=.*[A-Z]).{8,}")
      ]
    ],
      birthdate: ["",
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
    ]
    });
  }
update(): void {
    if (this.settingsForm.value.username !== ""){
      this.updateUser.username=this.settingsForm.value.username;
    }
    if (this.settingsForm.value.password !== ""){
      this.updateUser.password=this.settingsForm.value.password;
    }
    if (this.settingsForm.value.birthdate !== ""){
      this.updateUser.birthdate=this.settingsForm.value.birthdate;
    }
    if (this.settingsForm.value.email !== ""){
      this.updateUser.email=this.settingsForm.value.email;
    }
    if (this.settingsForm.value.address !== ""){
      this.updateUser.address=this.settingsForm.value.address;
    }
    this.settingsService.update(this.updateUser).subscribe(res=>{
      alert("Data changed");
        this.router.navigate(["/suggestion"]);
    });
}
}
