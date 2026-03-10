package com.talentotech.redcomunitaria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import com.talentotech.redcomunitaria.model.Venture;

public interface VentureRepository extends JpaRepository<Venture, Long> {

    // Consulta para producción total de los emprendimientos
    @Query("SELECT v.location.region, v.ventureType, SUM(v.totalProduction) FROM Venture v " +
       "GROUP BY v.location.region, v.ventureType")
     List<Object[]> findTotalProductionByRegionAndVentureType();

     //Consulta para obtener el porcentaje de emprendimientos por región
    @Query("SELECT v.location.region, COUNT(v) AS ventureCount, " +
           "(COUNT(v) * 100.0 / (SELECT COUNT(v2) FROM Venture v2)) AS venturePercentage " +
           "FROM Venture v " +
           "GROUP BY v.location.region")
     List<Object[]> findVenturePercentageByRegion();


    List<Venture> findByLocation_Region(String region);
    List<Venture> findByStatus(String status);
    List<Venture> findByVentureType(String ventureType);
    List<Venture> findByStatusAndVentureType(String status, String ventureType);
    List<Venture> findByStatusAndLocation_Region(String status, String region);
    List<Venture> findByVentureTypeAndLocation_Region(String ventureType, String region);
    List<Venture> findByStatusAndVentureTypeAndLocation_Region(String status, String ventureType, String region);
}