import { Injectable } from '@angular/core';
import { ApiService } from '../utilities/api.service';

@Injectable({
  providedIn: 'root'
})
export class MessagesService {

  constructor(private apiService: ApiService) { }

  public getMessages(){
    return this.apiService.get('messages');
  }
}
