/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superliga.controllers;

import com.superliga.App;
import com.superliga.models.Equipa;
import com.superliga.models.JogadorContrato;
import com.superliga.services.EquipaService;
import com.superliga.services.JogadorContratoService;
import com.superliga.utils.AlertBox;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import static java.util.Objects.isNull;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 *
 * @author 35191
 */
public class ConfigJogadoresEquipaController implements Initializable{
     @FXML
    private Button btnConfig;

    @FXML
    public TableView<JogadorContrato> tvJogadoresEquipa;

    @FXML
    private TableColumn<JogadorContrato, Integer> nrAtletaCol;

    @FXML
    private TableColumn<JogadorContrato, String> equipaCol;

    @FXML
    private TableColumn<JogadorContrato, Date> entradaCol;

    @FXML
    private TableColumn<JogadorContrato, Date> saidaCol;

    @FXML
    private TableColumn<JogadorContrato, Integer> nrCamisolaCol;

    @FXML 
    private ComboBox<Equipa> comboEquipas;
    
    @FXML
    private Button btnAdicionar;
    
    @FXML
    private Button btnEditar;
    
    @FXML
    private Button btnEliminar;
    
    private JogadorContratoService jogadoresContratoService = new JogadorContratoService();
    private EquipaService equipaService = new EquipaService();
    private ConfigJogadorEquipaController configJogadorEquipaController;
    private JogadoresEquipaController jogadoresEquipaController;
    private Scene scene;
    
    private Stage stage;
    
    private String titulo;
    
    private Equipa equipa;
    
    private Integer position;
    ObservableList obsListJogadoresEquipa;

    /**
     * Método de Inicialização (interface Initializable)
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        comboEquipas.setConverter(new StringConverter<Equipa>(){

            @Override
            public String toString(Equipa equipa) {
                return "Nome : "+ equipa.getNome();
            }

            @Override
            public Equipa fromString(String string) {
                return null;
            }
        });
        
        ObservableList obsEquipa = FXCollections.observableArrayList(equipaService.getAll());
        comboEquipas.getItems().addAll(obsEquipa);
    }
     /**
    * Método para passar valores do JogadoresEquipaController
    *
     * @param equipa
    * @param pos
     * @param equipasController
    */
    public void setJogadoresEquipaView(Equipa equipa, Integer pos, JogadoresEquipaController jogadoresEquipaController) {
        this.jogadoresEquipaController  = jogadoresEquipaController;

        if (!isNull(equipa)) {
            this.equipa = equipa;
            this.position = pos;
        }
        refreshTable(equipa);
        comboEquipas.getSelectionModel().select(this.equipa);
    }
    
    /**
     * Método para listagem de Jogadores após seleção da combobox Equipas.
     * @param event 
     */
    @FXML
    void actionComboBox(ActionEvent event){
        
        Equipa equipaCombo = comboEquipas.getSelectionModel().getSelectedItem();
    
        refreshTable(equipaCombo);
    } 
    /**
     * Método para adicionar Jogador
     * @param event 
     */
    @FXML
    void add (ActionEvent event) {
        Equipa equipa = comboEquipas.getSelectionModel().getSelectedItem();
         try {
             showModalConfigJogadorEquipaView(equipa, "configJogadorEquipa", "Adicionar Jogador", null, null);
         } catch (IOException ex) {
             System.out.println("Erro : " + ex);
         }
                
    }
    
    /**
     * Método para Editar Jogador
     * @param event 
     */
    @FXML
    void edit (ActionEvent event) {
        JogadorContrato jogador = tvJogadoresEquipa.getSelectionModel().getSelectedItem();
        Integer pos = tvJogadoresEquipa.getSelectionModel().getSelectedIndex();
        Equipa equipa = comboEquipas.getSelectionModel().getSelectedItem();
        if(!isNull(jogador)){

            try {
                showModalConfigJogadorEquipaView(equipa, "configJogadorEquipa", "Editar Jogador", jogador, pos);
            } catch (IOException ex) {
                System.out.println("Erro: " + ex);
            }
        }else{
            AlertBox alerta = new AlertBox(Alert.AlertType.ERROR, "Jogador em falta", "Por favor, selecione um Jogador.");
        }
         
    }
    
    /**
     * Método para Eliminar Jogador
     * @param event 
     */
    @FXML
    void delete (ActionEvent event) {
        JogadorContrato jogador = tvJogadoresEquipa.getSelectionModel().getSelectedItem();
        Equipa equipa = comboEquipas.getSelectionModel().getSelectedItem();
        if (jogador != null){
            AlertBox alerta = new AlertBox(Alert.AlertType.CONFIRMATION, "Eliminar", "Tem a certeza que pretende eliminar este Jogador?");

                if (alerta.getButton().get() == ButtonType.OK) {

                    Boolean response = jogadoresContratoService.deleteByPrimaryKey(jogador.getNrAtleta(), jogador.getEntrada());

                    if (response) {
                        AlertBox alertaConf = new AlertBox(Alert.AlertType.CONFIRMATION, "SUCESSO", "Jogador eliminada com SUCESSO!");
                        refreshTable(equipa);
                    } else {
                        AlertBox alertaErr = new AlertBox(Alert.AlertType.ERROR, "ERRO", "Não foi possível eliminar o Jogador.");
                    }
                }
        }else{
            AlertBox alerta = new AlertBox(Alert.AlertType.ERROR, "Jogador em falta", "Por favor, selecione um Jogador.");
        }          
    }
    
     /**
     * Método de atualização dos dados na tabela JogadoresEquipas
     * @param 
     */
    public void refreshTable(Equipa equipa) {
        obsListJogadoresEquipa = FXCollections.observableArrayList(jogadoresContratoService.getByTeamId(equipa.getId()));
        configColums();
        tvJogadoresEquipa.getItems().setAll(obsListJogadoresEquipa);
    }
    
    
    /**
     * Método de Configuração dos dados na tabela JogadoresEquipas
     */
    private void configColums() {
        nrAtletaCol.setCellValueFactory(new PropertyValueFactory<>("nrAtleta"));
        equipaCol.setCellValueFactory(new PropertyValueFactory<>("idEquipa"));
        entradaCol.setCellValueFactory(new PropertyValueFactory<>("entrada"));
        saidaCol.setCellValueFactory(new PropertyValueFactory<>("saida"));
        nrCamisolaCol.setCellValueFactory(new PropertyValueFactory<>("nrCamisola"));
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
    private void showModalConfigJogadorEquipaView(Equipa equipa, String fxml, String titulo, JogadorContrato jogador, Integer pos) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.load();

        configJogadorEquipaController = fxmlLoader.getController();
        configJogadorEquipaController.setView(equipa, titulo, jogador,  pos, this);
        scene = new Scene(fxmlLoader.getRoot());
        stage = new Stage();
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.show();
    }
}
