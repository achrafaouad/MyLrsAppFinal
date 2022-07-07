package com.example.MyLrsAppFinal.Models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Synoptique_param {

    private int year;
    private String routeName;
    private String event;
    private int voie;
    private Double pkd;
    private Double pkf;


}
