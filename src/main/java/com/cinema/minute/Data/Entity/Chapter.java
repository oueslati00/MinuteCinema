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
@Table(name="Chapter")
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max=20)
    private String name;

    @ManyToOne
    @JoinColumn(name="formation_id")
    private Formation formation;

    @OneToMany(mappedBy = "chapter")
    private List<Cours> cours;

    @Override
    public String toString(){
        return "name" + name ;
    }
}
