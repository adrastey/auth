module com.example.avtorisacion {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens com.example.avtorisacion to javafx.fxml;
    exports com.example.avtorisacion;
    exports com.example.avtorisacion.models;
    opens com.example.avtorisacion.models to javafx.fxml;
    exports com.example.avtorisacion.controllers;
    opens com.example.avtorisacion.controllers to javafx.fxml;
}