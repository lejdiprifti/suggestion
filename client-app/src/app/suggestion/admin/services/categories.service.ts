import { Injectable } from '@angular/core';
import { ApiService } from '@ikubinfo/core/utilities/api.service';

@Injectable({
  providedIn: 'root'
})
export class CategoriesService {

  constructor(private apiService: ApiService) { }
  id:number;
  getCategories(){
    return this.apiService.get('categories');
  }

  getCategoryById(id: number){
    return this.apiService.get('categories/id/'+id);
  }
  edit(id: number , category: any){
    return this.apiService.put('categories/'+id , category);
  }

  delete(id: number){
    return this.apiService.delete('categories/'+id);
  }
setId(id:number){
  this.id=id;
}
 getId(){
   return this.id;
 }
}
