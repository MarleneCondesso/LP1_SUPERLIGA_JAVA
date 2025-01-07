package com.superliga.controllers;

import com.superliga.models.Epoca;
import com.superliga.models.Jornada;
import com.superliga.services.EpocaService;
import com.superliga.services.JornadaService;
import com.superliga.utils.AlertBox;
import java.net.URL;
import java.sql.Date;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 *
 * @author ruteg
 */
public class ConfigJornadaController implements Initializable {

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
    private TextField txtNrJornada;
    @FXML
    private DatePicker txtInicio;
    @FXML
    private DatePicker txtFim;

    private Stage stage;
    private String title;
    private Integer position;
    private Jornada jornada;
    private ConfigJornadasController configJornadasController;
    private EpocaService epocaService = new EpocaService();
    private JornadaService service = new JornadaService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
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

    }

    public ConfigJornadaController() {
    }

    /**
     * Método para passar valores para configJornadasController
     *
     * @param jornada
     * @param title
     * @param configJornadasController
     */
    public void setView(Jornada jornada, String title, ConfigJornadasController configJornadasController) {
        this.configJornadasController = configJornadasController;
        if (!isNull(jornada)) {
            this.jornada = jornada;
        } else {
            this.jornada = null;
        }

        setValuesToForm();
        this.lblTitulo.setText(title);
    }

    /**
     * Método de alteração dos valores dos componentes do formulário
     */
    public void setValuesToForm() {
        System.out.println("eetrggf");
        if (!isNull(this.jornada)) {
            try {
                this.txtNrJornada.setDisable(true);
                this.comboEpoca.getSelectionModel().select(epocaService.getByPrimaryKey(this.jornada.getNrEpoca()));
                this.txtNrJornada.setText(String.valueOf(this.jornada.getNrJornada()));
                this.txtInicio.setValue(this.jornada.getDataInicio().toLocalDate());
                this.txtFim.setValue(this.jornada.getDataFim().toLocalDate());
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
        this.txtNrJornada.setText("");
        this.txtInicio.setValue(null);
        this.txtFim.setValue(null);
    }

    /**
     * Método para sair da janela
     *
     * @param event
     */
    @FXML
    private void cancel(MouseEvent event) {
        if (this.comboEpoca.getSelectionModel().getSelectedItem() != null
                || !this.txtNrJornada.getText().isEmpty()
                || this.txtInicio.getValue() != null
                || this.txtFim.getValue() != null) {
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
                || this.txtNrJornada.getText().isEmpty()
                || this.txtInicio.getValue() == null
                || this.txtFim.getValue() == null) {
            AlertBox alerta = new AlertBox(Alert.AlertType.ERROR, "Erro", "Por favor, preencha todos os campos.");
        } else if (this.jornada == null) {
            createJornada();
        } else {
            updateJornada();
        }
    }

    /**
     * Método para atribuir valores introduzidos ao objeto, para criar ou editar
     */
    private void setValuesToObject() {
        if (isNull(this.jornada)) {
            this.jornada = new Jornada();
        }

        this.jornada.setNrEpoca(this.comboEpoca.getSelectionModel().getSelectedItem().getNumeroEpoca());
        this.jornada.setNrJornada(Integer.parseInt(this.txtNrJornada.getText()));
        this.jornada.setDataInicio(Date.valueOf(this.txtInicio.getValue()));
        this.jornada.setDataFim(Date.valueOf(this.txtFim.getValue()));

    }

    /**
     * Método de criar
     */
    public void createJornada() {

        setValuesToObject();

        Boolean response = service.create(
                this.jornada.getNrEpoca(),
                this.jornada.getNrJornada(),
                this.jornada.getDataInicio(),
                this.jornada.getDataFim()
        );
        if (response) {
            AlertBox alertaConf = new AlertBox(Alert.AlertType.CONFIRMATION, "SUCESSO", "Jornada criada com SUCESSO!");

            closeModalView(this.btnGuardar);
            configJornadasController.refreshTable();
        } else {
            AlertBox alertaErr = new AlertBox(Alert.AlertType.ERROR, "ERRO", "Não foi possível criar a Jornada.");
        }
    }

    /**
     * Método de atualizar
     */
    public void updateJornada() {

        setValuesToObject();

        Boolean response = service.update(
                this.jornada.getNrEpoca(),
                this.jornada.getNrJornada(),
                this.jornada.getDataInicio(),
                this.jornada.getDataFim()
        );

        if (response) {
            AlertBox alertaConf = new AlertBox(Alert.AlertType.CONFIRMATION, "SUCESSO", "Jornada atualizada com SUCESSO!");

            closeModalView(this.btnGuardar);
            configJornadasController.refreshTable();

        } else {
            AlertBox alertaErr = new AlertBox(Alert.AlertType.ERROR, "ERRO", "Não foi possível atualizar a Jornada.");
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
