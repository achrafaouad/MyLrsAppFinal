package com.example.MyLrsAppFinal.Models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ponctuelDataForMapReq {
    private Long thematiqueid;
    private String attribute;
    private String operateur;
    private String valeur;

}
