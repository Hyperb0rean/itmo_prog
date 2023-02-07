package com.greg.lab8;

import com.greg.lab8.client.Client;
import com.greg.lab8.client.util.LocaleHolder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        ResourceBundle rb  = ResourceBundle.getBundle("com.greg.lab8.GUILabels",LocaleHolder.getLocale());

        FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 500);
        stage.setTitle(rb.getString("authWindow"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        LocaleHolder.setLocale(new Locale("ru","RU"));
        launch();

    }
}