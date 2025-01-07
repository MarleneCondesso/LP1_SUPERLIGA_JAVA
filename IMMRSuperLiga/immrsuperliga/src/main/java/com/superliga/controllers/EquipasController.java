/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superliga.controllers;

import com.superliga.App;
import com.superliga.models.Equipa;
import com.superliga.services.EquipaService;
import com.superliga.services.EstadioService;
import com.superliga.utils.AlertBox;
import java.io.IOException;
import java.net.URL;
import static java.util.Objects.isNull;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 *
 * @author 35191
 */
public class EquipasController implements Initializable{

    
    @FXML
    private TableView<Equipa> tvEquipas;

    @FXML
    private TableColumn<Equipa, Integer> idCol;

    @FXML
    private TableColumn<Equipa, String> nomeCol;

    @FXML
    private TableColumn<Equipa, String> siglaCol;

    @FXML
    private TableColumn<Equipa, String> estadioCol;

    @FXML
    private Button btnJogadores;

    @FXML
    private Button btnConfig;

    private EquipaService serviceEquipas = new EquipaService();
    private EstadioService estadioService = new EstadioService();
    ObservableList obsListEquipas;
    
    ConfigEquipasController configEquipasController;
    JogadoresEquipaController jogadoresEquipaController;
    private Scene scene;
    private Stage stage;
    private String title;
    
    /**
     * Método para abrir Configurações
     * @param event 
     */
    @FXML
    void openConfigurations(ActionEvent event) {
        try {
            showModalConfigView("configEquipas");
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    /**
     * Método para abrir jogadores da Equipa
     * @param event 
     */
    @FXML
    void openPlayers(ActionEvent event) {
        Equipa equipa = tvEquipas.getSelectionModel().getSelectedItem();

        Integer pos = tvEquipas.getSelectionModel().getSelectedIndex();
        
            if (!isNull(equipa)) {

            try {
                showModalJogadoresEquipaView("jogadoresEquipa", equipa, pos);
            } catch (IOException ex) {
                System.out.println("Erro : " + ex);
            }

            } else {
                AlertBox alerta = new AlertBox (Alert.AlertType.ERROR, "Equipa em falta", "Por favor, selecione uma Equipa.");
            }
        
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshTable();
    }
    
      /**
     * Método de atualização dos dados na tabela Equipas
     * @param 
     */
    public void refreshTable() {
        ObservableList obsList = FXCollections.observableArrayList(serviceEquipas.getAll());
        configColums();
        tvEquipas.getItems().setAll(obsList);
    }
    
    
    /**
     * Método de Configuração dos dados na tabela Equipas
     */
    private void configColums() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        siglaCol.setCellValueFactory(new PropertyValueFactory<>("sigla"));
        estadioCol.setCellValueFactory(equipa -> new SimpleStringProperty(estadioService.getByPrimaryKey(equipa.getValue().getIdEstadio()).getNome()));
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

        configEquipasController = fxmlLoader.getController();

        scene = new Scene(fxmlLoader.getRoot());
        stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * Método para abrir a view das Config
     *
     * @param fxml
     * @throws IOException
     */
    private void showModalJogadoresEquipaView(String fxml, Equipa equipa, Integer pos) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.load();

        jogadoresEquipaController = fxmlLoader.getController();
        jogadoresEquipaController.setJogadoresEquipaView(equipa, pos, this);
        scene = new Scene(fxmlLoader.getRoot());
        stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
}
