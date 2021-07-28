package com.cinema.minute.Data.Repository;

import com.cinema.minute.Data.Entity.Cours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourRepo  extends JpaRepository<Cours,Long> {
}
