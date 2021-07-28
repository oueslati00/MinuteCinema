package com.cinema.minute.Data.Entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Formation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @NotBlank
    @Size(max = 20)
    @Column(name = "name")
    private String name;

    @Column(name = "first_date")
    private LocalDate FirstDate;

    @Column(name = "second_date")
    private LocalDate FinalDate;

    @NotBlank
    @Column(name = "description")
    private String description;


    @OneToMany(mappedBy = "formation")
    private List<Chapter> chapter;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return "name : " + name ;
    }
}
