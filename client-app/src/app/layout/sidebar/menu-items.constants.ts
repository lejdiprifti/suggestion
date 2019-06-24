
import { MenuItem } from './menu-item'
import { RoleEnum } from '@ikubinfo/core/models/role.enum';

export const menuItems: Array<MenuItem> = [{
    url: '/suggestion/dashboard',
    icon: 'fa-dashboard',
    label: 'Dashboard',
    allowedRoles: [RoleEnum.ADMIN, RoleEnum.USER]
},{
    url: '/suggestion/subscriptions',
    icon: 'fa fa-th-list',
    label: 'Subscriptions',
    allowedRoles: [RoleEnum.USER]
},{
    url: '/suggestion/settings',
    icon: 'fa fa-fw fa-wrench',
    label: 'Settings',
    allowedRoles: [RoleEnum.ADMIN, RoleEnum.USER]
},
{
    url: '/suggestion/viewposts',
    icon: 'fa-table',
    label: 'Posts',
    allowedRoles: [ RoleEnum.USER]
},{
    url: '/suggestion/posts',
    icon: 'fa-table',
    label: 'Manage posts',
    allowedRoles: [RoleEnum.ADMIN]
},{
    url: '/suggestion/propose',
    icon: 'fa-table',
    label: 'Suggestions',
    allowedRoles: [RoleEnum.USER]
}

]