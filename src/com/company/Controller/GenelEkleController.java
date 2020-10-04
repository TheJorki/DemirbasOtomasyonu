package com.company.Controller;

import com.company.ConnectDB.ConnectionUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/*
 *Created by Berkay KADAMLI
 */
public class GenelEkleController implements Initializable {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Stage dialogStage = new Stage();
    Scene scene;

    @FXML
    private TextField adsoyadtxt;

    @FXML
    private TextField pctxt;

    @FXML
    private TextField laptoptxt;

    @FXML
    private TextField sistemtxt;

    @FXML
    private TextField mon1txt;

    @FXML
    private TextField mon2txt;

    @FXML
    private TextField mousetxt;

    @FXML
    private TextField klavyetxt;

    @FXML
    private TextField officetxt;

    @FXML
    private TextField meslektxt;
    String adsoyad, bilgisayar, laptop, sistem, monitor_1, monitor_2, mouse, klavye, microsoft_office, meslek;

    public GenelEkleController() {
        connection = ConnectionUtil.conDB();
    }

    int findCountId() {
        try {
            preparedStatement = connection.prepareStatement("SELECT max(id) max_id FROM public.exceldeneme ");
            int i = 0;
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                i = resultSet.getInt("max_id");
            }
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @FXML
    void eklebtn(MouseEvent event) {
        adsoyad = adsoyadtxt.getText().toUpperCase();
        bilgisayar = pctxt.getText().toUpperCase();
        laptop = laptoptxt.getText().toUpperCase();
        sistem = sistemtxt.getText().toUpperCase();
        monitor_1 = mon1txt.getText().toUpperCase();
        monitor_2 = mon2txt.getText().toUpperCase();
        mouse = mousetxt.getText().toUpperCase();
        klavye = klavyetxt.getText().toUpperCase();
        microsoft_office = officetxt.getText().toUpperCase();
        meslek = meslektxt.getText().trim();
        String sql = "INSERT INTO public.exceldeneme (id,adsoyad,bilgisayar,laptop,sistem,monitor_1,monitor_2,mouse,klavye,microsoft,meslek) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        int cnt = findCountId();
        cnt++;
        System.out.println(cnt);
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, cnt);
            preparedStatement.setString(2, adsoyad);
            preparedStatement.setString(3, bilgisayar);
            preparedStatement.setString(4, laptop);
            preparedStatement.setString(5, sistem);
            preparedStatement.setString(6, monitor_1);
            preparedStatement.setString(7, monitor_2);
            preparedStatement.setString(8, mouse);
            preparedStatement.setString(9, klavye);
            preparedStatement.setString(10, microsoft_office);
            preparedStatement.setString(11, meslek);
            int i = preparedStatement.executeUpdate();
            if (i == 1)
                System.out.println("Insertion succesfull");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void anamenuBtn(ActionEvent event) {

        try {
            Node node = (Node) event.getSource();
            dialogStage = (Stage) node.getScene().getWindow();
            dialogStage.close();
            scene = new Scene(FXMLLoader.load(getClass().getResource("../View/AnaEkran.fxml")));
            dialogStage.setScene(scene);
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void adSoyadDegistir(String s) {
        adsoyadtxt.setText(s);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
