import { Role } from '@ikubinfo/core/models/role.enum';


export interface User {
    username?: string;
    password?: string;
    email?: string;
    birthdate?: Date;
    id?: number;
    role?: Role;
    address?: string;
}