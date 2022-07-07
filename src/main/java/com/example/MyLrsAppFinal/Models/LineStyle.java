package com.example.MyLrsAppFinal.Models;


public class LineStyle {

    private String text;
    private String align;
    private String baseline;
    private String rotation;
    private String font;
    private String weight;
    private String placement;
    private String maxangle;
    private boolean overflow;
    private String size;
    private String height;
    private String offsetX;
    private String offsetY;
    private String color;
    private String outline;
    private String outlineWidth;
    private String maxreso;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public String getBaseline() {
        return baseline;
    }

    public void setBaseline(String baseline) {
        this.baseline = baseline;
    }

    public String getRotation() {
        return rotation;
    }

    public void setRotation(String rotation) {
        this.rotation = rotation;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPlacement() {
        return placement;
    }

    public void setPlacement(String placement) {
        this.placement = placement;
    }

    public String getMaxangle() {
        return maxangle;
    }

    public void setMaxangle(String maxangle) {
        this.maxangle = maxangle;
    }

    public boolean isOverflow() {
        return overflow;
    }

    public void setOverflow(boolean overflow) {
        this.overflow = overflow;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(String offsetX) {
        this.offsetX = offsetX;
    }

    public String getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(String offsetY) {
        this.offsetY = offsetY;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOutline() {
        return outline;
    }

    public void setOutline(String outline) {
        this.outline = outline;
    }

    public String getOutlineWidth() {
        return outlineWidth;
    }

    public void setOutlineWidth(String outlineWidth) {
        this.outlineWidth = outlineWidth;
    }

    public String getMaxreso() {
        return maxreso;
    }

    public void setMaxreso(String maxreso) {
        this.maxreso = maxreso;
    }

    public LineStyle(String text, String align, String baseline, String rotation, String font, String weight, String placement, String maxangle, boolean overflow, String size, String height, String offsetX, String offsetY, String color, String outline, String outlineWidth, String maxreso) {
        this.text = text;
        this.align = align;
        this.baseline = baseline;
        this.rotation = rotation;
        this.font = font;
        this.weight = weight;
        this.placement = placement;
        this.maxangle = maxangle;
        this.overflow = overflow;
        this.size = size;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.color = color;
        this.outline = outline;
        this.outlineWidth = outlineWidth;
        this.maxreso = maxreso;
    }

    @Override
    public String toString() {
        return "lineStyle{" +
                "text='" + text + '\'' +
                ", align='" + align + '\'' +
                ", baseline='" + baseline + '\'' +
                ", rotation='" + rotation + '\'' +
                ", font='" + font + '\'' +
                ", weight='" + weight + '\'' +
                ", placement='" + placement + '\'' +
                ", maxangle='" + maxangle + '\'' +
                ", overflow=" + overflow +
                ", size='" + size + '\'' +
                ", height='" + height + '\'' +
                ", offsetX='" + offsetX + '\'' +
                ", offsetY='" + offsetY + '\'' +
                ", color='" + color + '\'' +
                ", outline='" + outline + '\'' +
                ", outlineWidth='" + outlineWidth + '\'' +
                ", maxreso='" + maxreso + '\'' +
                '}';
    }
}

