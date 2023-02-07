package com.greg.lab8;


import java.io.IOException;
import java.net.URL;
import java.util.*;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.greg.lab8.client.util.ClientCommandManager;
import com.greg.lab8.client.util.LocaleHolder;
import com.greg.lab8.client.util.RequestManager;
import com.greg.lab8.client.util.UserHolder;
import com.greg.lab8.common.util.data.Organization;
import com.greg.lab8.common.util.data.OrganizationType;
import com.greg.lab8.common.util.data.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button vizualizationButton;
    @FXML
    private Button localizationButton;

    @FXML
    private TableColumn<Organization, Date> date;

    @FXML
    private TableColumn<Organization, Integer> employees;

    @FXML
    private TableColumn<Organization, Integer> id;

    @FXML
    private TableColumn<Organization, String> name;

    @FXML
    private TableView<Organization> organisationsTableView;

    @FXML
    private TableColumn<Organization, String> street;

    @FXML
    private TableColumn<Organization, Integer> town_x;

    @FXML
    private TableColumn<Organization, Integer> town_y;

    @FXML
    private TableColumn<Organization, Float> town_z;

    @FXML
    private TableColumn<Organization, Float> turnover;

    @FXML
    private TableColumn<Organization, OrganizationType> type;

    @FXML
    private TableColumn<Organization, Integer> x;

    @FXML
    private TableColumn<Organization, Double> y;

    ObservableList<Organization> organizations;


    private  String addWindow;
    private  String removeWindow;
    private  String updateWindow;
    private  String visualizeWindow;
    private  String localizeWindow;
    private  String mainWindow;
    private  String charts;

    @FXML
    void initialize() {

        refresh();



        LinkedList<Organization> organizationsList = new LinkedList<>();

        organizations = FXCollections.observableArrayList(organizationsList);



        id.setCellValueFactory(new PropertyValueFactory<Organization,Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Organization,String>("name"));
        date.setCellValueFactory(new PropertyValueFactory<Organization,Date>("creationDate"));
        turnover.setCellValueFactory(new PropertyValueFactory<Organization,Float>("annualTurnover"));
        employees.setCellValueFactory(new PropertyValueFactory<Organization,Integer>("employeesCount"));
        type.setCellValueFactory(new PropertyValueFactory<Organization,OrganizationType>("type"));
        street.setCellValueFactory(new PropertyValueFactory<Organization,String>("street"));
        x.setCellValueFactory(new PropertyValueFactory<Organization,Integer>("x"));
        town_x.setCellValueFactory(new PropertyValueFactory<Organization,Integer>("town_x"));
        town_y.setCellValueFactory(new PropertyValueFactory<Organization,Integer>("town_y"));
        y.setCellValueFactory(new PropertyValueFactory<Organization,Double>("y"));
        town_z.setCellValueFactory(new PropertyValueFactory<Organization,Float>("town_z"));


        organisationsTableView.setItems(organizations);
        organisationsTableView.setEditable(false);


        Task<Void> task = new Task<Void>() {
            @Override protected Void call() throws Exception {
                while (true){
                    Thread.sleep(1000);
                    ClientCommandManager commandManager = new ClientCommandManager();
                    RequestManager manager = commandManager.getRequestManager();
                    User user =  UserHolder.getUser();
                    manager.sendRequest(manager.makeRequest(user.getName(),"show",""));
                    GsonBuilder builder = new GsonBuilder();

                    Platform.runLater(() ->{
                        LinkedList<Organization> organizationsList = new LinkedList<>();
                        if(manager.isResponseCode()){
                            organizationsList = new LinkedList<>((ArrayList<Organization>) builder.create().fromJson(
                                    manager.getResponseMessage().toString(), new TypeToken<List<Organization>>(){}.getType()
                            ));
                        }
                        organizations = FXCollections.observableArrayList(organizationsList);
                        organisationsTableView.setItems(organizations);
                        organisationsTableView.refresh();
                    });
                }
            }
        };

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();

        addButton.setOnAction(actionEvent -> {


            Task<Void> task1 = new Task<Void>() {
                @Override protected Void call() throws Exception {
                   Platform.runLater(() -> {
                       FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("add.fxml"));
                       Parent root = null;
                       try {
                           root = fxmlLoader.load();
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                       Scene scene = new Scene(root, 400, 580);
                       Stage stage =new Stage();
                       stage.setTitle(addWindow);
                       stage.setResizable(false);
                       stage.setScene(scene);
                       stage.show();
                   });
                    return null;
                }
            };

            Thread th2 = new Thread(task1);
            th2.setDaemon(true);
            th2.start();

        });

        clearButton.setOnAction(actionEvent -> {

            Task<Void> task1 = new Task<Void>() {
                @Override protected Void call() throws Exception {
                    ClientCommandManager commandManager = new ClientCommandManager();
                    RequestManager manager = commandManager.getRequestManager();
                    manager.sendRequest(manager.makeRequest(UserHolder.getUser().getName(),"clear",""));
                    return null;
                }
            };

            Thread th2 = new Thread(task1);
            th2.setDaemon(true);
            th2.start();

        });

        updateButton.setOnAction(actionEvent -> {

            Task<Void> task1 = new Task<Void>() {
                @Override protected Void call() throws Exception {

                        Platform.runLater(() ->{
                            FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("update.fxml"));
                            Parent root = null;
                            try {
                                root = fxmlLoader.load();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Scene scene = new Scene(root, 400, 580);
                            Stage stage =new Stage();
                            stage.setTitle(updateWindow);
                            stage.setResizable(false);
                            stage.setScene(scene);
                            stage.show();
                        });
                    return null;
                }
            };

            Thread th1 = new Thread(task1);
            th1.setDaemon(true);
            th1.start();
        });

        removeButton.setOnAction(actionEvent -> {


            Task<Void> task1 = new Task<Void>() {
                @Override protected Void call() throws Exception {

                        Platform.runLater(() ->{
                            FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("remove.fxml"));
                            Parent root = null;
                            try {
                                root = fxmlLoader.load();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Scene scene = new Scene(root, 400, 580);
                            Stage stage =new Stage();
                            stage.setTitle(removeWindow);
                            stage.setResizable(false);
                            stage.setScene(scene);
                            stage.show();
                        });
                    return null;
                }
            };

            Thread th1 = new Thread(task1);
            th1.setDaemon(true);
            th1.start();
        });

        vizualizationButton.setOnAction(actionEvent -> {
            Stage stage =new Stage();
            stage.setTitle(visualizeWindow);
            final NumberAxis xAxis = new NumberAxis(-100,200,10);
            final NumberAxis yAxis = new NumberAxis(-100,200,10);
            final BubbleChart<Number,Number> blc = new BubbleChart<Number,Number>(xAxis,yAxis);
            xAxis.setLabel("X");
            yAxis.setLabel("Y");
            blc.setTitle(charts);



            ClientCommandManager cmndmgr = new ClientCommandManager();
            RequestManager mgr = cmndmgr.getRequestManager();
            mgr.sendRequest(mgr.makeRequest(UserHolder.getUser().getName(),"show",""));
            GsonBuilder builder1 = new GsonBuilder();
            LinkedList<Organization> list = new LinkedList<>();
            if(mgr.isResponseCode()){
                list = new LinkedList<>((ArrayList<Organization>) builder1.create().fromJson(
                        mgr.getResponseMessage().toString(), new TypeToken<List<Organization>>(){}.getType()
                ));
            }
            Scene scene = new Scene(blc);
            for (Organization o: list) {
                if(o!=null){
                    XYChart.Series<Number,Number> series = new XYChart.Series<Number,Number>();
                    series.setName(o.getName());
                    series.getData().add(new XYChart.Data<Number,Number>(o.getX(),o.getY(),o.getAnnualTurnover()*20/o.getEmployeesCount()));
                    blc.getData().addAll(series);
                }
            }

            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

        });

        localizationButton.setOnAction(actionEvent -> {



            Task<Void> task1 = new Task<Void>() {
                @Override protected Void call() throws Exception {
                        Platform.runLater(() ->{

                            FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("localization.fxml"));
                            Parent root = null;
                            try {
                                root = fxmlLoader.load();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Scene scene = new Scene(root, 400, 580);
                            Stage stage =new Stage();
                            stage.setTitle(localizeWindow);
                            stage.setResizable(false);
                            stage.setScene(scene);
                            localizationButton.getScene().getWindow().hide();
                            stage.show();
                            refresh();

                        });
                        return null;
                }
            };

            Thread th1 = new Thread(task1);
            th1.setDaemon(true);
            th1.start();




        });
    }

    public void refresh(){

        ResourceBundle rb  = ResourceBundle.getBundle("com.greg.lab8.GUILabels",LocaleHolder.getLocale());
        addWindow = rb.getString("addWindow");
        removeWindow = rb.getString("removeWindow");
        updateWindow = rb.getString("updateWindow");
        visualizeWindow = rb.getString("visualizeWindow");
        localizeWindow = rb.getString("localizeWindow");
        mainWindow = rb.getString("mainWindow");
        charts = rb.getString("charts");
        addButton.setText(rb.getString("addButton"));
        clearButton.setText(rb.getString("clearButton"));
        updateButton.setText(rb.getString("updateButton"));
        removeButton.setText(rb.getString("removeButton"));
        vizualizationButton.setText(rb.getString("visualizeButton"));
        localizationButton.setText(rb.getString("localizeButton"));
    }

}


