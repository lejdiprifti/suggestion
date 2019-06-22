import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PostsOfCategoryComponent } from './posts-of-category.component';

describe('PostsOfCategoryComponent', () => {
  let component: PostsOfCategoryComponent;
  let fixture: ComponentFixture<PostsOfCategoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PostsOfCategoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PostsOfCategoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
