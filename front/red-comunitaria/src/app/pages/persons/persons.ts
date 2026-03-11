import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { PersonService } from '../../services/person.service';
import { LocationService } from '../../services/location.service';
import { UserService } from '../../services/user.service';
import { Person } from '../../models/person.model';
import { Location } from '../../models/location.model';
import { User } from '../../models/user.model';

@Component({
  selector: 'app-persons',
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './persons.html',
  styleUrl: './persons.css'
})
export class PersonsComponent implements OnInit {
  private personService = inject(PersonService);
  private locationService = inject(LocationService);
  private userService = inject(UserService);
  
  persons: Person[] = [];
  locations: Location[] = [];
  users: User[] = [];
  showForm = false;
  isEditing = false;
  
  currentPerson: any = {
    firstName: '',
    lastName: '',
    birthDate: '',
    gender: '',
    educationLevel: '',
    location: { id: null },
    user: { id: null }
  };

  ngOnInit() {
    this.loadPersons();
    this.loadLocations();
    this.loadUsers();
  }

  loadPersons() {
    this.personService.findAll().subscribe({
      next: (data) => this.persons = data,
      error: (error) => console.error('Error cargando personas:', error)
    });
  }

  loadLocations() {
    this.locationService.findAll().subscribe({
      next: (data) => this.locations = data,
      error: (error) => console.error('Error cargando ubicaciones:', error)
    });
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

  editPerson(person: Person) {
    this.currentPerson = {
      ...person,
      location: { id: person.location.id },
      user: { id: person.user.id }
    };
    this.showForm = true;
    this.isEditing = true;
  }

  savePerson() {
    const personData = {
      ...this.currentPerson,
      location: this.locations.find(l => l.id === Number(this.currentPerson.location.id)),
      user: this.users.find(u => u.id === Number(this.currentPerson.user.id))
    };

    if (this.isEditing && this.currentPerson.id) {
      this.personService.update(this.currentPerson.id, personData).subscribe({
        next: () => {
          this.loadPersons();
          this.closeForm();
        },
        error: (error) => console.error('Error actualizando persona:', error)
      });
    } else {
      this.personService.create(personData).subscribe({
        next: () => {
          this.loadPersons();
          this.closeForm();
        },
        error: (error) => console.error('Error creando persona:', error)
      });
    }
  }

  closeForm() {
    this.showForm = false;
    this.resetForm();
  }

  resetForm() {
    this.currentPerson = {
      firstName: '',
      lastName: '',
      birthDate: '',
      gender: '',
      educationLevel: '',
      location: { id: null },
      user: { id: null }
    };
  }
}
