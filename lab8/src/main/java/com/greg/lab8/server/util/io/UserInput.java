package com.greg.lab8.server.util.io;

import com.greg.lab8.common.util.data.*;

import java.net.SocketAddress;
import java.util.Scanner;

public class UserInput implements Readable {

    private final Scanner scanner =new Scanner(System.in);

    public Scanner getScanner() {
        return scanner;
    }

    public  String askName(){
        System.out.println("Введите название организации:");
        return scanner.nextLine();
    }

    public  Coordinates askCoordinates(){
        Coordinates coordinates = new Coordinates();
        System.out.println("Введите координату x организации:");
        coordinates.setX(scanner.nextInt());
        scanner.nextLine();
        System.out.println("Введите координату у организации:");
        coordinates.setY(scanner.nextDouble());
        scanner.nextLine();
        return coordinates;
    }

    public  Float askAnnualTurnover(){
        System.out.println("Введите оборот организации:");
        return scanner.nextFloat();
    }

    public   OrganizationType askOrgType(){
        System.out.println("Выбереете тип организации из предложенных(числом):\n    1.GOVERNMENT\n" +
                "    2.TRUST\n" +
                "    3.PRIVATE_LIMITED_COMPANY\n" +
                "    4.OPEN_JOINT_STOCK_COMPANY");
        OrganizationType result = OrganizationType.values()[scanner.nextInt()-1];
        scanner.nextLine();
        return result;
    }
    public  int askEmployeeCount(){
        System.out.println("Введите количество работников организации:");
        int result = scanner.nextInt();
        scanner.nextLine();
        return result;
    }

    public  Address askPostalAdress(){
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

    @Override
    public  Organization readOrganisation(){
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

    @Override
    public String read() {
        return scanner.nextLine()+" ";
    }

    @Override
    public SocketAddress getCurrentClient() {
        return null;
    }

    @Override
    public User readUser() {
        return null;
    }

}
