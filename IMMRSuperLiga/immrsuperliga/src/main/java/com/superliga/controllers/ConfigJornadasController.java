package com.superliga.controllers;

import com.superliga.App;
import com.superliga.models.Epoca;
import com.superliga.models.Jornada;
import com.superliga.services.EpocaService;
import com.superliga.services.JornadaService;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 *
 * @author ruteg
 */
public class ConfigJornadasController implements Initializable {
    @FXML
    public ComboBox<Epoca> comboEpocas;
    @FXML
    public TableView<Jornada> tblJornadas;
    @FXML
    private TableColumn<Jornada, String> colNrJornada;
    @FXML
    private TableColumn<Jornada, Date> colDataInicio;
    @FXML
    private TableColumn<Jornada, Date> colDataFim;
    @FXML
    private Button btnAdicionar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;
    
    private Epoca selectedEpoca;
    
    private ConfigJornadaController configJornadaController;
    private ConfigJornadasController configJornadasController;
    private EpocaService epocaService = new EpocaService();
    private JornadaService service = new JornadaService();

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
        this.comboEpocas.setConverter(new StringConverter<Epoca>() {
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
        this.comboEpocas.getItems().addAll(obsListEpocas);

        this.comboEpocas.setOnAction((e) -> {
            selectedEpoca = this.comboEpocas.getSelectionModel().getSelectedItem();
            if (!isNull(selectedEpoca)) {
                refreshTable();
            }
        });

    }

    /**
     * Método para atualização dos dados na tabela
     */
    public void refreshTable() {
        ObservableList obsList = FXCollections.observableArrayList(service.getAllByEpoca(selectedEpoca.getNumeroEpoca()));
        configColumns();
        tblJornadas.getItems().setAll(obsList);
    }

    /**
     * Método de configuração dos dados na tabela
     */
    private void configColumns() {
        colNrJornada.setCellValueFactory(new PropertyValueFactory<>("nrJornada"));
        colDataInicio.setCellValueFactory(new PropertyValueFactory<>("dataInicio"));
        colDataFim.setCellValueFactory(new PropertyValueFactory<>("dataFim"));
    }

    /**
     * Método para abrir a view Modo Adicionar ou Editar
     *
     * @param fxml
     * @param jornada
     * @param titulo
     * @param pos
     * @throws IOException
     */
    private void showModeEditionView(String fxml, Jornada jornada, String titulo) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.load();

        configJornadaController = fxmlLoader.getController();
        configJornadaController.setView(jornada, titulo, this);
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

        showModeEditionView("configJornada", null, "Adicionar Jornada");

    }

    /**
     * Método para Editar
     *
     * @param event
     */
    @FXML
    private void edit(ActionEvent event) throws IOException {
        Jornada jornada = tblJornadas.getSelectionModel().getSelectedItem();

        if (!isNull(jornada)) {

            showModeEditionView("configJornada", jornada, "Editar Jornada");

        } else {
            AlertBox alerta = new AlertBox(Alert.AlertType.ERROR, "Jornada em falta", "Por favor, selecione uma Jornada.");
        }

    }

    /**
     * Método para Eliminar
     *
     * @param event
     */
    @FXML
    private void delete(ActionEvent event) {
        Jornada jornada = tblJornadas.getSelectionModel().getSelectedItem();

        if (!isNull(jornada)) {
            AlertBox alerta = new AlertBox(Alert.AlertType.CONFIRMATION, "Eliminar", "Tem a certeza que pretende eliminar esta Jornada?");

            if (alerta.getButton().get() == ButtonType.OK) {

                Boolean response = service.deleteByPrimaryKey(jornada.getNrEpoca(), jornada.getNrJornada());

                if (response) {
                    AlertBox alertaConf = new AlertBox(Alert.AlertType.CONFIRMATION, "SUCESSO", "Jornada eliminada com SUCESSO!");
                    refreshTable();
                } else {
                    AlertBox alertaErr = new AlertBox(Alert.AlertType.ERROR, "ERRO", "Não foi possível eliminar a Jornada.");
                }
            }
        } else {
            AlertBox alerta = new AlertBox(Alert.AlertType.ERROR, "Jornada em falta", "Por favor, selecione uma Jornada.");
        }
    }

}
