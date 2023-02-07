package com.greg.lab8;

import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.greg.lab8.client.util.*;
import com.greg.lab8.common.util.data.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class UpdateController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label emplLabel;

    @FXML
    private TextField emplText;

    @FXML
    private Label idLabel;

    @FXML
    private TextField idtext;

    @FXML
    private Label nameLabel;

    @FXML
    private TextField nameText;

    @FXML
    private Label streetLabel;

    @FXML
    private TextField streetText;

    @FXML
    private Label townLabel;

    @FXML
    private TextField townXText;

    @FXML
    private TextField townYText;

    @FXML
    private TextField townZText;

    @FXML
    private Label turnLabel;

    @FXML
    private TextField turnText;

    @FXML
    private ChoiceBox<String> typeComboBox;

    @FXML
    private Label typeLabel;

    @FXML
    private Button updateButton;

    @FXML
    private Label updateLabel;

    @FXML
    private Label xLabel;

    @FXML
    private TextField xText;

    @FXML
    private Label yLabel;

    @FXML
    private TextField yText;

    @FXML
    void initialize() {

        ResourceBundle rb  = ResourceBundle.getBundle("com.greg.lab8.GUILabels", LocaleHolder.getLocale());
        updateLabel.setText(rb.getString("updateLabel"));
        updateButton.setText(rb.getString("updateButton"));
        nameLabel.setText(rb.getString("nameLabel"));
        xLabel.setText(rb.getString("xLabel"));
        yLabel.setText(rb.getString("yLabel"));
        turnLabel.setText(rb.getString("turnLabel"));
        emplLabel.setText(rb.getString("emplLabel"));
        typeLabel.setText(rb.getString("typeLabel"));
        streetLabel.setText(rb.getString("streetLabel"));
        townLabel.setText(rb.getString("townLabel"));

        typeComboBox.getItems().addAll(Arrays.stream(OrganizationType.values()).map(Objects::toString).collect(Collectors.toList()));

        updateButton.setOnAction(actionEvent -> {
            Organization organization = new Organization();
            organization.setName(nameText.getText());
            organization.setCreationDate();
            organization.setCoordinates(new Coordinates(Integer.parseInt(xText.getText()),Double.parseDouble(yText.getText())));
            organization.setAnnualTurnover(Float.parseFloat(turnText.getText()));
            organization.setEmployeesCount(Integer.parseInt(emplText.getText()));
            organization.setType(OrganizationType.valueOf(typeComboBox.getValue()));
            organization.setPostalAddress(new Address(streetText.getText(),new Location(Integer.parseInt(townXText.getText()),Integer.parseInt(townYText.getText()),Float.parseFloat(townZText.getText()))));
            OrganisationHolder.setOrganization(organization);

            ClientCommandManager commandManager = new ClientCommandManager();
            RequestManager manager = commandManager.getRequestManager();
            manager.sendRequest(manager.makeRequest(UserHolder.getUser().getName(),"update ",idtext.getText(),OrganisationHolder.getOrganization()));
            if(manager.isResponseCode()){
                updateButton.getScene().getWindow().hide();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Authorization Error");
                alert.setHeaderText("Unable to authorise user");
                alert.setContentText(manager.getResponseMessage());
                alert.showAndWait();
            }
        });
    }

}
