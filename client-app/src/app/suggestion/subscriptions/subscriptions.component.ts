import { Component, OnInit } from '@angular/core';
import { CategoryService } from '@ikubinfo/core/services/category.service';
import { Router } from '@angular/router';
import { LoggerService } from '@ikubinfo/core/utilities/logger.service';
import { ConfirmationService } from 'primeng/components/common/confirmationservice';



@Component({
  selector: 'ikubinfo-subscriptions',
  templateUrl: './subscriptions.component.html',
  styleUrls: ['./subscriptions.component.css']
})
export class SubscriptionsComponent implements OnInit {
  subscriptions: Object;
 
  constructor(private confirmationService: ConfirmationService,private categoryService: CategoryService, private router: Router,private logger: LoggerService ) { 
    this.subscriptions=[];
  }
  
  ngOnInit() {
    this.loadSubscribedCategories();
  }

  loadSubscribedCategories(){
    this.categoryService.getSubscribedCategories().subscribe(data=>{
      this.subscriptions=data;
  });
  }
  unsubscribe(id:number){
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

  viewPosts(id:number){
    this.router.navigate(['suggestion/categories/'+id+'/posts']);
  }

  
    
}
