import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { LocationService } from '../../services/location.service';
import { Location } from '../../models/location.model';

@Component({
  selector: 'app-locations',
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './locations.html',
  styleUrl: './locations.css'
})
export class LocationsComponent implements OnInit {
  private locationService = inject(LocationService);
  
  locations: Location[] = [];
  showForm = false;
  isEditing = false;
  
  currentLocation: Location = {
    country: '',
    region: '',
    city: ''
  };

  ngOnInit() {
    this.loadLocations();
  }

  loadLocations() {
    this.locationService.findAll().subscribe({
      next: (data) => this.locations = data,
      error: (error) => console.error('Error cargando ubicaciones:', error)
    });
  }

  openForm() {
    this.showForm = true;
    this.isEditing = false;
    this.resetForm();
  }

  editLocation(location: Location) {
    this.currentLocation = { ...location };
    this.showForm = true;
    this.isEditing = true;
  }

  saveLocation() {
    if (this.isEditing && this.currentLocation.id) {
      this.locationService.update(this.currentLocation.id, this.currentLocation).subscribe({
        next: () => {
          this.loadLocations();
          this.closeForm();
        },
        error: (error) => console.error('Error actualizando ubicación:', error)
      });
    } else {
      this.locationService.create(this.currentLocation).subscribe({
        next: () => {
          this.loadLocations();
          this.closeForm();
        },
        error: (error) => console.error('Error creando ubicación:', error)
      });
    }
  }

  closeForm() {
    this.showForm = false;
    this.resetForm();
  }

  resetForm() {
    this.currentLocation = {
      country: '',
      region: '',
      city: ''
    };
  }
}
