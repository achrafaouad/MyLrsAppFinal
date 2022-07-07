package com.example.MyLrsAppFinal.Models;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString(exclude = {"Users"})
@Table(name = "Province")
public class Province {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;

    public Long getId_province() {
        return id;
    }

    public void setId_province(Long id_province) {
        this.id = id_province;
    }

    @ManyToOne(cascade = CascadeType.MERGE)
    private Region region;



}
