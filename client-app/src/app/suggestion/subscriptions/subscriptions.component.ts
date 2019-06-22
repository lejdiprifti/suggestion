import { Component, OnInit } from '@angular/core';
import { CategoryService } from '@ikubinfo/core/services/category.service';
import { Router } from '@angular/router';
import { PostsOfCategoryComponent } from '../posts-of-category/posts-of-category.component';


@Component({
  selector: 'ikubinfo-subscriptions',
  templateUrl: './subscriptions.component.html',
  styleUrls: ['./subscriptions.component.css']
})
export class SubscriptionsComponent implements OnInit {
  subscriptions: Object;
  constructor(private categoryService: CategoryService, private router: Router ) { 
    this.subscriptions=[];
  }
  
  ngOnInit() {
    this.loadSubscribedCategories();
  }

  loadSubscribedCategories(){
    this.categoryService.getSubscribedCategories().subscribe(data=>{
      this.subscriptions=data;
      console.log(this.subscriptions);
  });
  }
  unsubscribe(id:number){
    this.categoryService.unsubscribe(id).subscribe(res=>{
      this.router.navigate(['suggestion/subscriptions']);
    }); 
  }

  viewPosts(id:number){
    this.router.navigate(['suggestion/categories/'+id+'/posts']);
  }
}
