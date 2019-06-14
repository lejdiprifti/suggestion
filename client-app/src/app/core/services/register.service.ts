import { Injectable } from '@angular/core';
import { ApiService } from '../utilities/api.service';
import { User } from '../models/user';
import { Subject, Observable } from 'rxjs';
import { RoleEnum } from '../models/role.enum';
import { Register } from '../models/register';
import { AbstractControl } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {
  
registerUser: Register;
  onUserChanged: Subject<User>;
  
  constructor(private apiService: ApiService) { 
    this.onUserChanged=new Subject<User>();
    this.loadData();
  }
  
  register(registerUser: Register){
    return this.apiService.post('register', registerUser);
  }

  logout(): void {
    this.setData(null);
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

get isLoggedIn(): any {
    this.loadData();
    return sessionStorage.getItem("userData");
}

private loadData() {

    if (sessionStorage.getItem("userData")) {
        this.registerUser = JSON.parse(sessionStorage.getItem("userData"));
    }
    else {
        this.registerUser = null;
    }

}
 
get role(): RoleEnum {
  if (this.isLoggedIn && this.registerUser.role) {
      return this.registerUser.role.id;
  }
  return null;
}

getUserByUsername(username: string): Observable<any> {
    return this.apiService.get<User>('users/'+username);
}

}
