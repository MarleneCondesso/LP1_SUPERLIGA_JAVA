package com.superliga.controllers;

import com.superliga.models.Epoca;
import com.superliga.models.Equipa;
import com.superliga.models.Estadio;
import com.superliga.models.Jogo;
import com.superliga.models.Jornada;
import com.superliga.services.EpocaService;
import com.superliga.services.EquipaService;
import com.superliga.services.EstadioService;
import com.superliga.services.JogoService;
import com.superliga.services.JornadaService;
import com.superliga.utils.AlertBox;
import java.net.URL;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import static java.util.Objects.isNull;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import java.time.format.DateTimeFormatter;
import jfxtras.scene.control.LocalDateTimePicker;
import jfxtras.scene.control.LocalDateTimeTextField;

/**
 *
 * @author ruteg
 */
public class ConfigJogoController implements Initializable {

    @FXML
    private Label lblTitulo;
    @FXML
    private Button btnLimpar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardar;

    @FXML
    private ComboBox<Epoca> comboEpoca;
    @FXML
    private ComboBox<Jornada> comboJornada;
    @FXML
    private ComboBox<Estadio> comboEstadio;
    @FXML
    private ComboBox<Equipa> comboEquipaCasa;
    @FXML
    private ComboBox<Equipa> comboEquipaFora;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtIntervalo;
    @FXML
    private TextField txtDuracao;
    @FXML
    private LocalDateTimeTextField txtDataHoraJogo;

    private Stage stage;
    private String title;
    private Integer position;
    private Jogo jogo;
    
    private Epoca selectedEpoca;
    private Jornada selectedJornada;

    private ConfigJogosController configJogosController;
    private EpocaService epocaService = new EpocaService();
    private JornadaService jornadaService = new JornadaService();
    private EquipaService equipaService = new EquipaService();
    private EstadioService estadioService = new EstadioService();
    private JogoService service = new JogoService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.comboJornada.setDisable(true);

        this.comboEstadio.setConverter(new StringConverter<Estadio>() {

            @Override
            public String toString(Estadio estadio) {

                return estadio.getNome();
            }

            @Override
            public Estadio fromString(String string) {
                return null;
            }
        });

        ObservableList obsEstadios = FXCollections.observableArrayList(estadioService.getAll());
        this.comboEstadio.getItems().addAll(obsEstadios);
        
        this.comboEquipaCasa.setConverter(new StringConverter<Equipa>() {

            @Override
            public String toString(Equipa equipa) {

                return equipa.getNome();
            }

            @Override
            public Equipa fromString(String string) {
                return null;
            }
        });
        this.comboEquipaFora.setConverter(new StringConverter<Equipa>() {

            @Override
            public String toString(Equipa equipa) {

                return equipa.getNome();
            }

            @Override
            public Equipa fromString(String string) {
                return null;
            }
        });
        ObservableList obsEquipas = FXCollections.observableArrayList(equipaService.getAll());
        this.comboEquipaCasa.getItems().addAll(obsEquipas);
        this.comboEquipaFora.getItems().addAll(obsEquipas);
        
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

        ObservableList obsEpocas = FXCollections.observableArrayList(epocaService.getAll());
        this.comboEpoca.getItems().addAll(obsEpocas);
        
