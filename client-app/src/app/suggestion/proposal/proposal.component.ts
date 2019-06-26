import { Component, OnInit } from '@angular/core';
import { Category } from '@ikubinfo/core/models/category';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { CategoryService } from '@ikubinfo/core/services/category.service';
import { LoggerService } from '@ikubinfo/core/utilities/logger.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'ikubinfo-proposal',
  templateUrl: './proposal.component.html',
  styleUrls: ['./proposal.component.css']
})
export class ProposalComponent implements OnInit {

  category: Category;
  suggestionForm: FormGroup;
  
  constructor(private fb: FormBuilder, private categoryService: CategoryService,private logger: LoggerService
    ,private active: ActivatedRoute) { }

  ngOnInit() {
    this.initInputData();
  }

  initInputData() {
    this.suggestionForm=this.fb.group({
      categoryName: ['' , Validators.required],
      categoryDescription:  ['' , Validators.required],
    });   
    }

  clearData() {
    this.initInputData();
  }

  getData(){
    return {
      categoryName: this.suggestionForm.get('categoryName').value,
      categoryDescription: this.suggestionForm.get('categoryDescription').value
    }
  }

  async addData($event): Promise<void> {
    const id = this.active.snapshot.paramMap.get('id');
   this.categoryService.updateSuggestion(id,this.getData()).subscribe(res=>{
     console.log(this.getData());
     this.logger.success("Success", "Category was added successfully"); 
   }, 
   err=>{
     this.logger.error("Error", "Category already exists.");
   });
    
   
  }

}
