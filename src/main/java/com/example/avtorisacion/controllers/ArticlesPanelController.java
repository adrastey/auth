package com.example.avtorisacion.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.avtorisacion.DB;
import com.example.avtorisacion.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ArticlesPanelController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exit_button, add_article_button;

    @FXML
    private VBox panelVBox;

    static int id;

    @FXML
    void initialize() throws SQLException, IOException {

        DB db = new DB();
        ResultSet resultSet = db.getArticles();
        while (resultSet.next()){
            Node node = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("article.fxml")));

            Label title = (Label) node.lookup("#title");
            title.setText(resultSet.getString("title"));

            Label intro = (Label) node.lookup("#intro");
            intro.setText(resultSet.getString("intro"));

            node.setOnMouseEntered(mouseEvent -> {
                node.setStyle("-fx-background-color: #707173");
            });

            node.setOnMouseExited(mouseEvent -> {
                node.setStyle("-fx-background-color: #343434");
            });

            //node.setId(resultSet.getString("id"));

            node.setOnMouseClicked(event -> {
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                Node source = (Node) event.getSource();
                try {
                    source.setId(resultSet.getString("id"));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                id = Integer.parseInt(panelVBox.getId());
                try {
                    HelloApplication.setScene("info-article.fxml", stage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });


            panelVBox.getChildren().add(node);
            panelVBox.setSpacing(10);
        }

        exit_button.setOnAction(event -> {
            try {
                exitUser(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        add_article_button.setOnAction(event -> {
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            try {
                HelloApplication.setScene("add_article.fxml", stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    private void exitUser(ActionEvent event) throws IOException {
        File file = new File("user.settings");
        file.delete();

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        HelloApplication.setScene("main.fxml", stage);
    }

}
