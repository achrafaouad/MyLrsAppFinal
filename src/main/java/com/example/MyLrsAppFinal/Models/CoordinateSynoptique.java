package com.example.MyLrsAppFinal.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CoordinateSynoptique {
    private String x;
    private List<Number> y = new ArrayList<Number>();
}


