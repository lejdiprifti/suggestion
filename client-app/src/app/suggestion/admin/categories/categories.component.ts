import { Component, OnInit } from '@angular/core';

import { LoggerService } from '@ikubinfo/core/utilities/logger.service';
import { CategoriesService } from '../services/categories.service';
import { CategoryComponent } from '../category/category.component';
import { Router } from '@angular/router';
import { ConfirmationService } from 'primeng/components/common/confirmationservice';


@Component({
  selector: 'ikubinfo-dashboard',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css']
})

export class CategoriesComponent implements OnInit {
  categories: Object;
  constructor(private confirmationService: ConfirmationService, private categoriesService: CategoriesService,private router: Router,
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
    this.confirmationService.confirm({
      message: 'Do you want to delete this category?',
      header: 'Delete Confirmation',
      icon: 'pi pi-info-circle',
      accept: () => {
        return this.categoriesService.delete(id).subscribe(res=>{
          this.logger.info("Deleted","Category deleted successfully.");
          this.getAllCategories();
        },
        err => {
          this.logger.error('Error', 'An error accured');
        });
      }
    });
  }

  add(){
    this.router.navigate(['suggestion/category/add']);
  }
}
