package com.example.MyLrsAppFinal.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RefModel {
    private String route_name;
    private String geometry;
    private Double pkd;
    private Double pkf;
    private int voie;
    private Long province;
    private int reference;
}
