package com.company.Controller;

import com.company.ConnectDB.ConnectionUtil;
import com.company.Model.GenelModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/*
 *Created by Berkay KADAMLI
 */
public class GenelEkranController implements Initializable {
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    Stage dialogStage = new Stage();
    Scene scene;


    @FXML
    private TextField aramatxt;

    @FXML
    private TableColumn<GenelModel, Integer> col_id;
    @FXML
    private TableColumn<GenelModel, String> col_adsoyad;
    @FXML
    private TableColumn<GenelModel, String> col_bilgisayar;
    @FXML
    private TableColumn<GenelModel, String> col_laptop;
    @FXML
    private TableColumn<GenelModel, String> col_sistem;
    @FXML
    private TableColumn<GenelModel, String> col_mon1;
    @FXML
    private TableColumn<GenelModel, String> col_mon2;

    @FXML
    private TableColumn<GenelModel, String> col_mouse;

    @FXML
    private TableColumn<GenelModel, String> col_klavye;

    @FXML
    private TableColumn<GenelModel, String> col_office;

    @FXML
    private TableColumn<GenelModel, String> col_meslek;
    @FXML
    private TableView<GenelModel> table;

    ObservableList<GenelModel> observableList = FXCollections.observableArrayList();

    public GenelEkranController() {
        connection = ConnectionUtil.conDB();
    }

