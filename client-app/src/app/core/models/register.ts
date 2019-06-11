import { Role } from './role';


export interface Register {
    username?: string;
    password?: string;
    role?: Role;
    birthdate?: Date;
    email?: string;
    address?: string;
    flag?: boolean;
}