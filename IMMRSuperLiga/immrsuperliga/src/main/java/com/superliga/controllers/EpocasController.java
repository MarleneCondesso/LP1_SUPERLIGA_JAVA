package com.superliga.controllers;

import com.superliga.App;
import com.superliga.models.Epoca;
import com.superliga.services.EpocaService;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ruteg
 */
public class EpocasController implements Initializable {

    @FXML
    public TableView<Epoca> tblEpocas;
    @FXML
    private TableColumn<Epoca, String> colNrEpoca;
    @FXML
    private TableColumn<Epoca, Date> colDataInicio;
    @FXML
    private TableColumn<Epoca, Date> colDataFim;
    @FXML
    private Button btnConfig;

    ObservableList obsList;
    private ConfigEpocaController configEpocaController;
    private ConfigEpocasController configEpocasController;
    private EpocaService service = new EpocaService();

    private Scene scene;

    private Stage stage;

    private String title;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshTable();
    }

    /**
     * Método para atualização dos dados na tabela
     */
    public void refreshTable() {
        obsList = FXCollections.observableArrayList(service.getAll());
        configColumns();
        tblEpocas.getItems().setAll(obsList);
    }

    /**
     * Método de configuração dos dados na tabela
     */
    private void configColumns() {
        colNrEpoca.setCellValueFactory(epoca -> new SimpleStringProperty(epoca.getValue().getNumeroEpoca()));
        colDataInicio.setCellValueFactory(new PropertyValueFactory<>("dataInicio"));
        colDataFim.setCellValueFactory(new PropertyValueFactory<>("dataFim"));
    }

    /**
     * Método para abrir a view das Config
     *
     * @param fxml
     * @throws IOException
     */
    private void showModalConfigView(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.load();

        configEpocasController = fxmlLoader.getController();

        scene = new Scene(fxmlLoader.getRoot());
        stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Método para abrir Configurações
     *
     * @param event
     */
    @FXML
    private void openConfig(ActionEvent event) {
        try {
            showModalConfigView("configEpocas");
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

}
