import { Component, OnInit } from '@angular/core';
import { CategoryService } from '@ikubinfo/core/services/category.service';
import { LoggerService } from '@ikubinfo/core/utilities/logger.service';
import { MenuItem } from 'primeng/components/common/menuitem';
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
  items: MenuItem[];
  selectedSuggestion: Category;
  cols: any[];
  constructor(private categoryService: CategoryService,private logger:LoggerService, private router: Router, private active: ActivatedRoute,
    private confirmationService: ConfirmationService) { }

  ngOnInit() {
    this.categories=[];
    this.loadSuggestions();
    
    this.items = [
      { label: 'Edit', icon: 'pi pi-pencil', command: (event) => this.viewSuggestion(this.selectedSuggestion) },
      { label: 'Delete', icon: 'pi pi-times', command: (event) => this.deleteSuggestion(this.selectedSuggestion) }
    ];
    
    this.cols = [
      { field: 'categoryName', header: 'Name' },
      { field: 'categoryDescription', header: 'Description' },
      { field: 'categoryState', header: 'Status'}
    ];
  }

  viewSuggestion(suggestion: Category) {
    this.router.navigate(['propose/'+suggestion.categoryId], { relativeTo: this.active.parent });
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
