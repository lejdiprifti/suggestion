import { Injectable } from '@angular/core';
import { ApiService } from '../utilities/api.service';
import { Post } from '../models/post';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CommentsService {
  url="comments"
  constructor(private apiService: ApiService) { }

public getAllComments(): Observable<Comment>{
  return this.apiService.get(this.url);
}

public delete(id: number): Observable<any>{
  return this.apiService.delete(this.url+"/"+id);
}


}
