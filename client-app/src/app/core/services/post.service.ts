import { Injectable } from '@angular/core';
import { ApiService } from '@ikubinfo/core/utilities/api.service';
import { Observable } from 'rxjs';
import { Post } from '../models/post';

@Injectable()
export class PostService {

    url = 'posts';

    constructor(private apiService: ApiService) {

    }

    private async convertRawDataToPost(rawData: any): Promise<Post> {
        const { user,  postId , postName, postDescription, category } = rawData; 
        const post: Post = {
          userId: user.id, 
          id: postId,
          title: postName,
          body: postDescription,
          category: category.categoryName };
          return post;
    }


    private async convertRawDataArrayToPost(rawData: any): Promise<Array<Post>> {
        const posts: Array<Post>  = [];
        rawData.forEach(async element => {
             const post: Post =  await this.convertRawDataToPost(element);
             posts.push(post);
        });
        return posts;
    }

    public async all(): Promise<Post[]> {
        const posts = await this.apiService.get<Array<any>>(this.url).toPromise();
        return await this.convertRawDataArrayToPost(posts);
    }

    public readAsync = (id: number): Observable<Post> => {
        const url = this.url + `/${id}`;
        return this.apiService.get<Post>(url);
    }

    public editAsync = (
        id: number,
        post: Post
    ): Observable<Post> => {
        const url = this.url + `/${id}`;
        return this.apiService.put<Post>(url, post);
    }

    public createAsync = (
        post: Post
    ): Observable<Post> => {
        return this.apiService.post<Post>(this.url, post);
    }

    public likePost = (
        id: number
    ): Promise<Post> => {
        const url = this.url + `/like/${id}`;
        const rawPost =  this.apiService.putNoBody<Post>(url).toPromise();
        return this.convertRawDataToPost(rawPost);
    }

    public unLikePost = (
        id: number
    ): Promise<Post> => {
        const url = this.url + `/unlike/${id}`;
        const rawPost =  this.apiService.putNoBody<Post>(url).toPromise();
        return this.convertRawDataToPost(rawPost);
    }

    public deleteAsync = (id: number): Observable<void> => {
        const url = this.url + `/${id}`;
        return this.apiService.delete(url);
    }
}