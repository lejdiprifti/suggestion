import { Injectable } from '@angular/core';
import { Category } from '../models/category';
import { Observable } from 'rxjs';
import { ApiService } from '../utilities/api.service';



@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  url = 'categories';
  constructor(private apiService: ApiService) { }

getAllCategories() {
 return this.apiService.get('categories');
}

subscribe(id: number){
  return this.apiService.put('categories/subscribe/'+id);
}

getSubscribedCategories(){
  return this.apiService.get('categories/subscribed');
}

unsubscribe(id: number){
  return this.apiService.put('categories/unsubscribe/'+id);
}

getPostsOfCategory(id:number){
  return this.apiService.get('categories/'+id+'/posts');
}

isSubscribed(id:number){
  return this.apiService.get('categories/'+id+'/check');
}


private async convertToCategory(categoryRaw: any): Promise<Category> {
  let category: Category = {
      categoryName: categoryRaw.categoryName,
      categoryDescription: categoryRaw.categoryDescription,
      user: categoryRaw.user //&& categoryRaw.user.id || 0 
  }
  return category;
}

public async convertToCategories(categoryRaw: any): Promise<Array<Category>> {
  let categories: Array<Category> = [];
  categoryRaw.forEach(async element => {
      categories.push(await this.convertToCategory(element));
  });
  return categories;
}

public  allCategories = async (): Promise<Array<Category>> => {
 return this.convertToCategories(this.apiService.get<Array<any>>(this.url).toPromise());
}


// public editAsync = (
//     id: number,
//     post: Post
// ): Observable<Post> => {
//     const url = this.url + `/${id}`;
//     return this.apiService.put<Post>(url, post);
// }

public suggestCategory(category: any){
  return this.apiService.post('suggestions',category);
}


public deleteAsync = (id: number): Observable<void> => {
  const url = this.url + `/${id}`;
  return this.apiService.delete(url);
}

public getSuggestions(){
  return this.apiService.get('suggestions');
}

public deleteSuggestion(id:number){
  return this.apiService.delete('suggestions/'+id);
}

public updateSuggestion(id: any,category: Category){
  return this.apiService.put('suggestions/'+id);
}
}
