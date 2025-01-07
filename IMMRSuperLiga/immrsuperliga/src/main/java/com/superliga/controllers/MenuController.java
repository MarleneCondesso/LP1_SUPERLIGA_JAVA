package com.superliga.controllers;

import com.superliga.App;
import com.superliga.utils.AlertBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author 35191
 */
public class MenuController implements Initializable {

    @FXML
    private Button btnSair;

    @FXML
    private StackPane stackMenu;

    @FXML
    private StackPane stackMenuSec;

    @FXML
    private VBox vboxMenu;

    @FXML
    private Button btnJornadas;

    @FXML
    private Button btnEpocas;

    @FXML
    private Button btnJogos;

    @FXML
    private Button btnEquipas;

    @FXML
    private Button btnEstatisticas;

    @FXML
    private Button btnConfiguracoes;

    @FXML
    public StackPane stackPrincipal;

    @FXML
    private ImageView img;

    Button btnVoltar = new Button("Menu Principal");

    Button btnConfigPaises = new Button("Países");
    Button btnConfigTipoPosicoes = new Button("Tipos de Posições");
    Button btnConfigTipoEventos = new Button("Tipos de Eventos");
    Button btnConfigEstadios = new Button("Estádios");
    Button btnConfigLocalidades = new Button("Localidade");
    Button btnConfigJogadores = new Button("Jogadores");
    Button btnConfigEpocas = new Button("Épocas");
    Button btnConfigEquipas = new Button("Equipas");
    Button btnConfigJogos = new Button("Jogos");
    Button btnConfigJornadas = new Button("Jornadas");
    Button btnConfigJogadoresEquipa = new Button("Jogadores Equipa");
    Button btnConfigJogadoresJogo = new Button("Jogadores Jogo");
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        acoesBotoesConfig();
    }

    @FXML
    void actionBtnConfig(ActionEvent event) {
        stackMenuSec.toFront();
        fillMenuConfig();
    }

    @FXML
    void botaoSair(ActionEvent event) {
        AlertBox alerta = new AlertBox(Alert.AlertType.CONFIRMATION, "Sair da Aplicação", "Tem a certeza que deseja sair da Aplicação?");

        if (alerta.getButton().get() == ButtonType.OK) {
            ((Stage) btnSair.getScene().getWindow()).close();
        } else {
            alerta.getAlert().close();
        }
    }

    /**
     * Configuração de Menu Inicial
     */
    private void fillMenuConfig() {
        configButtons();
        VBox vboxMenu = new VBox();
        vboxMenu.getChildren().addAll(btnVoltar, btnConfigEpocas, btnConfigJornadas, btnConfigJogos, btnConfigEquipas, btnConfigEstadios, btnConfigPaises, btnConfigLocalidades, btnConfigJogadores, btnConfigJogadoresEquipa, btnConfigJogadoresJogo, btnConfigTipoEventos, btnConfigTipoPosicoes);
        vboxMenu.setAlignment(Pos.TOP_CENTER);
        vboxMenu.setSpacing(15);
        vboxMenu.setStyle("-fx-padding: 12px;\n"
                + "    -fx-border-insets: 12px;\n"
                + "    -fx-background-insets: 12px;");
        stackMenuSec.getChildren().setAll(vboxMenu);

    }

    public void acoesBotoesConfig() {
        btnVoltar.setOnMouseClicked((MouseEvent event) -> {
            stackMenuSec.getChildren().clear();
            stackPrincipal.getChildren().setAll(img);
            stackMenuSec.toBack();
            stackMenu.toFront();
        });

        btnConfigEpocas.setOnMouseClicked((MouseEvent e) -> {
            try {
                stackPrincipal.getChildren().setAll(App.loadFXML("configEpocas"));
            } catch (IOException ex) {
                System.out.println("Erro: " + ex);
            }
        });

        btnConfigJornadas.setOnMouseClicked((MouseEvent e) -> {
            try {
                stackPrincipal.getChildren().setAll(App.loadFXML("configJornadas"));
            } catch (IOException ex) {
                System.out.println("Erro: " + ex);
            }
        });

        btnConfigEquipas.setOnMouseClicked((MouseEvent e) -> {
            try {
                stackPrincipal.getChildren().setAll(App.loadFXML("configEquipas"));
            } catch (IOException ex) {
                System.out.println("Erro: " + ex);
            }
        });

        btnConfigEstadios.setOnMouseClicked((MouseEvent e) -> {
            try {
                stackPrincipal.getChildren().setAll(App.loadFXML("configEstadios"));
            } catch (IOException ex) {
                System.out.println("Erro: " + ex);
            }
        });

        btnConfigJogadores.setOnMouseClicked((MouseEvent e) -> {
            try {
                stackPrincipal.getChildren().setAll(App.loadFXML("configJogadores"));
            } catch (IOException ex) {
                System.out.println("Erro: " + ex);
            }
        });
        
        btnConfigJogadoresEquipa.setOnMouseClicked((MouseEvent e) -> {
            try {
                stackPrincipal.getChildren().setAll(App.loadFXML("configJogadoresEquipa"));
            } catch (IOException ex) {
                System.out.println("Erro: " + ex);
            }
        });
        
        btnConfigJogadoresJogo.setOnMouseClicked((MouseEvent e) -> {
            try {
                stackPrincipal.getChildren().setAll(App.loadFXML("configJogadoresJogo"));
            } catch (IOException ex) {
                System.out.println("Erro: " + ex);
            }
        });
        
        btnConfigJogos.setOnMouseClicked((MouseEvent e) -> {
            try {
                stackPrincipal.getChildren().setAll(App.loadFXML("configJogos"));
            } catch (IOException ex) {
                System.out.println("Erro: " + ex);
            }
        });

        btnConfigLocalidades.setOnMouseClicked((MouseEvent e) -> {
            try {
                stackPrincipal.getChildren().setAll(App.loadFXML("configLocalidades"));
            } catch (IOException ex) {
                System.out.println("Erro: " + ex);
            }
        });

        btnConfigPaises.setOnMouseClicked((MouseEvent e) -> {
            try {
                stackPrincipal.getChildren().setAll(App.loadFXML("configPaises"));
            } catch (IOException ex) {
                System.out.println("Erro: " + ex);
            }
        });

        btnConfigTipoPosicoes.setOnMouseClicked((MouseEvent e) -> {
            try {
                stackPrincipal.getChildren().setAll(App.loadFXML("configTipoPosicoes"));
            } catch (IOException ex) {
                System.out.println("Erro: " + ex);
            }
        });

        btnConfigTipoEventos.setOnMouseClicked((MouseEvent e) -> {
            try {
                stackPrincipal.getChildren().setAll(App.loadFXML("configTipoEventos"));
            } catch (IOException ex) {
                System.out.println("Erro: " + ex);
            }
        });
    }

    @FXML
    private void actionBtnEpocas(ActionEvent e) {
        System.out.println("olaaaa");
        try {
            stackPrincipal.getChildren().setAll(App.loadFXML("epocas"));
        } catch (IOException ex) {
            System.out.println("Erro: " + ex);
        }
    }

    @FXML
    private void actionBtnJornadas(ActionEvent e) {
        try {
            stackPrincipal.getChildren().setAll(App.loadFXML("jornadas"));
        } catch (IOException ex) {
            System.out.println("Erro: " + ex);
        }
    }

    @FXML
    private void actionBtnJogos(ActionEvent e) {
        try {
            stackPrincipal.getChildren().setAll(App.loadFXML("jogos"));
        } catch (IOException ex) {
            System.out.println("Erro: " + ex);
        }
    }

    @FXML
    private void actionBtnEquipas(ActionEvent e) {
        try {
            stackPrincipal.getChildren().setAll(App.loadFXML("equipas"));
        } catch (IOException ex) {
            System.out.println("Erro: " + ex);
        }
    }

    @FXML
    private void actionBtnEventos(ActionEvent e) {
        try {
            stackPrincipal.getChildren().setAll(App.loadFXML("eventos"));
        } catch (IOException ex) {
            System.out.println("Erro: " + ex);
        }
    }

    @FXML
    private void actionBtnEstatisticas(ActionEvent e) {
        try {
            stackPrincipal.getChildren().setAll(App.loadFXML("estatisticas"));
        } catch (IOException ex) {
            System.out.println("Erro: " + ex);
        }
    }

    @FXML
    private void actionBtnClassificacoes(ActionEvent e) {
        try {
            stackPrincipal.getChildren().setAll(App.loadFXML("classificacoes"));
        } catch (IOException ex) {
            System.out.println("Erro: " + ex);
        }
    }

    /**
     * Método de configuração de aspeto dos Botões dos Menus.
     */
    public void configButtons() {

        btnVoltar.setCursor(Cursor.HAND);
        btnConfigEpocas.setCursor(Cursor.HAND);
        btnConfigEquipas.setCursor(Cursor.HAND);
        btnConfigEstadios.setCursor(Cursor.HAND);
        btnConfigJogadores.setCursor(Cursor.HAND);
        btnConfigJogos.setCursor(Cursor.HAND);
        btnConfigJornadas.setCursor(Cursor.HAND);
        btnConfigLocalidades.setCursor(Cursor.HAND);
        btnConfigPaises.setCursor(Cursor.HAND);
        btnConfigTipoPosicoes.setCursor(Cursor.HAND);
        btnConfigTipoEventos.setCursor(Cursor.HAND);
        btnConfigJogadoresEquipa.setCursor(Cursor.HAND);
        btnConfigJogadoresJogo.setCursor(Cursor.HAND);
        
        btnVoltar.setPrefWidth(220);
        btnConfigEpocas.setPrefWidth(220);
        btnConfigEquipas.setPrefWidth(220);
        btnConfigEstadios.setPrefWidth(220);
        btnConfigJogadores.setPrefWidth(220);
        btnConfigJogos.setPrefWidth(220);
        btnConfigTipoPosicoes.setPrefWidth(220);
        btnConfigPaises.setPrefWidth(220);
        btnConfigLocalidades.setPrefWidth(220);
        btnConfigJornadas.setPrefWidth(220);
        btnConfigTipoEventos.setPrefWidth(220);
        btnConfigJogadoresEquipa.setPrefWidth(220);
        btnConfigJogadoresJogo.setPrefWidth(220);
        
        btnVoltar.setPrefHeight(45);
        btnConfigEpocas.setPrefHeight(45);
        btnConfigEstadios.setPrefHeight(45);
        btnConfigEquipas.setPrefHeight(45);
        btnConfigJogadores.setPrefHeight(45);
        btnConfigTipoEventos.setPrefHeight(45);
        btnConfigTipoPosicoes.setPrefHeight(45);
        btnConfigPaises.setPrefHeight(45);
        btnConfigLocalidades.setPrefHeight(45);
        btnConfigJornadas.setPrefHeight(45);
        btnConfigJogos.setPrefHeight(45);
        btnConfigJogadoresEquipa.setPrefHeight(45);
        btnConfigJogadoresJogo.setPrefHeight(45);
        
        btnVoltar.setStyle("-fx-background-color: #D9E8F6; -fx-background-radius: 80; -fx-border-color: #3F5D77; -fx-border-radius: 80; -fx-border-width:5;");
        btnConfigEpocas.setStyle("-fx-background-color: #B2C5E7; -fx-background-radius: 80; -fx-border-color: #3F5D77; -fx-border-radius: 80; -fx-border-width:5;");
        btnConfigEquipas.setStyle("-fx-background-color:  #B2C5E7; -fx-background-radius: 80; -fx-border-color: #3F5D77; -fx-border-radius: 80; -fx-border-width:5;");
        btnConfigEstadios.setStyle("-fx-background-color:  #B2C5E7; -fx-background-radius: 80; -fx-border-color: #3F5D77; -fx-border-radius: 80; -fx-border-width:5;");
        btnConfigJogos.setStyle("-fx-background-color:  #B2C5E7; -fx-background-radius: 80; -fx-border-color: #3F5D77; -fx-border-radius: 80; -fx-border-width:5;");
        btnConfigJogadores.setStyle("-fx-background-color:  #B2C5E7; -fx-background-radius: 80; -fx-border-color: #3F5D77; -fx-border-radius: 80; -fx-border-width:5;");
        btnConfigTipoPosicoes.setStyle("-fx-background-color:  #B2C5E7; -fx-background-radius: 80; -fx-border-color: #3F5D77; -fx-border-radius: 80; -fx-border-width:5;");
        btnConfigPaises.setStyle("-fx-background-color:  #B2C5E7; -fx-background-radius: 80; -fx-border-color: #3F5D77; -fx-border-radius: 80; -fx-border-width:5;");
        btnConfigLocalidades.setStyle("-fx-background-color:  #B2C5E7; -fx-background-radius: 80; -fx-border-color: #3F5D77; -fx-border-radius: 80; -fx-border-width:5;");
        btnConfigJornadas.setStyle("-fx-background-color: #B2C5E7; -fx-background-radius: 80; -fx-border-color: #3F5D77; -fx-border-radius: 80; -fx-border-width:5;");
        btnConfigTipoEventos.setStyle("-fx-background-color:  #B2C5E7; -fx-background-radius: 80; -fx-border-color: #3F5D77; -fx-border-radius: 80; -fx-border-width:5;");
        btnConfigJogadoresEquipa.setStyle("-fx-background-color: #B2C5E7; -fx-background-radius: 80; -fx-border-color: #3F5D77; -fx-border-radius: 80; -fx-border-width:5;");
        btnConfigJogadoresJogo.setStyle("-fx-background-color:  #B2C5E7; -fx-background-radius: 80; -fx-border-color: #3F5D77; -fx-border-radius: 80; -fx-border-width:5;");

    }

}
