package com.company.Controller;

import com.company.ConnectDB.ConnectionUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

/*
 *Created by Berkay KADAMLI
 */
public class AnaEkranController implements Initializable {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Stage dialogStage = new Stage();
    Scene scene;
    @FXML
    private TextField aramatxt;

    @FXML
    private Label labeltxt;

    public AnaEkranController() {
        connection = ConnectionUtil.conDB();
    }

    @FXML
    void excelBtn(ActionEvent event) throws SQLException, IOException {
        String sql = "SELECT * FROM public.exceldeneme";
        preparedStatement = connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();

        OutputStream os = new FileOutputStream("D:\\demirbasExcel.csv");

        os.write(239);
        os.write(187);
        os.write(191);
        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(os,"UTF8"));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Id");
        stringBuilder.append(";");
        stringBuilder.append("Ad Soyad");
        stringBuilder.append(";");
        stringBuilder.append("Bilgisayar");
        stringBuilder.append(";");
        stringBuilder.append("Laptop");
        stringBuilder.append(";");
        stringBuilder.append("Sistem");
        stringBuilder.append(";");
        stringBuilder.append("Monitor 1");
        stringBuilder.append(";");
        stringBuilder.append("Monitor 2");
        stringBuilder.append(";");
        stringBuilder.append("Mouse");
        stringBuilder.append(";");
        stringBuilder.append("Klavye");
        stringBuilder.append(";");
        stringBuilder.append("Microsoft");
        stringBuilder.append(";");
        stringBuilder.append("Meslek");
        stringBuilder.append("\r\n");

        while (resultSet.next()) {
            stringBuilder.append(resultSet.getString("id"));
            stringBuilder.append(";");
            stringBuilder.append(resultSet.getString("adsoyad"));
            stringBuilder.append(";");
            stringBuilder.append(resultSet.getString("bilgisayar"));
            stringBuilder.append(";");
            stringBuilder.append(resultSet.getString("laptop"));
            stringBuilder.append(";");
            stringBuilder.append(resultSet.getString("sistem"));
            stringBuilder.append(";");
            stringBuilder.append(resultSet.getString("monitor_1"));
            stringBuilder.append(";");
            stringBuilder.append(resultSet.getString("monitor_2"));
            stringBuilder.append(";");
            stringBuilder.append(resultSet.getString("mouse"));
            stringBuilder.append(";");
            stringBuilder.append(resultSet.getString("klavye"));
            stringBuilder.append(";");
            stringBuilder.append(resultSet.getString("microsoft"));
            stringBuilder.append(";");
            stringBuilder.append(resultSet.getString("meslek"));
            stringBuilder.append("\r\n");
        }
        printWriter.write(stringBuilder.toString());
        printWriter.close();
        labeltxt.setVisible(true);
        labeltxt.setText("D Klasörüne Excel Çıkartılmıştır");

    }


    @FXML
    void eklebtn(ActionEvent event) {
        labeltxt.setVisible(false);
        try {
            StringBuilder stringBuilder = new StringBuilder();
            String aramaKutusu = aramatxt.getText().toLowerCase().trim();
            String words[] = aramaKutusu.split(" ");

            for (String a : words) {
                stringBuilder.append(a).append("_");
            }

            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            aramaKutusu = stringBuilder.toString();
            String sql = "CREATE TABLE " + aramaKutusu + " (demirbasKayitTarih TEXT,stokKayitNo TEXT,verilen_malzeme_adi TEXT,birim TEXT,marka TEXT,model TEXT,seri_no TEXT,durumu TEXT,aciklama TEXT )";
            preparedStatement = connection.prepareStatement(sql);
            int i = preparedStatement.executeUpdate();
            if (i == 1)
                System.out.println("Creating table successfully");

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../View/GenelEkleEkran.fxml"));
            loader.load();
            GenelEkleController genelEkleController = loader.getController();
            int k = genelEkleController.findCountId();
            k++;
            sql = "INSERT INTO public.exceldeneme (id,adsoyad,bilgisayar,laptop,sistem,monitor_1,monitor_2,mouse,klavye,microsoft,meslek) VALUES (?,?,?,?,?,?,?,?,?,?,?) ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, k);
            preparedStatement.setString(2, aramatxt.getText().toUpperCase());
            preparedStatement.setString(3, "");
            preparedStatement.setString(4, "");
            preparedStatement.setString(5, "");
            preparedStatement.setString(6, "");
            preparedStatement.setString(7, "");
            preparedStatement.setString(8, "");
            preparedStatement.setString(9, "");
            preparedStatement.setString(10, "");
            preparedStatement.setString(11, "");
            i = preparedStatement.executeUpdate();
            if (i == 1)
                System.out.println("Inserting successfully");
            FXMLLoader loader2 = new FXMLLoader();
            loader2.setLocation(getClass().getResource("../View/EkleEkran.fxml"));
            loader2.load();
            EkleController ekleController = loader2.getController();
            ekleController.KisiTextDegistir(aramatxt.getText().toUpperCase());
            Parent p = loader2.getRoot();
            Node node = (Node) event.getSource();
            dialogStage = (Stage) node.getScene().getWindow();
            dialogStage.close();
            Stage stage = new Stage();
            stage.setScene(new Scene(p));
            stage.setTitle("PiriReis Demirbaş Takip Programı");
            stage.showAndWait();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void genelbtn(ActionEvent event) {
        labeltxt.setVisible(false);
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
    void silbtn(ActionEvent event) {
        labeltxt.setVisible(false);
        StringBuilder stringBuilder = new StringBuilder();
        String aramaKutusu = aramatxt.getText().toLowerCase().trim();
        String words[] = aramaKutusu.split(" ");
        for (String a : words) {
            stringBuilder.append(a).append("_");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        aramaKutusu = stringBuilder.toString();
        String sql = "DROP TABLE " + aramaKutusu;
        try {
            preparedStatement = connection.prepareStatement(sql);
            int i = preparedStatement.executeUpdate();
            if (i == 1)
                System.out.println("Table Doesn't exist");
            else
                System.out.println("Deleting Operating Succesfully");
        } catch (SQLException e) {
            labeltxt.setVisible(true);
            labeltxt.setText("Girmiş Olduğunuz Kayıt Bulunamadı Lütfen Tekrar Deneyiniz.");
        }
        aramatxt.clear();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labeltxt.setVisible(false);
    }
}