        this.comboEpoca.setOnAction((e) -> {
            selectedEpoca = this.comboEpoca.getSelectionModel().getSelectedItem();
            if (!isNull(selectedEpoca)) {
                this.comboJornada.setDisable(false);
                setComboJornadas();
            }
        });

    }
    
    private void setComboJornadas() {
        this.comboJornada.setConverter(new StringConverter<Jornada>() {
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
        this.comboJornada.getItems().addAll(obsListJornadas);
    }

    public ConfigJogoController() {
    }

    /**
     * Método para passar valores para configJornadasController
     *
     * @param jogo
     * @param title
     * @param pos
     * @param configJogosController
     */
    public void setView(Jogo jogo, String title, Integer pos, ConfigJogosController configJogosController) {
        this.configJogosController = configJogosController;

        if (!isNull(jogo)) {
            this.jogo = jogo;
            this.position = pos;
        } else {
            this.jogo = null;
            this.position = null;
        }
        setValuesToForm();
        this.lblTitulo.setText(title);
    }

    /**
     * Método de alteração dos valores dos componentes do formulário
     */
    public void setValuesToForm() {
        if (!isNull(this.jogo)) {
            try {
                setComboJornadas();
                this.comboJornada.setDisable(false);
                
                this.comboEpoca.getSelectionModel().select(epocaService.getByPrimaryKey(this.jogo.getNrEpoca()));
                this.comboJornada.getSelectionModel().select(jornadaService.getByPrimaryKey(this.jogo.getNrEpoca(), this.jogo.getNrJornada()));
                this.comboEquipaCasa.getSelectionModel().select(equipaService.getByPrimaryKey(this.jogo.getIdEquipaCasa()));
                this.comboEquipaFora.getSelectionModel().select(equipaService.getByPrimaryKey(this.jogo.getIdEquipaFora()));
                this.comboEstadio.getSelectionModel().select(estadioService.getByPrimaryKey(this.jogo.getIdEstadio()));
                
                this.txtId.setText(String.valueOf(this.jogo.getId()));
                this.txtDataHoraJogo.setLocalDateTime(this.jogo.getDataHora().toLocalDateTime());
                this.txtIntervalo.setText(String.valueOf(this.jogo.getIntervalo()));
                this.txtDuracao.setText(String.valueOf(this.jogo.getDuracao()));
  
            } catch (NullPointerException e) {
            }
        } else {
            cleanFields();
        }

    }

    /**
     * Método para limpar os campos
     */
    @FXML
    private void cleanFields() {
        this.comboEpoca.getSelectionModel().clearSelection();
        this.comboJornada.getSelectionModel().clearSelection();
        this.comboEquipaCasa.getSelectionModel().clearSelection();
        this.comboEquipaFora.getSelectionModel().clearSelection();
        this.comboEstadio.getSelectionModel().clearSelection();
        this.txtDataHoraJogo.setLocalDateTime(null);
        this.txtId.setText("");
        this.txtIntervalo.setText("");
        this.txtDuracao.setText("");
    }

    /**
     * Método para sair da janela
     *
     * @param event
     */
    @FXML
    private void cancel(MouseEvent event) {
        if (this.comboEpoca.getSelectionModel().getSelectedItem() != null
                || this.comboJornada.getSelectionModel().getSelectedItem() != null
                || this.comboEquipaCasa.getSelectionModel().getSelectedItem() != null
                || this.comboEquipaFora.getSelectionModel().getSelectedItem() != null
                || this.comboEstadio.getSelectionModel().getSelectedItem() != null
                || this.txtDataHoraJogo.getLocalDateTime() != null
                || !this.txtIntervalo.getText().isEmpty()
                || !this.txtDuracao.getText().isEmpty()
                ) {
            AlertBox alerta = new AlertBox(Alert.AlertType.CONFIRMATION, "Cancelar", "Tem a certeza que deseja cancelar? Vai perder as informações preenchidas.");

            if (alerta.getButton().get() == ButtonType.OK) {
                closeModalView(btnCancelar);
            }
            if (alerta.getButton().get() == ButtonType.NO) {
                alerta.getAlert().close();
            }
        } else {
            closeModalView(btnCancelar);
        }
    }

    /**
     * Método para iniciar a ação de gravar
     *
     * @param event
     */
    @FXML
    private void save(MouseEvent event) {

        if (this.comboEpoca.getSelectionModel().getSelectedItem() == null
                || this.comboJornada.getSelectionModel().getSelectedItem() == null
                || this.comboEquipaCasa.getSelectionModel().getSelectedItem() == null
                || this.comboEquipaFora.getSelectionModel().getSelectedItem() == null
                || this.comboEstadio.getSelectionModel().getSelectedItem() == null
                || this.txtDataHoraJogo.getLocalDateTime() == null
                || this.txtIntervalo.getText().isEmpty()
                || this.txtDuracao.getText().isEmpty()
                ) {
            AlertBox alerta = new AlertBox(Alert.AlertType.ERROR, "Erro", "Por favor, preencha todos os campos.");
        } else if (this.jogo == null) {
            createJogo();
        } else {
            updateJogo();
        }
    }

    /**
     * Método para atribuir valores introduzidos ao objeto, para criar ou editar
     */
    private void setValuesToObject() {
        if (isNull(this.jogo)) {
            this.jogo = new Jogo();
        }

        if (!this.txtId.getText().isEmpty()) {
            this.jogo.setId(Integer.parseInt(this.txtId.getText()));
        }
        this.jogo.setNrEpoca(this.comboEpoca.getSelectionModel().getSelectedItem().getNumeroEpoca());
        this.jogo.setNrJornada(this.comboJornada.getSelectionModel().getSelectedItem().getNrJornada());
        this.jogo.setIdEquipaCasa(this.comboEquipaCasa.getSelectionModel().getSelectedItem().getId());
        this.jogo.setIdEquipaFora(this.comboEquipaFora.getSelectionModel().getSelectedItem().getId());
        this.jogo.setIdEstadio(this.comboEstadio.getSelectionModel().getSelectedItem().getId());
        this.jogo.setIntervalo(Integer.parseInt(this.txtIntervalo.getText()));
        this.jogo.setDuracao(Integer.parseInt(this.txtDuracao.getText()));
        this.jogo.setDataHora(Timestamp.valueOf(this.txtDataHoraJogo.getLocalDateTime()));
    }

    /**
     * Método de criar
     */
    public void createJogo() {

        setValuesToObject();

        Boolean response = service.create(
                this.jogo.getIdEquipaCasa(),
                this.jogo.getIdEquipaFora(),
                this.jogo.getNrEpoca(),
                String.valueOf(this.jogo.getNrJornada()),
                this.jogo.getDataHora(),
                this.jogo.getIntervalo(),
                this.jogo.getDuracao(),
                this.jogo.getIdEstadio()
        );
        if (response) {
            AlertBox alertaConf = new AlertBox(Alert.AlertType.CONFIRMATION, "SUCESSO", "Jogo criado com SUCESSO!");

            closeModalView(this.btnGuardar);
            configJogosController.refreshTable();
        } else {
            AlertBox alertaErr = new AlertBox(Alert.AlertType.ERROR, "ERRO", "Não foi possível criar o Jogo.");
        }
    }

    /**
     * Método de atualizar
     */
    public void updateJogo() {

        setValuesToObject();

        Boolean response = service.update(
                this.jogo.getId(),
                this.jogo.getIdEquipaCasa(),
                this.jogo.getIdEquipaFora(),
                this.jogo.getNrEpoca(),
                String.valueOf(this.jogo.getNrJornada()),
                this.jogo.getDataHora(),
                this.jogo.getIntervalo(),
                this.jogo.getDuracao(),
                this.jogo.getIdEstadio()
        );

        if (response) {
            AlertBox alertaConf = new AlertBox(Alert.AlertType.CONFIRMATION, "SUCESSO", "Jogo atualizado com SUCESSO!");

            closeModalView(this.btnGuardar);
            configJogosController.refreshTable();

        } else {
            AlertBox alertaErr = new AlertBox(Alert.AlertType.ERROR, "ERRO", "Não foi possível atualizar o Jogo.");
        }
    }

    /**
     * Método para fechar a view
     *
     * @param btn
     */
    public void closeModalView(Button btn) {
        try {
            Stage stage = (Stage) btn.getScene().getWindow();
            stage.close();
        } catch (RuntimeException e) {

        }
    }
}
