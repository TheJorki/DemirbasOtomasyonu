package com.company.Controller;

import com.company.ConnectDB.ConnectionUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PipedReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/*
 *Created by Berkay KADAMLI
 */
public class EkleController implements Initializable {
    @FXML
    private TextField demirbastxt;

    @FXML
    private TextField stokkayittxt;

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
    private TextField kisitxt;

    Connection connection;
    PreparedStatement preparedStatement = null;
    Scene scene;
    Stage dialogStage = new Stage();
    String demirbaskayittarih, stokkayitno, verilen_malzeme_adi, birim, marka, model, seri_no, durumu, aciklama;

    public EkleController() {
        connection = ConnectionUtil.conDB();
    }

    @FXML
    void AnaMenuBtn(ActionEvent event) {
        //Kendi form sayfasını kapatmaya yarıyor..
        Node node = (Node) event.getSource();
        dialogStage = (Stage) node.getScene().getWindow();
        dialogStage.close();
        //buraya kadar kod
        try {
            //Yeni form ekranını ayarlıyoruz.
            scene = new Scene(FXMLLoader.load(getClass().getResource("../View/AnaEkran.fxml")));
            dialogStage.setScene(scene);
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void DatabaseEkleBtn(MouseEvent event) throws SQLException, IOException {


        StringBuilder stringBuilder = new StringBuilder();
        String aramaKutusu = kisitxt.getText().toLowerCase().trim();
        String words[] = aramaKutusu.split(" ");
        for (String a : words) {
            stringBuilder.append(a).append("_");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        aramaKutusu = stringBuilder.toString();

        demirbaskayittarih = demirbastxt.getText();
        stokkayitno = stokkayittxt.getText();
        verilen_malzeme_adi = malzemetxt.getText();
        birim = birimtxt.getText();
        marka = markatxt.getText();
        model = modeltxt.getText();
        seri_no = seriNotxt.getText();
        durumu = durumtxt.getText();
        aciklama = aciklamatxt.getText();

        String sql = "INSERT INTO " + aramaKutusu + " (demirbaskayittarih,stokkayitno,verilen_malzeme_adi,birim,marka,model,seri_no,durumu,aciklama) VALUES (?,?,?,?,?,?,?,?,?) ";
        preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, demirbaskayittarih);
        preparedStatement.setString(2, stokkayitno);
        preparedStatement.setString(3, verilen_malzeme_adi);
        preparedStatement.setString(4, birim);
        preparedStatement.setString(5, marka);
        preparedStatement.setString(6, model);
        preparedStatement.setString(7, seri_no);
        preparedStatement.setString(8, durumu);
        preparedStatement.setString(9, aciklama);

        int k = preparedStatement.executeUpdate();
        if (k == 1) {
            System.out.println("OK");
            textSil();
        } else {
            System.out.println("OK either");
            textSil();
        }


        //Genel ekleControllerdan findCountId yi bulacağız.


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textSil();

        // AnaEkrandan arama kutusunun textini alıyorum
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/AnaEkran.fxml"));
//        AnaEkranController anaEkranController = loader.getController();
//        String aramaKutusu = anaEkranController.getAramaKutuText();
//        kisitxt.setText(aramaKutusu);
    }

    void textSil() {
        demirbastxt.clear();
        stokkayittxt.clear();
        malzemetxt.clear();
        birimtxt.clear();
        markatxt.clear();
        modeltxt.clear();
        seriNotxt.clear();
        durumtxt.clear();
        aciklamatxt.clear();
    }

    public void KisiTextDegistir(String s) {
        kisitxt.setText(s);
        kisitxt.setEditable(false);
    }
}
