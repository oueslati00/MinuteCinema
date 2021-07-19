package com.cinema.minute.Data.Repository;

import com.cinema.minute.Data.Entity.Cours;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourRepo  extends JpaRepository<Cours,Long> {
}
