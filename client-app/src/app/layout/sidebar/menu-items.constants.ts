
import { MenuItem } from './menu-item'
import { RoleEnum } from '@ikubinfo/core/models/role.enum';

export const menuItems: Array<MenuItem> = [{
    url: '/suggestion/dashboard',
    icon: 'fa-dashboard',
    label: 'Dashboard',
    allowedRoles: [RoleEnum.ADMIN, RoleEnum.USER]
<<<<<<< HEAD
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

},
{
    url: '/suggestion/view',
=======
},
{
    url: '/suggestion/viewPosts',
>>>>>>> abe503bc4db72727371943146c97fb0c176ca473
    icon: 'fa-table',
    label: 'Posts',
    allowedRoles: [ RoleEnum.USER]
},{
    url: '/suggestion/posts',
    icon: 'fa-table',
    label: 'Manage posts',
    allowedRoles: [RoleEnum.ADMIN]

}]