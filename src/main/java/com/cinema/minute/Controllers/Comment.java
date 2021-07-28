package com.cinema.minute.Controllers;

import com.cinema.minute.Service.CommentService;
import com.cinema.minute.ui.Model.Request.Comments.CommentRequest;
import com.cinema.minute.ui.Model.Response.Comments.CommentResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Api(description = "service de gestion de commentaire ")
@RestController
@RequestMapping("/api/user/Comment/")
public class Comment {

    private CommentService commentService;

    @Autowired
    public Comment(CommentService commentService){
        this.commentService =commentService;
    }

     @ApiOperation(value = "ajouter un commenaire ")
    @PostMapping()
  //  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addComment(@RequestBody CommentRequest commentRequest){
        commentService.addComment(commentRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @ApiOperation(value = "recive tout les commentaire ajouter par un Id utilisateur  ")
    @GetMapping(value = "user/{id}")
    public ResponseEntity<?> getAllCommentByUserId(@PathVariable long id){
        List<?> commentresponses =commentService.getCommentsByUser(id);
        return new ResponseEntity<>(commentresponses,HttpStatus.OK);
    }
    @ApiOperation(value = "recive tout les commentaire ajouter dans un chapter   ")
    @GetMapping(value = "cours/{id}")
    public ResponseEntity<?> getAllCommentByCoursId(@PathVariable long id){
        List<?> commentresponses =commentService.getCommentByCours(id);
        return new ResponseEntity<>(commentresponses,HttpStatus.OK);
    }
    @ApiOperation(value = "recive le commentaire par son Id ")
    @GetMapping(value = "{id}")
    public ResponseEntity<?> getCommentById(@PathVariable long id){
        CommentResponse commentresponses =commentService.getComments(id);
        return new ResponseEntity<>(commentresponses,HttpStatus.OK);
    }
    @ApiOperation(value = "recive tout les commentaire ajouter par un Id utilisateur  ")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeComment(@PathVariable long id){
        commentService.removeComment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @ApiOperation(value = "update comment by Id // on peut l'utiliser en cas de besions ")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@RequestBody CommentRequest commentRequest,@PathVariable Long id){
        commentService.updateComment(commentRequest,id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
