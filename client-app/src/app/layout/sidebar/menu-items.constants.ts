
import { MenuItem } from './menu-item'
import { RoleEnum } from '@ikubinfo/core/models/role.enum';

export const menuItems: Array<MenuItem> = [{
    url: '/suggestion/dashboard',
    icon: 'fa-dashboard',
    label: 'Dashboard',
    allowedRoles: [RoleEnum.ADMIN, RoleEnum.USER]
},{
    url: '/suggestion/subscriptions',
    icon: 'fa-subscriptions',
    label: 'Subscriptions',
    allowedRoles: [RoleEnum.USER]
},{
    url: '/suggestion/settings',
    icon: 'fa-settings',
    label: 'Settings',
    allowedRoles: [RoleEnum.ADMIN, RoleEnum.USER]
},{
    url: '/suggestion/posts',
    icon: 'fa-table',
    label: 'Manage posts',
    allowedRoles: [RoleEnum.ADMIN]

}]