    @FXML
    void eklebtn(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/GenelEkleEkran.fxml"));
        loader.load();
        GenelEkleController genelEkleController = loader.getController();
        genelEkleController.adSoyadDegistir(aramatxt.getText().toUpperCase());
        Parent p = loader.getRoot();
        Node node = (Node) event.getSource();
        dialogStage = (Stage) node.getScene().getWindow();
        dialogStage.close();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.setTitle("PiriReis Demirbaş Takip Programı");
        stage.showAndWait();

//        try {
//            //Yeni form ekranını ayarlıyoruz.
//            scene = new Scene(FXMLLoader.load(getClass().getResource("../View/GenelEkleEkran.fxml")));
//            dialogStage.setScene(scene);
//            dialogStage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @FXML
    void anamenubtn(MouseEvent event) {
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
    void toplambtn(MouseEvent event) {
        String alertInfo = "Toplam Bilgisayar sayısı :: " + findTotal("bilgisayar") + " \nToplam Laptop Sayısı :: " + findTotal("laptop")
                + "\nToplam Monitor 1 sayısı :: " + findTotal("monitor_1") + "\nToplam Monitor 2 sayısı :: " + findTotal("monitor_2")
                + "\nToplam Mouse sayısı :: " + findTotal("mouse") + "\nToplam Klavye sayısı :: " + findTotal("klavye") + "\nToplam Microsoft Office sayısı :: " + findTotal("microsoft");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(alertInfo);
        alert.setHeaderText("Genel Toplam Sayısı");
        alert.setTitle("Genel Toplam");
        alert.showAndWait();
    }

    @FXML
    void bulbtn(MouseEvent event) throws SQLException {
        table.getItems().clear();
        String aramaKutusu = aramatxt.getText().toUpperCase();
        String sql = "SELECT * FROM public.exceldeneme WHERE adsoyad = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, aramaKutusu);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            observableList.add(new GenelModel(
                    resultSet.getInt("id"),
                    resultSet.getString("adsoyad"),
                    resultSet.getString("bilgisayar"),
                    resultSet.getString("laptop"),
                    resultSet.getString("sistem"),
                    resultSet.getString("monitor_1"),
                    resultSet.getString("monitor_2"),
                    resultSet.getString("mouse"),
                    resultSet.getString("klavye"),
                    resultSet.getString("microsoft"),
                    resultSet.getString("meslek")));
        }
        table.setItems(observableList);
    }

    int findTotal(String s) {
        try {
            preparedStatement = connection.prepareStatement("SELECT COUNT(" + s + ") max FROM public.exceldeneme ");
            int i = 0;
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                i = resultSet.getInt("max");
            }
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @FXML
    void silbtn(MouseEvent event) {
        StringBuilder stringBuilder = new StringBuilder();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Silmek İstediğinize Emin Misiniz ?");
        alert.setHeaderText("Silme İşlemi");
        alert.setTitle("Silme İşlemi");
        Optional<ButtonType> buttonResult = alert.showAndWait();

        String aramaKutusu2 = aramatxt.getText().toLowerCase().trim();
        String words[] = aramaKutusu2.split(" ");

        for (String a : words) {
            stringBuilder.append(a).append("_");
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        aramaKutusu2 = stringBuilder.toString();
        if (buttonResult.get() == ButtonType.OK) {
            String aramaKutusu = aramatxt.getText().toUpperCase();
            String sql = "DELETE FROM public.exceldeneme WHERE id = ?";
//            String sql2 = "DROP TABLE " + aramaKutusu2;
            try {
                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setInt(1,  table.getSelectionModel().getSelectedItem().getId());
                int i = preparedStatement.executeUpdate();
                if (i == 1)
                    System.out.println("Deleting a row is completed");
//                preparedStatement = connection.prepareStatement(sql2);
//                i = preparedStatement.executeUpdate();
//                if (i == 1)
//                    System.out.println("Deleting Table is completed");
                listAll();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }

    void listAll() {
        observableList.clear();
        String sql = "SELECT * FROM public.exceldeneme ORDER BY id ASC";
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                observableList.add(new GenelModel(
                        resultSet.getInt("id"),
                        resultSet.getString("adsoyad"),
                        resultSet.getString("bilgisayar"),
                        resultSet.getString("laptop"),
                        resultSet.getString("sistem"),
                        resultSet.getString("monitor_1"),
                        resultSet.getString("monitor_2"),
                        resultSet.getString("mouse"),
                        resultSet.getString("klavye"),
                        resultSet.getString("microsoft"),
                        resultSet.getString("meslek")));
            }
            table.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void yenileBtn(MouseEvent event) {
        aramatxt.clear();
        listAll();
    }

    @FXML
    void guncelleBtn(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/Guncelle.fxml"));
        loader.load();
        GuncelleController guncelleController = loader.getController();
//        String adsoyad,String meslek,String office,String klavye,String mouse,String mon1,String mon2,String sistem,String laptop,String pc
        guncelleController.textDegistir(aramatxt.getText().toUpperCase(),
                table.getSelectionModel().getSelectedItem().getMeslek(),
                table.getSelectionModel().getSelectedItem().getMicrosoft_office(),
                table.getSelectionModel().getSelectedItem().getKlavye(),
                table.getSelectionModel().getSelectedItem().getMouse(),
                table.getSelectionModel().getSelectedItem().getMonitor_1(),
                table.getSelectionModel().getSelectedItem().getMonitor_2(),
                table.getSelectionModel().getSelectedItem().getSistem(),
                table.getSelectionModel().getSelectedItem().getLaptop(),
                table.getSelectionModel().getSelectedItem().getBilgisayar()
                );
        //buraya bütün textfieldları doldurmasını iste

        Parent p = loader.getRoot();
        Node node = (Node) event.getSource();
        dialogStage = (Stage) node.getScene().getWindow();
        dialogStage.close();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.setTitle("PiriReis Demirbaş Takip Programı");
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_adsoyad.setCellValueFactory(new PropertyValueFactory<>("adsoyad"));
        col_bilgisayar.setCellValueFactory(new PropertyValueFactory<>("bilgisayar"));
        col_laptop.setCellValueFactory(new PropertyValueFactory<>("laptop"));
        col_sistem.setCellValueFactory(new PropertyValueFactory<>("sistem"));
        col_mon1.setCellValueFactory(new PropertyValueFactory<>("monitor_1"));
        col_mon2.setCellValueFactory(new PropertyValueFactory<>("monitor_2"));
        col_mouse.setCellValueFactory(new PropertyValueFactory<>("mouse"));
        col_klavye.setCellValueFactory(new PropertyValueFactory<>("klavye"));
        col_office.setCellValueFactory(new PropertyValueFactory<>("microsoft_office"));
        col_meslek.setCellValueFactory(new PropertyValueFactory<>("meslek"));
        listAll();

        //Tabloda bir yere basıldığında sütunlardan sadece adsoyad sütununu alıp arama kutucuğuna yazdırıyor
        table.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 1)//tek tıklamada kutucuğa yazdırıyor
                    aramatxt.setText(table.getSelectionModel().getSelectedItem().getAdsoyad());
                    //Çift tıklamada ona ait sayfaya gidiyor
                else if (event.getClickCount() == 2) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../View/Kisi.fxml"));
                    try {
                        loader.load();
                        KisiController kisiController = loader.getController();
                        kisiController.kisiEkle(aramatxt.getText().toUpperCase() + " - " + table.getSelectionModel().getSelectedItem().getMeslek());
                        Parent p = loader.getRoot();
                        //Ana Sayfanın kapanmasını istemediğim için bunlar yorum satırında
//                        Node node = (Node) event.getSource();
//                        dialogStage = (Stage) node.getScene().getWindow();
//                        dialogStage.close();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(p));
                        stage.setTitle("PiriReis Demirbaş Takip Programı");
                        stage.showAndWait();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
