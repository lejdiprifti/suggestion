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
import { FormsModule } from '@angular/forms';
import { CategorySuggestionsComponent } from './category-suggestions/category-suggestions.component';

@NgModule({
    imports: [CommonsModule, SuggestionRoutingModule, LayoutModule,ButtonModule, FormsModule, InputTextModule,InputTextareaModule, PanelModule],
    exports: [],
    declarations: [DashboardComponent, PostComponent, PostsComponent, CategorySuggestionsComponent],
    providers: []
})
export class SuggestionModule { }
