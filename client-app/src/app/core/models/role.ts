import { RoleEnum } from './role.enum';

export interface Role {
    id:RoleEnum;
    roleName?:string;
    roleDescription?:string;
}