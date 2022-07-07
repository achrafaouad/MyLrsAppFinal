package com.example.MyLrsAppFinal.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Symbology {
    @Id
    @GeneratedValue
    @Column(name = "symbology_id", nullable = false)
    public Long symbology_id;

    @Override
    public String toString() {
        return "symbology{" +
                "symbology_id=" + symbology_id +
                ", symbology='" + symbology + '\'' +
                '}';
    }


    public Symbology(String symbology) {
        this.symbology = symbology;
    }

    public Symbology(Long symbology_id, String symbology) {
        this.symbology_id = symbology_id;
        this.symbology = symbology;
    }

    public Symbology() {
    }

    public String getSymbology() {
        return symbology;
    }

    public void setSymbology(String symbology) {
        this.symbology = symbology;
    }
    @Column(columnDefinition = "VARCHAR2(1000)")
    public String symbology;

    public Long getSymbology_id() {
        return symbology_id;
    }

    public void setSymbology_id(Long symbology_id) {
        this.symbology_id = symbology_id;
    }
}
