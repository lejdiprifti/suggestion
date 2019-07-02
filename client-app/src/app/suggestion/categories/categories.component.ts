import { Component, OnInit } from '@angular/core';

import { LoggerService } from '@ikubinfo/core/utilities/logger.service';

import { Router, ActivatedRoute } from '@angular/router';
import { ConfirmationService } from 'primeng/components/common/confirmationservice';
import { Category } from '@ikubinfo/core/models/category';
import { MenuItem } from 'primeng/components/common/menuitem';

import { CategoriesService } from '@ikubinfo/core/services/categories.service';
import { Subscription } from 'rxjs';


@Component({
  selector: 'ikubinfo-dashboard',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css']
})

export class CategoriesComponent implements OnInit {
  categories: Object;
  items: MenuItem[];
  cols: Array<{field:string, header:string}>;
  selectedCategory: Category;
  constructor(private confirmationService: ConfirmationService, private categoriesService: CategoriesService,private router: Router,
     private logger: LoggerService,private active: ActivatedRoute) { }

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

  getAllCategories() : Subscription {
    return this.categoriesService.getCategories().subscribe(res=>{
      this.categories=res;
    },
      err => {
        this.logger.error('Error', 'An error accured');
    });
  }
  edit(category: Category){
 
    this.router.navigate(['suggestion/category/'+category.categoryId]);
  }

  delete(category: Category) : void{
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

  add(): void{
    this.router.navigate(['suggestion/category/add']);
  }
}
