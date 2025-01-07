/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superliga.controllers;

import com.superliga.models.Equipa;
import com.superliga.models.Jogador;
import com.superliga.models.JogadorContrato;
import com.superliga.services.EquipaService;
import com.superliga.services.JogadorContratoService;
import com.superliga.services.JogadorService;
import com.superliga.utils.AlertBox;
import java.net.URL;
import java.sql.Date;
import static java.util.Objects.isNull;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 *
 * @author 35191
 */
public class ConfigJogadorEquipaController implements Initializable{
   @FXML
    private Label labelTitulo;

    @FXML
    private Button btnLimpar;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnGuardar;

    @FXML
    private ComboBox<Jogador> comboBoxNrAtleta;

    @FXML
    private ComboBox<Equipa> comboEquipa;

    @FXML
    private DatePicker txtDataEntrada;

    @FXML
    private DatePicker txtDataSaida;

    @FXML
    private TextField txtCamisola;

    private Stage stage;

    private String title;
    
    private Integer position;

    private Equipa equipa;
    private JogadorContrato jogador;

    private JogadorContratoService jogadorContratoService = new JogadorContratoService();
    private JogadorService jogadorService = new JogadorService();
    private EquipaService equipaService = new EquipaService();
    private ConfigJogadoresEquipaController configJogadoresEquipaController;

    /**
     * Método para passar valores do JogadoresEquipaController
     *
     * @param equipa
     * @param jogador
     * @param title
     * @param pos
     * @param configJogadoresEquipaController
     */
    public void setView(Equipa equipa, String title, JogadorContrato jogador, Integer pos, ConfigJogadoresEquipaController configJogadoresEquipaController) {
        this.configJogadoresEquipaController = configJogadoresEquipaController;

        if (!isNull(jogador)) {
            this.equipa = equipa;
            this.jogador = jogador;
            this.position = pos;
            this.title = title;
        } else {
            this.equipa = equipa;
            this.jogador = null;
            this.position = null;
        }
        setValuestoForm();
        this.labelTitulo.setText(title);
    }
    
