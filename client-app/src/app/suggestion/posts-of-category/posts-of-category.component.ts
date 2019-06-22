import { Component, OnInit } from '@angular/core';
import { CategoryService } from '@ikubinfo/core/services/category.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'ikubinfo-posts-of-category',
  templateUrl: './posts-of-category.component.html',
  styleUrls: ['./posts-of-category.component.css']
})
export class PostsOfCategoryComponent implements OnInit {
  
  posts: Object;
  constructor(private categoryService: CategoryService,private active: ActivatedRoute) { }

  ngOnInit() {
    this.loadPosts();
  }

  loadPosts(){
    const id = this.active.snapshot.paramMap.get('id')
    this.categoryService.getPostsOfCategory(Number(id)).subscribe(res=>{
      this.posts=res;
    },
    err=>{
    
    });
  }
}
