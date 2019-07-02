import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';
import { LoggerService } from '@ikubinfo/core/utilities/logger.service';
import { ConfirmationService } from 'primeng/components/common/confirmationservice';

import { CategoriesService } from '@ikubinfo/core/services/categories.service';



@Component({
  selector: 'ikubinfo-subscriptions',
  templateUrl: './subscriptions.component.html',
  styleUrls: ['./subscriptions.component.css']
})
export class SubscriptionsComponent implements OnInit {
  subscriptions: Object;
 
  constructor(private confirmationService: ConfirmationService,private categoryService: CategoriesService, private router: Router,private logger: LoggerService ) { 
    this.subscriptions=[];
  }
  
  ngOnInit() {
    this.loadSubscribedCategories();
  }

  loadSubscribedCategories() : void{
    this.categoryService.getSubscribedCategories().subscribe(data=>{
      this.subscriptions=data;
  },
  err=>{
    this.logger.error("Error","Something bad happened!");
  });
  }
  unsubscribe(id:number): void{
    this.confirmationService.confirm({
      message: 'Are you sure you want to unsubscribe to this category?',
      header: 'Unsubscribe Confirmation',
      icon: 'pi pi-info-circle',
      accept: () => {
    this.categoryService.unsubscribe(id).subscribe(res=>{
      this.loadSubscribedCategories();
      this.logger.info("Info", "Unsubscribed successfully!");
    },err=>{
      this.logger.error("Error", "Something bad happened.");
    });
  } 
  });
}

  viewPosts(id:number): void{
    this.router.navigate(['suggestion/categories/'+id+'/posts']);
  }

  
    
}
