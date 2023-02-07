package com.greg.lab8.common.util.data;

import com.greg.lab8.common.commands.exceptions.LimitExceededException;

public class Coordinates {
    private Integer x; //Значение поля должно быть больше -98, Поле не может быть null
    private double y; //Значение поля должно быть больше -167

    public Coordinates() {

    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        try {
            if(x <= -98){
                this.x = -97;
                throw new LimitExceededException("Значение поля должно быть больше -98");
            }
            else {
                this.x = x;
            }

        }catch (LimitExceededException ex){
            System.err.println(ex.getMessage());
        }
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        try {
            if(y <= -167){
                this.y = -166;
                throw new LimitExceededException("Значение поля должно быть больше -167");
            }
            else {
                this.y = y;
            }

        }catch (LimitExceededException ex){
            System.err.println(ex.getMessage());
        }
    }

    public Coordinates(Integer x, double y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates(Integer x) {
        this.x = x;
    }

    public boolean vallidateInput(){
        return x>-98 & y>-167 & x!=0;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
