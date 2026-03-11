import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { InnovationService } from '../../services/innovation.service';
import { PersonService } from '../../services/person.service';
import { LocationService } from '../../services/location.service';
import { UserService } from '../../services/user.service';
import { Innovation } from '../../models/innovation.model';
import { Person } from '../../models/person.model';
import { Location } from '../../models/location.model';
import { User } from '../../models/user.model';

@Component({
  selector: 'app-innovations',
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './innovations.html',
  styleUrl: './innovations.css'
})
export class InnovationsComponent implements OnInit {
  private innovationService = inject(InnovationService);
  private personService = inject(PersonService);
  private locationService = inject(LocationService);
  private userService = inject(UserService);
  
  innovations: Innovation[] = [];
  persons: Person[] = [];
  locations: Location[] = [];
  users: User[] = [];
  showForm = false;
  isEditing = false;
  
  currentInnovation: any = {
    name: '',
    innovationType: '',
    innovationLevel: '',
    innovationDate: '',
    impactScore: null,
    description: '',
    person: { id: null },
    location: { id: null },
    user: { id: null }
  };

  ngOnInit() {
    this.loadInnovations();
    this.loadPersons();
    this.loadLocations();
    this.loadUsers();
  }

  loadInnovations() {
    this.innovationService.findAll().subscribe({
      next: (data) => this.innovations = data,
      error: (error) => console.error('Error cargando innovaciones:', error)
    });
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

  editInnovation(innovation: Innovation) {
    this.currentInnovation = {
      ...innovation,
      person: { id: innovation.person.id },
      location: { id: innovation.location.id },
      user: { id: innovation.user.id }
    };
    this.showForm = true;
    this.isEditing = true;
  }

  saveInnovation() {
    const innovationData = {
      ...this.currentInnovation,
      person: this.persons.find(p => p.id === Number(this.currentInnovation.person.id)),
      location: this.locations.find(l => l.id === Number(this.currentInnovation.location.id)),
      user: this.users.find(u => u.id === Number(this.currentInnovation.user.id))
    };

    if (this.isEditing && this.currentInnovation.id) {
      this.innovationService.update(this.currentInnovation.id, innovationData).subscribe({
        next: () => {
          this.loadInnovations();
          this.closeForm();
        },
        error: (error) => console.error('Error actualizando innovación:', error)
      });
    } else {
      this.innovationService.create(innovationData).subscribe({
        next: () => {
          this.loadInnovations();
          this.closeForm();
        },
        error: (error) => console.error('Error creando innovación:', error)
      });
    }
  }

  deleteInnovation(id: number) {
    if (confirm('¿Está seguro de eliminar esta innovación?')) {
      this.innovationService.delete(id).subscribe({
        next: () => this.loadInnovations(),
        error: (error) => console.error('Error eliminando innovación:', error)
      });
    }
  }

  closeForm() {
    this.showForm = false;
    this.resetForm();
  }

  resetForm() {
    this.currentInnovation = {
      name: '',
      innovationType: '',
      innovationLevel: '',
      innovationDate: '',
      impactScore: null,
      description: '',
      person: { id: null },
      location: { id: null },
      user: { id: null }
    };
  }
}
