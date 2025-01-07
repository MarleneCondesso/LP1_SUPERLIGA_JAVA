package com.superliga.controllers;

import com.superliga.App;
import com.superliga.models.Localidade;
import com.superliga.services.LocalidadeService;
import com.superliga.services.PaisService;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author 35191
 */
public class ConfigLocalidadesController implements Initializable {

    @FXML
    private Button btnAdicionar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;
    @FXML
    public TableView<Localidade> tblLocalidades;
    @FXML
    private TableColumn<Localidade, Integer> colId;
    @FXML
    private TableColumn<Localidade, String> colNome;
    @FXML
    private TableColumn<Localidade, String> colPais;

    private ConfigLocalidadeController configLocalidadeController;
    private LocalidadeService service = new LocalidadeService();
    private PaisService paisService = new PaisService();

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
        refreshTable();
    }

    /**
     * Método para atualização dos dados na tabela
     */
    public void refreshTable() {
        ObservableList obsList = FXCollections.observableArrayList(service.getAll());
        configColumns();
        tblLocalidades.getItems().setAll(obsList);
    }

    /**
     * Método de configuração dos dados na tabela
     */
    private void configColumns() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colPais.setCellValueFactory(pais -> new SimpleStringProperty(paisService.getByPrimaryKey(pais.getValue().getIdPais()).getDescricao()));

    }

    /**
     * Método para abrir a view Modo Adicionar ou Editar
     *
     * @param fxml
     * @param epoca
     * @param titulo
     * @param pos
     * @throws IOException
     */
    private void showModeEditionView(String fxml, Localidade local, String titulo, Integer pos) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.load();

        configLocalidadeController = fxmlLoader.getController();
        configLocalidadeController.setView(local, titulo, pos, this);
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

        showModeEditionView("configLocalidade", null, "Adicionar Localidade", null);

    }

    /**
     * Método para Editar
     *
     * @param event
     */
    @FXML
    private void edit(ActionEvent event) throws IOException {
        Localidade local = tblLocalidades.getSelectionModel().getSelectedItem();
        Integer pos = tblLocalidades.getSelectionModel().getSelectedIndex();

        if (!isNull(local)) {

            showModeEditionView("configLocalidade", local, "Editar Localidade", pos);

        } else {
            AlertBox alerta = new AlertBox(Alert.AlertType.ERROR, "Localidade em falta", "Por favor, selecione uma Localidade.");
        }

    }

    /**
     * Método para Eliminar
     *
     * @param event
     */
    @FXML
    private void delete(ActionEvent event) {
        Localidade local = tblLocalidades.getSelectionModel().getSelectedItem();

        if (!isNull(local)) {
            AlertBox alerta = new AlertBox(Alert.AlertType.CONFIRMATION, "Eliminar", "Tem a certeza que pretende eliminar esta Localidade?");

            if (alerta.getButton().get() == ButtonType.OK) {

                Boolean response = service.deleteByPrimaryKey(local.getId());

                if (response) {
                    AlertBox alertaConf = new AlertBox(Alert.AlertType.CONFIRMATION, "SUCESSO", "Localidade eliminada com SUCESSO!");
                    refreshTable();
                } else {
                    AlertBox alertaErr = new AlertBox(Alert.AlertType.ERROR, "ERRO", "Não foi possível eliminar a Localidade.");
                }
            }
        } else {
            AlertBox alerta = new AlertBox(Alert.AlertType.ERROR, "Localidade em falta", "Por favor, selecione uma Localidade.");
        }
    }

}
