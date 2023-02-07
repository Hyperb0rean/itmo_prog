package com.greg.lab8.client.util;

import com.greg.lab8.common.util.data.*;

import java.util.Scanner;

public class Asker {

    Scanner scanner = new Scanner(System.in);

    private   String askName(){
        System.out.println("Введите название организации:");
        return scanner.nextLine();
    }

    private Coordinates askCoordinates(){
        Coordinates coordinates = new Coordinates();
        System.out.println("Введите координату x организации:");
        coordinates.setX(scanner.nextInt());
        scanner.nextLine();
        System.out.println("Введите координату у организации:");
        coordinates.setY(scanner.nextDouble());
        scanner.nextLine();
        return coordinates;
    }

    private   Float askAnnualTurnover(){
        System.out.println("Введите оборот организации:");
        return scanner.nextFloat();
    }

    private OrganizationType askOrgType(){
        System.out.println("Выбереете тип организации из предложенных(числом):\n    1.GOVERNMENT\n" +
                "    2.TRUST\n" +
                "    3.PRIVATE_LIMITED_COMPANY\n" +
                "    4.OPEN_JOINT_STOCK_COMPANY");
        OrganizationType result = OrganizationType.values()[scanner.nextInt()-1];
        scanner.nextLine();
        return result;
    }
    private   int askEmployeeCount(){
        System.out.println("Введите количество работников организации:");
        int result = scanner.nextInt();
        scanner.nextLine();
        return result;
    }

    private Address askPostalAdress(){
        System.out.println("Введите почтовый адрес организации:");
        Address address = new Address();
        System.out.println("Введите название улицы организации:");
        address.setStreet(scanner.nextLine());
        Location location = new Location();
        System.out.println("Введите координату x адреса:");
        location.setX(scanner.nextInt());
        scanner.nextLine();
        System.out.println("Введите координату y адреса:");
        location.setY(scanner.nextInt());
        scanner.nextLine();
        System.out.println("Введите координату z адреса:");
        location.setZ(scanner.nextFloat());
        scanner.nextLine();
        address.setTown(location);
        return address;
    }

    public Organization askOrganisation(){
        Organization organization = new Organization();

        organization.generateId();
        organization.setCreationDate();
        organization.setName(askName());
        organization.setCoordinates(askCoordinates());
        organization.setAnnualTurnover(askAnnualTurnover());
        organization.setEmployeesCount(askEmployeeCount());
        organization.setType(askOrgType());
        organization.setPostalAddress(askPostalAdress());

        return organization;
    }

    public User askUser(){
        User user = new User();
        user.setName(askUsername());
        user.setPassword(askPassword());
        return user;
    }

    private String askUsername(){
        System.out.println("Введите имя пользователя");
        return  scanner.nextLine();
    }
    private String askPassword(){
        System.out.println("Введите пароль");
        return scanner.nextLine();
    }
}