    /**
     * Método de Inicialização (interface Initializable)
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        comboEquipa.setConverter(new StringConverter<Equipa>() {

            @Override
            public String toString(Equipa equipa) {
                return "Nome : " + equipa.getNome();
            }

            @Override
            public Equipa fromString(String string) {
                return null;
            }
        });

        ObservableList obsEquipa = FXCollections.observableArrayList(equipaService.getAll());
        comboEquipa.getItems().addAll(obsEquipa);
    
    
        comboBoxNrAtleta.setConverter(new StringConverter<Jogador>() {

        @Override
        public String toString(Jogador jogador) {
            return "Número : " + jogador.getNrAtleta();
        }

        @Override
        public Jogador fromString(String string) {
            return null;
        }
        });

        ObservableList obsNrAtleta = FXCollections.observableArrayList(jogadorService.getAll());
        comboBoxNrAtleta.getItems().addAll(obsNrAtleta);

    }
    
    /**
     * Método para Cancelar
     *
     * @param event
     */
    @FXML
    void cancel(MouseEvent event) {
        if (comboBoxNrAtleta.getSelectionModel().getSelectedItem() != null
                || comboEquipa.getSelectionModel().getSelectedItem() != null
                || txtDataEntrada.getValue() != null
                || !txtCamisola.getText().isEmpty()) {
            AlertBox alerta = new AlertBox (Alert.AlertType.CONFIRMATION, "Cancelar", "Tem a certeza que deseja cancelar? Vai perder as informações preenchidas.");

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

    /**
     * Método para Guardar
     *
     * @param event
     */
    @FXML
    void save (MouseEvent event) {
        if (comboBoxNrAtleta.getSelectionModel().getSelectedItem() == null
                || comboEquipa.getSelectionModel().getSelectedItem() == null
                || txtDataEntrada.getValue() == null
                || txtCamisola.getText().isEmpty()) {
            AlertBox alerta = new AlertBox(Alert.AlertType.ERROR, "Erro", "Por favor, preencha todos os campos.");
        } else if (this.jogador == null) {
            create();
        } else {
            update();
        }
    }

    /**
     * Método para limpar Campos
     *
     * @param event
     */
    @FXML
    void cleanFields () {
        comboBoxNrAtleta.getSelectionModel().clearSelection();
        comboEquipa.getSelectionModel().clearSelection();
        txtDataEntrada.setValue(null);
        txtDataSaida.setValue(null);
        txtCamisola.setText("");
    }

    /**
     * Método de alteração dos valores dos componentes do formulário
     */
    public void setValuestoForm() {
        if (!isNull(this.jogador)) {

            try {
                this.comboBoxNrAtleta.getSelectionModel().select(jogadorService.getByPrimaryKey(this.jogador.getNrAtleta()));
                this.comboEquipa.getSelectionModel().select(equipaService.getByPrimaryKey(this.jogador.getIdEquipa()));
                this.txtDataEntrada.setValue(this.jogador.getEntrada().toLocalDate());
                this.txtDataSaida.setValue(this.jogador.getSaida().toLocalDate());
                this.txtCamisola.setText(String.valueOf(this.jogador.getNrCamisola()));
            } catch (NullPointerException e) {
            }

        } else {
            this.comboEquipa.getSelectionModel().select(this.equipa.getId());
        }
    }

    /**
     * Método para atribuir valores introduzidos ao objeto Jogador, para criar
     * ou editar
     */
    private void setValuesToObject() {
        if(isNull(this.jogador)){
            this.jogador = new JogadorContrato();
        }
        this.jogador.setNrAtelta(this.comboBoxNrAtleta.getSelectionModel().getSelectedItem().getNrAtleta());
        this.jogador.setIdEquipa(this.comboEquipa.getSelectionModel().getSelectedItem().getId());
        this.jogador.setEntrada(Date.valueOf(this.txtDataEntrada.getValue()));
        if (txtDataSaida.getValue() != null) {
            this.jogador.setSaida(Date.valueOf(this.txtDataSaida.getValue()));
        } else {
            this.jogador.setSaida(null);
        }
        this.jogador.setNrCamisola(Integer.valueOf(this.txtCamisola.getText()));
        
    }
    
    /**
     * Método de criar Jogador
     */
    public void create() {

        setValuesToObject();

        Boolean response = jogadorContratoService.create(this.jogador.getNrAtleta(), 
                                                        this.jogador.getIdEquipa(),
                                                        this.jogador.getEntrada(),
                                                        this.jogador.getSaida(),
                                                        this.jogador.getNrCamisola());
        if (response) {
            AlertBox alertaConf = new AlertBox(Alert.AlertType.CONFIRMATION, "SUCESSO", "Jogador criado com SUCESSO!");

            closeModalView(this.btnGuardar);
            configJogadoresEquipaController.refreshTable(this.equipa);
        } else {
            AlertBox alertaErr = new AlertBox(Alert.AlertType.ERROR, "ERRO", "Não foi possível criar o Jogador.");
        }
    }

    /**
     * Método de atualizar Jogador
     */
    public void update() {

        setValuesToObject();

        Boolean response = jogadorContratoService.update(this.jogador.getNrAtleta(),
                                                         this.jogador.getIdEquipa(),
                                                         this.jogador.getEntrada(),
                                                         this.jogador.getSaida(),
                                                         this.jogador.getNrCamisola());
        if (response) {
            AlertBox alertaConf = new AlertBox (Alert.AlertType.CONFIRMATION, "SUCESSO", "Jogador atualizado com SUCESSO!");

            closeModalView(this.btnGuardar);
            configJogadoresEquipaController.tvJogadoresEquipa.getItems().set(this.position, this.jogador);
        } else {
            AlertBox alertaErr = new AlertBox (Alert.AlertType.ERROR, "ERRO", "Não foi possível atualizar o Jogador.");
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
