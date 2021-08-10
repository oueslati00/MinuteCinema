package com.cinema.minute.Service;

import com.cinema.minute.Data.Entity.Comment;
import com.cinema.minute.Data.Entity.Cours;
import com.cinema.minute.Data.Entity.User;
import com.cinema.minute.Data.Repository.CommentRepository;
import com.cinema.minute.Data.Repository.CourRepo;
import com.cinema.minute.Data.Repository.UserRepository;
import com.cinema.minute.ui.Model.Request.Comments.CommentRequest;
import com.cinema.minute.ui.Model.Response.Comments.CommentResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private CommentRepository commentRepository;
    private CourRepo courRepo;

    @Autowired
    public CommentService(UserRepository userRepository, ModelMapper modelMapper, CommentRepository commentRepository,CourRepo courRepo) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.commentRepository = commentRepository;
        this.courRepo = courRepo;
    }




    public void addComment(CommentRequest commentRequest) {
        //TODO : validate UserName with Token

        User user = userRepository.findByUsername(commentRequest.getUsername()).orElseThrow(()-> new RuntimeException("this user does not exsist"));
       long courid= commentRequest.getCour();
        System.out.println(courid);
       Cours cours =courRepo.findById(courid).orElseThrow(()-> new RuntimeException("this  cours does not exist"));
        Comment comment = new Comment();
        comment.setCour(cours);
        comment.setDescription(commentRequest.getDescription());
       comment.setUser(user);
        comment.setLocalDate(LocalDate.now());
        commentRepository.save(comment);


    }

    public List<?> getCommentsByUser(Long idUser) {
      User user= userRepository.findById(idUser).orElseThrow(()-> new RuntimeException("this user does not exist"));
       return user.getComments().stream()
               .sorted(Comparator.comparing(Comment::getLocalDate))
               .map(comment -> {
                   CommentResponse commentResponse=null;
                   try {
                       commentResponse = new CommentResponse();
                       commentResponse.setUserName(user.getUsername());
                       commentResponse.setCourId(comment.getId());
                       commentResponse.setDescription(comment.getDescription());
                       commentResponse.setLocalDate(comment.getLocalDate());

                   }catch(NullPointerException e){}
                   return commentResponse;
               })
               .collect(Collectors.toList());
    }

    public List<?> getCommentByCours(Long Id){

        Cours cours= courRepo.findById(Id).orElseThrow(()-> new RuntimeException("this cours does not exist"));
        return cours.getComments().stream()
                .sorted(Comparator.comparing(Comment::getLocalDate))
                .map(comment -> {
                    CommentResponse commentResponse=null;
                    try {
                        commentResponse = new CommentResponse();
                        commentResponse.setUserName(comment.getUser().getUsername());
                        commentResponse.setCourId(comment.getId());
                        commentResponse.setDescription(comment.getDescription());
                        commentResponse.setLocalDate(comment.getLocalDate());
                        commentResponse.setUserId(comment.getUser().getId());

                    }catch(NullPointerException e){}
                    return commentResponse;
                }).collect(Collectors.toList());

    }

    public void removeComment(long id) {
          commentRepository.deleteById(id);
    }


    public void updateComment(CommentRequest commentRequest,Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(()-> new RuntimeException("this comment does not exist"));
        comment.setDescription(commentRequest.getDescription());
        comment.setLocalDate(LocalDate.now());
        commentRepository.save(comment);
           }

    public CommentResponse getComments(long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(()-> new RuntimeException("this comment does not exist"));
         CommentResponse commentResponse = modelMapper.map(comment,CommentResponse.class);
         commentResponse.setUserName(comment.getUser().getUsername());
         return commentResponse;

    }
}
