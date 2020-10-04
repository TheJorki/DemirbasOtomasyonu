package com.company.Controller;

import com.company.ConnectDB.ConnectionUtil;
import com.company.Model.KisiModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
public class KisiController implements Initializable {

    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    Stage dialogStage = new Stage();
    Scene scene;

    @FXML
    private TableView<KisiModel> table;
    @FXML
    private TableColumn<KisiModel, String> col_kayitTarih;
    @FXML
    private TableColumn<KisiModel, String> col_demirbasNo;
    @FXML
    private TableColumn<KisiModel, String> col_malzemeTur;
    @FXML
    private TableColumn<KisiModel, String> col_birim;
    @FXML
    private TableColumn<KisiModel, String> col_marka;
    @FXML
    private TableColumn<KisiModel, String> col_modelNo;
    @FXML
    private TableColumn<KisiModel, String> col_seriNo;
    @FXML
    private TableColumn<KisiModel, String> col_durum;
    @FXML
    private TableColumn<KisiModel, String> col_aciklama;
    @FXML
    private Label kisiaditxt;


    public KisiController() {
        connection = ConnectionUtil.conDB();
    }

    ObservableList<KisiModel> observableList = FXCollections.observableArrayList();

    @FXML
    void gosterBtn(MouseEvent event) {
        observableList.clear();
        listAll();
    }

    @FXML
    void ekleBtn(MouseEvent event) throws IOException {

        String[] aramaKutusu = kisiaditxt.getText().trim().split("-");


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/EkleEkran.fxml"));
        loader.load();
        EkleController ekleController = loader.getController();
        ekleController.KisiTextDegistir(aramaKutusu[0].trim());

        Parent p = loader.getRoot();
        Node node = (Node) event.getSource();
        dialogStage = (Stage) node.getScene().getWindow();
        dialogStage.close();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.setTitle("PiriReis Demirbaş Takip Programı");
        stage.show();

    }

    @FXML
    void silBtn(MouseEvent event) throws SQLException {
        String sql = "DELETE FROM " + tabloAratmak() + " WHERE demirbaskayittarih = ? and stokkayitno = ? AND verilen_malzeme_adi = ? AND birim = ? AND  marka = ? AND model = ? AND seri_no = ? AND durumu = ? AND aciklama = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, table.getSelectionModel().getSelectedItem().getDemirbasKayitTarih());
        preparedStatement.setString(2, table.getSelectionModel().getSelectedItem().getStokKayitNo());
        preparedStatement.setString(3, table.getSelectionModel().getSelectedItem().getVerilen_malzeme_adi());
        preparedStatement.setString(4, table.getSelectionModel().getSelectedItem().getBirim());
        preparedStatement.setString(5, table.getSelectionModel().getSelectedItem().getMarka());
        preparedStatement.setString(6, table.getSelectionModel().getSelectedItem().getModel());
        preparedStatement.setString(7, table.getSelectionModel().getSelectedItem().getSeri_no());
        preparedStatement.setString(8, table.getSelectionModel().getSelectedItem().getDurumu());
        preparedStatement.setString(9, table.getSelectionModel().getSelectedItem().getAciklama());
        int i = preparedStatement.executeUpdate();
        if (i == 1)
            System.out.println("...OK...");
        observableList.clear();
        listAll();

    }

    @FXML
    void guncelleBtn(MouseEvent event) throws IOException {
//buradan güncellemek istediğimiz verilerin satırda duran verileri gidip bir tane yeni sayfa açtığımda oraya yazmasını sağlıyacam ve oraya bunları yazdıracam
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/KisiGuncelle.fxml"));
        loader.load();
        KisiGuncelleController kisiGuncelleController = loader.getController();
        kisiGuncelleController.textleriDegistir(tabloAratmak(),
                table.getSelectionModel().getSelectedItem().getDemirbasKayitTarih(),
                table.getSelectionModel().getSelectedItem().getStokKayitNo(),
                table.getSelectionModel().getSelectedItem().getVerilen_malzeme_adi(),
                table.getSelectionModel().getSelectedItem().getBirim(),
                table.getSelectionModel().getSelectedItem().getMarka(),
                table.getSelectionModel().getSelectedItem().getModel(),
                table.getSelectionModel().getSelectedItem().getSeri_no(),
                table.getSelectionModel().getSelectedItem().getDurumu(),
                table.getSelectionModel().getSelectedItem().getAciklama());
        Parent p = loader.getRoot();
        Node node = (Node) event.getSource();
        dialogStage = (Stage) node.getScene().getWindow();
        dialogStage.close();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.setTitle("PiriReis Demirbaş Takip Programı");
        stage.show();


    }


    String tabloAratmak() {
        StringBuilder stringBuilder = new StringBuilder();
        String aramaKutusu = kisiaditxt.getText().toLowerCase().trim();
        String words[] = aramaKutusu.split("-");
        String enson[] = words[0].trim().split(" ");

        for (String a : enson) {
            stringBuilder.append(a).append("_");
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        aramaKutusu = stringBuilder.toString();
        return aramaKutusu;
    }

    void listAll() {
        String sql = "SELECT * FROM public." + tabloAratmak();
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                observableList.add(new KisiModel(
                        resultSet.getString("demirbaskayittarih"),
                        resultSet.getString("stokkayitno"),
                        resultSet.getString("verilen_malzeme_adi"),
                        resultSet.getString("birim"),
                        resultSet.getString("marka"),
                        resultSet.getString("model"),
                        resultSet.getString("seri_no"),
                        resultSet.getString("durumu"),
                        resultSet.getString("aciklama")));
            }
            table.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void kisiEkle(String s) {
        kisiaditxt.setText(s);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        col_kayitTarih.setCellValueFactory(new PropertyValueFactory<>("demirbasKayitTarih"));
        col_demirbasNo.setCellValueFactory(new PropertyValueFactory<>("stokKayitNo"));
        col_malzemeTur.setCellValueFactory(new PropertyValueFactory<>("verilen_malzeme_adi"));
        col_birim.setCellValueFactory(new PropertyValueFactory<>("birim"));
        col_marka.setCellValueFactory(new PropertyValueFactory<>("marka"));
        col_modelNo.setCellValueFactory(new PropertyValueFactory<>("model"));
        col_seriNo.setCellValueFactory(new PropertyValueFactory<>("seri_no"));
        col_durum.setCellValueFactory(new PropertyValueFactory<>("durumu"));
        col_aciklama.setCellValueFactory(new PropertyValueFactory<>("aciklama"));

    }
}
