package com.example.avtorisacion.controllers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import com.example.avtorisacion.DB;
import com.example.avtorisacion.HelloApplication;
import com.example.avtorisacion.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button avt_button;

    @FXML
    private TextField avt_login;

    @FXML
    private PasswordField avt_password;

    @FXML
    private Button reg_button;

    @FXML
    private TextField reg_email;

    @FXML
    private TextField reg_login;

    @FXML
    private PasswordField reg_password;

    @FXML
    private CheckBox reg_rules;

    private DB db = new DB();

    @FXML
    void initialize() {
        reg_button.setOnAction(event -> {
            registrationUser();
        });
        avt_button.setOnAction(event -> {
            try {
                authUser(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void authUser(ActionEvent event) throws IOException {
        String login = avt_login.getCharacters().toString();
        String password = avt_password.getCharacters().toString();

        avt_login.setStyle("-fx-border-color: #fafafa");
        avt_password.setStyle("-fx-border-color: #fafafa");


        if (login.length() <= 1)
            avt_login.setStyle("-fx-border-color: #e06249");
        else if (password.length() <= 3)
            avt_password.setStyle("-fx-border-color: #e06249");
        else if (!db.authUser(login,md5String(password)))
            avt_button.setText("пользователя нет");
        else
        {
            avt_login.setText("");
            avt_password.setText("");
            avt_button.setText("Всё голово :))");

            FileOutputStream fos = new FileOutputStream("user.settings");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(new User(login));
            oos.close();

            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            HelloApplication.setScene("articles-panel.fxml", stage);
        }
    }

    private void registrationUser()
    {
        String login = reg_login.getCharacters().toString();
        String email = reg_email.getCharacters().toString();
        String password = reg_password.getCharacters().toString();

        reg_login.setStyle("-fx-border-color: #fafafa");
        reg_email.setStyle("-fx-border-color: #fafafa");
        reg_password.setStyle("-fx-border-color: #fafafa");


        if (login.length() <= 1)
            reg_login.setStyle("-fx-border-color: #e06249");
        else if (email.length() <= 5 || !email.contains("@") || !email.contains("."))
            reg_email.setStyle("-fx-border-color: #e06249");
        else if (password.length() <= 3)
            reg_password.setStyle("-fx-border-color: #e06249");
        else if (!reg_rules.isSelected())
            reg_button.setText("Поставьте галочку, сука");
        else if (db.isExistUser(login))
            reg_button.setText("Введите другой логин");
        else {
            db.regUser(login, email, md5String(password));
            reg_login.setText("");
            reg_email.setText("");
            reg_password.setText("");
            reg_button.setText("Всё голово :))");
        }
    }

    public static String md5String(String password) {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = messageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(password.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        BigInteger bigInteger = new BigInteger(1, digest);
        String m5dHex = bigInteger.toString(16);

        while (m5dHex.length() < 32)
            m5dHex = "0" + m5dHex;

        return m5dHex;
    }

}
