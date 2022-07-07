package com.example.MyLrsAppFinal.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ponctuelEventWithGeometry {

    private Long id;
    private String route_name;
    private Long route_id;
    private  Double pkEvent;
    private int voie;

    private String event_name;
    private String jsond;



}
