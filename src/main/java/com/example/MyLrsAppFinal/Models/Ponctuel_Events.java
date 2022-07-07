package com.example.MyLrsAppFinal.Models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.RouteMatcher;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "Ponctuel_Events")
public class Ponctuel_Events {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;
    private String route_name;
    @Column(name = "PKEVENT", nullable = true)
    private  Double pkEvent;

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
    private Date date_ajoute;
    private String Actif;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="route_id",referencedColumnName = "route_id")
    private Lrs_routes route;

//tack ckare of
    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(
            name="event_typeId",
            referencedColumnName = "id"
    )
    private EventType event_type;


}
