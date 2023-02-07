package com.greg.lab8.common.util.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.greg.lab8.common.commands.exceptions.LimitExceededException;
import com.greg.lab8.common.commands.exceptions.NullStringException;

import java.util.Date;

public class Organization implements Comparable<Organization>{

    private static int idGen;
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Float annualTurnover; //Поле может быть null, Значение поля должно быть больше 0
    private int employeesCount; //Значение поля должно быть больше 0
    private OrganizationType type; //Поле может быть null
    private Address postalAddress; //Поле может быть null


    private String street;
    private Integer x;
    private Double y;
    private Integer town_x;
    private Integer town_y;

    public String getStreet() {
        return getPostalAddress().getStreet();
    }

    public Integer getX() {
        return getCoordinates().getX();
    }

    public Double getY() {
        return getCoordinates().getY();
    }

    public Integer getTown_x() {
        return getPostalAddress().getTown().getX();
    }

    public Integer getTown_y() {
        return getPostalAddress().getTown().getY();
    }

    public Float getTown_z() {
        return getPostalAddress().getTown().getZ();
    }

    private Float town_z;

    public Organization() {

    }

    public int getId() {
        return id;
    }

    static {
        idGen=1;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void generateId() {
        id = idGen;
        idGen++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        try {
            if(name.isEmpty()){
                this.name = "undefined";
                throw new NullStringException("Строка не может быть пустой");
            }
            else {
                this.name = name;
            }

        }catch (NullStringException ex){
            System.err.println(ex.getMessage());
        }
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(){
        this.creationDate = new Date();
    }

    public Float getAnnualTurnover() {
        return annualTurnover;
    }

    public void setAnnualTurnover(Float annualTurnover) {
        try {
            if(annualTurnover <=0){
                this.annualTurnover = 0.01f;
                throw new LimitExceededException("Значение поля должно быть больше 0");
            }
            else {
                this.annualTurnover = annualTurnover;
            }

        }catch (LimitExceededException ex){
            System.err.println(ex.getMessage());
        }
    }

    public int getEmployeesCount() {
        return employeesCount;
    }

    public void setEmployeesCount(int employeesCount) {
        try {
            if(employeesCount <=0){
                this.employeesCount = 1;
                throw new LimitExceededException("Значение поля должно быть больше 0");
            }
            else {
                this.employeesCount = employeesCount;
            }

        }catch (LimitExceededException ex){
            System.err.println(ex.getMessage());
        }
    }

    public OrganizationType getType() {
        return type;
    }

    public void setType(OrganizationType type) {
        this.type = type;
    }

    public Address getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(Address postalAddress) {
        this.postalAddress = postalAddress;
    }

    public Organization(String name, Coordinates coordinates, Date creationDate, Float annualTurnover, int employeesCount) {
        this.name = name;
        this.coordinates = coordinates;
        this.annualTurnover = annualTurnover;
        this.employeesCount = employeesCount;
    }

    public Organization(String name, Coordinates coordinates, Float annualTurnover, int employeesCount, OrganizationType type) {
        this.name = name;
        this.coordinates = coordinates;
        this.annualTurnover = annualTurnover;
        this.employeesCount = employeesCount;
        this.type = type;
    }

    public Organization(String name, Coordinates coordinates, Float annualTurnover, int employeesCount, OrganizationType type, Address postalAddress) {
        this.name = name;
        this.coordinates = coordinates;
        this.annualTurnover = annualTurnover;
        this.employeesCount = employeesCount;
        this.type = type;
        this.postalAddress = postalAddress;
    }


    @Override
    public int compareTo(Organization o) {
        return this.getId() - o.getId();
    }

    @Override
    public String toString() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String result = gson.toJson(this);
        return result;
    }

    public boolean vallidateInput() throws NullPointerException{
        if(postalAddress!=null){
            return (!name.isEmpty())&(coordinates!=null)&coordinates.vallidateInput()&(annualTurnover>0)&(employeesCount>0)& postalAddress.vallidateInput();
        }
        else {
            return (!name.isEmpty())&(coordinates!=null)&coordinates.vallidateInput()&(annualTurnover>0)&(employeesCount>0);
        }
    }
}
