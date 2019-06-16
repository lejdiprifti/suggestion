import { Injectable } from '@angular/core';
import { ApiService } from '../utilities/api.service';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private apiService: ApiService) { }

}
