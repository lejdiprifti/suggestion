import { Role } from './role';


export interface User {
    id?: number;
    username?: string;
    password?: string;
    role?: Role;
    birthdate?: Date;
    email?: string;
    address?: string;
    categories?: Array<string>;
    posts?: Array<string>;
    flag?: boolean;
}