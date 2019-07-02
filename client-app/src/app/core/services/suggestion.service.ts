import { Injectable } from '@angular/core';
import { Category } from '../models/category';
import { Observable } from 'rxjs';
import { ApiService } from '../utilities/api.service';



@Injectable({
  providedIn: 'root'
})
export class SuggestionService {
  
  url = 'suggestions';
  constructor(private apiService: ApiService) { }

suggestCategory(category: Category){
  return this.apiService.post(this.url,category);
}

public getSuggestions(){
  return this.apiService.get(this.url);
}

public deleteSuggestion(id:number){
  return this.apiService.delete(this.url+'/'+id);
}

public updateSuggestion(id: any,category: Category){
  return this.apiService.put(this.url+'/'+id+'',category);
}

public getSuggestionById(id: any) {
  return this.apiService.get(this.url+'/'+id);
}
}
