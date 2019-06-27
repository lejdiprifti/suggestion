import { Component, OnInit, Input } from '@angular/core';
import { CategoryService } from '@ikubinfo/core/services/category.service';
import { Router } from '@angular/router';
import { LoggerService } from '@ikubinfo/core/utilities/logger.service';
import { ConfirmationService } from 'primeng/components/common/confirmationservice';

@Component({
  selector: 'ikubinfo-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})


export class DashboardComponent implements OnInit {
  categories: any;
  constructor(private confirmationService: ConfirmationService,private logger: LoggerService,private categoryService: CategoryService, private router: Router) { 
    this.categories=[];
  }

  ngOnInit() {
   this.loadData();
  }

 subscribe(id: number){
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

loadData(){
  this.categoryService.getUnsubscribedCategories().subscribe(res=>{
    this.categories=res;
  },err=>{
    this.logger.error("Error","Something bad happened.");
  });
}
}
