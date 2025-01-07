package com.superliga.controllers;

import com.superliga.App;
import com.superliga.models.Epoca;
import com.superliga.models.Jornada;
import com.superliga.services.EpocaService;
import com.superliga.services.JornadaService;
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
import javafx.scene.control.Button;
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
public class JornadasController implements Initializable {
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
    private Button btnConfig;
    private Epoca selectedEpoca;
    
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
        this.colNrJornada.setCellValueFactory(new PropertyValueFactory<>("nrJornada"));
        this.colDataInicio.setCellValueFactory(new PropertyValueFactory<>("dataInicio"));
        this.colDataFim.setCellValueFactory(new PropertyValueFactory<>("dataFim"));
    }

    /**
     * Método para abrir a view das Config
     *
     * @param fxml
     * @throws IOException
     */
    private void showModalConfigView(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.load();

        configJornadasController = fxmlLoader.getController();

        scene = new Scene(fxmlLoader.getRoot());
        stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Método para abrir Configurações
     *
     * @param event
     */
    @FXML
    private void openConfig(ActionEvent event) {
        try {
            showModalConfigView("configJornadas");
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

}