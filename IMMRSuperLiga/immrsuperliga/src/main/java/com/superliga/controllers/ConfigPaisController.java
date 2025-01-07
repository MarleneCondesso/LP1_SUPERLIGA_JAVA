package com.superliga.controllers;

import com.superliga.models.Pais;
import com.superliga.services.PaisService;
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
public class ConfigPaisController implements Initializable {

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
    private Pais pais;
    private ConfigPaisesController configPaisesController;
    private PaisService service = new PaisService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public ConfigPaisController() {
    }

    /**
     * Método para passar valores para configPaisesController
     *
     * @param pais
     * @param title
     * @param pos
     * @param configPaisesController
     */
    public void setView(Pais pais, String title, Integer pos, ConfigPaisesController configPaisesController) {
        this.configPaisesController = configPaisesController;

        if (!isNull(pais)) {
            this.pais = pais;
            this.position = pos;
        } else {
            this.pais = null;
            this.position = null;
        }
        setValuesToForm();
        this.lblTitulo.setText(title);
    }

    /**
     * Método de alteração dos valores dos componentes do formulário
     */
    public void setValuesToForm() {

        if (!isNull(this.pais)) {
            try {
                this.txtId.setText(String.valueOf(this.pais.getId()));
                this.txtNome.setText(this.pais.getDescricao());
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
        } else if (this.pais == null) {
            createPais();
        } else {
            updatePais();
        }
    }

    /**
     * Método para atribuir valores introduzidos ao objeto, para criar ou editar
     */
    private void setValuesToObject() {
        if (isNull(this.pais)) {
            this.pais = new Pais();
        }

        this.pais.setId(Integer.parseInt(this.txtId.getText()));
        this.pais.setDescricao(this.txtNome.getText());

    }

    /**
     * Método de criar
     */
    public void createPais() {

        setValuesToObject();

        Boolean response = service.create(
                this.pais.getId(),
                this.pais.getDescricao()
        );
        if (response) {
            AlertBox alertaConf = new AlertBox(Alert.AlertType.CONFIRMATION, "SUCESSO", "País criado com SUCESSO!");

            closeModalView(this.btnGuardar);
            configPaisesController.refreshTable();
        } else {
            AlertBox alertaErr = new AlertBox(Alert.AlertType.ERROR, "ERRO", "Não foi possível criar o País.");
        }
    }

    /**
     * Método de atualizar
     */
    public void updatePais() {

        setValuesToObject();

        Boolean response = service.update(
                this.pais.getId(),
                this.pais.getDescricao()
        );

        if (response) {
            AlertBox alertaConf = new AlertBox(Alert.AlertType.CONFIRMATION, "SUCESSO", "País atualizado com SUCESSO!");

            closeModalView(this.btnGuardar);
            configPaisesController.refreshTable();

        } else {
            AlertBox alertaErr = new AlertBox(Alert.AlertType.ERROR, "ERRO", "Não foi possível atualizar o País.");
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
