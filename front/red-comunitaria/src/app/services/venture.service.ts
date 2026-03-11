import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Venture } from '../models/venture.model';

@Injectable({
  providedIn: 'root'
})
export class VentureService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/api/ventures';

  findAll(): Observable<Venture[]> {
    return this.http.get<Venture[]>(this.apiUrl);
  }

  findById(id: number): Observable<Venture> {
    return this.http.get<Venture>(`${this.apiUrl}/${id}`);
  }

  create(venture: Venture): Observable<Venture> {
    return this.http.post<Venture>(this.apiUrl, venture);
  }

  update(id: number, venture: Venture): Observable<Venture> {
    return this.http.put<Venture>(`${this.apiUrl}/${id}`, venture);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  filterVentures(status?: string, ventureType?: string, region?: string): Observable<Venture[]> {
    let params = new HttpParams();
    if (status) params = params.set('status', status);
    if (ventureType) params = params.set('ventureType', ventureType);
    if (region) params = params.set('region', region);
    return this.http.get<Venture[]>(`${this.apiUrl}/filter`, { params });
  }

  getTotalProductionByRegionAndVentureType(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/statistics/production`);
  }

  getVenturePercentageByRegion(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/statistics/venture-percentage`);
  }

  getTopCountriesByVentureCount(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/statistics/top-countries`);
  }
}