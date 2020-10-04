package com.company.Controller;

import com.company.ConnectDB.ConnectionUtil;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

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
public class GuncelleController implements Initializable {
    @FXML
    private TextField adsoyadtxt;

    @FXML
    private TextField meslektxt;

    @FXML
    private TextField laptoptxt;

    @FXML
    private TextField pctxt;

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
    private Label basarilabel;

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Stage dialogStage = new Stage();
    Scene scene;
    public GuncelleController() {
        connection = ConnectionUtil.conDB();
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
    @FXML
    void genelBtn(MouseEvent event) {
        try {
            Node node = (Node) event.getSource();
            dialogStage = (Stage) node.getScene().getWindow();
            dialogStage.close();
            scene = new Scene(FXMLLoader.load(getClass().getResource("../View/GenelEkran.fxml")));
            dialogStage.setScene(scene);
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void guncelleBtn(MouseEvent event) throws SQLException, InterruptedException {
        String adSoyad= adsoyadtxt.getText().toUpperCase().trim();
        String sql = "UPDATE public.exceldeneme SET meslek =?, microsoft =?, klavye = ?, mouse = ?, monitor_1 =?, monitor_2 =?, sistem =?, laptop =?, bilgisayar =? WHERE adsoyad=?" ;

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,meslektxt.getText());
        preparedStatement.setString(2,officetxt.getText());
        preparedStatement.setString(3,klavyetxt.getText());
        preparedStatement.setString(4,mousetxt.getText());
        preparedStatement.setString(5,mon1txt.getText());
        preparedStatement.setString(6,mon2txt.getText());
        preparedStatement.setString(7,sistemtxt.getText());
        preparedStatement.setString(8,laptoptxt.getText());
        preparedStatement.setString(9,pctxt.getText());
        preparedStatement.setString(10,adSoyad);
        int i = preparedStatement.executeUpdate();
        basarilabel.setVisible(true);
        PauseTransition wait = new PauseTransition(Duration.seconds(3));
        wait.setOnFinished((e) -> {
            basarilabel.setVisible(false);
            wait.playFromStart();
        });
        wait.play();

    }

    public void textDegistir(String adsoyad,String meslek,String office,String klavye,String mouse,String mon1,String mon2,String sistem,String laptop,String pc) {
        adsoyadtxt.setText(adsoyad);
        meslektxt.setText(meslek);
        officetxt.setText(office);
        klavyetxt.setText(klavye);
        mousetxt.setText(mouse);
        mon1txt.setText(mon1);
        mon2txt.setText(mon2);
        sistemtxt.setText(sistem);
        laptoptxt.setText(laptop);
        pctxt.setText(pc);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        adsoyadtxt.setEditable(false);
    }
}
