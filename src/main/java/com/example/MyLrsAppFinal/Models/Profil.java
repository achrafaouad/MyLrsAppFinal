package com.example.MyLrsAppFinal.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profil {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prof")
    @SequenceGenerator(name = "prof", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String description;
    private Date dateAjout;

    @ElementCollection
    private List<String> ihm = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToMany
    @JoinTable(
            name = "ddd",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_province"))
    private Set<Province> provinces = new HashSet<>();

}
