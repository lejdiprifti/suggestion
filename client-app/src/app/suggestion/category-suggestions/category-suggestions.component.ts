import { Component, OnInit } from '@angular/core';
import { CategoryService } from '@ikubinfo/core/services/category.service';
import { Category } from '@ikubinfo/core/models/category';
import { LoggerService } from '@ikubinfo/core/utilities/logger.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'ikubinfo-category-suggestions',
  templateUrl: './category-suggestions.component.html',
  styleUrls: ['./category-suggestions.component.css']
})
export class CategorySuggestionsComponent implements OnInit {

  
  category: Category;
  suggestionForm: FormGroup;
  icons: Array<String>;
  constructor(private router: Router,private fb: FormBuilder, private categoryService: CategoryService,private logger: LoggerService) { }

  ngOnInit() {
    this.initInputData();
  }

  initInputData() {
    this.suggestionForm=this.fb.group({
      categoryName: ['' , Validators.required],
      icon:[''],
      categoryDescription:  [''],
      
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

  clearData() {
    this.initInputData();
  }

  getData(){
    return {
      categoryName: this.suggestionForm.get('categoryName').value,
      categoryDescription: this.suggestionForm.get('categoryDescription').value,
      icon: this.suggestionForm.get('icon').value
    }
  }

  async addData($event): Promise<void> {
 
   this.categoryService.suggestCategory(this.getData()).subscribe(res=>{
     this.router.navigate(['suggestion/propose']);
     this.logger.success("Success", "Category was proposed successfully."); 
   }, 
   err=>{
     this.logger.error("Error", "Category already exists.");
   });
    
   
  }

  changeIcon(e) {
    this.getData().icon = e.target.value, {
    onlySelf: true
    }
  }
}
