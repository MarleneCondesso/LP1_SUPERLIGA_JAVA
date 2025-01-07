package com.superliga.controllers;

import com.superliga.models.Equipa;
import com.superliga.models.JogadorJogo;
import com.superliga.models.Jogo;
import com.superliga.models.OnzeInicial;
import com.superliga.models.Posicao;
import com.superliga.services.EquipaService;
import com.superliga.services.JogadorJogoService;
import com.superliga.services.JogadorService;
import com.superliga.services.OnzeInicialService;
import com.superliga.services.PosicaoService;
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
 * @author ruteg
 */
public class ConfigJogadorJogoController implements Initializable {

    @FXML
    private Label lblTitulo;
    @FXML
    private Button btnLimpar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardar;
    @FXML
    private TextField txtInfoJogo;
    @FXML
    private ComboBox<JogadorJogo> comboJogador;
    @FXML
    private ComboBox<Posicao> comboPosicao;

    private Stage stage;
    private String title;
    private Jogo selectedJogo;
    private Equipa selectedEquipa;

    private JogadorJogo jogadorJogo;
    private OnzeInicial jogadorOnzeInicial;

    private ConfigJogadoresJogoController configJogadoresJogoController;
    private JogadorService jogadorService = new JogadorService();
    private JogadorJogoService service = new JogadorJogoService();
    private OnzeInicialService onzeInicialService = new OnzeInicialService();
    private PosicaoService posicaoService = new PosicaoService();
    private EquipaService equipaService = new EquipaService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.txtInfoJogo.setText("");
        /*
        
        DÁ ERROOOOOOO :(((((
        
        this.comboJogador.setConverter(new StringConverter<OnzeInicial>() {
            @Override
            public String toString(OnzeInicial jogador) {

                return jogador.getNumeroAtleta() + " | " + jogador.getNome();
            }

            @Override
            public OnzeInicial fromString(String string) {
                return null;
            }
        });
        ObservableList listJogadores = FXCollections.observableArrayList(onzeInicialService.getAllJogoEquipa(this.selectedJogo.getId(), this.selectedEquipa.getNome()));
        this.comboJogador.getItems().addAll(listJogadores);
         */

        /**
         * ERRADO, mas MELHOR QUE NADA!!!! *
         */
        this.comboJogador.setConverter(new StringConverter<JogadorJogo>() {
            @Override
            public String toString(JogadorJogo jogador) {

                return String.valueOf(jogador.getNrAtleta());
            }

            @Override
            public JogadorJogo fromString(String string) {
                return null;
            }
        });
        ObservableList listJogadores = FXCollections.observableArrayList(service.getAll());
        this.comboJogador.getItems().addAll(listJogadores);

