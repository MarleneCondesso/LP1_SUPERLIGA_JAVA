package com.superliga.controllers;

import com.superliga.models.Jogador;
import com.superliga.services.JogadorService;
import com.superliga.utils.AlertBox;
import java.net.URL;
import java.sql.Date;
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
 * @author 35191
 */
public class ConfigJogadorController implements Initializable {

    @FXML
    private Label lblTitulo;
    @FXML
    private Button btnLimpar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardar;
    @FXML
    private TextField txtNrAtleta;
    @FXML
    private TextField txtNome;
    @FXML
    private DatePicker txtDataNasc;

    private Stage stage;
    private String title;
    private Integer position;
    private Jogador jogador;
    private ConfigJogadoresController configJogadoresController;
    private JogadorService service = new JogadorService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        

    }

    public ConfigJogadorController() {
    }

    /**
     * Método para passar valores para configJogadoresController
     *
     * @param jogador
     * @param title
     * @param pos
     * @param configJogadoresController
     */
    public void setView(Jogador jogador, String title, Integer pos, ConfigJogadoresController configJogadoresController) {
        this.configJogadoresController = configJogadoresController;

        if (!isNull(jogador)) {
            this.jogador = jogador;
            this.position = pos;
        } else {
            this.jogador = null;
            this.position = null;
        }
        setValuesToForm();
        this.lblTitulo.setText(title);
    }

    /**
     * Método de alteração dos valores dos componentes do formulário
     */
    public void setValuesToForm() {

        if (!isNull(this.jogador)) {
            try {
                this.txtNrAtleta.setText(String.valueOf(this.jogador.getNrAtleta()));
                this.txtNome.setText(this.jogador.getNome());
                this.txtDataNasc.setValue(this.jogador.getDataNasc().toLocalDate());
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
        this.txtNrAtleta.setText("");
        this.txtNome.setText("");
        this.txtDataNasc.setValue(null);
    }

    /**
     * Método para sair da janela
     *
     * @param event
     */
    @FXML
    private void cancel(MouseEvent event) {
        if (!this.txtNrAtleta.getText().isEmpty()
                || !this.txtNome.getText().isEmpty()
                || this.txtDataNasc.getValue() != null) {
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

        if (this.txtNrAtleta.getText().isEmpty()
                || this.txtNome.getText().isEmpty()
                || this.txtDataNasc.getValue() == null) {
            AlertBox alerta = new AlertBox(Alert.AlertType.ERROR, "Erro", "Por favor, preencha todos os campos.");
        } else if (this.jogador == null) {
            createJogador();
        } else {
            updateJogador();
        }
    }

    /**
     * Método para atribuir valores introduzidos ao objeto, para criar ou editar
     */
    private void setValuesToObject() {
        if (isNull(this.jogador)) {
            this.jogador = new Jogador();
        }

        this.jogador.setNrAtleta(Integer.parseInt(this.txtNrAtleta.getText()));
        this.jogador.setNome(this.txtNome.getText());
        this.jogador.setDataNasc(Date.valueOf(this.txtDataNasc.getValue()));

    }

    /**
     * Método de criar
     */
    public void createJogador() {

        setValuesToObject();

        Boolean response = service.create(
                this.jogador.getNrAtleta(),
                this.jogador.getNome(),
                this.jogador.getDataNasc()
        );
        if (response) {
            AlertBox alertaConf = new AlertBox(Alert.AlertType.CONFIRMATION, "SUCESSO", "Jogador criado com SUCESSO!");

            closeModalView(this.btnGuardar);
            configJogadoresController.refreshTable();
        } else {
            AlertBox alertaErr = new AlertBox(Alert.AlertType.ERROR, "ERRO", "Não foi possível criar o Jogador.");
        }
    }

    /**
     * Método de atualizar
     */
    public void updateJogador() {

        setValuesToObject();

        Boolean response = service.update(
                this.jogador.getNrAtleta(),
                this.jogador.getNome(),
                this.jogador.getDataNasc()
        );

        if (response) {
            AlertBox alertaConf = new AlertBox(Alert.AlertType.CONFIRMATION, "SUCESSO", "Jogador atualizado com SUCESSO!");

            closeModalView(this.btnGuardar);
            configJogadoresController.refreshTable();

        } else {
            AlertBox alertaErr = new AlertBox(Alert.AlertType.ERROR, "ERRO", "Não foi possível atualizar o Jogador.");
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
