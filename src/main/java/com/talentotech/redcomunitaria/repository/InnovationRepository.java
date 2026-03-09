package com.talentotech.redcomunitaria.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.talentotech.redcomunitaria.model.Innovation;

public interface InnovationRepository extends JpaRepository<Innovation, Long> {

    List<Innovation> findByInnovationType(String innovationType);

    List<Innovation> findByInnovationLevel(String innovationLevel);
}