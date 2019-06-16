import { Component, OnInit } from '@angular/core';
import { CategoryService } from '@ikubinfo/core/services/category.service';

@Component({
  selector: 'ikubinfo-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})


export class DashboardComponent implements OnInit {
  categories: Object;


  constructor(private categoryService: CategoryService) { 
    this.categories=[];
  }

  ngOnInit() {
    this.categoryService.getAllCategories().subscribe(res=>{
      this.categories=res;
    });
  }

 subscribe(id: number){
   this.categoryService.subscribe(id).subscribe(res=>{
   });
 }
}
