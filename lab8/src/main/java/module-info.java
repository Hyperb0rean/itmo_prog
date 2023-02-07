module com.greg.lab8 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.sql;


    opens com.greg.lab8 to javafx.fxml, com.greg.lab8.common.util.data;
    opens com.greg.lab8.common.util.data to com.google.gson, javafx.base;
    exports com.greg.lab8.client;
    exports com.greg.lab8;
    opens com.greg.lab8.client to javafx.fxml;
}