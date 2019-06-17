import { Component, OnInit } from '@angular/core';
import { CategoryService } from '@ikubinfo/core/services/category.service';
import { Router } from '@angular/router';


@Component({
  selector: 'ikubinfo-subscriptions',
  templateUrl: './subscriptions.component.html',
  styleUrls: ['./subscriptions.component.css']
})
export class SubscriptionsComponent implements OnInit {
  subscriptions: Object;
  constructor(private categoryService: CategoryService, private router: Router) { 
    this.subscriptions=[];
  }
  
  ngOnInit() {
    this.categoryService.getSubscribedCategories().subscribe(data=>{
        this.subscriptions=data;
        console.log(this.subscriptions);
    });
  }
  unsubscribe(id:number){
    this.categoryService.unsubscribe(id).subscribe(res=>{
      this.router.navigate(['/suggestion/subscriptions']);
    });
  }

}
