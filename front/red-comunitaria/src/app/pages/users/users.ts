import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { UserService } from '../../services/user.service';
import { User } from '../../models/user.model';

@Component({
  selector: 'app-users',
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './users.html',
  styleUrl: './users.css'
})
export class UsersComponent implements OnInit {
  private userService = inject(UserService);
  
  users: User[] = [];
  showForm = false;
  isEditing = false;
  
  currentUser: User = {
    username: '',
    email: '',
    password: '',
    role: 'USER'
  };

  ngOnInit() {
    this.loadUsers();
  }

  loadUsers() {
    this.userService.findAll().subscribe({
      next: (data) => this.users = data,
      error: (error) => console.error('Error cargando usuarios:', error)
    });
  }

  openForm() {
    this.showForm = true;
    this.isEditing = false;
    this.resetForm();
  }

  editUser(user: User) {
    this.currentUser = { ...user };
    this.showForm = true;
    this.isEditing = true;
  }

  saveUser() {
    if (this.isEditing && this.currentUser.id) {
      this.userService.update(this.currentUser.id, this.currentUser).subscribe({
        next: () => {
          this.loadUsers();
          this.closeForm();
        },
        error: (error) => console.error('Error actualizando usuario:', error)
      });
    } else {
      this.userService.create(this.currentUser).subscribe({
        next: () => {
          this.loadUsers();
          this.closeForm();
        },
        error: (error) => console.error('Error creando usuario:', error)
      });
    }
  }

  closeForm() {
    this.showForm = false;
    this.resetForm();
  }

  resetForm() {
    this.currentUser = {
      username: '',
      email: '',
      password: '',
      role: 'USER'
    };
  }
}
