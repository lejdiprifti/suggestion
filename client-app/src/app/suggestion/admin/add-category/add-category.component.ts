import { Component, OnInit } from '@angular/core';
import { Category } from '@ikubinfo/core/models/category';
import { CategoriesService } from '../services/categories.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

import { Router } from '@angular/router';
import { LoggerService } from '@ikubinfo/core/utilities/logger.service';
import { ConfirmationService } from 'primeng/primeng';

@Component({
  selector: 'ikubinfo-add-category',
  templateUrl: './add-category.component.html',
  styleUrls: ['./add-category.component.css']
})
export class AddCategoryComponent implements OnInit {

  constructor(private confirmationService: ConfirmationService,private logger: LoggerService,private categoriesService: CategoriesService, private router: Router,private fb: FormBuilder) { }
  category: Category;
  categoryForm: FormGroup;
  icons: Array<String>;
  ngOnInit() {
    this.category={};
    this.categoryForm=this.fb.group({
      title: ['' , Validators.required] ,
      icon: [''],
      description: ['' ],
    });
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

  submit(){
    this.category.categoryName=this.categoryForm.value.title;
    this.category.categoryDescription=this.categoryForm.value.description;
    this.category.icon=this.categoryForm.value.icon;
    this.confirmationService.confirm({
      message: 'Are you sure you want to add this category?',
      header: 'Accept Confirmation',
      icon: 'pi pi-info-circle',
      accept: () => {
    return this.categoriesService.add(this.category).subscribe(res=>{
      this.router.navigate(['suggestion/categories']);
      this.logger.success("Success", "Category was successfully created.");
    }, err=>{
      this.logger.error("Error", "Category name already exists.");
    });
  }
});
    }
  fillForm(data: Category={}){
    this.categoryForm.get('title').setValue(data.categoryName);
    this.categoryForm.get('description').setValue(data.categoryDescription);
    this.categoryForm.get('icon').setValue(data.icon);
  }

  
  changeIcon(e) {
    this.category.icon = e.target.value, {
    onlySelf: true
    }
  }

}
