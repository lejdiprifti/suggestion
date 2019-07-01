import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

import { Category } from '@ikubinfo/core/models/category';
import { Router } from '@angular/router';
import { LoggerService } from '@ikubinfo/core/utilities/logger.service';
import { CategoriesService } from '@ikubinfo/core/services/categories.service';

@Component({
  selector: 'ikubinfo-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit {
   categoryForm: FormGroup
  category: Category;
  icons: Array<String>;
  constructor(private categoriesService: CategoriesService,private logger: LoggerService,private fb: FormBuilder,private router: Router) { }

  ngOnInit() {
    this.loadData();
    this.categoryForm=this.fb.group({
      title: ['', Validators.required] ,
      icon: [''],
      description: [''],
    });
    this.category={

    }
    this.icons=[
      'sports',
      'music',
      'nature',
      'restaurants',
      'hotels',
      'news',
      'cars',
      'holidays',
      'technology',
    ]
    }

    reset(): void {
      this.fillForm(this.category);
    }

 loadData(){
    return this.categoriesService.getCategoryById(this.categoriesService.getId()).subscribe(res=>{
      this.category=res;
      this.categoryForm.get('title').setValue(this.category.categoryName);
    this.categoryForm.get('description').setValue(this.category.categoryDescription);
    this.categoryForm.get('icon').setValue(this.category.icon);
    });
  }

  submit(){
    this.category.categoryName=this.categoryForm.value.title;
    this.category.categoryDescription=this.categoryForm.value.description;
    this.category.icon=this.categoryForm.value.icon;
    this.categoriesService.edit(this.categoriesService.getId(),this.category).subscribe(res=>{
      this.router.navigate(['suggestion/categories']);
      this.logger.success("Success", "Data saved");
    }, err=>{
      this.logger.error("Error", "Category name exists.");
    });
  }

  fillForm(data: Category={}){
    this.categoryForm.get('title').setValue(data.categoryName);
    this.categoryForm.get('description').setValue(data.categoryDescription);
    this.categoryForm.get('icon').setValue(data.icon);
  }
  
  changeIcon(e) {
   return this.category.icon = e.target.value, {
    onlySelf: true
    }
  }
}
