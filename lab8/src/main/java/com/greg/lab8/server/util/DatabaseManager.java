package com.greg.lab8.server.util;

import com.greg.lab8.common.util.data.*;



import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Random;

public class DatabaseManager extends CollectionManager{
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

     public DatabaseManager(){

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:1099/studs","s335046","vch051");
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try
        {
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found !!");
            return;
        }

        try {
            Statement statement = connection.createStatement();
            //statement.executeUpdate("DROP TABLE IF EXISTS organizations;");
            //statement.executeUpdate("DROP TABLE IF EXISTS users;");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
                    "\"user\" VARCHAR(256) PRIMARY KEY ," +
                    "password VARCHAR(64)," +
                    "salt VARCHAR(64)," +
                    "\"group\" VARCHAR(16)" +
                    ");");

            statement.executeUpdate("CREATE SEQUENCE IF NOT EXISTS idOrg");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS organizations (" +
                    "id INT PRIMARY KEY, " +
                    "\"user\" VARCHAR(256) REFERENCES users(\"user\")," +
                    "\"name\" VARCHAR(1024) NOT NULL, " +
                    "coord_X INT NOT NULL CHECK (coord_X >-98)," +
                    "coord_Y REAL CHECK (coord_Y>-167)," +
                    "\"date\" VARCHAR(256)," +
                    "turn REAL CHECK (turn >0)," +
                    "empl INT CHECK (empl >0)," +
                    "org_type SMALLINT CHECK (org_type>=0) CHECK (org_type<4)," +
                    "street VARCHAR(166)," +
                    "town_X INT NOT NULL, " +
                    "town_Y SMALLINT NOT NULL," +
                    "town_Z REAL" +
                    ");");

            statement.close();
            updateCollection();

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        this.initDate = new Date();
        this.modDate = initDate;
    }

    synchronized public void updateCollection() throws  SQLException{
        this.organizations=new LinkedList<Organization>();
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery("SELECT * from organizations");
        while (res.next()){
            Organization org = new Organization();
            org.setId(res.getInt("id"));
            org.setCreationDate();
            org.setName(res.getString("name"));
            org.setCoordinates(new Coordinates(res.getInt("coord_X"),res.getFloat("coord_Y")));
            org.setAnnualTurnover(res.getFloat("turn"));
            org.setEmployeesCount(res.getInt("empl"));
            org.setType(OrganizationType.values()[res.getShort("org_type")]);
            org.setPostalAddress(new Address(res.getString("street"),new Location(res.getInt("town_X"),res.getInt("town_Y"),res.getFloat("town_Z"))));
            this.organizations.add(org);
        }
        res.close();
        statement.close();
    }

    synchronized public void add(Organization organization,User user) throws SQLException {

            PreparedStatement statement = connection.prepareStatement("INSERT INTO organizations (\"id\",\"name\",coord_X,coord_Y,\"date\",turn,empl,org_type,street,town_X,town_Y,town_Z,\"user\")" +
                    "VALUES (nextval('idOrg'),?,?,?,?,?,?,?,?,?,?,?,?)");
//            statement.setInt(1,organization.getId());
            statement.setString(1,organization.getName());
            statement.setInt(2,organization.getCoordinates().getX());
            statement.setFloat(3, (float) organization.getCoordinates().getY());
            statement.setString(4, organization.getCreationDate().toString());
            statement.setFloat(5,organization.getAnnualTurnover());
            statement.setInt(6,organization.getEmployeesCount());
            statement.setInt(7,organization.getType().ordinal());
            statement.setString(8,organization.getPostalAddress().getStreet());
            statement.setInt(9,organization.getPostalAddress().getTown().getX());
            statement.setInt(10,organization.getPostalAddress().getTown().getY());
            statement.setFloat(11,organization.getPostalAddress().getTown().getZ());
            statement.setString(12, user.getName());

            statement.executeUpdate();

            this.modDate = new Date();
            statement.close();
            updateCollection();
    }

    synchronized public void remove(int id,User user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM organizations WHERE id = ? AND \"user\" = ?");
        statement.setInt(1,id);
        statement.setString(2, user.getName());
        if(statement.executeUpdate() == 0) throw new SQLException("Нет разрешения на изменение данного объекта");
        this.modDate = new Date();
        statement.close();
       updateCollection();
    }

    synchronized public void clear(User user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE from organizations WHERE \"user\" = ?");
        statement.setString(1, user.getName());
        statement.executeUpdate();
        this.modDate = new Date();
        statement.close();
    }

    synchronized public void findUser(User user) throws SQLException, IllegalArgumentException, NoSuchAlgorithmException {

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
        ResultSet resultSet = statement.executeQuery();
        boolean userNotFound = true;
        while (resultSet.next()){
            if(user.getName().equals(resultSet.getString("user"))){
                userNotFound = false;
                String salt = resultSet.getString("salt");
                byte[] inputBytes = (user.getPassword() + salt).getBytes(StandardCharsets.UTF_8);
                MessageDigest md = MessageDigest.getInstance("SHA-384");
                md.update(inputBytes);
                if(!(new String(md.digest(),StandardCharsets.UTF_8).equals(resultSet.getString("password")))) throw new IllegalArgumentException("Неправильно введен логин или пароль");
            }

        }

        if(userNotFound) throw new IllegalArgumentException("Такого пользователя не существует, проверьте корректность введенных данных или зарегистрируйтесь.");

        resultSet.close();
        statement.close();

    }
    synchronized public void insertUser(User user) throws SQLException, NoSuchAlgorithmException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO users (\"user\",password,salt,\"group\") VALUES (?,?,?,?)");
        statement.setString(1, user.getName());

        byte[] saltBytes = new byte[16];
        Random random = new SecureRandom();
        random.nextBytes(saltBytes);
        String salt = new String(saltBytes,StandardCharsets.UTF_8);
        //String salt = "test";
        byte[] inputBytes = (user.getPassword() + salt).getBytes(StandardCharsets.UTF_8);
        MessageDigest md = MessageDigest.getInstance("SHA-384");
        md.update(inputBytes);

        statement.setString(2,new String(md.digest(),StandardCharsets.UTF_8));
        statement.setString(3,salt);
        statement.setString(4,"default");
        statement.executeUpdate();
        statement.close();
    }
}
