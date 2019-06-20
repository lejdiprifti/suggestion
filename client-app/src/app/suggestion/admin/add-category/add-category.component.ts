import { Component, OnInit } from '@angular/core';
import { Category } from '@ikubinfo/core/models/category';
import { CategoriesService } from '../services/categories.service';
import { FormGroup, FormBuilder } from '@angular/forms';

import { Router } from '@angular/router';
import { LoggerService } from '@ikubinfo/core/utilities/logger.service';

@Component({
  selector: 'ikubinfo-add-category',
  templateUrl: './add-category.component.html',
  styleUrls: ['./add-category.component.css']
})
export class AddCategoryComponent implements OnInit {

  constructor(private logger: LoggerService,private categoriesService: CategoriesService, private router: Router,private fb: FormBuilder) { }
  category: Category;
  categoryForm: FormGroup;
  ngOnInit() {
    this.category={};
    this.categoryForm=this.fb.group({
      title: [''] ,
      description: [''],
    });
  }

  reset(): void {
    this.fillForm(this.category);
  }

  submit(){
    console.log(this.categoriesService.getId());
    this.category.categoryName=this.categoryForm.value.title;
    this.category.categoryDescription=this.categoryForm.value.description;
    console.log(this.category);
    return this.categoriesService.add(this.category).subscribe(res=>{
      this.router.navigate(['suggestion/categories']);
      this.logger.success("Success", "Category created");
    }, err=>{
      this.logger.error("Error", "Category name exists.");
    });
  }

  fillForm(data: Category={}){
    this.categoryForm.get('title').setValue(data.categoryName);
    this.categoryForm.get('description').setValue(data.categoryDescription);
  }
}
