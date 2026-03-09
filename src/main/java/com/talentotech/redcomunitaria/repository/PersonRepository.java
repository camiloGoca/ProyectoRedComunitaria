package com.talentotech.redcomunitaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.talentotech.redcomunitaria.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}