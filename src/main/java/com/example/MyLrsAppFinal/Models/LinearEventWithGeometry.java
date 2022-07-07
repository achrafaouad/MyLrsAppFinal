package com.example.MyLrsAppFinal.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LinearEventWithGeometry {


    private Long id;
    private String route_name;
    private Long route_id;
    private  Double pkd;
    private  Double pkf;
    private int voie;

    private String event_name;
    private String jsond;
}
