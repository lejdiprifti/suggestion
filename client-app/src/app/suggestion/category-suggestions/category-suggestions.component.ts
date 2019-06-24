import { Component, OnInit } from '@angular/core';
import { CategoryService } from '@ikubinfo/core/services/category.service';
import { Category } from '@ikubinfo/core/models/category';
import { LoggerService } from '@ikubinfo/core/utilities/logger.service';

@Component({
  selector: 'ikubinfo-category-suggestions',
  templateUrl: './category-suggestions.component.html',
  styleUrls: ['./category-suggestions.component.css']
})
export class CategorySuggestionsComponent implements OnInit {

  
  category: Category;

  
  constructor(private categoryService: CategoryService,private logger: LoggerService) { }

  ngOnInit() {
    this.initInputData();
  }

  initInputData() {
    this.category = {
      categoryName: '',
      categoryDescription: ''
    }
  }
  clearData() {
    this.initInputData();
  }
  async addData($event): Promise<void> {
    try {
    const category = await this.categoryService.createCategory(this.category);
    this.logger.success("Add Success", "Category was added successfully");  
  
  } catch(e) {
    this.logger.error("Error Happened", "Category was added successfully");
  }
   
  }

}
