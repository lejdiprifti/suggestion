import { Component, OnInit } from '@angular/core';
import { CategoryService } from '@ikubinfo/core/services/category.service';
import { Router } from '@angular/router';
import { LoggerService } from '@ikubinfo/core/utilities/logger.service';



@Component({
  selector: 'ikubinfo-subscriptions',
  templateUrl: './subscriptions.component.html',
  styleUrls: ['./subscriptions.component.css']
})
export class SubscriptionsComponent implements OnInit {
  subscriptions: Object;
 
  constructor(private categoryService: CategoryService, private router: Router,private logger: LoggerService ) { 
    this.subscriptions=[];
  }
  
  ngOnInit() {
    this.loadSubscribedCategories();
  }

  loadSubscribedCategories(){
    this.categoryService.getSubscribedCategories().subscribe(data=>{
      this.subscriptions=data;
  });
  }
  unsubscribe(id:number){
    this.categoryService.unsubscribe(id).subscribe(res=>{
      this.loadSubscribedCategories();
      this.logger.info("Info", "Unsubscribed successfully!");
    }); 
  }

  viewPosts(id:number){
    this.router.navigate(['suggestion/categories/'+id+'/posts']);
  }

  
}
