import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { CategoriesService } from '../services/categories.service';
import { Category } from '@ikubinfo/core/models/category';
import { Router } from '@angular/router';
import { LoggerService } from '@ikubinfo/core/utilities/logger.service';

@Component({
  selector: 'ikubinfo-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit {
   categoryForm: FormGroup
  category: Category;
  constructor(private categoriesService: CategoriesService,private logger: LoggerService,private fb: FormBuilder,private router: Router) { }

  ngOnInit() {
    this.loadData();
    this.categoryForm=this.fb.group({
      title: ['', Validators.required] ,
      description: ['' , Validators.required],
    });
    this.category={

    }
    
    }

    reset(): void {
      this.fillForm(this.category);
    }

 loadData(){
    return this.categoriesService.getCategoryById(this.categoriesService.getId()).subscribe(res=>{
      this.category=res;
      this.categoryForm.get('title').setValue(this.category.categoryName);
    this.categoryForm.get('description').setValue(this.category.categoryDescription);
    });
  }

  submit(){
    console.log(this.categoriesService.getId());
    this.category.categoryName=this.categoryForm.value.title;
    this.category.categoryDescription=this.categoryForm.value.description;
    console.log(this.category);
    this.categoriesService.edit(this.categoriesService.getId(),this.category).subscribe(res=>{
      this.router.navigate(['suggestion/categories']);
      this.logger.success("Success", "Data saved");
    }, err=>{
      console.log(this.category);
      this.logger.error("Error", "Category name exists.");
    });
  }

  fillForm(data: Category={}){
    this.categoryForm.get('title').setValue(data.categoryName);
    this.categoryForm.get('description').setValue(data.categoryDescription);
  }
  
  
}
