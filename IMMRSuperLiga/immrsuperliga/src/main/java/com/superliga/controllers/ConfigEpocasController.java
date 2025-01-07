package com.superliga.controllers;

import com.superliga.App;
import com.superliga.models.Epoca;
import com.superliga.services.EpocaService;
import com.superliga.utils.AlertBox;
import static java.util.Objects.isNull;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
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
 * FXML Controller class
 *
 * @author ruteg
 */
public class ConfigEpocasController implements Initializable {

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
    @FXML
    private Button btnAdicionar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;

    ObservableList obsList;
    private ConfigEpocaController configEpocaController;
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
     * Método para abrir a view Modo Adicionar ou Editar
     *
     * @param fxml
     * @param epoca
     * @param titulo
     * @param pos
     * @throws IOException
     */
    private void showModeEditionView(String fxml, Epoca epoca, String titulo, Integer pos) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.load();

        configEpocaController = fxmlLoader.getController();
        configEpocaController.setView(epoca, titulo, pos, this);
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

        showModeEditionView("configEpoca", null, "Adicionar Época", null);

    }

    /**
     * Método para Editar
     *
     * @param event
     */
    @FXML
    private void edit(ActionEvent event) throws IOException {
        Epoca epoca = tblEpocas.getSelectionModel().getSelectedItem();
        Integer pos = tblEpocas.getSelectionModel().getSelectedIndex();

        if (!isNull(epoca)) {

            showModeEditionView("configEpoca", epoca, "Editar Época", pos);

        } else {
            AlertBox alerta = new AlertBox(Alert.AlertType.ERROR, "Época em falta", "Por favor, selecione uma Época.");
        }

    }

    /**
     * Método para Eliminar
     *
     * @param event
     */
    @FXML
    private void delete(ActionEvent event) {
        Epoca epoca = tblEpocas.getSelectionModel().getSelectedItem();

        if (!isNull(epoca)) {
            AlertBox alerta = new AlertBox(Alert.AlertType.CONFIRMATION, "Eliminar", "Tem a certeza que pretende eliminar esta Época?");

            if (alerta.getButton().get() == ButtonType.OK) {

                Boolean response = service.deleteByPrimaryKey(epoca.getNumeroEpoca());

                if (response) {
                    AlertBox alertaConf = new AlertBox(Alert.AlertType.CONFIRMATION, "SUCESSO", "Época eliminada com SUCESSO!");
                    refreshTable();
                } else {
                    AlertBox alertaErr = new AlertBox(Alert.AlertType.ERROR, "ERRO", "Não foi possível eliminar a Época.");
                }
            }
        } else {
            AlertBox alerta = new AlertBox(Alert.AlertType.ERROR, "Época em falta", "Por favor, selecione uma Época.");
        }
    }

}
