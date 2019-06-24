import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AdminGuard } from '@ikubinfo/core/guards/admin-guard';
import { DashboardComponent } from '@ikubinfo/suggestion/dashboard/dashboard.component';
import { PostComponent } from '@ikubinfo/suggestion/post/post.component';
import { PostsComponent } from '@ikubinfo/suggestion/posts/posts.component';
import { FullComponent } from '@ikubinfo/layout/full/full.component';
<<<<<<< HEAD
import { SettingsComponent } from './settings/settings.component';
import { SubscriptionsComponent } from './subscriptions/subscriptions.component';
import { UserGuard } from '@ikubinfo/core/guards/user-guard';
=======
import { UserGuard } from '@ikubinfo/core/guards/user-guard';
import { PostViewComponent } from './posts/post-view/post-view.component';
>>>>>>> abe503bc4db72727371943146c97fb0c176ca473

import { PostViewComponent } from './posts/post-view/post-view.component';
const suggestionRoutes: Routes = [
    {
        path: '',
        component: FullComponent,
        children: [
            { path: 'dashboard', component: DashboardComponent },
            { path: 'settings', component: SettingsComponent},
            { path: 'subscriptions', component: SubscriptionsComponent , canActivate: [UserGuard]},
            { path: 'posts', component: PostsComponent, canActivate: [AdminGuard] },
<<<<<<< HEAD
            { path: 'view', component: PostViewComponent, canActivate: [UserGuard] },
=======
            { path: 'viewPosts', component: PostViewComponent, canActivate: [UserGuard] },
>>>>>>> abe503bc4db72727371943146c97fb0c176ca473
            { path: 'post', component: PostComponent, canActivate: [AdminGuard] },
            { path: 'post/:id', component: PostComponent, canActivate: [AdminGuard] },
            { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
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
