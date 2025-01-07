package com.superliga.controllers;

import com.superliga.App;
import com.superliga.models.Estadio;
import com.superliga.services.EstadioService;
import com.superliga.services.LocalidadeService;
import com.superliga.services.PaisService;
import com.superliga.utils.AlertBox;
import java.io.IOException;
import java.net.URL;
import static java.util.Objects.isNull;
import java.util.ResourceBundle;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author 35191
 */
public class ConfigEstadiosController implements Initializable {

    @FXML
    private Button btnAdicionar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;
    @FXML
    public TableView<Estadio> tblEstadios;
    @FXML
    private TableColumn<Estadio, Integer> colId;
    @FXML
    private TableColumn<Estadio, String> colNome;
    @FXML
    private TableColumn<Estadio, String> colLocalidade;
    @FXML
    private TableColumn<Estadio, String> colPais;

    private ConfigEstadioController configEstadioontroller;
    private EstadioService service = new EstadioService();
    private LocalidadeService localService = new LocalidadeService();
    private PaisService paisService = new PaisService();

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
        tblEstadios.getItems().setAll(obsList);
    }

    /**
     * Método de configuração dos dados na tabela
     */
    private void configColumns() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colLocalidade.setCellValueFactory(pais -> new SimpleStringProperty(localService.getByPrimaryKey(pais.getValue().getIdLocal()).getDescricao()));
        colPais.setCellValueFactory(pais -> new SimpleStringProperty(paisService.getByPrimaryKey(pais.getValue().getIdPais()).getDescricao()));

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
    private void showModeEditionView(String fxml, Estadio estadio, String titulo, Integer pos) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.load();

        configEstadioontroller = fxmlLoader.getController();
        configEstadioontroller.setView(estadio, titulo, pos, this);
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

        showModeEditionView("configEstadio", null, "Adicionar Estádio", null);

    }

    /**
     * Método para Editar
     *
     * @param event
     */
    @FXML
    private void edit(ActionEvent event) throws IOException {
        Estadio estadio = tblEstadios.getSelectionModel().getSelectedItem();
        Integer pos = tblEstadios.getSelectionModel().getSelectedIndex();

        if (!isNull(estadio)) {

            showModeEditionView("configEstadio", estadio, "Editar Estádio", pos);

        } else {
            AlertBox alerta = new AlertBox(Alert.AlertType.ERROR, "Estádio em falta", "Por favor, selecione um Estádio.");
        }

    }

    /**
     * Método para Eliminar
     *
     * @param event
     */
    @FXML
    private void delete(ActionEvent event) {
        Estadio estadio = tblEstadios.getSelectionModel().getSelectedItem();

        if (!isNull(estadio)) {
            AlertBox alerta = new AlertBox(Alert.AlertType.CONFIRMATION, "Eliminar", "Tem a certeza que pretende eliminar este Estádio?");

            if (alerta.getButton().get() == ButtonType.OK) {

                Boolean response = service.deleteByPrimaryKey(estadio.getId());

                if (response) {
                    AlertBox alertaConf = new AlertBox(Alert.AlertType.CONFIRMATION, "SUCESSO", "Estádio eliminado com SUCESSO!");
                    refreshTable();
                } else {
                    AlertBox alertaErr = new AlertBox(Alert.AlertType.ERROR, "ERRO", "Não foi possível eliminar o Estádio.");
                }
            }
        } else {
            AlertBox alerta = new AlertBox(Alert.AlertType.ERROR, "Estádio em falta", "Por favor, selecione um Estádio.");
        }
    }

}