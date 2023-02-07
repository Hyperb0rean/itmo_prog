package com.greg.lab8;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.greg.lab8.client.util.*;
import com.greg.lab8.common.util.data.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label authLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Label passwordLabel;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Button registerButton;

    @FXML
    private Label usernameLabel;

    @FXML
    private TextField usernameText;



    @FXML
    void initialize() {

        ResourceBundle rb  = ResourceBundle.getBundle("com.greg.lab8.GUILabels", LocaleHolder.getLocale());
        authLabel.setText(rb.getString("authLabel"));
        usernameLabel.setText(rb.getString("usernameLabel"));
        passwordLabel.setText(rb.getString("passwordLabel"));
        loginButton.setText(rb.getString("loginButton"));
        registerButton.setText(rb.getString("registerButton"));

        loginButton.setOnAction(actionEvent -> {

            ClientCommandManager commandManager = new ClientCommandManager();
            RequestManager manager = commandManager.getRequestManager();
            User user =  new User(usernameText.getText(),passwordText.getText());
            UserHolder.setUser(user);
            manager.sendRequest(manager.makeRequest(usernameText.getText(),"login",user));

            if(manager.isResponseCode()){


                loginButton.getScene().getWindow().hide();

                FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("main.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load(), 1000, 600);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage stage =new Stage();
                stage.setTitle(rb.getString("mainWindow"));
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();



            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("authErrorTitle"));
                alert.setHeaderText(rb.getString("authErrorMessage"));
                alert.setContentText(manager.getResponseMessage());
                alert.showAndWait();
            }

        });

        registerButton.setOnAction(actionEvent -> {
            loginButton.setOnAction(event -> {

                ClientCommandManager commandManager = new ClientCommandManager();
                RequestManager manager = commandManager.getRequestManager();
                User user =  new User(usernameText.getText(),passwordText.getText());
                UserHolder.setUser(user);
                manager.sendRequest(manager.makeRequest(usernameText.getText(),"register",user));


                if(manager.isResponseCode()){


                    loginButton.getScene().getWindow().hide();

                    FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("main.fxml"));
                    Parent root = null;
                    try {
                        root = fxmlLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene scene = new Scene(root, 1000, 600);
                    Stage stage =new Stage();
                    stage.setTitle(rb.getString("mainWindow"));
                    stage.setResizable(false);
                    stage.setScene(scene);
                    stage.show();



                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(rb.getString("authErrorTitle"));
                    alert.setHeaderText(rb.getString("authErrorMessage"));
                    alert.setContentText(manager.getResponseMessage());
                    alert.showAndWait();
                }

            });
        });
    }

}