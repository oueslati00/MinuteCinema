package com.cinema.minute.Data.Repository;

import com.cinema.minute.Data.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long > {
}
