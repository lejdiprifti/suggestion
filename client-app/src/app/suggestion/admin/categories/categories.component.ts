import { Component, OnInit } from '@angular/core';

import { LoggerService } from '@ikubinfo/core/utilities/logger.service';
import { CategoriesService } from '../services/categories.service';
import { CategoryComponent } from '../category/category.component';
import { Router, ActivatedRoute } from '@angular/router';
import { ConfirmationService } from 'primeng/components/common/confirmationservice';
import { Category } from '@ikubinfo/core/models/category';
import { MenuItem } from 'primeng/components/common/menuitem';


@Component({
  selector: 'ikubinfo-dashboard',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css']
})

export class CategoriesComponent implements OnInit {
  categories: Object;
  items: MenuItem[];
  cols: any[];
  selectedCategory: Category;
  constructor(private confirmationService: ConfirmationService, private categoriesService: CategoriesService,private router: Router,
     private logger: LoggerService, private categoryComponent: CategoryComponent,private active: ActivatedRoute) { }

  ngOnInit() {
    this.categories=[];
    this.getAllCategories();

    this.items = [
      { label: 'Edit', icon: 'pi pi-pencil', command: (event) => this.edit(this.selectedCategory) },
      { label: 'Delete', icon: 'pi pi-times', command: (event) => this.delete(this.selectedCategory) }
    ];
    
    this.cols = [
      { field: 'categoryName', header: 'Name' },
      { field: 'categoryDescription', header: 'Description' },
      {field: 'icon', header: 'Icon'}
    ];
  }

  getAllCategories(){
    return this.categoriesService.getCategories().subscribe(res=>{
      this.categories=res;
    },
      err => {
        this.logger.error('Error', 'An error accured');
    });
  }
  edit(category: Category){
    this.categoriesService.setId(category.categoryId);
    this.router.navigate(['suggestion/category']), { relativeTo: this.active.parent };
  }

  delete(category: Category){
    this.confirmationService.confirm({
      message: 'Do you want to delete this category?',
      header: 'Delete Confirmation',
      icon: 'pi pi-info-circle',
      accept: () => {
        return this.categoriesService.delete(category.categoryId).subscribe(res=>{
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
    this.router.navigate(['suggestion/category/add']) ,{ relativeTo: this.active.parent };
  }
}
