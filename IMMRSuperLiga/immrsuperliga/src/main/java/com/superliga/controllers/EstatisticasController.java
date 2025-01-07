/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superliga.controllers;

import com.superliga.models.CartoesGeraisEquipas;
import com.superliga.models.CartoesGeraisJogadores;
import com.superliga.models.Epoca;
import com.superliga.models.Equipa;
import com.superliga.models.EquipasComMaisVitorias;
import com.superliga.models.Jornada;
import com.superliga.models.MelhoresMarcadoresGerais;
import com.superliga.models.MelhoresMarcadoresGeraisEquipas;
import com.superliga.services.CartoesGeraisService;
import com.superliga.services.EpocaService;
import com.superliga.services.EquipaService;
import com.superliga.services.JornadaService;
import com.superliga.services.MelhoresMarcadoresGeraisService;
import java.net.URL;
import static java.util.Objects.isNull;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;

/**
 *
 * @author 35191
 */
public class EstatisticasController implements Initializable {

    @FXML
    private Pane pane;

    @FXML
    private Button btnAdicionarC;

    @FXML
    private Button btnEditarC;

    @FXML
    private Button btnEliminarC;

    @FXML
    private Label lblTable;
    
    
    
    @FXML
    private Tab vitoriasTab;
    
    @FXML
    private TableView<EquipasComMaisVitorias> tvEquipaVitorias;

    @FXML
    private TableColumn<EquipasComMaisVitorias, String> epocaVitoriaCol;

    @FXML
    private TableColumn<EquipasComMaisVitorias, Integer> equipaVitoriaCol;

    @FXML
    private TableColumn<EquipasComMaisVitorias, Integer> vitoriasCol;

    @FXML
    private ComboBox<?> comboJornadaVitorias;
    
    
    
    @FXML
    private Tab marcadoresGeraisETab;

    @FXML
    private TableView<MelhoresMarcadoresGeraisEquipas> tvMarcadoresGeraisE;

    @FXML
    private TableColumn<MelhoresMarcadoresGeraisEquipas, Integer> idEquipaCol;

    @FXML
    private TableColumn<MelhoresMarcadoresGeraisEquipas, String> nomeEquipaCol;

    @FXML
    private TableColumn<MelhoresMarcadoresGeraisEquipas, Integer> golosEquipaCol;

    @FXML
    private ComboBox<Jornada> comboJornadaEquipas;

    
    
    @FXML
    private Tab marcadoresGeraisTab;

    @FXML
    private TableView<MelhoresMarcadoresGerais> tvMarcadoresGeraisJg;

    @FXML
    private TableColumn<MelhoresMarcadoresGerais, String> nrAtletaCol;

    @FXML
    private TableColumn<MelhoresMarcadoresGerais, String> nomeCol;

    @FXML
    private TableColumn<MelhoresMarcadoresGerais, String> golosCol;

    
    
    @FXML
    private Tab cartoesEquipasTab;

    @FXML
    private TableView<CartoesGeraisEquipas> tvCartoesEquipasGerais;

    @FXML
    private TableColumn<CartoesGeraisEquipas, String> equipaCol;

    @FXML
    private TableColumn<CartoesGeraisEquipas, String> equipaCartaoCol;

    @FXML
    private TableColumn<CartoesGeraisEquipas, Integer> equipaQtdCol;

    
    
    @FXML
    private Tab cartoesJogadoresTab;

    @FXML
    private TableView<CartoesGeraisJogadores> tvCartoesJogadoresGerais;

    @FXML
    private TableColumn<CartoesGeraisJogadores, String> jogadorCol;

    @FXML
    private TableColumn<CartoesGeraisJogadores, String> cartaoCol;

    @FXML
    private TableColumn<CartoesGeraisJogadores, Integer> quantidadeCol;

    @FXML
    private ComboBox<Epoca> comboEpoca;

    @FXML
    private ComboBox<Jornada> comboJornadaJogadores;

    private Epoca selectedEpoca;
    private Jornada selectedJornada;

    private EpocaService epocaService = new EpocaService();
    private JornadaService jornadaService = new JornadaService();
    private EquipaService equipaService = new EquipaService();
    
    private MelhoresMarcadoresGeraisService marcadoresGeraisService = new MelhoresMarcadoresGeraisService();
    private CartoesGeraisService cartoesGeraisService = new CartoesGeraisService();
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.comboJornadaJogadores.setVisible(false);
        
        setComboEpocas();

