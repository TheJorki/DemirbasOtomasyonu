package com.company.Controller;

import com.company.ConnectDB.ConnectionUtil;
import javafx.animation.PauseTransition;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/*
 *Created by Berkay KADAMLI
 */
public class KisiGuncelleController implements Initializable {

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    Stage dialogStage = new Stage();
    Scene scene;

    public KisiGuncelleController() {
        connection = ConnectionUtil.conDB();
    }

    @FXML
    private TextField kayittarihtxt;
    @FXML
    private TextField demirbastxt;
    @FXML
    private TextField malzemetxt;
    @FXML
    private TextField birimtxt;
    @FXML
    private TextField markatxt;
    @FXML
    private TextField modeltxt;
    @FXML
    private TextField seriNotxt;
    @FXML
    private TextField durumtxt;
    @FXML
    private TextField aciklamatxt;
    @FXML
    private Label labeltxt;

    @FXML
    private Label basarilabel;

    List list = new ArrayList();


    @FXML
    void guncelleBtn(MouseEvent event) throws SQLException {
        //UPDATE SQL SORGUSU YAZILACAK TEXTFIELDDAN ALINAN DEĞERLERİ GÖNDERECEK

        String sql = "UPDATE " + labeltxt.getText() + " SET demirbaskayittarih = ?,stokkayitno = ?,verilen_malzeme_adi = ?,birim = ?,marka = ?,model = ?,seri_no = ?,durumu = ?,aciklama = ? " +
                "WHERE demirbaskayittarih = ? AND stokkayitno = ? AND verilen_malzeme_adi = ? AND birim = ? AND marka = ? AND model = ? AND seri_no = ? AND durumu = ? AND aciklama = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, kayittarihtxt.getText());
        preparedStatement.setString(2, demirbastxt.getText());
        preparedStatement.setString(3, malzemetxt.getText());
        preparedStatement.setString(4, birimtxt.getText());
        preparedStatement.setString(5, markatxt.getText());
        preparedStatement.setString(6, modeltxt.getText());
        preparedStatement.setString(7, seriNotxt.getText());
        preparedStatement.setString(8, durumtxt.getText());
        preparedStatement.setString(9, aciklamatxt.getText());
        preparedStatement.setString(10, (String) list.get(0));
        preparedStatement.setString(11, (String) list.get(1));
        preparedStatement.setString(12, (String) list.get(2));
        preparedStatement.setString(13, (String) list.get(3));
        preparedStatement.setString(14, (String) list.get(4));
        preparedStatement.setString(15, (String) list.get(5));
        preparedStatement.setString(16, (String) list.get(6));
        preparedStatement.setString(17, (String) list.get(7));
        preparedStatement.setString(18, (String) list.get(8));

        int i = preparedStatement.executeUpdate();
        if (i == 1)
            System.out.println("Updating table successfully");

        basarilabel.setVisible(true);
        PauseTransition wait = new PauseTransition(Duration.seconds(3));
        wait.setOnFinished((e) -> {
            basarilabel.setVisible(false);
            wait.playFromStart();
        });
        wait.play();

    }
    @FXML
    void anamenuBtn(MouseEvent event) {
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



    public void textleriDegistir(String label, String kayittarih, String demirbas, String malzeme, String birim, String marka, String model, String seriNo, String durum, String aciklama) {
        labeltxt.setText(label);
        kayittarihtxt.setText(kayittarih);
        demirbastxt.setText(demirbas);
        malzemetxt.setText(malzeme);
        birimtxt.setText(birim);
        markatxt.setText(marka);
        modeltxt.setText(model);
        seriNotxt.setText(seriNo);
        durumtxt.setText(durum);
        aciklamatxt.setText(aciklama);
        list.add(kayittarih);
        list.add(demirbas);
        list.add(malzeme);
        list.add(birim);
        list.add(marka);
        list.add(model);
        list.add(seriNo);
        list.add(durum);
        list.add(aciklama);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        kayittarihtxt.setEditable(true);
        basarilabel.setVisible(false);
    }
}
