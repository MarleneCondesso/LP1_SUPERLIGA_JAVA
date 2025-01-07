package com.superliga.controllers;

import com.superliga.models.Localidade;
import com.superliga.models.Pais;
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
public class ConfigLocalidadeController implements Initializable {

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
    private ComboBox<Pais> comboPais;

    private Stage stage;
    private String title;
    private Integer position;
    private Localidade local;
    private ConfigLocalidadesController configLocalidadesController;
    private LocalidadeService service = new LocalidadeService();
    private PaisService paisService = new PaisService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.comboPais.setConverter(new StringConverter<Pais>() {

            @Override
            public String toString(Pais pais) {
                return pais.getDescricao();
            }

            @Override
            public Pais fromString(String string) {
                return null;
            }
        });

        ObservableList obsPaises = FXCollections.observableArrayList(paisService.getAll());
        this.comboPais.getItems().addAll(obsPaises);

    }

    public ConfigLocalidadeController() {
    }

    /**
     * Método para passar valores para configLocalidadesController
     *
     * @param local
     * @param title
     * @param pos
     * @param configLocalidadesController
     */
    public void setView(Localidade local, String title, Integer pos, ConfigLocalidadesController configLocalidadesController) {
        this.configLocalidadesController = configLocalidadesController;

        if (!isNull(local)) {
            this.local = local;
            this.position = pos;
        } else {
            this.local = null;
            this.position = null;
        }
        setValuesToForm();
        this.lblTitulo.setText(title);
    }

    /**
     * Método de alteração dos valores dos componentes do formulário
     */
    public void setValuesToForm() {

        if (!isNull(this.local)) {
            try {
                this.txtId.setText(String.valueOf(this.local.getId()));
                this.txtNome.setText(this.local.getDescricao());
                this.comboPais.getSelectionModel().select(paisService.getByPrimaryKey(this.local.getIdPais()));
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
        this.comboPais.getSelectionModel().clearSelection();
    }

    /**
     * Método para sair da janela
     *
     * @param event
     */
    @FXML
    private void cancel(MouseEvent event) {
        if (!this.txtNome.getText().isEmpty()
                || this.comboPais.getSelectionModel().getSelectedItem() != null) {
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
                || this.comboPais.getSelectionModel().getSelectedItem() == null) {
            AlertBox alerta = new AlertBox(Alert.AlertType.ERROR, "Erro", "Por favor, preencha todos os campos.");
        } else if (this.local == null) {
            createLocalidade();
        } else {
            updateLocalidade();
        }
    }

    /**
     * Método para atribuir valores introduzidos ao objeto, para criar ou editar
     */
    private void setValuesToObject() {
        if (isNull(this.local)) {
            this.local = new Localidade();
        }
        if(!this.txtId.getText().isEmpty()) {
            this.local.setId(Integer.parseInt(this.txtId.getText()));
        }
        this.local.setDescricao(this.txtNome.getText());
        this.local.setIdPais(this.comboPais.getSelectionModel().getSelectedItem().getId());

    }

    /**
     * Método de criar
     */
    public void createLocalidade() {

        setValuesToObject();

        Boolean response = service.create(
                this.local.getIdPais(),
                this.local.getDescricao()
        );
        if (response) {
            AlertBox alertaConf = new AlertBox(Alert.AlertType.CONFIRMATION, "SUCESSO", "Localidade criada com SUCESSO!");

            closeModalView(this.btnGuardar);
            configLocalidadesController.refreshTable();
        } else {
            AlertBox alertaErr = new AlertBox(Alert.AlertType.ERROR, "ERRO", "Não foi possível criar a Localidade.");
        }
    }

    /**
     * Método de atualizar
     */
    public void updateLocalidade() {

        setValuesToObject();

        Boolean response = service.update(
                this.local.getId(),
                this.local.getIdPais(),
                this.local.getDescricao()
        );

        if (response) {
            AlertBox alertaConf = new AlertBox(Alert.AlertType.CONFIRMATION, "SUCESSO", "Localidade atualizada com SUCESSO!");

            closeModalView(this.btnGuardar);
            configLocalidadesController.refreshTable();

        } else {
            AlertBox alertaErr = new AlertBox(Alert.AlertType.ERROR, "ERRO", "Não foi possível atualizar a Localidade.");
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
