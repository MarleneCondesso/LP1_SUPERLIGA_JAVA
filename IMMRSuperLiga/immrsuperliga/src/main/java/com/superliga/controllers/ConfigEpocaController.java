package com.superliga.controllers;

import com.superliga.models.Epoca;
import com.superliga.services.EpocaService;
import com.superliga.utils.AlertBox;
import com.superliga.utils.DateFormatterConverter;
import com.superliga.utils.LocalDateTimeAttributeConverter;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import static java.util.Objects.isNull;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author ruteg
 */
public class ConfigEpocaController implements Initializable {

    @FXML
    private Label lblTitulo;
    @FXML
    private Button btnLimpar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardar;
    @FXML
    private TextField txtNrEpoca;
    @FXML
    private DatePicker txtInicio;
    @FXML
    private DatePicker txtFim;

    private Stage stage;
    private String title;
    private Integer position;
    private Epoca epoca;
    private ConfigEpocasController configEpocasController;
    private EpocaService service = new EpocaService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public ConfigEpocaController() {
    }

    /**
     * Método para passar valores para configEpocaController
     *
     * @param epoca
     * @param title
     * @param pos
     * @param configEpocasController
     */
    public void setView(Epoca epoca, String title, Integer pos, ConfigEpocasController configEpocasController) {
        this.configEpocasController = configEpocasController;

        if (!isNull(epoca)) {
            this.epoca = epoca;
            this.position = pos;
        } else {
            this.epoca = null;
            this.position = null;
        }
        setValuesToForm();
        this.lblTitulo.setText(title);
    }

    /**
     * Método de alteração dos valores dos componentes do formulário
     */
    public void setValuesToForm() {
        if (!isNull(this.epoca)) {
            try {
                
                this.txtNrEpoca.setText(this.epoca.getNumeroEpoca());
                this.txtNrEpoca.setDisable(true);
                this.txtNrEpoca.setEditable(false);
                this.txtInicio.setValue(this.epoca.getDataInicio().toLocalDate());
                this.txtFim.setValue(this.epoca.getDataFim().toLocalDate());
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
        this.txtNrEpoca.setText("");
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
        if (!this.txtNrEpoca.getText().isEmpty()
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

        if (this.txtNrEpoca.getText().isEmpty() || this.txtInicio.getValue() == null || this.txtFim.getValue() == null) {
            AlertBox alerta = new AlertBox(Alert.AlertType.ERROR, "Erro", "Por favor, preencha todos os campos.");
        } else if (this.epoca == null) {
            createEpoca();
        } else {
            updateEpoca();
        }
    }

    /**
     * Método para atribuir valores introduzidos ao objeto, para criar ou editar
     */
    private void setValuesToObject() {
        if (isNull(this.epoca)) {
            this.epoca = new Epoca();
        }

        this.epoca.setNumeroEpoca(txtNrEpoca.getText());
        this.epoca.setDataInicio(Date.valueOf(this.txtInicio.getValue()));
        this.epoca.setDataFim(Date.valueOf(this.txtFim.getValue()));

    }

    /**
     * Método de criar
     */
    public void createEpoca() {

        setValuesToObject();

        Boolean response = service.create(
                this.epoca.getNumeroEpoca(),
                this.epoca.getDataInicio(),
                this.epoca.getDataFim()
        );
        
        if (response) {
            AlertBox alertaConf = new AlertBox(Alert.AlertType.CONFIRMATION, "SUCESSO", "Época criada com SUCESSO!");

            closeModalView(this.btnGuardar);
            configEpocasController.refreshTable();
        } else {
            AlertBox alertaErr = new AlertBox(Alert.AlertType.ERROR, "ERRO", "Não foi possível criar a Época.");
        }
    }

    /**
     * Método de atualizar
     */
    public void updateEpoca() {

        setValuesToObject();

        Boolean response = service.update(
                this.epoca.getNumeroEpoca(),
                this.epoca.getDataInicio(),
                this.epoca.getDataFim()
        );

        if (response) {
            AlertBox alertaConf = new AlertBox(Alert.AlertType.CONFIRMATION, "SUCESSO", "Época atualizada com SUCESSO!");

            closeModalView(this.btnGuardar);
            configEpocasController.refreshTable();

        } else {
            AlertBox alertaErr = new AlertBox(Alert.AlertType.ERROR, "ERRO", "Não foi possível atualizar a Época.");
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
