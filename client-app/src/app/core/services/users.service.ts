import { Injectable } from '@angular/core';
import { ApiService } from '@ikubinfo/core/utilities/api.service';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  constructor(private apiService: ApiService) { }
  url='users';

  getUsers(){
    return this.apiService.get(this.url);
  }
}
