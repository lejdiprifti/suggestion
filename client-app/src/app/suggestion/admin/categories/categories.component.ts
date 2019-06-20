import { Component, OnInit } from '@angular/core';

import { LoggerService } from '@ikubinfo/core/utilities/logger.service';
import { CategoriesService } from '../services/categories.service';
import { CategoryComponent } from '../category/category.component';
import { Router } from '@angular/router';


@Component({
  selector: 'ikubinfo-dashboard',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css']
})

export class CategoriesComponent implements OnInit {
  categories: Object;
  constructor(private categoriesService: CategoriesService,private router: Router,
     private logger: LoggerService, private categoryComponent: CategoryComponent) { }

  ngOnInit() {
    this.categories=[];
    this.getAllCategories();
  }

  getAllCategories(){
    return this.categoriesService.getCategories().subscribe(res=>{
      this.categories=res;
    })
  }
  edit(id:number){
    this.categoriesService.setId(id);
    this.router.navigate(['suggestion/category']);
  }

  delete(id: number){
    return this.categoriesService.delete(id).subscribe(res=>{
      this.logger.info("Deleted","Category deleted successfully.");
      this.getAllCategories();
    })
  }

  add(){
    this.router.navigate(['suggestion/category/add']);
  }
}
