import { Injectable } from '@angular/core';
import { ApiService } from '../utilities/api.service';
import { Register } from '../models/register';
import { Router } from '@angular/router';



@Injectable({
  providedIn: 'root'
})
export class SettingsService {

  constructor(private apiService: ApiService,private router: Router ) {
  
  }

  update(registerUser: Register){
    if (registerUser.username !== ""){
      this.router.navigate(['login']);
    }
    return this.apiService.put('users',registerUser);
  }
  
  deleteAccount(){
    return this.apiService.delete('users');
  }


}

 