import { Injectable } from '@angular/core';
import { ApiService } from '@ikubinfo/core/utilities/api.service';

@Injectable({
  providedIn: 'root'
})
export class ProposalsService {
  url='suggestions';
  constructor(private apiService: ApiService) { }

  getProposals(){
    return this.apiService.get(this.url);
  }

  accept(id: number){
    return this.apiService.put(this.url+'/accept/'+id);
  }
  decline(id: number){
    return this.apiService.put(this.url+'/decline/'+id);
  }
}
