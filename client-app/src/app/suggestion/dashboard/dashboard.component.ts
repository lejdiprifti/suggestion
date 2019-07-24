import { Component, OnInit, Input } from '@angular/core';
import { SuggestionService } from '@ikubinfo/core/services/suggestion.service';
import { Router } from '@angular/router';
import { LoggerService } from '@ikubinfo/core/utilities/logger.service';
import { ConfirmationService } from 'primeng/components/common/confirmationservice';
import { CategoriesService } from '@ikubinfo/core/services/categories.service';
import { Category } from '@ikubinfo/core/models/category';

@Component({
  selector: 'ikubinfo-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})


export class DashboardComponent implements OnInit {
  categories: Category;
  constructor(private confirmationService: ConfirmationService,private logger: LoggerService,private categoryService: CategoriesService, private router: Router) { 
    this.categories={};
  }

  ngOnInit() {
   this.loadData();
  }

 subscribe(id: number) : void{
  this.confirmationService.confirm({
    message: 'Do you want to subscribe to this category?',
    header: 'Subscribe Confirmation',
    icon: 'pi pi-info-circle',
    accept: () => {
   this.categoryService.subscribe(id).subscribe(res=>{
     this.loadData();
     this.logger.success("Success","You subscribed successfully!");
   },
   err=>{
     this.logger.error("Error","Something bad happened.");
   });
 }
  });
}

loadData() : void{
  this.categoryService.getUnsubscribedCategories().subscribe(res=>{
    this.categories=res;
  },err=>{
    this.logger.error("Error","Something bad happened.");
  });
}

getSubscriptionByName(keyword: string):void{
  this.categoryService.getUnsubscribedCategoriesByName(keyword).subscribe(data=>{
    this.categories=data;
  },
  err=>{
    this.logger.error("Error", "Something bad happened.");
  });
}
}
