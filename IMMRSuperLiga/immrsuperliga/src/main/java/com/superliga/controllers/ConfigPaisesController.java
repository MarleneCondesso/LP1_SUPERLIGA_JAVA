package com.superliga.controllers;

import com.superliga.App;
import com.superliga.models.Pais;
import com.superliga.services.PaisService;
import com.superliga.utils.AlertBox;
import java.io.IOException;
import java.net.URL;
import static java.util.Objects.isNull;
import java.util.ResourceBundle;
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
public class ConfigPaisesController implements Initializable {
    @FXML
    private Button btnAdicionar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;
    @FXML
    public TableView<Pais> tblPaises;
    @FXML
    private TableColumn<Pais, Integer> colId;
    @FXML
    private TableColumn<Pais, String> colNome;

    private ConfigPaisController configPaisController;
    private PaisService service = new PaisService();

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
        tblPaises.getItems().setAll(obsList);
    }
    
    /**
     * Método de configuração dos dados na tabela
     */
    private void configColumns() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("descricao"));
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
    private void showModeEditionView(String fxml, Pais pais, String titulo, Integer pos) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.load();

        configPaisController = fxmlLoader.getController();
        configPaisController.setView(pais, titulo, pos, this);
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

        showModeEditionView("configPais", null, "Adicionar País", null);

    }

    /**
     * Método para Editar
     *
     * @param event
     */
    @FXML
    private void edit(ActionEvent event) throws IOException {
        Pais pais = tblPaises.getSelectionModel().getSelectedItem();
        Integer pos = tblPaises.getSelectionModel().getSelectedIndex();

        if (!isNull(pais)) {

            showModeEditionView("configPais", pais, "Editar País", pos);

        } else {
            AlertBox alerta = new AlertBox(Alert.AlertType.ERROR, "País em falta", "Por favor, selecione um País.");
        }

    }

    /**
     * Método para Eliminar
     *
     * @param event
     */
    @FXML
    private void delete(ActionEvent event) {
         Pais pais = tblPaises.getSelectionModel().getSelectedItem();

        if (!isNull(pais)) {
            AlertBox alerta = new AlertBox(Alert.AlertType.CONFIRMATION, "Eliminar", "Tem a certeza que pretende eliminar este País?");

            if (alerta.getButton().get() == ButtonType.OK) {

                Boolean response = service.deleteByPrimaryKey(pais.getId());

                if (response) {
                    AlertBox alertaConf = new AlertBox(Alert.AlertType.CONFIRMATION, "SUCESSO", "País eliminado com SUCESSO!");
                    refreshTable();
                } else {
                    AlertBox alertaErr = new AlertBox(Alert.AlertType.ERROR, "ERRO", "Não foi possível eliminar o País.");
                }
            }
        } else {
            AlertBox alerta = new AlertBox(Alert.AlertType.ERROR, "País em falta", "Por favor, selecione um País.");
        }
    }

}