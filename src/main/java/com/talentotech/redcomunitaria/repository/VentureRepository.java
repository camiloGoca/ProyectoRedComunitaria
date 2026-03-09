package com.talentotech.redcomunitaria.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.talentotech.redcomunitaria.model.Venture;

public interface VentureRepository extends JpaRepository<Venture, Long> {

    List<Venture> findByStatus(String status);

    List<Venture> findByVentureType(String ventureType);
}