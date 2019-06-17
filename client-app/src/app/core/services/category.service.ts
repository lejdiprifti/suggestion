import { Injectable } from '@angular/core';
import { ApiService } from '../utilities/api.service';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

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
}
