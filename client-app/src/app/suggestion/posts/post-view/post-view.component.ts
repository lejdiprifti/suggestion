import { Component, OnInit } from '@angular/core';
import { PostService } from '@ikubinfo/core/services/post.service';
import { Router, ActivatedRoute } from '@angular/router';
import { LoggerService } from '@ikubinfo/core/utilities/logger.service';

@Component({
  selector: 'ikubinfo-post-view',
  templateUrl: './post-view.component.html',
  styleUrls: ['./post-view.component.css']
})
export class PostViewComponent implements OnInit {

  
  posts: Object;

  constructor(private postService: PostService, private router: Router, private active: ActivatedRoute,
 private logger: LoggerService) { }

    ngOnInit() {
      this.loadPosts();
    }
  
    loadPosts(){
      this.postService.getAllPosts().subscribe(res=>{
        this.posts=res;
      },
      err=>{
        this.logger.error("Error", "Something bad happened");
      });
    }
  
    like(id: number){
      this.postService.like(id).subscribe(res=>{
        this.logger.success("Success","You liked the post!");
        this.loadPosts();
      }, 
      err=>{
        this.logger.error("Error","Something bad happened.");
      });
    }
  
    unlike(id: number){
      this.postService.unlike(id).subscribe(res=>{
        this.logger.warning("Success","You disliked the post!");
        this.loadPosts();
      }, 
      err=>{
        this.logger.error("Error","Something bad happened.");
      });
    }
    
  }
  