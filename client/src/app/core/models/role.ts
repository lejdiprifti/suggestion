import { RoleEnum } from './role.enum';

export interface Role {
    id:RoleEnum;
    name?:string;
    description?:string;
}