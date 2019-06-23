import { Component, OnInit, Input } from '@angular/core';
import { CategoryService } from '@ikubinfo/core/services/category.service';
import { Router } from '@angular/router';

@Component({
  selector: 'ikubinfo-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})


export class DashboardComponent implements OnInit {
  categories: any;
  text: string;
  constructor(private categoryService: CategoryService, private router: Router) { 
    this.categories=[];
    this.text='';
  }

  ngOnInit() {
   this.loadData();
  }

 subscribe(id: number){
   this.categoryService.subscribe(id).subscribe(res=>{
     this.loadData();
   });
 }

loadData(){
  this.categoryService.getAllCategories().subscribe(res=>{
    this.categories=res;
    this.categories.forEach((category) => {
         this.categoryService.isSubscribed(category.categoryId).subscribe(res=>{
           category.isSubscribed = true;
         });
      });
  });
}
}
