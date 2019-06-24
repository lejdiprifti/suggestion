import { Component, OnInit } from '@angular/core';
import { Post } from '@ikubinfo/core/models/post';
import { MenuItem, ConfirmationService } from 'primeng/primeng';
import { PostService } from '@ikubinfo/core/services/post.service';
import { Router, ActivatedRoute } from '@angular/router';
import { LoggerService } from '@ikubinfo/core/utilities/logger.service';

@Component({
  selector: 'ikubinfo-post-view',
  templateUrl: './post-view.component.html',
  styleUrls: ['./post-view.component.css']
})
export class PostViewComponent implements OnInit {

  
  posts: Array<Post>;
  items: MenuItem[];

  cols: any[];

  selectedPost: Post;

  constructor(private postService: PostService, private router: Router, private active: ActivatedRoute,
    private confirmationService: ConfirmationService, private logger: LoggerService) { }

  async ngOnInit() {
    await this.loadPosts();

    // this.items = [
    //   { label: 'View', icon: 'pi pi-pencil', command: (event) => this.viewPost(this.selectedPost) }
    // ];

    this.cols = [
      { field: 'title', header: 'Title' },
      { field: 'body', header: 'Body' },
      { field: 'category', header: 'Category'},
      { field: 'liked', header: 'Liked'}
    ];
  }

  viewPost(post: Post) {
    this.router.navigate(['post', post.id], { relativeTo: this.active.parent });
  }



  async loadPosts(): Promise<void> {
    try {

    this.posts = await this.postService.all();
    } catch(e) {
      this.logger.error('Error', 'An error accured');
    };
  }

 async changeLiked($event,rowData: any) {
    const { liked , id } = rowData;
    const isLiked = !liked;
    let postChanged = null;

    try {
    if (isLiked) {
      postChanged = await this.postService.likePost(id);
    } else {
      postChanged = await this.postService.unLikePost(id);
    }

    this.posts.forEach(e => e.liked = postChanged.liked);
    console.log(rowData)
  } catch(e) {
    this.logger.error('Error', 'An error accured');  
  }
  }



}
