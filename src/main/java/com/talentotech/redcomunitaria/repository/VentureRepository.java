package com.talentotech.redcomunitaria.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.talentotech.redcomunitaria.model.Venture;

public interface VentureRepository extends JpaRepository<Venture, Long> {

    List<Venture> findByLocation_Region(String region);
    List<Venture> findByStatus(String status);
    List<Venture> findByVentureType(String ventureType);
    List<Venture> findByStatusAndVentureType(String status, String ventureType);
    List<Venture> findByStatusAndLocation_Region(String status, String region);
    List<Venture> findByVentureTypeAndLocation_Region(String ventureType, String region);
    List<Venture> findByStatusAndVentureTypeAndLocation_Region(String status, String ventureType, String region);
}