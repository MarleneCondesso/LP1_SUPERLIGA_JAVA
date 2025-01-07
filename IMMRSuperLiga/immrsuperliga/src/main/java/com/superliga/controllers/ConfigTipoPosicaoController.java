package com.superliga.controllers;

import com.superliga.models.Posicao;
import com.superliga.services.PosicaoService;
import com.superliga.utils.AlertBox;
import java.net.URL;
import static java.util.Objects.isNull;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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
public class ConfigTipoPosicaoController implements Initializable {

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
    private TextField txtDescricao;

    private Stage stage;
    private String title;
    private Integer position;
    private Posicao posicao;
    private ConfigTipoPosicoesController configTipoPosicoesController;
    private PosicaoService service = new PosicaoService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public ConfigTipoPosicaoController() {
    }

    /**
     * Método para passar valores para configTipoPosicoesController
     *
     * @param posicao
     * @param title
     * @param pos
     * @param configTipoPosicoesController
     */
    public void setView(Posicao posicao, String title, Integer pos, ConfigTipoPosicoesController configTipoPosicoesController) {
        this.configTipoPosicoesController = configTipoPosicoesController;

        if (!isNull(posicao)) {
            this.posicao = posicao;
            this.position = pos;
        } else {
            this.posicao = null;
            this.position = null;
        }
        setValuesToForm();
        this.lblTitulo.setText(title);
    }

    /**
     * Método de alteração dos valores dos componentes do formulário
     */
    public void setValuesToForm() {

        if (!isNull(this.posicao)) {
            try {
                this.txtId.setText(this.posicao.getId());
                this.txtDescricao.setText(this.posicao.getDescricao());
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
        this.txtDescricao.setText("");
    }

    /**
     * Método para sair da janela
     *
     * @param event
     */
    @FXML
    private void cancel(MouseEvent event) {
        if (!this.txtId.getText().isEmpty() || !this.txtDescricao.getText().isEmpty()) {
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

        if (this.txtId.getText().isEmpty() || this.txtDescricao.getText().isEmpty()) {
            AlertBox alerta = new AlertBox(Alert.AlertType.ERROR, "Erro", "Por favor, preencha todos os campos.");
        } else if (this.posicao == null) {
            createTipoPosicao();
        } else {
            updateTipoPosicao();
        }
    }

    /**
     * Método para atribuir valores introduzidos ao objeto, para criar ou editar
     */
    private void setValuesToObject() {
        if (isNull(this.posicao)) {
            this.posicao = new Posicao();
        }

        this.posicao.setId(this.txtId.getText());
        this.posicao.setDescricao(this.txtDescricao.getText());

    }

    /**
     * Método de criar
     */
    public void createTipoPosicao() {

        setValuesToObject();

        Boolean response = service.create(
                this.posicao.getId(),
                this.posicao.getDescricao()
        );
        if (response) {
            AlertBox alertaConf = new AlertBox(Alert.AlertType.CONFIRMATION, "SUCESSO", "Tipo de Posição criado com SUCESSO!");

            closeModalView(this.btnGuardar);
            configTipoPosicoesController.refreshTable();
        } else {
            AlertBox alertaErr = new AlertBox(Alert.AlertType.ERROR, "ERRO", "Não foi possível criar o Tipo de Posição.");
        }
    }

    /**
     * Método de atualizar
     */
    public void updateTipoPosicao() {

        setValuesToObject();

        Boolean response = service.update(
                this.posicao.getId(),
                this.posicao.getDescricao()
        );

        if (response) {
            AlertBox alertaConf = new AlertBox(Alert.AlertType.CONFIRMATION, "SUCESSO", "Tipo de Posição atualizado com SUCESSO!");

            closeModalView(this.btnGuardar);
            configTipoPosicoesController.refreshTable();

        } else {
            AlertBox alertaErr = new AlertBox(Alert.AlertType.ERROR, "ERRO", "Não foi possível atualizar o Tipo de Posição.");
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
