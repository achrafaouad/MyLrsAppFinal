package com.example.MyLrsAppFinal.Models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "Linear_Event")
public class Linear_Event {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;
    private String route_name;
    private  Double pkd;
    private  Double pkf;
    @Column(name = "voie",nullable = true)
    private int voie;
    private String image;
    private String c1;
    private String c2;
    private String c3;

    private Double d1;
    private Double d2;
    private Double d3;

    private Date t1;
    private Date t2;
    private Date t3;

    private Date date_ajoute ;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(
            name="event_typeId",
            referencedColumnName = "id"
    )
    private EventType event_type;

    private String Actif;


    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(
            name="route_Id",
            referencedColumnName = "route_id"
    )

    private Lrs_routes lrs_routes;


}
