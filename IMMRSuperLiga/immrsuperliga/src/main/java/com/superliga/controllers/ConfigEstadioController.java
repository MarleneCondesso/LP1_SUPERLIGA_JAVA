package com.superliga.controllers;

import com.superliga.models.Estadio;
import com.superliga.models.Localidade;
import com.superliga.models.Pais;
import com.superliga.services.EstadioService;
import com.superliga.services.LocalidadeService;
import com.superliga.services.PaisService;
import com.superliga.utils.AlertBox;
import java.net.URL;
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

/**
 *
 * @author 35191
 */
public class ConfigEstadioController implements Initializable {

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
    @FXML
    private ComboBox<Localidade> comboLocal;

    private Stage stage;
    private String title;
    private Integer position;
    private Estadio estadio;
    private ConfigEstadiosController configEstadiosController;
    private EstadioService service = new EstadioService();
    private LocalidadeService localService = new LocalidadeService();
    private PaisService paisService = new PaisService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.comboLocal.setConverter(new StringConverter<Localidade>() {

            @Override
            public String toString(Localidade local) {
                Pais pais = paisService.getByPrimaryKey(local.getIdPais());
               
                return pais.getDescricao() + ": " + local.getDescricao();
            }

            @Override
            public Localidade fromString(String string) {
                return null;
            }
        });

        ObservableList obsLocais = FXCollections.observableArrayList(localService.getAll());
        this.comboLocal.getItems().addAll(obsLocais);
    }

    public ConfigEstadioController() {
    }

    /**
     * Método para passar valores para configTipoEventosController
     *
     * @param estadio
     * @param title
     * @param pos
     * @param configEstadiosController
     */
    public void setView(Estadio estadio, String title, Integer pos, ConfigEstadiosController configEstadiosController) {
        this.configEstadiosController = configEstadiosController;

        if (!isNull(estadio)) {
            this.estadio = estadio;
            this.position = pos;
        } else {
            this.estadio = null;
            this.position = null;
        }
        setValuesToForm();
        this.lblTitulo.setText(title);
    }

    /**
     * Método de alteração dos valores dos componentes do formulário
     */
    public void setValuesToForm() {

        if (!isNull(this.estadio)) {
            try {
                this.txtId.setText(String.valueOf(this.estadio.getId()));
                this.txtNome.setText(this.estadio.getNome());
                this.comboLocal.getSelectionModel().select(localService.getByPrimaryKey(this.estadio.getIdLocal()));
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
        this.comboLocal.getSelectionModel().clearSelection();
    }

    /**
     * Método para sair da janela
     *
     * @param event
     */
    @FXML
    private void cancel(MouseEvent event) {
        if (!this.txtNome.getText().isEmpty()
                || this.comboLocal.getSelectionModel().getSelectedItem() != null
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

        if (this.txtNome.getText().isEmpty()
                || this.comboLocal.getSelectionModel().getSelectedItem() == null
                ) {
            AlertBox alerta = new AlertBox(Alert.AlertType.ERROR, "Erro", "Por favor, preencha todos os campos.");
        } else if (this.estadio == null) {
            createEstadio();
        } else {
            updateEstadio();
        }
    }

    /**
     * Método para atribuir valores introduzidos ao objeto, para criar ou editar
     */
    private void setValuesToObject() {
        if (isNull(this.estadio)) {
            this.estadio = new Estadio();
        }

        if(!this.txtId.getText().isEmpty()) {
            this.estadio.setId(Integer.parseInt(this.txtId.getText()));
        }
        this.estadio.setNome(this.txtNome.getText());
        this.estadio.setIdLocal(this.comboLocal.getSelectionModel().getSelectedItem().getId());
        this.estadio.setIdPais(this.comboLocal.getSelectionModel().getSelectedItem().getIdPais());

    }

    /**
     * Método de criar
     */
    public void createEstadio() {

        setValuesToObject();
        Boolean response = service.create(
                this.estadio.getNome(),
                this.estadio.getIdLocal(),
                this.estadio.getIdPais()
        );
        if (response) {
            AlertBox alertaConf = new AlertBox(Alert.AlertType.CONFIRMATION, "SUCESSO", "Estádio criado com SUCESSO!");

            closeModalView(this.btnGuardar);
            configEstadiosController.refreshTable();
        } else {
            AlertBox alertaErr = new AlertBox(Alert.AlertType.ERROR, "ERRO", "Não foi possível criar o Estádio.");
        }
    }

    /**
     * Método de atualizar
     */
    public void updateEstadio() {

        setValuesToObject();

        Boolean response = service.update(
                this.estadio.getId(),
                this.estadio.getNome(),
                this.estadio.getIdLocal(),
                this.estadio.getIdPais()
        );

        if (response) {
            AlertBox alertaConf = new AlertBox(Alert.AlertType.CONFIRMATION, "SUCESSO", "Estádio atualizado com SUCESSO!");

            closeModalView(this.btnGuardar);
            configEstadiosController.refreshTable();

        } else {
            AlertBox alertaErr = new AlertBox(Alert.AlertType.ERROR, "ERRO", "Não foi possível atualizar o Estádio.");
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
