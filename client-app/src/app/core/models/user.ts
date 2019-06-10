import { Role } from './role';


export interface User {
    username?: string;
    password?: string;
    email?: string;
    birthdate?: Date;
    id?: number;
    role?: Role;
    address?: string;
}