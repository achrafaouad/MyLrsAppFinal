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
@Table(name = "Lrs_routes")
public class Lrs_routes {
    @Id
    @GeneratedValue
    @Column(name = "route_id", nullable = false)
    private Long route_id;
    private String route_name;

    @Column(name = "voie",nullable = true)
    private int voie;

    @Column(name = "pkd",nullable = true)
    private double pkd;
    @Column(name = "pkf",nullable = true)
    private double pkf;


    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="province_id",referencedColumnName = "id")
    public Province province;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="reference_id",referencedColumnName = "id")

    public Reference reference;

}
