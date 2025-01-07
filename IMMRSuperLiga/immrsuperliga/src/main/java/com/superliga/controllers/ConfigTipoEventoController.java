package com.superliga.controllers;

import com.superliga.models.TipoEvento;
import com.superliga.services.TipoEventoService;
import com.superliga.utils.AlertBox;
import java.net.URL;
import static java.util.Objects.isNull;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author 35191
 */
public class ConfigTipoEventoController implements Initializable {

    @FXML
    private Label lblTitulo;
    @FXML
    private Button btnLimpar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardar;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtNome;

    private Stage stage;
    private String title;
    private Integer position;
    private TipoEvento tpEvento;
    private ConfigTipoEventosController configTipoEventosController;
    private TipoEventoService service = new TipoEventoService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public ConfigTipoEventoController() {
    }

    /**
     * Método para passar valores para configTipoEventosController
     *
     * @param tpEvento
     * @param title
     * @param pos
     * @param configTipoEventosController
     */
    public void setView(TipoEvento tpEvento, String title, Integer pos, ConfigTipoEventosController configTipoEventosController) {
        this.configTipoEventosController = configTipoEventosController;

        if (!isNull(tpEvento)) {
            this.tpEvento = tpEvento;
            this.position = pos;
        } else {
            this.tpEvento = null;
            this.position = null;
        }
        setValuesToForm();
        this.lblTitulo.setText(title);
    }

    /**
     * Método de alteração dos valores dos componentes do formulário
     */
    public void setValuesToForm() {

        if (!isNull(this.tpEvento)) {
            try {
                this.txtId.setText(this.tpEvento.getId());
                this.txtNome.setText(this.tpEvento.getNome());
                this.txtId.setDisable(true);
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
        this.txtId.setText("");
        this.txtNome.setText("");
    }

    /**
     * Método para sair da janela
     *
     * @param event
     */
    @FXML
    private void cancel(MouseEvent event) {
        if (!this.txtId.getText().isEmpty() || !this.txtNome.getText().isEmpty()) {
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

        if (this.txtId.getText().isEmpty() || this.txtNome.getText().isEmpty()) {
            AlertBox alerta = new AlertBox(Alert.AlertType.ERROR, "Erro", "Por favor, preencha todos os campos.");
        } else if (this.tpEvento == null) {
            createTipoEvento();
        } else {
            updateTipoEvento();
        }
    }

    /**
     * Método para atribuir valores introduzidos ao objeto, para criar ou editar
     */
    private void setValuesToObject() {
        if (isNull(this.tpEvento)) {
            this.tpEvento = new TipoEvento();
        }

        this.tpEvento.setId(this.txtId.getText());
        this.tpEvento.setNome(this.txtNome.getText());

    }

    /**
     * Método de criar
     */
    public void createTipoEvento() {

        setValuesToObject();

        Boolean response = service.create(
                this.tpEvento.getId(),
                this.tpEvento.getNome()
        );
        if (response) {
            AlertBox alertaConf = new AlertBox(Alert.AlertType.CONFIRMATION, "SUCESSO", "Tipo de Evento criado com SUCESSO!");

            closeModalView(this.btnGuardar);
            configTipoEventosController.refreshTable();
        } else {
            AlertBox alertaErr = new AlertBox(Alert.AlertType.ERROR, "ERRO", "Não foi possível criar o Tipo de Evento.");
        }
    }

    /**
     * Método de atualizar
     */
    public void updateTipoEvento() {

        setValuesToObject();

        Boolean response = service.update(
                this.tpEvento.getId(),
                this.tpEvento.getNome()
        );

        if (response) {
            AlertBox alertaConf = new AlertBox(Alert.AlertType.CONFIRMATION, "SUCESSO", "Tipo de Evento atualizado com SUCESSO!");

            closeModalView(this.btnGuardar);
            configTipoEventosController.refreshTable();

        } else {
            AlertBox alertaErr = new AlertBox(Alert.AlertType.ERROR, "ERRO", "Não foi possível atualizar o Tipo de Evento.");
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
