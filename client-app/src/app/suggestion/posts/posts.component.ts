import { PostService } from '@ikubinfo/core/services/post.service';
import { Component, OnInit } from '@angular/core';
import { Post } from '@ikubinfo/core/models/post';
import { MenuItem, ConfirmationService, Message } from 'primeng/primeng';
import { Router, ActivatedRoute } from '@angular/router';
import { LoggerService } from '@ikubinfo/core/utilities/logger.service';



@Component({
  selector: 'ikubinfo-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.css']
})
export class PostsComponent implements OnInit {
  
  posts: Array<Post>;
  items: MenuItem[];

  cols: any[];

  selectedPost: Post;
  constructor(private postService: PostService, private router: Router, private active: ActivatedRoute,
    private confirmationService: ConfirmationService, private logger: LoggerService) { }

  ngOnInit() {
    this.loadPosts();

    this.items = [
      { label: 'Edit', icon: 'pi pi-pencil', command: (event) => this.viewPost(this.selectedPost) },
      { label: 'Delete', icon: 'pi pi-times', command: (event) => this.deletePost(this.selectedPost) }
    ];
    
    this.cols = [
      { field: 'postName', header: 'Title' },
      { field: 'postDescription', header: 'Body' },
      {field: 'image', header: 'Image'},
      {field: 'link', header: 'Source'}
    ];
  }

  viewPost(post: Post):void {
    this.router.navigate(['suggestion/post', post.postId]);
  }

  addPost():void {
    this.router.navigate(['suggestion/post']);
  }

  loadPosts(): void {
    this.postService.getAllPosts().subscribe(items => {
      this.posts = items;
    },
    err => {
      this.logger.error('Error', 'An error accured');
  });
  }

  deletePost(post: Post): void {
    this.confirmationService.confirm({
      message: 'Do you want to delete this record?',
      header: 'Delete Confirmation',
      icon: 'pi pi-info-circle',
      accept: () => {
        return this.postService.deletePost(post.postId).subscribe(res => {
          this.logger.info('Confirmed', 'Record deleted');
          this.loadPosts();
        },
        err => {
          this.logger.error('Error', 'An error accured');
        });
      }
    });
  }

}
