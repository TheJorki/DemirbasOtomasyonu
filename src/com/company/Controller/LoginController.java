package com.company.Controller;

import com.company.ConnectDB.ConnectionUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

/*
 *Created by Berkay KADAMLI
 */
public class LoginController implements Initializable {

    String username, password;

    @FXML
    private TextField usertxt;
    @FXML
    private PasswordField passwordtxt;
    @FXML
    private Label loginlabel;

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Stage dialogStage = new Stage();
    Scene scene;

    public LoginController() {
        connection = ConnectionUtil.conDB();
    }


    @FXML
    void loginAction(ActionEvent event) {
        username = usertxt.getText().toLowerCase();
        password = passwordtxt.getText().toLowerCase();

        try {
            String sql = "SELECT * FROM public.kullanici WHERE username = ? and password = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                System.out.println("yanlış");
                usertxt.clear();
                passwordtxt.clear();
                loginlabel.setText("Kullanıcı Adı veya Parola yanlış Lütfen Tekrar Deneyiniz.");
            } else {
                Node node = (Node) event.getSource();
                dialogStage = (Stage) node.getScene().getWindow();
                dialogStage.close();
                scene = new Scene(FXMLLoader.load(getClass().getResource("../View/AnaEkran.fxml")));
                dialogStage.setScene(scene);
                dialogStage.show();
                loginlabel.setText("");
                loginlabel.setVisible(false);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
