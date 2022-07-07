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
public class Event_params {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false  ,insertable = false, updatable = false)
    private Long id;
    private String champs_event;
    private String champ_db_stock;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(
            name="event_Id",
            referencedColumnName = "id"
    )
    private EventType eventType;
}
