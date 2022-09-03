package com.example.MyLrsAppFinal.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Synoptique_param2 {

    private int year;
    private String routeName;
    private String event;
    private int voie;
    private Double pkd;
    private Double pkf;
    private String event1;
    private String event2;
    private String event3;
    private String attrribute1;
    private String attrribute2;
    private String attrribute3;

}
