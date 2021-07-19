package com.cinema.minute.Data.Entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@ToString
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Formateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @NotBlank
    @Size(max = 20)
    private String name;


    @Column(name = "age")
    private Integer age;

    @NotBlank
    @Size(max = 20)
    @Column(name = "title")
    private String title;

    @NotBlank
    @Size(max = 250)
    @Column(name = "picUrl")
    private String picUrl;


    @OneToMany(mappedBy="formateur")
    private List<Formation> formations;
}
