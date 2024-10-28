package com.example.avtorisacion.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.avtorisacion.DB;
import com.example.avtorisacion.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class InfoArticle {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea text_area;

    @FXML
    private Label text_name;

    DB db = new DB();

    @FXML
    void backToArticles(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        HelloApplication.setScene("articles-panel.fxml", stage);
    }

    @FXML
    void initialize() throws IOException {
        text_area.setText(db.getTextArea(ArticlesPanelController.id));
        text_name.setText(db.getTitle(ArticlesPanelController.id));
    }

}
