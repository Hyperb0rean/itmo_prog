package com.greg.lab8.common.util.data;

public class Location {
    private Integer x; //Поле не может быть null
    private Integer y; //Поле не может быть null
    private float z;

    public Location() {

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public Location(int x, Integer y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location(int x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public boolean vallidateInput(){
        return x!=null & y!=null;
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
