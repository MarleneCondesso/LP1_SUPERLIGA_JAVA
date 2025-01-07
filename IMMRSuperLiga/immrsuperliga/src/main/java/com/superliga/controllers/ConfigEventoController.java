package com.superliga.controllers;

import com.superliga.models.Evento;
import com.superliga.models.Jogador;
import com.superliga.models.JogadorJogo;
import com.superliga.models.Jogo;
import com.superliga.models.TipoEvento;
import com.superliga.services.EquipaService;
import com.superliga.services.EstadioService;
import com.superliga.services.EventoService;
import com.superliga.services.JogadorJogoService;
import com.superliga.services.JogadorService;
import com.superliga.services.JogoService;
import com.superliga.services.TipoEventoService;
import com.superliga.utils.AlertBox;
import java.math.BigDecimal;
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
 * @author ruteg
 */
public class ConfigEventoController implements Initializable {

    @FXML
    private Label lblTitulo;
    @FXML
    private Button btnLimpar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardar;
    @FXML
    private TextField txtEpocaJornada;
    @FXML
    private TextField txtJogo;
    @FXML
    private ComboBox<JogadorJogo> comboJogadorJogo;
    @FXML
    private ComboBox<TipoEvento> comboTipoEvento;
    @FXML
    private TextField txtTempo;

    private Evento evento;
    private Jogo selectedJogo;

    private ConfigEventosController configEventosController;
    private EquipaService equipaService = new EquipaService();
    private EstadioService estadioService = new EstadioService();
    
    private JogoService jogoService = new JogoService();
    private JogadorService jogadorService = new JogadorService();
    private JogadorJogoService jogadorJogoService = new JogadorJogoService();
    private TipoEventoService tipoEventoService = new TipoEventoService();
    private EventoService service = new EventoService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.txtJogo.setText("");
        
        this.comboJogadorJogo.setConverter(new StringConverter<JogadorJogo>() {
            @Override
            public String toString(JogadorJogo jogadorJogo) {
                Jogador jogador = jogadorService.getByPrimaryKey(jogadorJogo.getNrAtleta());
                return jogadorJogo.getNrAtleta() + " | " + jogador.getNome();
            }
            @Override
            public JogadorJogo fromString(String string) {
                return null;
            }
        });
        ObservableList obsListJogos = FXCollections.observableArrayList(jogadorJogoService.getAllByJogo(this.selectedJogo.getId()));
        this.comboJogadorJogo.getItems().addAll(obsListJogos);
    }

    public ConfigEventoController() {
    }

    /**
     * Método para passar valores para configEventosController
     *
     * @param evento
     * @param title
     * @param jogo
     * @param configEventosController
     */
    public void setView(Evento evento, String title, Jogo jogo, ConfigEventosController configEventosController) {
        this.configEventosController = configEventosController;
        this.selectedJogo = jogo;
        if (!isNull(evento)) {
            this.evento = evento;
        } else {
            this.evento = null;
        }
        setValuesToForm();
        this.lblTitulo.setText(title);
    }

    /**
     * Método de alteração dos valores dos componentes do formulário
     */
    public void setValuesToForm() {

        if (!isNull(this.selectedJogo)) {
            
            String textoJogo = "";
            textoJogo += equipaService.getByPrimaryKey(this.selectedJogo.getIdEquipaCasa());
            textoJogo += " VS. " + equipaService.getByPrimaryKey(this.selectedJogo.getIdEquipaFora());
            textoJogo += " | " + this.selectedJogo.getDataHora().toString();
            textoJogo += " | " + estadioService.getByPrimaryKey(this.selectedJogo.getIdEstadio());
            
            this.txtEpocaJornada.setText(this.selectedJogo.getNrEpoca() + " | " + this.selectedJogo.getNrJornada());
            this.txtJogo.setText(textoJogo);

            if (!isNull(this.evento)) {
                try {
                    this.comboJogadorJogo.getSelectionModel().select(jogadorJogoService.getByPrimaryKey(this.evento.getIdJogadorJogo(), this.evento.getIdJogo()));
                    this.comboTipoEvento.getSelectionModel().select(tipoEventoService.getByPrimaryKey(this.evento.getTipo()));
                } catch (NullPointerException e) {
                }
            } else {
                cleanFields();
            }
        }

    }

    /**
     * Método para limpar os campos
     */
    @FXML
    private void cleanFields() {
        this.comboJogadorJogo.getSelectionModel().clearSelection();
        this.comboTipoEvento.getSelectionModel().clearSelection();
        this.txtTempo.setText("");
    }

    /**
     * Método para sair da janela
     *
     * @param event
     */
    @FXML
    private void cancel(MouseEvent event) {
        if (this.comboJogadorJogo.getSelectionModel().getSelectedItem() != null
                || this.comboTipoEvento.getSelectionModel().getSelectedItem() != null
                || !this.txtTempo.getText().isEmpty()) {
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

        if (this.comboJogadorJogo.getSelectionModel().getSelectedItem() == null
                || this.comboTipoEvento.getSelectionModel().getSelectedItem() == null
                || this.txtTempo.getText().isEmpty()) {
            AlertBox alerta = new AlertBox(Alert.AlertType.ERROR, "Erro", "Por favor, preencha todos os campos.");
        } else if (this.evento == null) {
            createEvento();
        } else {
            updateEvento();
        }
    }

    /**
     * Método para atribuir valores introduzidos ao objeto, para criar ou editar
     */
    private void setValuesToObject() {
        if (isNull(this.evento)) {
            this.evento = new Evento();
        }

        this.evento.setIdJogo(this.selectedJogo.getId());
        this.evento.setIdJogadorJogo(this.comboJogadorJogo.getSelectionModel().getSelectedItem().getNrAtleta());
        this.evento.setTempo(BigDecimal.valueOf(Double.valueOf(this.txtTempo.getText())));
        this.evento.setTipo(this.comboTipoEvento.getSelectionModel().getSelectedItem().getId());
    }

    /**
     * Método de criar
     */
    public void createEvento() {

        setValuesToObject();

        Boolean response = service.create(
                this.evento.getIdJogadorJogo(),
                this.evento.getIdJogo(),
                this.evento.getTempo(),
                this.evento.getTipo()
        );
        if (response) {
            AlertBox alertaConf = new AlertBox(Alert.AlertType.CONFIRMATION, "SUCESSO", "Evento criado com SUCESSO!");

            closeModalView(this.btnGuardar);
            configEventosController.refreshTable();
        } else {
            AlertBox alertaErr = new AlertBox(Alert.AlertType.ERROR, "ERRO", "Não foi possível criar o Evento.");
        }
    }

    /**
     * Método de atualizar
     */
    public void updateEvento() {

        setValuesToObject();

        Boolean response = service.update(
                this.evento.getIdJogadorJogo(),
                this.evento.getIdJogo(),
                this.evento.getTempo(),
                this.evento.getTipo()
        );

        if (response) {
            AlertBox alertaConf = new AlertBox(Alert.AlertType.CONFIRMATION, "SUCESSO", "Evento atualizado com SUCESSO!");

            closeModalView(this.btnGuardar);
            configEventosController.refreshTable();

        } else {
            AlertBox alertaErr = new AlertBox(Alert.AlertType.ERROR, "ERRO", "Não foi possível atualizar o Evento.");
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
