import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AdminGuard } from '@ikubinfo/core/guards/admin-guard';
import { DashboardComponent } from '@ikubinfo/suggestion/dashboard/dashboard.component';
import { PostComponent } from '@ikubinfo/suggestion/post/post.component';
import { PostsComponent } from '@ikubinfo/suggestion/posts/posts.component';
import { FullComponent } from '@ikubinfo/layout/full/full.component';
import { SettingsComponent } from './settings/settings.component';
import { SubscriptionsComponent } from './subscriptions/subscriptions.component';
import { UserGuard } from '@ikubinfo/core/guards/user-guard';
import { ProposalsComponent } from './admin/proposals/proposals.component';
import { CategoriesComponent } from './admin/categories/categories.component';
import { CategoryComponent } from './admin/category/category.component';
import { AddCategoryComponent } from './admin/add-category/add-category.component';


const suggestionRoutes: Routes = [
    {
        path: '',
        component: FullComponent,
        children: [
            { path: 'dashboard', component: DashboardComponent , canActivate:[UserGuard]},
            { path: 'categories', component: CategoriesComponent, canActivate: [AdminGuard]},
            { path: 'settings', component: SettingsComponent},
            { path: 'category/add', component: AddCategoryComponent, canActivate: [AdminGuard]},
            { path: 'category', component: CategoryComponent, canActivate: [AdminGuard]},
            { path: 'subscriptions', component: SubscriptionsComponent , canActivate: [UserGuard]},
            { path: 'posts', component: PostsComponent, canActivate: [AdminGuard] },
            { path: 'proposals', component: ProposalsComponent, canActivate: [AdminGuard]},
            { path: 'post', component: PostComponent, canActivate: [AdminGuard] },
            { path: 'post/:id', component: PostComponent, canActivate: [AdminGuard] },
            { path: '', redirectTo: 'categories', pathMatch: 'full' , canActivate:[AdminGuard]}
        ]
    }
];

@NgModule({
    imports: [
        RouterModule.forChild(suggestionRoutes)
    ],
    exports: [
        RouterModule
    ]
})
export class SuggestionRoutingModule { }
