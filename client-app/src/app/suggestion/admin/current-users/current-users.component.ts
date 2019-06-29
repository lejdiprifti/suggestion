import { Component, OnInit } from '@angular/core';
import { UsersService } from '../services/users.service';
import { User } from '@ikubinfo/core/models/user';
import { LoggerService } from '@ikubinfo/core/utilities/logger.service';

@Component({
  selector: 'ikubinfo-current-users',
  templateUrl: './current-users.component.html',
  styleUrls: ['./current-users.component.css']
})
export class CurrentUsersComponent implements OnInit {
  users: Object;
  cols: any[];

  constructor(private usersService: UsersService, private logger:LoggerService) { }

  ngOnInit() {
    this.loadUsers();
    this.users={};

    this.cols = [
      { field: 'username', header: 'Username' },
      { field: 'password', header: 'Password' },
      { field: 'email', header: 'Email'},
      { field: 'address', header: 'Address'},
      {field: 'categories', header: 'Subscribed to'},
      {field: 'posts', header: 'Liked'}
    ];
  }

loadUsers(){
  return this.usersService.getActiveUsers().subscribe(res=>{
    this.users=res;
  },
  err=>{
    this.logger.error("Error","Something bad happened");
  });
}
}
