import { Injectable } from '@angular/core';
import { ApiService } from '../utilities/api.service';
import { Register } from '../models/register';



@Injectable({
  providedIn: 'root'
})
export class SettingsService {

  constructor(private apiService: ApiService ) {
  
  }

  update(registerUser: Register){
    return this.apiService.put('users',registerUser);
  }
  
  deleteAccount(){
    return this.apiService.delete('users');
  }


}

 