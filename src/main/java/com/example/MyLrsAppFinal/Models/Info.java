package com.example.MyLrsAppFinal.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Info {
    private Double kiloRoute;
    private Integer nbPoint;
    private Double kiloLinear;
}
