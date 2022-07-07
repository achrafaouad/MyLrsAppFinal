package com.example.MyLrsAppFinal.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;



@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DataSYnoptiqueAdvanced {
    private Integer year;
    private ArrayList<CoordinateSynoptique> data = new ArrayList<>();
}
