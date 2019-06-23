import { Injectable } from '@angular/core';
import { ApiService } from '@ikubinfo/core/utilities/api.service';
import { Observable } from 'rxjs';
import { Post } from '../models/post';

@Injectable()
export class PostService {

    url = 'posts';

    constructor(private apiService: ApiService) {

    }
    public hasLiked(id: number){
        return this.apiService.get(this.url+'/'+id+'/liked');
    }
    public like(id: number){
        return this.apiService.put(this.url+'/like/'+id);
    }

    public unlike(id: number){
        return this.apiService.put(this.url+'/unlike/'+id);
    }
    public allAsync = (): Observable<Array<Post>> => {
        return this.apiService.get<Array<Post>>(this.url);
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


    public deleteAsync = (id: number): Observable<void> => {
        const url = this.url + `/${id}`;
        return this.apiService.delete(url);
    }
}