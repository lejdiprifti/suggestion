import { Injectable } from '@angular/core';
import { ApiService } from '@ikubinfo/core/utilities/api.service';
import { Category } from '@ikubinfo/core/models/category';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CategoriesService {
  url='categories';
  
  constructor(private apiService: ApiService) { }
  id:number;
  getCategories(){
    return this.apiService.get(this.url);
  }

  getCategoryById(id: number){
    return this.apiService.get(this.url+'/id/'+id);
  }
  edit(id: number , category: Category){
    return this.apiService.put(this.url+'/'+id,category);
  }

  delete(id: number){
    return this.apiService.delete(this.url+'/'+id);
  }

  add(category: Category): Observable<Category>{
    return this.apiService.post(this.url,category);
  }


  subscribe(id: number){
    return this.apiService.put(this.url+'/subscribe/'+id);
  }
  
  getUnsubscribedCategories(){
    return this.apiService.get(this.url+'/unsubscribed');
  }
  
  getSubscribedCategories(){
    return this.apiService.get(this.url+'/subscribed');
  }
  
  unsubscribe(id: number){
    return this.apiService.put(this.url+'/unsubscribe/'+id);
  }
  
  getPostsOfCategory(id:number){
    return this.apiService.get(this.url+'/'+id+'/posts');
  }
  
  isSubscribed(id:number){
    return this.apiService.get(this.url+'/'+id+'/check');
  }

  getSubscribedCategoriesByName(name: string){
    return this.apiService.get(this.url+'/'+name+'/subscribed');
  }
  
  getUnsubscribedCategoriesByName(name: string){
    return this.apiService.get(this.url+'/'+name+'/unsubscribed');
  }
}
