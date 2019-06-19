import { Injectable } from '@angular/core';
import { ApiService } from '@ikubinfo/core/utilities/api.service';

@Injectable({
  providedIn: 'root'
})
export class ProposalsService {

  constructor(private apiService: ApiService) { }

  getProposals(){
    return this.apiService.get('suggestions');
  }

  accept(id: number){
    return this.apiService.put('suggestions/accept/'+id);
  }
  decline(id: number){
    return this.apiService.put('suggestions/decline/'+id);
  }
}
