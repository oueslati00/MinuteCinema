package com.cinema.minute.Data.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "File")
public class UploadFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(name = "url_file")
    private String UrlFile;

    @NotBlank
    @Column(name = "type")
    private String typeFile;

    @NotBlank
    @Column(name = "name")
    private String name;

    @OneToOne(mappedBy = "video")
    private Videodkika videodkika;

    @OneToMany(mappedBy = "file")
    private List<CompteRendu> compteRendu;

    @OneToMany(mappedBy = "video")
    private List<Cours> cours;

    @OneToMany(mappedBy = "imageUser")
    private List<User> user;
}
