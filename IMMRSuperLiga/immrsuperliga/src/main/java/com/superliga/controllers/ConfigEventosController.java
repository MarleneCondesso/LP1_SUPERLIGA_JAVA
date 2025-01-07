package com.superliga.controllers;

import com.superliga.App;
import com.superliga.models.Equipa;
import com.superliga.models.Evento;
import com.superliga.models.Jogo;
import com.superliga.services.EquipaService;
import com.superliga.services.EstadioService;
import com.superliga.services.EventoService;
import com.superliga.services.JogadorService;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author ruteg
 */
public class ConfigEventosController implements Initializable {

    @FXML
    public TableView<Evento> tblEventos;
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
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblJogo;
    
    private Jogo selectedJogo;

    private ConfigEventoController configEventoController;
    private ConfigEventosController configEventosController;

    private EquipaService equipaService = new EquipaService();
    private EstadioService estadioService = new EstadioService();
    private EventoService service = new EventoService();
    private TipoEventoService tipoEventoService = new TipoEventoService();
    private JogadorService jogadorService = new JogadorService();

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
     *
     * @param jogo
     */
    public void setView(Jogo jogo) {
        this.selectedJogo = jogo;
        Equipa casa = equipaService.getByPrimaryKey(this.selectedJogo.getIdEquipaCasa());
        Equipa fora = equipaService.getByPrimaryKey(this.selectedJogo.getIdEquipaFora());
        
        String textoJogo = "";
        textoJogo += casa.getNome();
        textoJogo += " VS " + fora.getNome();
        textoJogo += "  |  " + this.selectedJogo.getDataHora().toString();
        textoJogo += "  |  " + estadioService.getByPrimaryKey(this.selectedJogo.getIdEstadio()).getNome();
        
        String textoTitulo = "EVENTOS DO JOGO   :   " + casa.getSigla() + " VS " + fora.getSigla();

        this.lblJogo.setText(textoJogo);
        this.lblTitulo.setText(textoTitulo);
        refreshTable();
    }

    /**
     * Método de configuração dos dados na tabela
     */
    private void configColumns() {
        colTipoEvento.setCellValueFactory(evento -> new SimpleStringProperty(tipoEventoService.getByPrimaryKey(evento.getValue().getTipo()).getNome()));
        colTempo.setCellValueFactory(new PropertyValueFactory<>("tempo"));
        colJogador.setCellValueFactory(evento -> new SimpleStringProperty(jogadorService.getByPrimaryKey(evento.getValue().getIdJogadorJogo()).getNome()));
    }

    /**
     * Método para abrir a view Modo Adicionar ou Editar
     *
     * @param fxml
     * @param evento
     * @param titulo
     * @param pos
     * @throws IOException
     */
    private void showModeEditionView(String fxml, Evento evento, String titulo) throws IOException {
        System.out.println("oi");
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.load();

        configEventoController = fxmlLoader.getController();
        configEventoController.setView(evento, titulo, selectedJogo, this);
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

        showModeEditionView("configEvento", null, "Adicionar Evento");

    }

    /**
     * Método para Editar
     *
     * @param event
     */
    @FXML
    private void edit(ActionEvent event) throws IOException {
        Evento evento = tblEventos.getSelectionModel().getSelectedItem();
        Integer pos = tblEventos.getSelectionModel().getSelectedIndex();

        if (!isNull(evento)) {

            showModeEditionView("configEvento", evento, "Editar Evento");

        } else {
            AlertBox alerta = new AlertBox(Alert.AlertType.ERROR, "Evento em falta", "Por favor, selecione um Evento.");
        }

    }

    /**
     * Método para Eliminar
     *
     * @param event
     */
    @FXML
    private void delete(ActionEvent event) {
        Evento evento = tblEventos.getSelectionModel().getSelectedItem();

        if (!isNull(evento)) {
            AlertBox alerta = new AlertBox(Alert.AlertType.CONFIRMATION, "Eliminar", "Tem a certeza que pretende eliminar este Evento?");

            if (alerta.getButton().get() == ButtonType.OK) {

                Boolean response = service.deleteByPrimaryKey(evento.getIdJogadorJogo(), evento.getIdJogo(), evento.getTempo());

                if (response) {
                    AlertBox alertaConf = new AlertBox(Alert.AlertType.CONFIRMATION, "SUCESSO", "Evento eliminado com SUCESSO!");
                    refreshTable();
                } else {
                    AlertBox alertaErr = new AlertBox(Alert.AlertType.ERROR, "ERRO", "Não foi possível eliminar o Evento.");
                }
            }
        } else {
            AlertBox alerta = new AlertBox(Alert.AlertType.ERROR, "Evento em falta", "Por favor, selecione um Evento.");
        }
    }

}
