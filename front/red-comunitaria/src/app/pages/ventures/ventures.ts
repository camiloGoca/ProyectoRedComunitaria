import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { VentureService } from '../../services/venture.service';
import { PersonService } from '../../services/person.service';
import { LocationService } from '../../services/location.service';
import { UserService } from '../../services/user.service';
import { Venture } from '../../models/venture.model';
import { Person } from '../../models/person.model';
import { Location } from '../../models/location.model';
import { User } from '../../models/user.model';

@Component({
  selector: 'app-ventures',
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './ventures.html',
  styleUrl: './ventures.css'
})
export class VenturesComponent implements OnInit {
  private ventureService = inject(VentureService);
  private personService = inject(PersonService);
  private locationService = inject(LocationService);
  private userService = inject(UserService);
  
  ventures: Venture[] = [];
  filteredVentures: Venture[] = [];
  persons: Person[] = [];
  locations: Location[] = [];
  users: User[] = [];
  showForm = false;
  isEditing = false;
  showFilters = false;
  
  // Filtros
  filters = {
    status: '',
    ventureType: '',
    region: ''
  };

  // Opciones únicas para los filtros
  uniqueStatuses: string[] = [];
  uniqueVentureTypes: string[] = [];
  uniqueRegions: string[] = [];
  
  currentVenture: any = {
    name: '',
    ventureType: '',
    sector: '',
    startDate: '',
    status: '',
    employees: null,
    totalProduction: null,
    monthlyIncome: null,
    person: { id: null },
    location: { id: null },
    user: { id: null }
  };

  ngOnInit() {
    this.loadVentures();
    this.loadPersons();
    this.loadLocations();
    this.loadUsers();
  }

  loadVentures() {
    this.ventureService.findAll().subscribe({
      next: (data) => {
        this.ventures = data;
        this.filteredVentures = data;
        this.extractUniqueFilterValues();
      },
      error: (error) => console.error('Error cargando emprendimientos:', error)
    });
  }

  extractUniqueFilterValues() {
    // Extraer valores únicos para los filtros
    this.uniqueStatuses = [...new Set(this.ventures.map(v => v.status).filter(status => status !== undefined))];
    this.uniqueVentureTypes = [...new Set(this.ventures.map(v => v.ventureType).filter(vt => vt))];
    this.uniqueRegions = [...new Set(this.ventures.map(v => v.location?.region).filter(r => r))];
  }

  applyFilters() {
    const { status, ventureType, region } = this.filters;
    
    // Si no hay filtros, usar el endpoint sin filtros
    if (!status && !ventureType && !region) {
      this.loadVentures();
      return;
    }

    // Usar el endpoint de filtrado de el backend
    this.ventureService.filterVentures(status, ventureType, region).subscribe({
      next: (data) => {
        this.filteredVentures = data;
      },
      error: (error) => console.error('Error filtrando emprendimientos:', error)
    });
  }

  clearFilters() {
    this.filters = {
      status: '',
      ventureType: '',
      region: ''
    };
    this.loadVentures();
  }

  toggleFilters() {
    this.showFilters = !this.showFilters;
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

  editVenture(venture: Venture) {
    this.currentVenture = {
      ...venture,
      person: { id: venture.person.id },
      location: { id: venture.location.id },
      user: { id: venture.user.id }
    };
    this.showForm = true;
    this.isEditing = true;
  }

  saveVenture() {
    const ventureData = {
      ...this.currentVenture,
      person: this.persons.find(p => p.id === Number(this.currentVenture.person.id)),
      location: this.locations.find(l => l.id === Number(this.currentVenture.location.id)),
      user: this.users.find(u => u.id === Number(this.currentVenture.user.id))
    };

    if (this.isEditing && this.currentVenture.id) {
      this.ventureService.update(this.currentVenture.id, ventureData).subscribe({
        next: () => {
          this.loadVentures();
          this.closeForm();
        },
        error: (error) => console.error('Error actualizando emprendimiento:', error)
      });
    } else {
      this.ventureService.create(ventureData).subscribe({
        next: () => {
          this.loadVentures();
          this.closeForm();
        },
        error: (error) => console.error('Error creando emprendimiento:', error)
      });
    }
  }

  deleteVenture(id: number) {
    if (confirm('¿Está seguro de eliminar este emprendimiento?')) {
      this.ventureService.delete(id).subscribe({
        next: () => this.loadVentures(),
        error: (error) => console.error('Error eliminando emprendimiento:', error)
      });
    }
  }

  closeForm() {
    this.showForm = false;
    this.resetForm();
  }

  resetForm() {
    this.currentVenture = {
      name: '',
      ventureType: '',
      sector: '',
      startDate: '',
      status: '',
      employees: null,
      totalProduction: null,
      monthlyIncome: null,
      person: { id: null },
      location: { id: null },
      user: { id: null }
    };
  }
}
