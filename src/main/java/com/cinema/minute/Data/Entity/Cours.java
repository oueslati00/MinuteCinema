package com.cinema.minute.Data.Entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="cours")
public class Cours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max=20)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private Chapter chapter;

    @OneToMany(mappedBy = "cour")
    private List<CompteRendu> compteRendu;

    @ManyToOne
    @JoinColumn(name = "video" )
    private UploadFile video;

    @OneToMany(mappedBy = "cour")
    private List<Comment> comments;

    @Override
    public String toString() {
        return "name :" + name;
    }
}
