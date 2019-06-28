
import { MenuItem } from './menu-item'
import { RoleEnum } from '@ikubinfo/core/models/role.enum';

export const menuItems: Array<MenuItem> = [{
    url: '/suggestion/dashboard',
    icon: 'fa-dashboard',
    label: 'Dashboard',
    allowedRoles: [RoleEnum.USER]
},{
    url: '/suggestion/subscriptions',
    icon: 'fa fa-th-list',
    label: 'Subscriptions',
    allowedRoles: [RoleEnum.USER]
},,{
    url: '/suggestion/categories',
    icon: 'fa-dashboard',
    label: 'Manage categories',
    allowedRoles: [RoleEnum.ADMIN]
},{
    url: '/suggestion/viewposts',
    icon: 'fa-table',
    label: 'Posts',
    allowedRoles: [ RoleEnum.USER]
},{
    url: '/suggestion/proposals',
    icon: 'fa fa-fw fa-edit',
    label: 'Proposals',
    allowedRoles: [RoleEnum.ADMIN]
},{   
     url: '/suggestion/posts',
    icon: 'fa-table',
    label: 'Manage posts',
    allowedRoles: [RoleEnum.ADMIN]
},{
    url: '/suggestion/propose',
    icon: 'fa fa-fw fa-edit',
    label: 'My Proposals',
    allowedRoles: [RoleEnum.USER]
},{
    url: '/suggestion/users',
    icon: 'fa fa-th-list',
    label: 'Active users',
    allowedRoles: [RoleEnum.ADMIN]
},{
    url: '/suggestion/messages',
    icon: 'fa fa-th-list',
    label: 'Messages',
    allowedRoles: [RoleEnum.USER]
},{
    url: '/suggestion/settings',
    icon: 'fa fa-fw fa-wrench',
    label: 'Settings',
    allowedRoles: [RoleEnum.ADMIN, RoleEnum.USER]
}]
