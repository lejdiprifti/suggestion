import { Injectable } from '@angular/core';
import {
    Router,
    CanLoad, Route
} from '@angular/router';

import { AuthService } from '@ikubinfo/core/services/auth.service';
import { RegisterService } from '../services/register.service';

@Injectable({
    providedIn: 'root',
})
export class AuthGuard implements CanLoad {
    constructor(private authService: AuthService, private router: Router , private registerService: RegisterService) { }

    canLoad(route: Route): boolean {
        if (this.authService.isLoggedIn) {
            return true;
        }
        else {
            this.router.navigate(['/login']);
            return false;
        }

    }
}


