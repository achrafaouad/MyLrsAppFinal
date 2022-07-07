package com.example.MyLrsAppFinal.Models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MapPoint {

    private Double x;
    private Double y;
    private String c1;
    private String c2;
    private String c3;
    private String thematique;
    private String image;
    private Double d1;
    private Double d2;
    private Double d3;

    private Date t1;
    private Date t2;
    private Date t3;



}
