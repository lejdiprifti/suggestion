import { Injectable } from '@angular/core';
import { ApiService } from '../utilities/api.service';
import { Register } from '../models/register';
import { Router } from '@angular/router';



@Injectable({
  providedIn: 'root'
})
export class SettingsService {
url='users';
  constructor(private apiService: ApiService,private router: Router ) {
  
  }

  update(registerUser: Register){
    return this.apiService.put(this.url,registerUser);
  }
  
  deleteAccount(){
    return this.apiService.delete(this.url);
  }


}

 