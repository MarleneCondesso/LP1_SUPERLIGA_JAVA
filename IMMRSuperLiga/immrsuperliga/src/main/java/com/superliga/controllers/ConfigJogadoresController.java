package com.superliga.controllers;

import com.superliga.App;
import com.superliga.models.Jogador;
import com.superliga.services.JogadorService;
import com.superliga.utils.AlertBox;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author 35191
 */
public class ConfigJogadoresController implements Initializable {

    @FXML
    private Button btnAdicionar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;
    @FXML
    public TableView<Jogador> tblJogadores;
    @FXML
    private TableColumn<Jogador, Integer> colNrAtleta;
    @FXML
    private TableColumn<Jogador, String> colNome;
    @FXML
    private TableColumn<Jogador, Date> colDataNasc;

    private ConfigJogadorController configJogadorController;
    private JogadorService service = new JogadorService();

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
        ObservableList obsList = FXCollections.observableArrayList(service.getAll());
        configColumns();
        tblJogadores.getItems().setAll(obsList);
    }

    /**
     * Método de configuração dos dados na tabela
     */
    private void configColumns() {
        colNrAtleta.setCellValueFactory(new PropertyValueFactory<>("nrAtleta"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colDataNasc.setCellValueFactory(new PropertyValueFactory<>("dataNasc"));
    }

    /**
     * Método para abrir a view Modo Adicionar ou Editar
     *
     * @param fxml
     * @param epoca
     * @param titulo
     * @param pos
     * @throws IOException
     */
    private void showModeEditionView(String fxml, Jogador jogador, String titulo, Integer pos) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.load();

        configJogadorController = fxmlLoader.getController();
        configJogadorController.setView(jogador, titulo, pos, this);
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

        showModeEditionView("configJogador", null, "Adicionar Jogador", null);

    }

    /**
     * Método para Editar
     *
     * @param event
     */
    @FXML
    private void edit(ActionEvent event) throws IOException {
        Jogador jogador = tblJogadores.getSelectionModel().getSelectedItem();
        Integer pos = tblJogadores.getSelectionModel().getSelectedIndex();

        if (!isNull(jogador)) {

            showModeEditionView("configJogador", jogador, "Editar Jogador", pos);

        } else {
            AlertBox alerta = new AlertBox(Alert.AlertType.ERROR, "Jogador em falta", "Por favor, selecione um Jogador.");
        }

    }

    /**
     * Método para Eliminar
     *
     * @param event
     */
    @FXML
    private void delete(ActionEvent event) {
        Jogador jogador = tblJogadores.getSelectionModel().getSelectedItem();

        if (!isNull(jogador)) {
            AlertBox alerta = new AlertBox(Alert.AlertType.CONFIRMATION, "Eliminar", "Tem a certeza que pretende eliminar este Jogador?");

            if (alerta.getButton().get() == ButtonType.OK) {

                Boolean response = service.deleteByPrimaryKey(jogador.getNrAtleta());

                if (response) {
                    AlertBox alertaConf = new AlertBox(Alert.AlertType.CONFIRMATION, "SUCESSO", "Jogador eliminado com SUCESSO!");
                    refreshTable();
                } else {
                    AlertBox alertaErr = new AlertBox(Alert.AlertType.ERROR, "ERRO", "Não foi possível eliminar o Jogador.");
                }
            }
        } else {
            AlertBox alerta = new AlertBox(Alert.AlertType.ERROR, "Jogador em falta", "Por favor, selecione um Jogador.");
        }
    }

}
