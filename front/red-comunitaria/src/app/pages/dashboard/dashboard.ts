import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule, RouterModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class DashboardComponent {
  menuItems = [
    { path: '/users', label: 'Usuarios', icon: '👥' },
    { path: '/locations', label: 'Ubicaciones', icon: '📍' },
    { path: '/persons', label: 'Personas', icon: '👤' },
    { path: '/ventures', label: 'Emprendimientos', icon: '💼' },
    { path: '/innovations', label: 'Innovaciones', icon: '💡' },
    { path: '/reports', label: 'Reportes', icon: '📊' }
  ];
}
