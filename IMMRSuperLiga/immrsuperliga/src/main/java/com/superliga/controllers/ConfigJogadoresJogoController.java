package com.superliga.controllers;

import com.superliga.App;
import com.superliga.models.Equipa;
import com.superliga.models.JogadorJogo;
import com.superliga.models.Jogo;
import com.superliga.models.OnzeInicial;
import com.superliga.services.EquipaService;
import com.superliga.services.EstadioService;
import com.superliga.services.JogadorJogoService;
import com.superliga.services.OnzeInicialService;
import com.superliga.utils.AlertBox;
import java.io.IOException;
import java.net.URL;
import static java.util.Objects.isNull;
import java.util.ResourceBundle;
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
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author ruteg
 */
public class ConfigJogadoresJogoController implements Initializable {

    @FXML
    private TableView<OnzeInicial> tblOnzeCasa;
    @FXML
    private TableView<OnzeInicial> tblOnzeFora;
    @FXML
    private TableColumn<OnzeInicial, Integer> colNrAtleta;
    @FXML
    private TableColumn<OnzeInicial, String> colJogador;
    @FXML
    private TableColumn<OnzeInicial, String> colPosicao;
    @FXML
    private Button btnAdicionar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblJogo;
    @FXML
    private Tab onzeCasaTab;
    @FXML
    private Tab onzeForaTab;

    private Jogo selectedJogo;
    private Equipa selectedCasa;
    private Equipa selectedFora;

    private ConfigJogadorJogoController configJogadorJogoController;
    private ConfigJogadoresJogoController configJogadoresJogoController;

    private EquipaService equipaService = new EquipaService();
    private EstadioService estadioService = new EstadioService();
    private OnzeInicialService onzeInicialService = new OnzeInicialService();
    private JogadorJogoService service = new JogadorJogoService();

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
        this.lblTitulo.setText("");
        this.lblJogo.setText("");
    }

    /**
     * Método para atualização dos dados na tabela
     */
    public void refreshTables() {
        ObservableList equipaCasa = FXCollections.observableArrayList(onzeInicialService.getAllJogoEquipa(this.selectedJogo.getId(), selectedCasa.getNome()));
        ObservableList equipaFora = FXCollections.observableArrayList(onzeInicialService.getAllJogoEquipa(this.selectedJogo.getId(), selectedFora.getNome()));

        configColumns();
        tblOnzeCasa.getItems().setAll(equipaCasa);
        tblOnzeFora.getItems().setAll(equipaFora);
    }

    /**
     *
     * @param jogo
     */
    public void setView(Jogo jogo) {
        this.selectedJogo = jogo;
        selectedCasa = equipaService.getByPrimaryKey(this.selectedJogo.getIdEquipaCasa());
        selectedFora = equipaService.getByPrimaryKey(this.selectedJogo.getIdEquipaFora());

        String textoJogo = "";
        textoJogo += selectedCasa.getNome();
        textoJogo += " VS " + selectedFora.getNome();
        textoJogo += "  |  " + this.selectedJogo.getDataHora().toString();
        textoJogo += "  |  " + estadioService.getByPrimaryKey(this.selectedJogo.getIdEstadio()).getNome();

        String textoTitulo = "EQUIPAS DO JOGO:   " + selectedCasa.getSigla() + " VS " + selectedFora.getSigla();

        this.lblJogo.setText(textoJogo);
        this.lblTitulo.setText(textoTitulo);
        refreshTables();
    }

    /**
     * Método de configuração dos dados na tabela
     */
    private void configColumns() {
        colNrAtleta.setCellValueFactory(new PropertyValueFactory<>("numeroAtleta"));
        colJogador.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colPosicao.setCellValueFactory(new PropertyValueFactory<>("posicao"));
    }

    /**
     * Método para abrir a view Modo Adicionar ou Editar
     *
     * @param fxml
     * @param jogadorJogo
     * @param titulo
     * @param pos
     * @throws IOException
     */
    private void showModeEditionView(String fxml, OnzeInicial jogadorJogo, String titulo, Equipa equipaJogo) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.load();

        configJogadorJogoController = fxmlLoader.getController();
        configJogadorJogoController.setView(jogadorJogo, titulo, this.selectedJogo, equipaJogo, this);

        scene = new Scene(fxmlLoader.getRoot());
        stage = new Stage();
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Método para adicionar
     *
     * @param event
     */
    @FXML
    private void add(ActionEvent event) throws IOException {
        
        Equipa equipaJogo = null;

        if (onzeCasaTab.isSelected()) {
            equipaJogo = selectedCasa;
        } else if (onzeForaTab.isSelected()) {
            equipaJogo = selectedFora;
        }
        if (!isNull(equipaJogo)) {
                    showModeEditionView("configJogadorJogo", null, "Adicionar Jogador do Jogo", equipaJogo);
        } else {
            AlertBox alerta = new AlertBox(Alert.AlertType.ERROR, "Equipa do Jogo em falta", "Por favor, selecione uma Equipa do Jogo.\n(Selecione a Tab da equipa que pretende)");
        }


    }

    /**
     * Método para Editar
     *
     * @param event
     */
    @FXML
    private void edit(ActionEvent event) throws IOException {
        OnzeInicial jogadorOnzeInicial = null;
        Equipa equipaJogo = null;

        if (onzeCasaTab.isSelected()) {
            jogadorOnzeInicial = tblOnzeCasa.getSelectionModel().getSelectedItem();
            equipaJogo = selectedCasa;
        } else if (onzeForaTab.isSelected()) {
            jogadorOnzeInicial = tblOnzeFora.getSelectionModel().getSelectedItem();
            equipaJogo = selectedFora;
        }

        if (!isNull(jogadorOnzeInicial)) {

            showModeEditionView("configJogadorJogo", jogadorOnzeInicial, "Editar Jogador do Jogo", equipaJogo);

        } else {
            AlertBox alerta = new AlertBox(Alert.AlertType.ERROR, "Jogador do Jogo em falta", "Por favor, selecione um Jogador do Jogo.");
        }

    }

    /**
     * Método para Eliminar
     *
     * @param event
     */
    @FXML
    private void delete(ActionEvent event) {

        OnzeInicial jogadorOnzeInicial = null;

        if (onzeCasaTab.isSelected()) {
            jogadorOnzeInicial = tblOnzeCasa.getSelectionModel().getSelectedItem();
        } else if (onzeForaTab.isSelected()) {
            jogadorOnzeInicial = tblOnzeFora.getSelectionModel().getSelectedItem();
        }

        if (!isNull(jogadorOnzeInicial)) {
            AlertBox alerta = new AlertBox(Alert.AlertType.CONFIRMATION, "Eliminar", "Tem a certeza que pretende eliminar este Jogador do Jogo?");

            if (alerta.getButton().get() == ButtonType.OK) {

                Boolean response = service.deleteByPrimaryKey(jogadorOnzeInicial.getNumeroAtleta(), jogadorOnzeInicial.getIdJogo());

                if (response) {
                    AlertBox alertaConf = new AlertBox(Alert.AlertType.CONFIRMATION, "SUCESSO", "Jogador do Jogo eliminado com SUCESSO!");
                    refreshTables();
                } else {
                    AlertBox alertaErr = new AlertBox(Alert.AlertType.ERROR, "ERRO", "Não foi possível eliminar o Jogador do Jogo.");
                }
            }
        } else {
            AlertBox alerta = new AlertBox(Alert.AlertType.ERROR, "Jogador do Jogo em falta", "Por favor, selecione um Jogador do Jogo.");
        }
    }

}
