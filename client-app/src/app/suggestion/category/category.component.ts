import { Component, OnInit } from '@angular/core';
import { Category } from '@ikubinfo/core/models/category';

import { FormGroup, FormBuilder, Validators } from '@angular/forms';

import { Router, ActivatedRoute } from '@angular/router';
import { LoggerService } from '@ikubinfo/core/utilities/logger.service';
import { ConfirmationService, SelectItem } from 'primeng/primeng';
import { CategoriesService } from '@ikubinfo/core/services/categories.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'ikubinfo-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit {
  category: Category;
  categoryForm: FormGroup;
  icons: Array<SelectItem>;
  constructor(private active: ActivatedRoute,private confirmationService: ConfirmationService,private logger: LoggerService,private categoriesService: CategoriesService, private router: Router,private fb: FormBuilder) { }
 
  ngOnInit() {
    this.categoryForm=this.fb.group({
      categoryName: ['' , Validators.required] ,
      icon: [''],
      categoryDescription: ['' ],
    });
    this.loadData();
    this.icons=[
      {label :'sports', value: 'sports'},
      {label :'music', value: 'music'},
      {label :'nature', value: 'nature'},
      {label :'restaurants', value: 'restaurants'},
      {label: 'hotels', value:'hotels'},
      { label: 'news', value: 'news'},
      {label: 'cars', value: 'cars'},
      {label: 'holidays', value: 'holidays'},
      {label: 'technology', value: 'technology'}
    ];
  
    
  }

  reset(): void {
    this.fillForm(this.category);
  }

  submit(): void {
    if (this.category){
      this.categoriesService.edit(this.category.categoryId,this.getData()).subscribe(res=>{
        this.router.navigate(['suggestion/categories']);
        this.logger.success("Success", "Data saved");
      }, err=>{
        this.logger.error("Error", "Category name exists.");
      });
    }
    else{
      this.confirmationService.confirm({
        message: 'Are you sure you want to add this category?',
        header: 'Accept Confirmation',
        icon: 'pi pi-info-circle',
        accept: () => {
      return this.categoriesService.add(this.getData()).subscribe(res=>{
        this.router.navigate(['suggestion/categories']);
        this.logger.success("Success", "Category was successfully created.");
      }, err=>{
        this.logger.error("Error", "Category name already exists.");
      });
    }
  });
    }
  }
  getData(): Category {
    return {
      categoryName: this.categoryForm.get('categoryName').value,
      categoryDescription: this.categoryForm.get('categoryDescription').value,
      icon: this.categoryForm.get('icon').value || 'sports'
    }

  }
  fillForm(data: Category={}): void{
    this.categoryForm.get('categoryName').setValue(data.categoryName);
    this.categoryForm.get('categoryDescription').setValue(data.categoryDescription);
    this.categoryForm.get('icon').setValue(data.icon);
  }

  loadData(): Subscription{ 
    const id = this.active.snapshot.paramMap.get('id');
    if (id) {
    return this.categoriesService.getCategoryById(Number(id)).subscribe(res=>{
      this.category=res;
      this.categoryForm.get('categoryName').setValue(this.category.categoryName);
    this.categoryForm.get('categoryDescription').setValue(this.category.categoryDescription);
    this.categoryForm.get('icon').setValue(this.category.icon);
    },
    err=>{
      this.logger.error("Error","Something bad happened.");
    });
  }
  }

}
  