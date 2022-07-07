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
public class DataSynoptique {
    private String name;
    private ArrayList<CoordinateSynoptique> data = new ArrayList<>();

}
