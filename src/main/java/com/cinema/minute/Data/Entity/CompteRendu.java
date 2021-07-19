package com.cinema.minute.Data.Entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CompteRendu")
public class CompteRendu {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;


    @Column(name = "upload_date")
    private LocalDateTime localDateTime;

    @ManyToOne
    @JoinColumn(name = "user_id" , referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "file_id" , referencedColumnName = "id")
    private UploadFile file;

    @ManyToOne
    @JoinColumn(name = "cours_id" , referencedColumnName = "id")
    private Cours cour;
}
