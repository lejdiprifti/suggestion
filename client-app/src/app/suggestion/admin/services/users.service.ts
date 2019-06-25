import { Injectable } from '@angular/core';
import { ApiService } from '@ikubinfo/core/utilities/api.service';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  constructor(private apiService: ApiService) { }

  getActiveUsers(){
    return this.apiService.get('users');
  }
}
