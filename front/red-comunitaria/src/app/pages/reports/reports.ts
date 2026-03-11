import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { VentureService } from '../../services/venture.service';

interface ChartData {
  label: string;
  count: number;
  value: number;
  percentage?: number;
}

@Component({
  selector: 'app-reports',
  imports: [CommonModule, RouterModule],
  templateUrl: './reports.html',
  styleUrl: './reports.css'
})
export class ReportsComponent implements OnInit {
  private ventureService = inject(VentureService);
  
  productionStats: any[] = [];
  venturePercentages: ChartData[] = [];
  topCountries: ChartData[] = [];
  loading = true;

  ngOnInit() {
    this.loadReports();
  }

  loadReports() {
    this.loading = true;
    let loadedCount = 0;
    const totalReports = 3;

    const checkComplete = () => {
      loadedCount++;
      if (loadedCount === totalReports) {
        this.loading = false;
      }
    };
    
    this.ventureService.getTotalProductionByRegionAndVentureType().subscribe({
      next: (data) => {
        this.productionStats = data;
        checkComplete();
      },
      error: (error) => {
        console.error('Error cargando estadísticas de producción:', error);
        checkComplete();
      }
    });

    this.ventureService.getVenturePercentageByRegion().subscribe({
      next: (data) => {
        // data viene como: [region, cantidad, porcentaje]
        this.venturePercentages = data.map(item => ({
          label: item[0],      // región
          count: item[1],      // cantidad de emprendimientos
          value: item[2],      // porcentaje real del backend
          percentage: item[2]  // porcentaje para el gráfico
        }));
        checkComplete();
      },
      error: (error) => {
        console.error('Error cargando porcentajes:', error);
        checkComplete();
      }
    });

    this.ventureService.getTopCountriesByVentureCount().subscribe({
      next: (data) => {
        const maxValue = Math.max(...data.map(item => item[1]));
        this.topCountries = data.map(item => ({
          label: item[0],
          count: item[1],
          value: item[1],
          percentage: (item[1] / maxValue) * 100
        }));
        checkComplete();
      },
      error: (error) => {
        console.error('Error cargando top países:', error);
        checkComplete();
      }
    });
  }

  getBarWidth(value: number, maxValue: number): string {
    return `${(value / maxValue) * 100}%`;
  }
}
