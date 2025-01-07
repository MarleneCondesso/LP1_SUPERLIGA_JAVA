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
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author 35191
 */
public class ConfigEquipasController implements Initializable {

    
    @FXML
    public TableView<Equipa> tvEquipas;

    @FXML
    private TableColumn<Equipa, Integer> idCol;

    @FXML
    private TableColumn<Equipa, String> nomeCol;

    @FXML
    private TableColumn<Equipa, String> siglaCol;

    @FXML
    private TableColumn<Equipa, String> estadioCol;

    @FXML
    private Button btnAdicionar;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    private Scene scene;
    private Stage stage;
    private String title;
    
    private EquipaService equipaService = new EquipaService();
    private EstadioService estadioService = new EstadioService();
    
    ConfigEquipaController configEquipaController;
    @FXML
    void add (MouseEvent event) {
        try {
            showModalsConfigEquipaView("configEquipa", null, "Adicionar Equipa", null);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    void edit (MouseEvent event) {
    
        Equipa equipa = tvEquipas.getSelectionModel().getSelectedItem();
        Integer pos = tvEquipas.getSelectionModel().getSelectedIndex();
        
        if(!isNull(equipa)){

            try {
                
                showModalsConfigEquipaView("configEquipa", equipa, "Editar Equipa", pos);
            } catch (IOException ex) {
                Logger.getLogger(ConfigEquipasController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }else{
            AlertBox alerta = new AlertBox(Alert.AlertType.ERROR, "Equipa em falta", "Por favor, selecione uma Equipa.");
        }
    }

    @FXML
    void delete (MouseEvent event) {
                Equipa equipa = tvEquipas.getSelectionModel().getSelectedItem();
        
        if (equipa != null){
            AlertBox alerta = new AlertBox(Alert.AlertType.CONFIRMATION, "Eliminar", "Tem a certeza que pretende eliminar esta Equipa?");

                if (alerta.getButton().get() == ButtonType.OK) {

                    Boolean response = equipaService.deleteByPrimaryKey(equipa.getId());

                    if (response) {
                        AlertBox alertaConf = new AlertBox(Alert.AlertType.CONFIRMATION, "SUCESSO", "Equipa eliminada com SUCESSO!");
                        refreshTable();
                    } else {
                        AlertBox alertaErr = new AlertBox(Alert.AlertType.ERROR, "ERRO", "Não foi possível eliminar a Equipa.");
                    }
                }
        }else{
            AlertBox alerta = new AlertBox(Alert.AlertType.ERROR, "Equipa em falta", "Por favor, selecione uma Equipa.");
        }  
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshTable();
    }
    
    /**
     * Método para atualização dos dados na tabela
     */
    public void refreshTable() {
        ObservableList obsList = FXCollections.observableArrayList(equipaService.getAll());
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
        estadioCol.setCellValueFactory(estadio -> new SimpleStringProperty(estadioService.getByPrimaryKey(estadio.getValue().getIdEstadio()).getNome()));
    }
    
        /**
     * Método para abrir a view de Adicionar ou Editar Equipa.
     *
     * @param fxml
     * @param op
     * @param titulo
     * @param pos
     * @throws IOException
     */
    private void showModalsConfigEquipaView(String fxml, Equipa equipa, String titulo, Integer pos) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.load();

        configEquipaController = fxmlLoader.getController();
        configEquipaController.setEquipaView(equipa, titulo, pos, this);
        scene = new Scene(fxmlLoader.getRoot());
        stage = new Stage();
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.show();
    }
}
