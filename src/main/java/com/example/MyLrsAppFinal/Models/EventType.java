package com.example.MyLrsAppFinal.Models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "EventType")
public class EventType {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="name" ,unique = true ,nullable = false)
    private String name;

    @Column(name="style", columnDefinition = "VARCHAR2(1000)")
    private String style;

    @Column(name="Pointstyle", columnDefinition = "VARCHAR2(1000)")
    private String pointStyle;


}
