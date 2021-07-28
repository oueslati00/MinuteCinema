package com.cinema.minute.Controllers;

import com.cinema.minute.Service.CommentService;
import com.cinema.minute.ui.Model.Request.Comments.CommentRequest;
import com.cinema.minute.ui.Model.Response.Comments.CommentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/Comment/")
public class Comment {

    private CommentService commentService;

    @Autowired
    public Comment(CommentService commentService){
        this.commentService =commentService;
    }


    @PostMapping()
  //  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addComment(@RequestBody CommentRequest commentRequest){
        commentService.addComment(commentRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "user/{id}")
    public ResponseEntity<?> getAllCommentByUserId(@PathVariable long id){
        List<?> commentresponses =commentService.getCommentsByUser(id);
        return new ResponseEntity<>(commentresponses,HttpStatus.OK);
    }
    @GetMapping(value = "cours/{id}")
    public ResponseEntity<?> getAllCommentByCoursId(@PathVariable long id){
        List<?> commentresponses =commentService.getCommentByCours(id);
        return new ResponseEntity<>(commentresponses,HttpStatus.OK);
    }
    @GetMapping(value = "{id}")
    public ResponseEntity<?> getCommentById(@PathVariable long id){
        CommentResponse commentresponses =commentService.getComments(id);
        return new ResponseEntity<>(commentresponses,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeComment(@PathVariable long id){
        commentService.removeComment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@RequestBody CommentRequest commentRequest,@PathVariable Long id){
        commentService.updateComment(commentRequest,id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
