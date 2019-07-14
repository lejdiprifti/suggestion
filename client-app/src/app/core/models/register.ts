import { Role } from './role';


export interface Register {
    avatar?: string;
    username?: string;
    password?: string;
    role?: Role;
    birthdate?: Date;
    email?: string;
    address?: string;
    flag?: boolean;
}