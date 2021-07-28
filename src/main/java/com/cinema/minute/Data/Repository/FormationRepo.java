package com.cinema.minute.Data.Repository;

import com.cinema.minute.Data.Entity.Formation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormationRepo extends JpaRepository<Formation, Integer> {
}
