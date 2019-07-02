import { Component, OnInit } from '@angular/core';
import { LoggerService } from '@ikubinfo/core/utilities/logger.service';
import { UsersService } from '@ikubinfo/core/services/users.service';
import { User } from '@ikubinfo/core/models/user';

@Component({
  selector: 'ikubinfo-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
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

loadUsers() : Object{
  return this.usersService.getUsers().subscribe(res=>{
    this.users=res;
  },
  err=>{
    this.logger.error("Error","Something bad happened");
  });
}
}
