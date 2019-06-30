import { Component, OnInit } from '@angular/core';
import { CategoryService } from '@ikubinfo/core/services/category.service';
import { LoggerService } from '@ikubinfo/core/utilities/logger.service';
import { Category } from '@ikubinfo/core/models/category';
import { ConfirmationService } from 'primeng/components/common/confirmationservice';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'ikubinfo-list-of-proposals',
  templateUrl: './list-of-proposals.component.html',
  styleUrls: ['./list-of-proposals.component.css']
})
export class ListOfProposalsComponent implements OnInit {
  categories: Object;
  cols: any[];
  constructor(private categoryService: CategoryService,private logger:LoggerService, private router: Router, private active: ActivatedRoute,
    private confirmationService: ConfirmationService) { }

  ngOnInit() {
    this.categories=[];
    this.loadSuggestions();
  }
  

  loadSuggestions(){
    return this.categoryService.getSuggestions().subscribe(res=>{
      this.categories=res;
    },
    err=>{
     this.logger.error("Error","Something bad happened");
    })
  }

  deleteSuggestion(suggestion: Category){
    this.confirmationService.confirm({
      message: 'Do you want to delete this record?',
      header: 'Delete Confirmation',
      icon: 'pi pi-info-circle',
      accept: () => {
        return this.categoryService.deleteSuggestion(suggestion.categoryId).subscribe(res => {
          this.logger.info('Confirmed', 'Record deleted');
          this.loadSuggestions();
        },
        err => {
          this.logger.error('Error', 'An error accured');
        });
      }
    });
  }

  addPost(){
    this.router.navigate(['suggestion/proposal']);
  }
}
