package com.example.MyLrsAppFinal.Models;

public class LineColorFomFront {
    public String eventType;
    public String colors;

    public LineColorFomFront(String eventType, String colors) {
        this.eventType = eventType;
        this.colors = colors;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }

}
