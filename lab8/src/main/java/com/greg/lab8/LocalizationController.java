package com.greg.lab8;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import com.greg.lab8.client.util.LocaleHolder;
import com.greg.lab8.common.util.data.OrganizationType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LocalizationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label chooseLangLabel;

    @FXML
    private Label localLabel;

    @FXML
    private ChoiceBox<String> langBox;

    @FXML
    private Button localizationButton;

    @FXML
    void initialize() {
        ResourceBundle rb  = ResourceBundle.getBundle("com.greg.lab8.GUILabels", LocaleHolder.getLocale());
        localLabel.setText(rb.getString("localLabel"));
        localizationButton.setText(rb.getString("localizationButton"));
        chooseLangLabel.setText(rb.getString("chooseLangLabel"));

        langBox.getItems().addAll("Русский ru_RU", "Românesc ro_RO", "Ελληνική el_GR", "Español(Puerto-Rico) es_PR");

        localizationButton.setOnAction(actionEvent -> {
            LocaleHolder.setLocale(new Locale(langBox.getValue().split(" ")[1].split("_")[0],langBox.getValue().split(" ")[1].split("_")[1]));


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
            localizationButton.getScene().getWindow().hide();
        });

    }

}
