import { Component, OnInit } from '@angular/core';
import { SuggestionService } from '@ikubinfo/core/services/suggestion.service';
import { Category } from '@ikubinfo/core/models/category';
import { LoggerService } from '@ikubinfo/core/utilities/logger.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SelectItem } from 'primeng/primeng';

@Component({
  selector: 'ikubinfo-category-suggestions',
  templateUrl: './category-suggestions.component.html',
  styleUrls: ['./category-suggestions.component.css']
})
export class CategorySuggestionsComponent implements OnInit {

  
  category: Category;
  suggestionForm: FormGroup;
  icons: Array<SelectItem>;
  constructor(private router: Router,private fb: FormBuilder, private categoryService: SuggestionService,private logger: LoggerService) { }

  ngOnInit() {
    this.initInputData();
  }

  initInputData() :void {
    this.suggestionForm=this.fb.group({
      categoryName: ['' , Validators.required],
      icon:[''],
      categoryDescription:  [''],
      
    });   
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
    ]
  
    }

  clearData() :void {
    this.initInputData();
  }

  getData() : Category{
    return {
      categoryName: this.suggestionForm.get('categoryName').value,
      categoryDescription: this.suggestionForm.get('categoryDescription').value,
      icon: this.suggestionForm.get('icon').value
    }
  }

   addData(): void {
 
   this.categoryService.suggestCategory(this.getData()).subscribe(res=>{
     this.router.navigate(['suggestion/propose']);
     this.logger.success("Success", "Category was proposed successfully."); 
   }, 
   err=>{
     this.logger.error("Error", "Category already exists.");
   });
    
   
  }

}
