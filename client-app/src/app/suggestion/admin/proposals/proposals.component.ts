import { Component, OnInit } from '@angular/core';
import { ProposalsService } from '../services/proposals.service';
import { LoggerService } from '@ikubinfo/core/utilities/logger.service';
import { Router } from '@angular/router';

@Component({
  selector: 'ikubinfo-proposals',
  templateUrl: './proposals.component.html',
  styleUrls: ['./proposals.component.css']
})
export class ProposalsComponent implements OnInit {
  proposals: Object;
  constructor(private proposalsService: ProposalsService, private logger: LoggerService, private router: Router) { }

  ngOnInit() {
    this.proposals=[];
    this.getProposals();
  }


  getProposals(){
    this.proposalsService.getProposals().subscribe(res=>{
      this.proposals=res;
      console.log(this.proposals);
    },
    err=>{
      this.logger.error("Error","Something went wrong");
      
    });
  }

  accept(id: number){
    this.proposalsService.accept(id).subscribe(res=>{
      this.getProposals();
      this.logger.success("Success", "To be created...");
    });
   
  }

  decline(id: number){
    this.proposalsService.decline(id).subscribe(res=>{
      this.getProposals();
      this.logger.info("Deleted", "To be deleted...");
    });
   
  }
}
