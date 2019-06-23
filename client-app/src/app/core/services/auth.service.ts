

import { Injectable } from '@angular/core';


import { Observable, of, throwError, Subject } from 'rxjs';
import { isNullOrUndefined } from 'util';

import { User } from '@ikubinfo/core/models/user';
import { RoleEnum } from '@ikubinfo/core/models/role.enum';
import { Login } from '@ikubinfo/core/models/login';
import { HttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ApiService } from '../utilities/api.service';


 
@Injectable()
export class AuthService {

    user: User;
    onUserChanged: Subject<User>;

    constructor(private apiService:ApiService, private fb: FormBuilder) {
        this.onUserChanged = new Subject<User>();
        this.loadData();
    }
    
    login(loginContext: Login): Observable<any> {
     return this.apiService.post('login',loginContext);
    
    }


    logout(): void {
        this.setData(null);
    }

    getToken() {
        return this.getToken;
    }

    setData(data: {user:User, jwt:string}) {
        console.debug(
            data  ? "Setting current user info. : Clearing current user info." : ""
        );
        if (data) {
            this.user = data.user;
            // sessionStorage.setItem("userData", JSON.stringify({"id":9,"username":"r@r.r","password":"r","role":{"id":2,"roleName":"USER","roleDescription":"Shikon_postimet_dhe_propozon_kategori"},"birthdate":-62135769600000,"email":"r@r.r","address":"p","flag":true,"delete":true}))  
     
            sessionStorage.setItem("userData", JSON.stringify(data.user));
            sessionStorage.setItem("token", data.jwt);
            this.onUserChanged.next(data.user);
        } else {
            sessionStorage.removeItem("userData");
            sessionStorage.removeItem("token");
        }
        
    }

    get isLoggedIn(): any {
        this.loadData();
        return sessionStorage.getItem("token") && sessionStorage.getItem("userData");
    }

    private loadData() {

        if (sessionStorage.getItem("userData")) {
            this.user = JSON.parse(sessionStorage.getItem("userData"));
        }
        else {
            this.user = null;
        }

    }

    get role(): RoleEnum {
        if (this.isLoggedIn && this.user.role) {
            return this.user.role.id;
        }
        return null;
    }


}