        this.comboEpoca.setOnAction((e) -> {
            selectedEpoca = this.comboEpoca.getSelectionModel().getSelectedItem();
            if (!isNull(selectedEpoca)) {
                
                this.comboJornadaJogadores.getSelectionModel().selectFirst();
                this.comboJornadaEquipas.getSelectionModel().selectFirst();
                
                this.comboJornadaJogadores.setVisible(true);

                setComboJornadas(selectedEpoca);

                refreshTableBestGlobalScorerPlayerByEpoca(selectedEpoca);

                refreshTablBestGlobalScorerTeamByEpoca(selectedEpoca);

                refreshTableGlobalCardsPlayerByEpoca(selectedEpoca);

                refreshTableGlobalCardsTeamByEpoca(selectedEpoca);
                
                refreshTableTeamsWithMoreWinsByEpoca(selectedEpoca);
            }
        });

        this.comboJornadaJogadores.setOnAction((e) -> {
            selectedJornada = this.comboJornadaJogadores.getSelectionModel().getSelectedItem();
            if (!isNull(selectedJornada)) {
                refreshTableBestGlobalScorerPlayerByJornada(selectedJornada, selectedEpoca);
            }
        });

        this.comboJornadaEquipas.setOnAction((e) -> {
            selectedJornada = this.comboJornadaEquipas.getSelectionModel().getSelectedItem();
            if (!isNull(selectedJornada)) {
                refreshTableBestGlobalScorerTeamByJornada(selectedJornada, selectedEpoca);
            }
        });
    }

    private void setComboEpocas() {
        this.comboEpoca.setConverter(new StringConverter<Epoca>() {
            @Override
            public String toString(Epoca epoca) {
                return epoca.getNumeroEpoca();
            }

            @Override
            public Epoca fromString(String string) {
                return null;
            }
        });
        ObservableList obsListEpocas = FXCollections.observableArrayList(epocaService.getAll());
        this.comboEpoca.getItems().addAll(obsListEpocas);

    }

    private void setComboJornadas(Epoca epoca) {
        
        this.comboJornadaJogadores.setConverter(new StringConverter<Jornada>() {
            @Override
            public String toString(Jornada jornada) {
                return jornada.getNrJornada().toString();
            }

            @Override
            public Jornada fromString(String string) {
                return null;
            }
        });
        ObservableList obsListJornadas = FXCollections.observableArrayList(jornadaService.getAllByEpoca(epoca.getNumeroEpoca()));
        this.comboJornadaJogadores.getItems().addAll(obsListJornadas);

        this.comboJornadaEquipas.setConverter(new StringConverter<Jornada>() {
            @Override
            public String toString(Jornada jornada) {
                return jornada.getNrJornada().toString();
            }

            @Override
            public Jornada fromString(String string) {
                return null;
            }
        });
        ObservableList obsListJornadasE = FXCollections.observableArrayList(jornadaService.getAllByEpoca(epoca.getNumeroEpoca()));
        this.comboJornadaEquipas.getItems().addAll(obsListJornadasE);

    }

    
    
    
    /**
     * Método para atualização dos dados na tabela Melhores Marcadores Gerais
     * Equipa por Época
     */
    public void refreshTablBestGlobalScorerTeamByEpoca(Epoca epoca) {
        ObservableList obsList = FXCollections.observableArrayList(marcadoresGeraisService.getByEpocaEquipas(epoca.getNumeroEpoca()));
        configColumnsBestGlobalScorerTeam();
        tvMarcadoresGeraisE.getItems().setAll(obsList);
    }

    /**
     * Método para atualização dos dados na tabela Melhores Marcadoras Gerais
     * Equipa por Jornada
     */
    public void refreshTableBestGlobalScorerTeamByJornada(Jornada jornada, Epoca epoca) {
        ObservableList obsList = FXCollections.observableArrayList(marcadoresGeraisService.getByJornadaEquipas(epoca.getNumeroEpoca(), jornada.getNrJornada()));
        configColumnsBestGlobalScorerTeam();
        tvMarcadoresGeraisE.getItems().setAll(obsList);
    }

    /**
     * Método de configuração dos dados na tabela Melhores Marcadores Gerais
     * Equipa
     */
    private void configColumnsBestGlobalScorerTeam() {
        idEquipaCol.setCellValueFactory(marcadoresEquipa -> new SimpleObjectProperty(marcadoresEquipa.getValue().getEquipaId()));
        nomeEquipaCol.setCellValueFactory(marcadoresEquipa -> new SimpleObjectProperty(marcadoresEquipa.getValue().getEquipaNome()));
        golosEquipaCol.setCellValueFactory(marcadoresEquipa -> new SimpleObjectProperty(marcadoresEquipa.getValue().getGolos()));
    }
    
    
    
    
    /**
     * Método para atualização dos dados na tabela Melhores Marcadores Gerais
     * Jogadores por Jornada
     */
    public void refreshTableBestGlobalScorerPlayerByJornada(Jornada jornada, Epoca epoca) {
        ObservableList obsList = FXCollections.observableArrayList(marcadoresGeraisService.getByJornada(epoca.getNumeroEpoca(),jornada.getNrJornada()));
        configColumnsBestGlobalScorerPlayer();
        tvMarcadoresGeraisJg.getItems().setAll(obsList);
    }
    
    /**
     * Método para atualização dos dados na tabela Melhores Marcadores Gerais
     * Jogadores por Época
     */
    public void refreshTableBestGlobalScorerPlayerByEpoca(Epoca epoca) {
        ObservableList obsList = FXCollections.observableArrayList(marcadoresGeraisService.getByEpoca(epoca.getNumeroEpoca()));
        configColumnsBestGlobalScorerPlayer();
        tvMarcadoresGeraisJg.getItems().setAll(obsList);
    }

    /**
     * Método de configuração dos dados na tabela Melhores Marcadores Gerais
     * Jogadores
     */
    private void configColumnsBestGlobalScorerPlayer() {
        nrAtletaCol.setCellValueFactory(new PropertyValueFactory<>("AtletaNr"));
        nomeCol.setCellValueFactory(new PropertyValueFactory<>("AtletaNome"));
        golosCol.setCellValueFactory(new PropertyValueFactory<>("Golos"));
    }

    
    
    
    /**
     * Método para atualização dos dados na tabela Cartões Gerais Jogador
     */
    public void refreshTableGlobalCardsPlayerByEpoca(Epoca epoca) {
        ObservableList obsList = FXCollections.observableArrayList(cartoesGeraisService.getByEpoca(epoca.getNumeroEpoca()));
        configColumnsGlobalCardsPlayer();
        tvCartoesJogadoresGerais.getItems().setAll(obsList);
    }

    /**
     * Método de configuração dos dados na tabela Cartões Gerais Jogador
     */
    private void configColumnsGlobalCardsPlayer() {
        jogadorCol.setCellValueFactory(cartaoJogador -> new SimpleObjectProperty(cartaoJogador.getValue().getNomeJogador()));
        cartaoCol.setCellValueFactory(cartaoJogador -> new SimpleObjectProperty(cartaoJogador.getValue().getTipoEvento()));
        quantidadeCol.setCellValueFactory(cartaoJogador -> new SimpleObjectProperty(cartaoJogador.getValue().getQuantidade()));
    }

    
    
    
    /**
     * Método para atualização dos dados na tabela Cartões Gerais Equipa
     */
    public void refreshTableGlobalCardsTeamByEpoca(Epoca epoca) {
        ObservableList obsList = FXCollections.observableArrayList(cartoesGeraisService.getByEpocaE(epoca.getNumeroEpoca()));
        configColumnsGlobalCardsTeam();
        tvCartoesEquipasGerais.getItems().setAll(obsList);
    }

    /**
     * Método de configuração dos dados na tabela Cartões Gerais Equipa
     */
    private void configColumnsGlobalCardsTeam() {
        equipaCol.setCellValueFactory(cartaoEquipa -> new SimpleObjectProperty(cartaoEquipa.getValue().getEquipa()));
        equipaCartaoCol.setCellValueFactory(cartaoEquipa -> new SimpleObjectProperty(cartaoEquipa.getValue().getCartao()));
        equipaQtdCol.setCellValueFactory(cartaoEquipa -> new SimpleObjectProperty(cartaoEquipa.getValue().getContagem()));
    }
    
    
    
    
    
    /**
     * Método para atualização dos dados na tabela Cartões Gerais Equipa
     */
    public void refreshTableTeamsWithMoreWinsByEpoca(Epoca epoca) {
        ObservableList obsList = FXCollections.observableArrayList(equipaService.getTeamWithMoreVictories(epoca.getNumeroEpoca()));
        configColumnsTeamsWithMoreWins();
        tvEquipaVitorias.getItems().setAll(obsList);
    }

    /**
     * Método de configuração dos dados na tabela Cartões Gerais Equipa
     */
    private void configColumnsTeamsWithMoreWins() {
        epocaVitoriaCol.setCellValueFactory(equipasVitorias -> new SimpleObjectProperty(equipasVitorias.getValue().getNrEpoca()));
        equipaVitoriaCol.setCellValueFactory(equipasVitorias -> new SimpleObjectProperty(equipasVitorias.getValue().getEquipa()));
        vitoriasCol.setCellValueFactory(equipasVitorias -> new SimpleObjectProperty(equipasVitorias.getValue().getQuantidade()));
    }
}
