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
public class Video {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private  Double pkd;
    private  Double pkf;
    private int voie;
    private String path;


    @ManyToOne(cascade= CascadeType.MERGE)
    @JoinColumn(
            name="route_Id",
            referencedColumnName = "route_id"
    )

    private Lrs_routes lrs_routes;

}
