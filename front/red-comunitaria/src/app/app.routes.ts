import { Routes } from '@angular/router';
import { DashboardComponent } from './pages/dashboard/dashboard';
import { LoginComponent } from './pages/login/login';
import { UsersComponent } from './pages/users/users';
import { LocationsComponent } from './pages/locations/locations';
import { PersonsComponent } from './pages/persons/persons';
import { VenturesComponent } from './pages/ventures/ventures';
import { InnovationsComponent } from './pages/innovations/innovations';
import { ReportsComponent } from './pages/reports/reports';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'users', component: UsersComponent },
  { path: 'locations', component: LocationsComponent },
  { path: 'persons', component: PersonsComponent },
  { path: 'ventures', component: VenturesComponent },
  { path: 'innovations', component: InnovationsComponent },
  { path: 'reports', component: ReportsComponent }
];
