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
import { PostsOfCategoryComponent } from './posts-of-category/posts-of-category.component';
import { PostViewComponent } from './post-view/post-view.component';
import { CategorySuggestionsComponent } from './category-suggestions/category-suggestions.component';
import { ProposalsComponent } from './proposals/proposals.component';
import { CategoriesComponent } from './categories/categories.component';
import { CategoryComponent } from './category/category.component';
import { UsersComponent } from './users/users.component';
import { ListOfProposalsComponent } from './list-of-proposals/list-of-proposals.component';
import { MessagesComponent } from './messages/messages.component';


const suggestionRoutes: Routes = [
    {
        path: '',
        component: FullComponent,
        children: [
            { path: 'dashboard', component: DashboardComponent , canActivate:[UserGuard]},
            { path: 'categories', component: CategoriesComponent, canActivate: [AdminGuard]},
            { path: 'settings', component: SettingsComponent},
            { path: 'users', component: UsersComponent, canActivate: [AdminGuard]},
            { path: 'category/add', component: CategoryComponent, canActivate: [AdminGuard]},
            { path: 'category/:id', component: CategoryComponent, canActivate: [AdminGuard]},
           
            { path: 'subscriptions', component: SubscriptionsComponent , canActivate: [UserGuard]},
            { path: 'posts', component: PostsComponent, canActivate: [AdminGuard] },
            { path: 'viewposts', component: PostViewComponent, canActivate: [UserGuard] },
            { path: 'proposals', component: ProposalsComponent, canActivate: [AdminGuard]},
            { path: 'messages', component: MessagesComponent,canActivate:[UserGuard]},
            { path: 'post', component: PostComponent, canActivate: [AdminGuard] },
            { path: 'propose', component: ListOfProposalsComponent, canActivate: [UserGuard] },
            { path: 'proposal', component: CategorySuggestionsComponent, canActivate: [UserGuard] },
            { path: 'post/:id', component: PostComponent, canActivate: [AdminGuard] },
            {path: 'categories/:id/posts', component: PostsOfCategoryComponent, canActivate: [UserGuard]}
            
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
