package com.cinema.minute.Data.Repository;

import com.cinema.minute.Data.Entity.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepo extends JpaRepository<UploadFile,Integer> {
}
