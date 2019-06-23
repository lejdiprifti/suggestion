import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AdminGuard } from '@ikubinfo/core/guards/admin-guard';
import { DashboardComponent } from '@ikubinfo/suggestion/dashboard/dashboard.component';
import { PostComponent } from '@ikubinfo/suggestion/post/post.component';
import { PostsComponent } from '@ikubinfo/suggestion/posts/posts.component';
import { FullComponent } from '@ikubinfo/layout/full/full.component';
import { CategorySuggestionsComponent } from './category-suggestions/category-suggestions.component';
import { UserGuard } from '@ikubinfo/core/guards/user-guard';

const suggestionRoutes: Routes = [
    {
        path: '',
        component: FullComponent,
        children: [
            { path: 'dashboard', component: DashboardComponent },
            { path: 'posts', component: PostsComponent, canActivate: [AdminGuard] },
            { path: 'post', component: PostComponent, canActivate: [AdminGuard] },
            { path: 'suggestion-category', component: CategorySuggestionsComponent, canActivate: [UserGuard] },
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
