import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
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
   id: number;
   categoryForm: FormGroup
  category: Category;
  constructor(private categoriesService: CategoriesService,private logger: LoggerService,private fb: FormBuilder,private router: Router) { }

  ngOnInit() {
    
    this.categoryForm=this.fb.group({
      title: [''] ,
      description: [''],
    });
    this.category={

    }
    
    }

    reset(): void {
      this.fillForm(this.category);
    }

 loadData(id: number){
    return this.categoriesService.getCategoryById(id).subscribe(res=>{
      this.category=res;
      this.id=id;
      console.log(this.category);
    });
  }

  submit(){
    console.log(this.categoriesService.getId());
    this.category.categoryName=this.categoryForm.value.title;
    this.category.categoryDescription=this.categoryForm.value.description;
    return this.categoriesService.edit(this.categoriesService.getId(),this.category).subscribe(res=>{
      this.router.navigate(['suggestion/categories']);
      this.logger.success("Success", "Data saved");
    }, err=>{
      this.logger.error("Error", "Category name exists.");
    });
  }

  fillForm(data: Category={}){
    this.categoryForm.get('title').setValue(data.categoryName);
    this.categoryForm.get('description').setValue(data.categoryDescription);
  }
 
}
