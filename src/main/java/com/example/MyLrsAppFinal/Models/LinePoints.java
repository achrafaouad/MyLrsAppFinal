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
public class LinePoints {

    private Double x1;
    private Double y1;
    private Double x2;
    private Double y2;
    private String image;

    private String c1;
    private String c2;
    private String c3;
    private String thematique;

    private Double d1;
    private Double d2;
    private Double d3;

    private Date t1;
    private Date t2;
    private Date t3;

}
