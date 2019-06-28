import { Component, OnInit } from '@angular/core';
import { MessagesService } from '@ikubinfo/core/services/messages.service';
import { LoggerService } from '@ikubinfo/core/utilities/logger.service';

@Component({
  selector: 'ikubinfo-messages',
  templateUrl: './messages.component.html',
  styleUrls: ['./messages.component.css']
})
export class MessagesComponent implements OnInit {
  categories: any;
  constructor(private messagesService: MessagesService,private logger: LoggerService) { 
    this.categories={

    }
  }

  ngOnInit() {
    this.loadData();
  }

  loadData(){
    return this.messagesService.getMessages().subscribe(res=>{
      this.categories=res;
    },
    err=>{
      this.logger.error("Error", "Something bad happened.");
    });
  }
}
