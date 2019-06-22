import { Injectable } from '@angular/core';
import { ApiService } from '../utilities/api.service';
import { User } from '../models/user';
import { Subject, Observable } from 'rxjs';

import { Register } from '../models/register';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {
  
registerUser: Register;
  onUserChanged: Subject<User>;
  
  constructor(private apiService: ApiService) { 

  }
  
  register(registerUser: Register){
    return this.apiService.post('register', registerUser);
  }




setData(registerUser: Register) {
    console.debug(
      registerUser  ? "Setting current user info. : Clearing current user info." : ""
    );
    if (registerUser) {
      this.registerUser = registerUser;
        sessionStorage.setItem("userData", JSON.stringify(registerUser));
        this.onUserChanged.next(registerUser);
    } else {
        sessionStorage.removeItem("userData");

    }
    
}


 

getUserByUsername(username: string): Observable<any> {
    return this.apiService.get<User>('users/'+username);
}

}
