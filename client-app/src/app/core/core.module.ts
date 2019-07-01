
import { NgModule, Optional, SkipSelf } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ConfirmationService, MessageService } from 'primeng/primeng';


import { AuthService } from '@ikubinfo/core/services/auth.service';
import { PostService } from '@ikubinfo/core/services/post.service';
import { LoggerService } from '@ikubinfo/core/utilities/logger.service';
import { ApiService } from '@ikubinfo/core/utilities/api.service';
import { AuthGuard } from '@ikubinfo/core/guards/auth-guard';
import { TokenInterceptor } from '@ikubinfo/core/interceptors/token-interceptor';
import { CategoryService } from './services/category.service';


import { ProposalsService } from './services/proposals.service';
import { CategoriesService } from './services/categories.service';




@NgModule({
  imports: [
    HttpClientModule
  ],
  declarations: [],
  providers: [AuthService,ProposalsService, PostService, LoggerService, ApiService, AuthGuard,CategoriesService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    }, ConfirmationService,
    MessageService]

})
export class CoreModule {
  /*Prevent reimport of the core module */
  constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
    if (parentModule) {
      throw new Error(
        'CoreModule is already loaded. Import it in the AppModule only');
    }
  }
}
