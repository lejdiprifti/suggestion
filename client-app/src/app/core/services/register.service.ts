import { Injectable } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RegisterComponent } from '@ikubinfo/authentification/register/register.component';
import { ApiService } from '../utilities/api.service';
import { User } from '../models/user';
import { Subject } from 'rxjs';
import { RoleEnum } from '../models/role.enum';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {
  user: User;
  onUserChanged: Subject<User>;
  
  constructor(private apiService: ApiService) { 
    this.onUserChanged=new Subject<User>();
    this.loadData();
  }
  
  register(user: User){
    return this.apiService.post('/register', user);
  }

  logout(): void {
    this.setData(null);
}



setData(user: User) {
    console.debug(
        user  ? "Setting current user info. : Clearing current user info." : ""
    );
    if (user) {
        sessionStorage.setItem("userData", JSON.stringify(user));
        this.onUserChanged.next(user);
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
        this.user = JSON.parse(sessionStorage.getItem("userData"));
    }
    else {
        this.user = null;
    }

}


}
