import { Component, OnInit } from '@angular/core';
import { CategoryService } from '@ikubinfo/core/services/category.service';
import { Router } from '@angular/router';

@Component({
  selector: 'ikubinfo-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})


export class DashboardComponent implements OnInit {
  categories: Object;


  constructor(private categoryService: CategoryService, private router: Router) { 
    this.categories=[];
  }

  ngOnInit() {
    this.categoryService.getAllCategories().subscribe(res=>{
      this.categories=res;
      console.log(this.categories);
    });
  }

 subscribe(id: number){
   this.categoryService.subscribe(id).subscribe(res=>{
     this.router.navigate(['/suggestion/dashboard']);
   });
 }
}
