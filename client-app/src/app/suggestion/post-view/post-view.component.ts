import { Component, OnInit } from '@angular/core';
import { PostService } from '@ikubinfo/core/services/post.service';
import { Router, ActivatedRoute } from '@angular/router';
import { LoggerService } from '@ikubinfo/core/utilities/logger.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'ikubinfo-post-view',
  templateUrl: './post-view.component.html',
  styleUrls: ['./post-view.component.css']
})
export class PostViewComponent implements OnInit {

  
  posts: Object;
  commentForm: FormGroup;
  i :number=3;
  constructor(private fb: FormBuilder,private postService: PostService, private router: Router, private active: ActivatedRoute,
 private logger: LoggerService) { }

    ngOnInit() {
      this.loadPosts();
      this.commentForm = this.fb.group({
        description: [ '' , Validators.required]
      });
    }
  
    loadPosts() : void{
      this.postService.getAllPosts().subscribe(res=>{
        this.posts=res;
      },
      err=>{
        this.logger.error("Error", "Something bad happened");
      });
    }
  
    like(id: number) : void{
      this.postService.like(id).subscribe(res=>{
        this.logger.success("Success","You liked the post!");
        this.loadPosts();
      }, 
      err=>{
        this.logger.error("Error","Something bad happened.");
      });
    }
  
    unlike(id: number): void{
      this.postService.unlike(id).subscribe(res=>{
        this.logger.warning("Success","You disliked the post!");
        this.loadPosts();
      }, 
      err=>{
        this.logger.error("Error","Something bad happened.");
      });
    }
    
    getData() {
      return {
       description: this.commentForm.get('description').value
      }
    }

    comment(id:number){
      this.postService.comment(this.getData(),id).subscribe(res=>{
        this.commentForm.get('description').setValue('');
        this.loadPosts();
        this.logger.success("Success","You commented successfully!");
      },
      err=>{
        this.logger.error("Error","Something bad happened.");
      });
    }

    showComments(): void{
      this.i += 3;
    }
    showLessComments(): void{
      this.i=3;
    }
  }
  