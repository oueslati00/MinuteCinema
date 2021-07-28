package com.cinema.minute.ui.Model.Request.Comments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {
    private Integer cour;
    private String username;
    private String description;
}
