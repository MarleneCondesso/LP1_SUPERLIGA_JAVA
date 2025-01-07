package com.superliga.controllers;

import com.superliga.App;
import com.superliga.models.Epoca;
import com.superliga.models.Jogo;
import com.superliga.models.Jornada;
import com.superliga.services.EpocaService;
import com.superliga.services.EquipaService;
import com.superliga.services.EstadioService;
import com.superliga.services.JogoService;
import com.superliga.services.JornadaService;
import com.superliga.utils.AlertBox;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 *
 * @author ruteg
 */
public class ConfigJogosController implements Initializable {

    @FXML
    public ComboBox<Epoca> comboEpocas;
    @FXML
    public ComboBox<Jornada> comboJornadas;
    @FXML
    public TableView<Jogo> tblJogos;
    @FXML
    private TableColumn<Jogo, Integer> colId;
    @FXML
    private TableColumn<Jogo, String> colEquipaCasa;
    @FXML
    private TableColumn<Jogo, String> colEquipaFora;
    @FXML
    private TableColumn<Jogo, Timestamp> colData;
    @FXML
    private TableColumn<Jogo, Integer> colIntervalo;
    @FXML
    private TableColumn<Jogo, Integer> colDuracao;
    @FXML
    private TableColumn<Jogo, String> colEstadio;

    @FXML
    private Button btnAdicionar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;

    @FXML
    private HBox jogosToolbar;

    private Epoca selectedEpoca;
    private Jornada selectedJornada;

    private ConfigJogoController configJogoController;
    private ConfigJogadoresJogoController configJogadoresJogoController;
    private ConfigEventosController configEventosController;

    private EpocaService epocaService = new EpocaService();
    private JornadaService jornadaService = new JornadaService();
    private JogoService service = new JogoService();
    private EquipaService equipaService = new EquipaService();
    private EstadioService estadioService = new EstadioService();

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
        this.jogosToolbar.setVisible(false);
        this.comboJornadas.setVisible(false);

        setComboEpocas();

        this.comboEpocas.setOnAction((e) -> {
            this.selectedEpoca = this.comboEpocas.getSelectionModel().getSelectedItem();
            if (!isNull(this.selectedEpoca)) {
                this.comboJornadas.setVisible(true);
                setComboJornadas();
            }
        });

