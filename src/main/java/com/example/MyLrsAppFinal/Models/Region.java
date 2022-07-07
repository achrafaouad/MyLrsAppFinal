package com.example.MyLrsAppFinal.Models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name="Region")
public class Region{
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "nom_region",nullable = true)
    private String nom_region;

    public Long getId_region() {
        return id;
    }

    public void setId_region(Long id_region) {
        this.id = id_region;
    }

}
