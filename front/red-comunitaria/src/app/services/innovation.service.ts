import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Innovation } from '../models/innovation.model';

@Injectable({
  providedIn: 'root'
})
export class InnovationService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/api/innovations';

  findAll(): Observable<Innovation[]> {
    return this.http.get<Innovation[]>(this.apiUrl);
  }

  findById(id: number): Observable<Innovation> {
    return this.http.get<Innovation>(`${this.apiUrl}/${id}`);
  }

  create(innovation: Innovation): Observable<Innovation> {
    return this.http.post<Innovation>(this.apiUrl, innovation);
  }

  update(id: number, innovation: Innovation): Observable<Innovation> {
    return this.http.put<Innovation>(`${this.apiUrl}/${id}`, innovation);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  findByType(type: string): Observable<Innovation[]> {
    return this.http.get<Innovation[]>(`${this.apiUrl}/type/${type}`);
  }

  findByLevel(level: string): Observable<Innovation[]> {
    return this.http.get<Innovation[]>(`${this.apiUrl}/level/${level}`);
  }
}