        this.comboPosicao.setConverter(new StringConverter<Posicao>() {
            @Override
            public String toString(Posicao posicao) {

                return posicao.getDescricao();
            }

            @Override
            public Posicao fromString(String string) {
                return null;
            }
        });
        ObservableList listPosicoes = FXCollections.observableArrayList(posicaoService.getAll());
        this.comboPosicao.getItems().addAll(listPosicoes);

    }

    public ConfigJogadorJogoController() {
    }

    /**
     * Método para passar valores para configJogadoresJogosController
     *
     * @param jogadorOnzeInicial
     * @param title
     * @param jogo
     * @param equipa
     * @param configJogadoresJogoController
     */
    public void setView(OnzeInicial jogadorOnzeInicial, String title, Jogo jogo, Equipa equipa, ConfigJogadoresJogoController configJogadoresJogoController) {
        this.configJogadoresJogoController = configJogadoresJogoController;

        this.selectedJogo = jogo;
        this.selectedEquipa = equipa;

        if (!isNull(jogadorOnzeInicial)) {
            this.jogadorJogo = new JogadorJogo();

            this.jogadorJogo.setIdJogo(jogadorOnzeInicial.getIdJogo());
            //this.comboJogador.getSelectionModel().select(service.getByPrimaryKey(jogadorOnzeInicial.getNumeroAtleta(), jogadorOnzeInicial.getIdJogo()).getNrAtleta());
            this.comboJogador.getSelectionModel().select(service.getByPrimaryKey(jogadorOnzeInicial.getNumeroAtleta(), jogadorOnzeInicial.getIdJogo()));
            this.comboPosicao.getSelectionModel().select(posicaoService.getByPrimaryKey(posicaoService.getByDescription(jogadorOnzeInicial.getPosicao()).getId()));

        } else {
            this.jogadorJogo = null;
        }
        setValuesToForm();
        this.lblTitulo.setText(title);
    }

    /**
     * Método de alteração dos valores dos componentes do formulário
     */
    public void setValuesToForm() {

        Equipa selectedCasa = equipaService.getByPrimaryKey(this.selectedJogo.getIdEquipaCasa());
        Equipa selectedFora = equipaService.getByPrimaryKey(this.selectedJogo.getIdEquipaFora());

        String textoJogo = "";
        textoJogo += selectedCasa.getSigla();
        textoJogo += " VS " + selectedFora.getSigla();
        textoJogo += "  |  " + this.selectedJogo.getDataHora().toString();

        textoJogo += this.selectedJogo.getIdEquipaCasa();

        this.txtInfoJogo.setText(textoJogo);
    }

    /**
     * Método para limpar os campos
     */
    @FXML
    private void cleanFields() {
        this.comboJogador.getSelectionModel().clearSelection();
        this.comboPosicao.getSelectionModel().clearSelection();
    }

    /**
     * Método para sair da janela
     *
     * @param event
     */
    @FXML
    private void cancel(MouseEvent event) {
        if (this.comboJogador.getSelectionModel().getSelectedItem() != null
                || this.comboPosicao.getSelectionModel().getSelectedItem() != null) {
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

        if (this.comboJogador.getSelectionModel().getSelectedItem() == null
                || this.comboPosicao.getSelectionModel().getSelectedItem() == null) {
            AlertBox alerta = new AlertBox(Alert.AlertType.ERROR, "Erro", "Por favor, preencha todos os campos.");
        } else if (this.jogadorJogo == null) {
            createJogadorJogo();
        } else {
            updateJogadorJogo();
        }
    }

    /**
     * Método para atribuir valores introduzidos ao objeto, para criar ou editar
     */
    private void setValuesToObject() {

        if (isNull(this.jogadorJogo)) {
            this.jogadorJogo = new JogadorJogo();
        }

        this.jogadorJogo.setIdJogo(this.selectedJogo.getId());
        // this.jogadorJogo.setNrAtleta(this.comboJogador.getSelectionModel().getSelectedItem().getNumeroAtleta());
        this.jogadorJogo.setNrAtleta(this.comboJogador.getSelectionModel().getSelectedItem().getNrAtleta());
        this.jogadorJogo.setPosicao(this.comboPosicao.getSelectionModel().getSelectedItem().getId());
    }

    /**
     * Método de criar
     */
    public void createJogadorJogo() {

        setValuesToObject();

        Boolean response = service.create(
                this.jogadorJogo.getNrAtleta(),
                this.jogadorJogo.getIdJogo(),
                this.jogadorJogo.getPosicao()
        );
        if (response) {
            AlertBox alertaConf = new AlertBox(Alert.AlertType.CONFIRMATION, "SUCESSO", "Jogador do Jogo criado com SUCESSO!");

            closeModalView(this.btnGuardar);
            configJogadoresJogoController.refreshTables();
        } else {
            AlertBox alertaErr = new AlertBox(Alert.AlertType.ERROR, "ERRO", "Não foi possível criar o Jogador do Jogo.");
        }
    }

    /**
     * Método de atualizar
     */
    public void updateJogadorJogo() {

        setValuesToObject();

        Boolean response = service.update(
                this.jogadorJogo.getNrAtleta(),
                this.jogadorJogo.getIdJogo(),
                this.jogadorJogo.getPosicao()
        );

        if (response) {
            AlertBox alertaConf = new AlertBox(Alert.AlertType.CONFIRMATION, "SUCESSO", "Jogador do Jogo atualizado com SUCESSO!");

            closeModalView(this.btnGuardar);
            configJogadoresJogoController.refreshTables();

        } else {
            AlertBox alertaErr = new AlertBox(Alert.AlertType.ERROR, "ERRO", "Não foi possível atualizar o Jogador do Jogo.");
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
