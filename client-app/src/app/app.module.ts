import { CommonsModule } from '@ikubinfo/commons/commons.module';
import { Router } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {DatePipe} from '@angular/common';
import { CoreModule } from '@ikubinfo/core/core.module';
import { AppRoutingModule } from '@ikubinfo/app-routing.module';
import { AppComponent } from '@ikubinfo/app.component';
import { AuthentificationModule } from '@ikubinfo/authentification/authentification.module';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';




@NgModule({
  declarations: [
    AppComponent,
   


  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    CoreModule,
    AuthentificationModule,
    CommonsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule {  
  constructor(router: Router) {
  }
}