        this.comboJornadas.setOnAction((e) -> {
            this.selectedJornada = this.comboJornadas.getSelectionModel().getSelectedItem();
            if (!isNull(this.selectedJornada)) {
                refreshTable();
            }
        });
    }

    private void setComboEpocas() {
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
        ObservableList obsListEpocas = null;
        obsListEpocas = FXCollections.observableArrayList(epocaService.getAll());
        this.comboEpocas.getItems().addAll(obsListEpocas);

    }

    private void setComboJornadas() {
        this.comboJornadas.setConverter(new StringConverter<Jornada>() {
            @Override
            public String toString(Jornada jornada) {
                return jornada.getNrJornada().toString();
            }

            @Override
            public Jornada fromString(String string) {
                return null;
            }
        });
        ObservableList obsListJornadas = null;
        obsListJornadas = FXCollections.observableArrayList(jornadaService.getAll());
        this.comboJornadas.getItems().addAll(obsListJornadas);
    }

    /**
     * Método para atualização dos dados na tabela
     */
    public void refreshTable() {
        ObservableList obsList = FXCollections.observableArrayList(service.getAllByEpocaJornada(selectedJornada.getNrEpoca(), selectedJornada.getNrJornada()));
        configColumns();
        tblJogos.getItems().setAll(obsList);
        this.jogosToolbar.setVisible(true);
    }

    /**
     * Método de configuração dos dados na tabela
     */
    private void configColumns() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEquipaCasa.setCellValueFactory(jogo -> new SimpleStringProperty(equipaService.getByPrimaryKey(jogo.getValue().getIdEquipaCasa()).getNome()));
        colEquipaFora.setCellValueFactory(jogo -> new SimpleStringProperty(equipaService.getByPrimaryKey(jogo.getValue().getIdEquipaFora()).getNome()));
        colData.setCellValueFactory(new PropertyValueFactory<>("dataHora"));
        colIntervalo.setCellValueFactory(new PropertyValueFactory<>("intervalo"));
        colDuracao.setCellValueFactory(new PropertyValueFactory<>("duracao"));
        colEstadio.setCellValueFactory(jogo -> new SimpleStringProperty(estadioService.getByPrimaryKey(jogo.getValue().getIdEstadio()).getNome()));
    }

    /**
     * Método para abrir a view Modo Adicionar ou Editar
     *
     * @param fxml
     * @param jogo
     * @param titulo
     * @param pos
     * @throws IOException
     */
    private void showModeEditionView(String fxml, Jogo jogo, String titulo, Integer pos) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.load();

        configJogoController = fxmlLoader.getController();
        configJogoController.setView(jogo, titulo, pos, this);
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

        showModeEditionView("configJogo", null, "Adicionar Jogo", null);

    }

    /**
     * Método para Editar
     *
     * @param event
     */
    @FXML
    private void edit(ActionEvent event) throws IOException {
        Jogo jogo = tblJogos.getSelectionModel().getSelectedItem();
        Integer pos = tblJogos.getSelectionModel().getSelectedIndex();

        if (!isNull(jogo)) {

            showModeEditionView("configJogo", jogo, "Editar Jogo", pos);

        } else {
            AlertBox alerta = new AlertBox(Alert.AlertType.ERROR, "Jogo em falta", "Por favor, selecione um Jogo.");
        }

    }

    /**
     * Método para Eliminar
     *
     * @param event
     */
    @FXML
    private void delete(ActionEvent event) {
        Jogo jogo = tblJogos.getSelectionModel().getSelectedItem();

        if (!isNull(jogo)) {
            AlertBox alerta = new AlertBox(Alert.AlertType.CONFIRMATION, "Eliminar", "Tem a certeza que pretende eliminar este Jogo?");

            if (alerta.getButton().get() == ButtonType.OK) {

                Boolean response = service.deleteByPrimaryKey(jogo.getId());

                if (response) {
                    AlertBox alertaConf = new AlertBox(Alert.AlertType.CONFIRMATION, "SUCESSO", "Jogo eliminado com SUCESSO!");
                    refreshTable();
                } else {
                    AlertBox alertaErr = new AlertBox(Alert.AlertType.ERROR, "ERRO", "Não foi possível eliminar o Jogo.");
                }
            }
        } else {
            AlertBox alerta = new AlertBox(Alert.AlertType.ERROR, "Jogo em falta", "Por favor, selecione um Jogo.");
        }
    }

    /**
     * Método para abrir Equipas do Jogo
     *
     * @param event
     */
    @FXML
    private void openEquipas(ActionEvent event) {
        Jogo jogo = tblJogos.getSelectionModel().getSelectedItem();

        if (!isNull(jogo)) {

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("configJogadoresJogo" + ".fxml"));
                fxmlLoader.load();

                configJogadoresJogoController = fxmlLoader.getController();
                configJogadoresJogoController.setView(jogo);

                Scene scene = new Scene(fxmlLoader.getRoot());
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();

            } catch (IOException ex) {
                System.out.println(ex);
            }

        } else {
            AlertBox alerta = new AlertBox(Alert.AlertType.ERROR, "Jogo em falta", "Por favor, selecione um Jogo.");
        }
    }

    /**
     * Método para abrir Eventos do Jogo
     *
     * @param event
     */
    @FXML
    private void openEventos(ActionEvent event) throws IOException {
        Jogo jogo = tblJogos.getSelectionModel().getSelectedItem();

        if (!isNull(jogo)) {

            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("configEventos" + ".fxml"));
            fxmlLoader.load();

            configEventosController = fxmlLoader.getController();
            configEventosController.setView(jogo);

            Scene scene = new Scene(fxmlLoader.getRoot());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

        } else {
            AlertBox alerta = new AlertBox(Alert.AlertType.ERROR, "Jogo em falta", "Por favor, selecione um Jogo.");
        }

    }

}
