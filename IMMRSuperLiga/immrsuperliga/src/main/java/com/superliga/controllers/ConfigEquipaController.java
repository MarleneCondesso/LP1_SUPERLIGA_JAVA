/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superliga.controllers;

import com.superliga.models.Equipa;
import com.superliga.models.Estadio;
import com.superliga.services.EquipaService;
import com.superliga.services.EstadioService;
import com.superliga.utils.AlertBox;
import java.net.URL;
import java.sql.SQLException;
import static java.util.Objects.isNull;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 *
 * @author 35191
 */
public class ConfigEquipaController implements Initializable{

    
    @FXML
    private Label lblTitulo;

    @FXML
    private Button btnLimpar;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnGuardar;

    @FXML
    private TextField txtEquipa;

    @FXML
    private TextField txtSigla;

    @FXML
    private ComboBox<Estadio> comboEstadio;
    
    private Equipa equipa;
    private Integer position;
    
    private EquipaService equipaService = new EquipaService();
    private EstadioService estadioService = new EstadioService();
    ConfigEquipasController configEquipasController; 
    /**
    * Método para passar valores do EquipasController
    *
    * @param title
    * @param pos
    */
    public void setEquipaView(Equipa equipa, String title, Integer pos, ConfigEquipasController configEquipasController) {
        this.configEquipasController  = configEquipasController;

        if (!isNull(equipa)) {
            this.equipa = equipa;
            this.position = pos;
        } else {
            
            this.position = null;
        }
        setValuestoForm();
        this.lblTitulo.setText(title);
    }
    
    @FXML
    void cancel(MouseEvent event) {
        if (!txtEquipa.getText().isEmpty() || 
            !txtSigla.getText().isEmpty() ||
            !comboEstadio.getSelectionModel().getSelectedItem().equals(null)){
            AlertBox alerta = new AlertBox(Alert.AlertType.CONFIRMATION, "Cancelar", "Tem a certeza que deseja cancelar? Vai perder as informações preenchidas.");

            if (alerta.getButton().get() == ButtonType.OK) {
                closeModalView(btnCancelar);
            }
            if (alerta.getButton().get() == ButtonType.NO) {
                alerta.getAlert().close();
            }
        } else {
            closeModalView(btnCancelar);
        }
    }

    @FXML
    void cleanFields(MouseEvent event) {
        txtEquipa.setText("");
        txtSigla.setText("");
        comboEstadio.getSelectionModel().clearSelection();
    }

    @FXML
    void save(MouseEvent event) {
        if (txtEquipa.getText().isEmpty() 
                && txtSigla.getText().isEmpty()
                && comboEstadio.getSelectionModel().getSelectedItem().equals(null)) {
            AlertBox alerta = new AlertBox(Alert.AlertType.ERROR, "Erro", "Por favor, preencha todos os campos.");
        } else if (this.equipa == null) {
            createEquipa();
        } else {
            updateEquipa();
        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.comboEstadio.setConverter(new StringConverter<Estadio>() {

            @Override
            public String toString(Estadio estadio) {
                estadio = estadioService.getByPrimaryKey(estadio.getId());
               
                return "Nome : " + estadio.getNome();
            }

            @Override
            public Estadio fromString(String string) {
                return null;
            }
        });

        ObservableList obsLocais = FXCollections.observableArrayList(estadioService.getAll());
        this.comboEstadio.getItems().addAll(obsLocais);
    }
    
    /**
     * Método de alteração dos valores dos componentes do formulário
     */
    public void setValuestoForm() {
        
       
        if (!isNull(this.equipa)) {

            try {
                this.txtEquipa.setText(this.equipa.getNome());
                this.txtSigla.setText(this.equipa.getSigla());
                this.comboEstadio.getSelectionModel().select(this.equipa.getIdEstadio());
            } catch (NullPointerException e) {
            }

        }
    }
    
            /**
     * Método para atribuir valores introduzidos ao objeto Equipa, para
     * criar ou editar
     */
    private void setValuesToObject() {
        if (isNull(this.equipa)){
            this.equipa = new Equipa();
        }

        this.equipa.setNome(txtEquipa.getText());
        this.equipa.setSigla(txtSigla.getText());
        Estadio estadio = comboEstadio.getSelectionModel().getSelectedItem();
        this.equipa.setIdEstadio(estadio.getId());
        
    }
    
     /**
     * Método de criar Equipa
     */
        public void createEquipa() {

        setValuesToObject();
        
        Boolean response = false;
        try {
            response = equipaService.create(this.equipa.getNome(),
                    this.equipa.getSigla(),
                    this.equipa.getIdEstadio());
        } catch (SQLException ex) {
            Logger.getLogger(ConfigEquipaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (response) {
            AlertBox alertaConf = new AlertBox (Alert.AlertType.CONFIRMATION, "SUCESSO", "Equipa criada com SUCESSO!");

            closeModalView(this.btnGuardar);
            configEquipasController.refreshTable();
        } else {
            AlertBox alertaErr = new AlertBox (Alert.AlertType.ERROR, "ERRO", "Não foi possível criar a Equipa.");
        }
    }
               /**
     * Método de atualizar Equipa
     */
    public void updateEquipa() {

        setValuesToObject();

        Boolean response = equipaService.update(this.equipa.getId(),
                                                this.equipa.getNome(), 
                                                this.equipa.getSigla(), 
                                                this.equipa.getIdEstadio());
        if (response) {
            AlertBox alertaConf = new AlertBox(Alert.AlertType.CONFIRMATION, "SUCESSO", "Equipa atualizada com SUCESSO!");

            closeModalView(this.btnGuardar);
            configEquipasController.tvEquipas.getItems().set(this.position, this.equipa);
        } else {
            AlertBox alertaErr = new AlertBox(Alert.AlertType.ERROR, "ERRO", "Não foi possível atualizar a Equipa.");
        }

    }   
    
         /**
     * Método para fechar a view
     *
     * @param btn
     */
    public void closeModalView(Button btn) {
        try {
            Stage stage = (Stage) btn.getScene().getWindow();
            stage.close();
        } catch (RuntimeException e) {

        }
    }
}
