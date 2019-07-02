import { CommonsModule } from '@ikubinfo/commons/commons.module';

import { NgModule } from '@angular/core';
import { PanelModule } from 'primeng/panel';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { ButtonModule } from 'primeng/button';

import { PostComponent } from '@ikubinfo/suggestion/post/post.component';
import { PostsComponent } from '@ikubinfo/suggestion/posts/posts.component';
import { DashboardComponent } from '@ikubinfo/suggestion/dashboard/dashboard.component';
import { SuggestionRoutingModule } from '@ikubinfo/suggestion/suggestion-routing.module';
import { LayoutModule } from '@ikubinfo/layout/layout.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SettingsComponent } from './settings/settings.component';
import { SubscriptionsComponent } from './subscriptions/subscriptions.component';
import { PostsOfCategoryComponent } from './posts-of-category/posts-of-category.component';
import { PostViewComponent } from './post-view/post-view.component';
import { CategorySuggestionsComponent } from './category-suggestions/category-suggestions.component';
import { ProposalsComponent } from './proposals/proposals.component';
import { CategoriesComponent } from './categories/categories.component';

import { CategoryComponent } from './category/category.component';
import { UsersComponent } from './users/users.component';
import { ListOfProposalsComponent } from './list-of-proposals/list-of-proposals.component';
import { MessagesComponent } from './messages/messages.component';


@NgModule({
    imports: [CommonsModule, SuggestionRoutingModule, LayoutModule, FormsModule,ReactiveFormsModule],
    exports: [],
    declarations: [DashboardComponent, PostComponent, PostsComponent, SettingsComponent, SubscriptionsComponent,CategoriesComponent,ProposalsComponent,CategorySuggestionsComponent, CategoryComponent, UsersComponent,PostViewComponent,PostsOfCategoryComponent,ListOfProposalsComponent, MessagesComponent],
    providers: []
})
export class SuggestionModule { }
