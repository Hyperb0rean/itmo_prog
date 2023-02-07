package com.greg.lab8.common.util.data;

import com.greg.lab8.common.commands.exceptions.LimitExceededException;

public class Address {
    private String street; //Длина строки не должна быть больше 166, Поле может быть null
    private Location town; //Поле может быть null

    public Address() {

    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        try {
           if(street.length() > 166){
               this.street = null;
               throw new LimitExceededException("Длина строки не должна быть больше 166");
           }
           else {
               this.street = street;
           }

        }catch (LimitExceededException ex){
            System.err.println(ex.getMessage());
        }
    }

    public Location getTown() {
        return town;
    }

    public void setTown(Location town) {
        this.town = town;
    }

    public Address(String street, Location town) {
        this.street = street;
        this.town = town;
    }

    public boolean vallidateInput(){
        return street != null & town != null ? street.length() <= 166 & town.vallidateInput() :
                street == null & town != null ? town.vallidateInput():
                        street == null || street.length() <= 166;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", town=" + town +
                '}';
    }
}
