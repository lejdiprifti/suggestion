import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Post } from '@ikubinfo/core/models/post';
import { PostService } from '@ikubinfo/core/services/post.service';
import { LoggerService } from '@ikubinfo/core/utilities/logger.service';
import { Category } from '@ikubinfo/core/models/category';
import { SuggestionService } from '@ikubinfo/core/services/suggestion.service';
import { CategoriesService } from '@ikubinfo/core/services/categories.service';
import { SelectItem } from 'primeng/primeng';

@Component({
  selector: 'ikubinfo-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {
  category: Category;
  categories: Object;
  postForm: FormGroup;
  post: Post;
  images:  Array<SelectItem>;
  constructor(private categoryService: CategoriesService ,private fb: FormBuilder, private active: ActivatedRoute, private postService: PostService, private logger: LoggerService, private router: Router) { }

  ngOnInit() {
    this.initializeForm();
    this.loadData();
    this.category={};
    this.images=[
      {label:'football', value: 'football'},
      {label: 'music', value: 'music'},
      {label: 'nature', value: 'nature'},
      {label: 'soccer', value: 'soccer'},
      {label: 'restaurant', value: 'restaurant'},
      {label: 'hotels', value: 'hotels'},
      {label: 'news', value: 'news'},
      {label: 'cars', value: 'cars'},
      {label: 'holidays', value: 'holidays'},
      {label: 'technology', value: 'technology'}
    ]
  }

  loadData(): void {
    this.getCategories();
    const id = this.active.snapshot.paramMap.get('id');
    if (id) {
      this.postService.getPost(Number(id)).subscribe(data => {
          this.post = data;
          this.postForm.get('title').setValue(this.post.postName);
          this.postForm.get('description').setValue(this.post.postDescription);
          this.postForm.get('category').setValue(this.post.category.categoryId);
          this.postForm.get('image').setValue(this.post.image);
          this.postForm.get('link').setValue(this.post.link);
        },
        err => {
          this.logger.error('Error', 'An error accured');
        });
    }
  }

  reset(): void {
    this.fillForm(this.post);
  }

  initializeForm(): void {
    this.postForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      image: [''],
      category: ['' , Validators.required],
      link: [''],
    });
  }

  fillForm(data: Post = {}): void {
    this.postForm.get('title').setValue(data.postName);
    this.postForm.get('description').setValue(data.postDescription);
    this.postForm.get('category').setValue(data.categoryId);
    this.postForm.get('image').setValue(data.image);
    this.postForm.get('link').setValue(data.link);
  }

  getData(): Post {
    return {
      postName: this.postForm.get('title').value,
      postDescription: this.postForm.get('description').value,
      categoryId: this.postForm.get('category').value,
      image: this.postForm.get('image').value,
      link: this.postForm.get('link').value
    }

  }

  submit(): void {
    if (this.post) {
      this.postService.editPost(this.post.postId, this.getData()).subscribe(res => {
        this.logger.info('Success', 'Post was successfully added.');
        this.router.navigate(['suggestion/posts']);

      },
      err => {
        this.logger.error('Error', 'Post name already exists.');
      });
    }
    else {
      this.postService.createPost(this.getData()).subscribe(res => {
        this.logger.info('Success', 'Post was created successfully.');
        this.router.navigate(['suggestion/posts']);
      },
      err => {
        this.logger.error('Error', 'Post name already exists.');
      });

    }

  }

  getCategories() : Object{
    return this.categoryService.getCategories().subscribe(res=>{
      this.categories=res;
    },
    err=>{
      this.logger.error("Error","Something bad happened.");
    })
  }


}
