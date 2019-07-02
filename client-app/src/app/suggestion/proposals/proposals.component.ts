import { Component, OnInit } from '@angular/core';

import { LoggerService } from '@ikubinfo/core/utilities/logger.service';
import { Router } from '@angular/router';
import { ConfirmationService } from 'primeng/components/common/confirmationservice';
import { ProposalsService } from '@ikubinfo/core/services/proposals.service';

@Component({
  selector: 'ikubinfo-proposals',
  templateUrl: './proposals.component.html',
  styleUrls: ['./proposals.component.css']
})
export class ProposalsComponent implements OnInit {
  proposals: Object;
  constructor(private confirmationService: ConfirmationService,private proposalsService: ProposalsService, private logger: LoggerService, private router: Router) { }

  ngOnInit() {
    this.proposals=[];
    this.getProposals();
  }


  getProposals(): void{
    this.proposalsService.getProposals().subscribe(res=>{
      this.proposals=res;
      console.log(this.proposals);
    },
    err=>{
      this.logger.error("Error","Something went wrong");
      
    });
  }

  accept(id: number) : void{
    this.confirmationService.confirm({
      message: 'Do you want to accept this proposal?',
      header: 'Accept Confirmation',
      icon: 'pi pi-info-circle',
      accept: () => {
    this.proposalsService.accept(id).subscribe(res=>{
      this.getProposals();
      this.logger.success("Success", "Proposal was accepted.");
    
    },
    err=>{
      this.logger.error("Error","Something bad happened.");
    });
  
  }
  });
}

  decline(id: number): void{
    this.confirmationService.confirm({
      message: 'Do you want to decline this proposal?',
      header: 'Decline Confirmation',
      icon: 'pi pi-info-circle',
      accept: () => {
        this.proposalsService.decline(id).subscribe(res=>{
          this.getProposals();
          this.logger.info("Declined", "Proposal was successfully declined.");
    },
    err=>{
      this.logger.error("Error","Something bad happened.");
    });
  
  }
  });
  }
}

