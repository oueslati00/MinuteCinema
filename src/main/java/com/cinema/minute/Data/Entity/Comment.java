package com.cinema.minute.Data.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 10)
    @Column(name = "description")
    private String description;


    @Column(name="date_post")
    private LocalDate localDate;


    @ManyToOne
    @JoinColumn(name="cour_id")
    private Cours cour;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
