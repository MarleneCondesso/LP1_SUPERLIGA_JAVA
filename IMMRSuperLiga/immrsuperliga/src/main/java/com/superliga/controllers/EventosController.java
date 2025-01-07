package com.superliga.controllers;

import com.superliga.App;
import com.superliga.models.Epoca;
import com.superliga.models.Evento;
import com.superliga.models.Jogo;
import com.superliga.models.Jornada;
import com.superliga.services.EpocaService;
import com.superliga.services.EventoService;
import com.superliga.services.JogadorJogoService;
import com.superliga.services.JogoService;
import com.superliga.services.JornadaService;
import com.superliga.services.TipoEventoService;
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
public class EventosController implements Initializable {

    @FXML
    public ComboBox<Epoca> comboEpocas;
    @FXML
    public ComboBox<Jornada> comboJornadas;
    @FXML
    public ComboBox<Jogo> comboJogos;
    @FXML
    public TableView<Evento> tblEventos;
    @FXML
    private TableColumn<Evento, String> colJogo;
    @FXML
    private TableColumn<Evento, String> colTipoEvento;
    @FXML
    private TableColumn<Evento, String> colTempo;
    @FXML
    private TableColumn<Evento, String> colJogador;

    @FXML
    private Button btnAdicionar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;

    private Jogo selectedJogo;

    private ConfigEventoController configEventoController;
    private ConfigEventosController configEventosController;
    private EpocaService epocaService = new EpocaService();
    private JornadaService jornadaService = new JornadaService();
    private JogoService jogoService = new JogoService();
    private EventoService service = new EventoService();
    private TipoEventoService tipoEventoService = new TipoEventoService();
    private JogadorJogoService jogadorJogoService = new JogadorJogoService();

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

        this.comboJornadas.setVisible(false);
        this.comboJogos.setVisible(false);

        setComboEpocas();

        this.comboEpocas.setOnAction((e) -> {
            Epoca selectedEpoca = this.comboEpocas.getSelectionModel().getSelectedItem();
            if (!isNull(selectedEpoca)) {
                this.comboJornadas.setVisible(true);
                setComboJornadas();
            }
        });

        this.comboJornadas.setOnAction((e) -> {
            Jornada selectedJornada = this.comboJornadas.getSelectionModel().getSelectedItem();
            if (!isNull(selectedJornada)) {
                this.comboJornadas.setVisible(true);
                setComboJogos();
            }
        });

        this.comboJogos.setOnAction((e) -> {
            selectedJogo = this.comboJogos.getSelectionModel().getSelectedItem();
            if (!isNull(selectedJogo)) {
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
        ObservableList obsListEpocas = FXCollections.observableArrayList(epocaService.getAll());
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
        ObservableList obsListJornadas = FXCollections.observableArrayList(jornadaService.getAll());
        this.comboJornadas.getItems().addAll(obsListJornadas);
    }

    private void setComboJogos() {
        this.comboJogos.setConverter(new StringConverter<Jogo>() {
            @Override
            public String toString(Jogo jogo) {
                return jogo.getDataHora().toString();
            }

            @Override
            public Jogo fromString(String string) {
                return null;
            }
        });
        ObservableList obsListJogos = FXCollections.observableArrayList(jogoService.getAllByEpocaJornada(selectedJogo.getNrEpoca(), Integer.valueOf(selectedJogo.getNrJornada())));
        this.comboJogos.getItems().addAll(obsListJogos);
    }

    /**
     * Método para atualização dos dados na tabela
     */
    public void refreshTable() {
        ObservableList obsList = FXCollections.observableArrayList(service.getAllByJogo(selectedJogo.getId()));
        configColumns();
        tblEventos.getItems().setAll(obsList);
    }

    /**
     * Método de configuração dos dados na tabela
     */
    private void configColumns() {

        colJogo.setCellValueFactory(evento -> new SimpleStringProperty(jogoService.getByPrimaryKey(evento.getValue().getIdJogo()).toString()));
        colTipoEvento.setCellValueFactory(evento -> new SimpleStringProperty(tipoEventoService.getByPrimaryKey(evento.getValue().getTipo()).getNome()));
        colTempo.setCellValueFactory(new PropertyValueFactory<>("tempo"));
        colJogador.setCellValueFactory(evento -> new SimpleStringProperty(jogadorJogoService.getByPrimaryKey(evento.getValue().getIdJogadorJogo(), evento.getValue().getIdJogo()).toString()));

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

        configEventosController = fxmlLoader.getController();

        scene = new Scene(fxmlLoader.getRoot());
        stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

}
