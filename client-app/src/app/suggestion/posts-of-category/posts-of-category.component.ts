import { Component, OnInit } from '@angular/core';
import { SuggestionService } from '@ikubinfo/core/services/suggestion.service';
import { ActivatedRoute } from '@angular/router';
import { post } from 'selenium-webdriver/http';
import { Post } from '@ikubinfo/core/models/post';
import { PostService } from '@ikubinfo/core/services/post.service';
import { LoggerService } from '@ikubinfo/core/utilities/logger.service';
import { CategoriesService } from '@ikubinfo/core/services/categories.service';

@Component({
  selector: 'ikubinfo-posts-of-category',
  templateUrl: './posts-of-category.component.html',
  styleUrls: ['./posts-of-category.component.css']
})
export class PostsOfCategoryComponent implements OnInit {
  
  posts: Object;
  constructor(private logger: LoggerService,private categoryService: CategoriesService,private postService: PostService,private active: ActivatedRoute) { }

  ngOnInit() {
    this.posts={};
    this.loadPosts();
  }

  loadPosts() : void{
    const id = this.active.snapshot.paramMap.get('id')
    this.categoryService.getPostsOfCategory(Number(id)).subscribe(res=>{
      this.posts=res;
      console.log(this.posts);
    },
    err=>{
      this.logger.error("Error", "Something bad happened");
    });
  }

  like(id: number): void{
    this.postService.like(id).subscribe(res=>{
      this.logger.success("Success","You liked the post!");
      this.loadPosts();
    }, 
    err=>{
      this.logger.error("Error","Something bad happened.");
    });
  }

  unlike(id: number) : void{
    this.postService.unlike(id).subscribe(res=>{
      this.logger.warning("Warning","You disliked the post!");
      this.loadPosts();
    }, 
    err=>{
      this.logger.error("Error","Something bad happened.");
    });
  }
  
}
