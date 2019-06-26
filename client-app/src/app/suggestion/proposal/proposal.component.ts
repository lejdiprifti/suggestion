import { Component, OnInit } from '@angular/core';
import { Category } from '@ikubinfo/core/models/category';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { CategoryService } from '@ikubinfo/core/services/category.service';
import { LoggerService } from '@ikubinfo/core/utilities/logger.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'ikubinfo-proposal',
  templateUrl: './proposal.component.html',
  styleUrls: ['./proposal.component.css']
})
export class ProposalComponent implements OnInit {

  category: Category;
  suggestionForm: FormGroup;
  
  constructor(private fb: FormBuilder, private categoryService: CategoryService,private logger: LoggerService
    ,private active: ActivatedRoute, private router: Router) { }

  ngOnInit() {
    this.initInputData();
  }

  initInputData() {
    this.fillForm();
    this.suggestionForm=this.fb.group({
      categoryName: ['' , Validators.required],
      categoryDescription:  ['' , Validators.required],
    });   
    this.category={

    }
    }

  clearData() {
    this.fillForm();
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
     this.router.navigate(['suggestion/propose']);
     console.log(this.getData());
     this.logger.success("Success", "Category was added successfully"); 
   }, 
   err=>{
     this.logger.error("Error", "Category already exists.");
   });
    
   
  }

  fillForm(){
    const id = this.active.snapshot.paramMap.get('id');
    return this.categoryService.getSuggestionById(id).subscribe(res=>{
      this.category=res;
      this.suggestionForm.get('categoryName').setValue(this.category.categoryName);
    this.suggestionForm.get('categoryDescription').setValue(this.category.categoryDescription);
    })
    
  }
}

