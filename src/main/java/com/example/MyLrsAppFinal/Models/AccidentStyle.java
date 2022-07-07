package com.example.MyLrsAppFinal.Models;


public class AccidentStyle {
    private Number radius;
    private String color;
    private String Ocolor;
    private Number strokWidht;
    private Number maxRadius;
    private Number minRadius;

    public Number getRadius() {
        return radius;
    }

    public AccidentStyle(Number radius, String color, String ocolor, Number strokWidht, Number maxRadius, Number minRadius) {
        this.radius = radius;
        this.color = color;
        Ocolor = ocolor;
        this.strokWidht = strokWidht;
        this.maxRadius = maxRadius;
        this.minRadius = minRadius;
    }

    public void setRadius(Number radius) {
        this.radius = radius;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOcolor() {
        return Ocolor;
    }

    public void setOcolor(String ocolor) {
        Ocolor = ocolor;
    }

    public Number getStrokWidht() {
        return strokWidht;
    }

    public void setStrokWidht(Number strokWidht) {
        this.strokWidht = strokWidht;
    }

    public Number getMaxRadius() {
        return maxRadius;
    }

    public void setMaxRadius(Number maxRadius) {
        this.maxRadius = maxRadius;
    }

    public Number getMinRadius() {
        return minRadius;
    }

    public void setMinRadius(Number minRadius) {
        this.minRadius = minRadius;
    }

    public AccidentStyle() {
    }
}
