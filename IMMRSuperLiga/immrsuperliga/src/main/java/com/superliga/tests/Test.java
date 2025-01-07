/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superliga.tests;

import com.superliga.models.*;
import com.superliga.services.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author superliga
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int i;
               
        i = 90;
        switch (i) {
            case 10:
                try{
                    System.err.println("###Teste Tipos Evento INICIO:\n");

                    TipoEventoService tipoEventoService = new TipoEventoService();
                    ArrayList<TipoEvento> records =null;
                    // Get Todos Tipos Evento      
                    System.err.println("getAll\n");
                    records = tipoEventoService.getAll();
                    for (int j = 0; j < records.size(); j++) {
                        System.err.println(records.get(j).getId() + "-" + records.get(j).getNome());
                    }

                    System.err.println("\ngetByPrimaryKey EX\n");
                    // Obter Tipo de Evento, pk
                    TipoEvento tipoEvento = tipoEventoService.getByPrimaryKey("EX");
                    System.err.println(tipoEvento.getId() + "-" + tipoEvento.getNome()); 

                    // Criar Tipo de Evento         
                    System.err.println("\ncreate\n");

                    tipoEventoService.create("TT", "Teste");
                    tipoEventoService.create("TI", "Teste I");

                    // Alterar Tipo de Evento
                    System.err.println("update TT\n");
                    tipoEventoService.update("TT", "Teste Upd");

                    //
                    System.err.println("getAll\n");
                    records = tipoEventoService.getAll();
                    for (int j = 0; j < records.size(); j++) {
                        System.err.println(records.get(j).getId() + "-" + records.get(j).getNome());
                    }      

                    // Apagar Tipo de Evento
                    System.err.println("\ndeleteByPrimaryKey : TI \n");
                    boolean deleted=tipoEventoService.deleteByPrimaryKey("TI");
                    System.err.println("deleted \n"+deleted);

                    //
                    System.err.println("getAll\n");
                    records = tipoEventoService.getAll();
                    for (int j = 0; j < records.size(); j++) {
                        System.err.println(records.get(j).getId() + "-" + records.get(j).getNome());
                    }     
                }catch(Exception e){
                    System.err.println("Erro: "+e.toString());
                }
                System.err.println("###Teste Tipos Evento FIM.\n");
                break;    

        case 20:
                try {
                    System.err.println("###Teste Jogador INICIO:\n");
                    JogadorService jogadorService = new JogadorService();
                    ArrayList<Jogador> recordsJogador =null;

                    // Get Jogador  
                    System.err.println("getAll\n");
                    recordsJogador = jogadorService.getAll();
                    for (int j = 0; j < recordsJogador.size(); j++) {
                        System.err.println(recordsJogador.get(j).getNrAtleta()+ "-" + recordsJogador.get(j).getNome()+ "-" + recordsJogador.get(j).getDataNasc());
                    }

                    System.err.println("\ngetByPrimaryKey 200\n");
                    // Obter Jogador, pk
                    Jogador jogador = jogadorService.getByPrimaryKey(200);
                    System.err.println(jogador.getNrAtleta()+ "-" + jogador.getNome()+ "-" + jogador.getDataNasc()); 

                    // Criar Jogador      
                    System.err.println("create\n");
                    Date dt= null;
                    try {
                     dt= new SimpleDateFormat("ddMMyyyy").parse("01012001");

                    } catch (Exception e) {
                    }

                    try{                    
                        jogadorService.create(9999, "Atleta 9999", dt);
                    } catch (Exception e) {
                        System.out.println("ERROR insert Jogador 9999: " + e.toString());
                    }

                    jogadorService.create(8888, "Atleta 8888", dt);

                     // Alterar Jogador
                    System.err.println("update 9999\n");
                    jogadorService.update(9999, "Atleta 9999 Upd", new Date());
                    //
                    System.err.println("getAll\n");
                    recordsJogador = jogadorService.getAll();
                    for (int j = 0; j < recordsJogador.size(); j++) {
                        System.err.println(recordsJogador.get(j).getNrAtleta()+ "-" + recordsJogador.get(j).getNome()+ "-" + recordsJogador.get(j).getDataNasc());
                    }      

                    // Apagar Jogador
                    System.err.println("deleteByPrimaryKey 8888 \n");
                    jogadorService.deleteByPrimaryKey(8888);
                    //
                    System.err.println("getAll\n");
                    recordsJogador = jogadorService.getAll();
                    for (int j = 0; j < recordsJogador.size(); j++) {
                        System.err.println(recordsJogador.get(j).getNrAtleta()+ "-" + recordsJogador.get(j).getNome()+ "-" + recordsJogador.get(j).getDataNasc());
                    }     
                }catch(Exception e){
                    System.err.println("Erro: "+e.toString());
                }
                System.err.println("###Teste Jogador FIM.\n");
                break; 
        case 30:
                try{
                    System.err.println("###Teste Posicao INICIO:\n");
                    PosicaoService posicaoService = new PosicaoService();
                    ArrayList<Posicao> recordsPosicao =null;
 
                    // Get Todas Posicoes     
                    System.err.println("getAll\n");
                    recordsPosicao = posicaoService.getAll();
                    for (int j = 0; j < recordsPosicao.size(); j++) {
                        System.err.println(recordsPosicao.get(j).getId()+ "-" + recordsPosicao.get(j).getDescricao());
                    }

                    System.err.println("\ngetByPrimaryKey GR\n");
                    // Obter Posicao, pk
                    Posicao posicao = posicaoService.getByPrimaryKey("GR");
                    System.err.println(posicao.getId()+ "-" + posicao.getDescricao()); 

                    // Criar Posicao        
                    System.err.println("create\n");

                    posicaoService.create("AA", "Posicao INS AA");
                    posicaoService.create("BB", "Posicao INS BB");

                     // Alterar Posicao
                    System.err.println("update BB\n");
                    posicaoService.update("BB", "Posicao INS BB upd"); 
                    //
                    System.err.println("getAll\n");
                    recordsPosicao = posicaoService.getAll();
                    for (int j = 0; j < recordsPosicao.size(); j++) {
                        System.err.println(recordsPosicao.get(j).getId()+ "-" + recordsPosicao.get(j).getDescricao());
                    }      

                    // Apagar Posicao
                    System.err.println("deleteByPrimaryKey AA \n");
                    posicaoService.deleteByPrimaryKey("AA");
                    //
                    System.err.println("getAll\n");
                    recordsPosicao = posicaoService.getAll();
                    for (int j = 0; j < recordsPosicao.size(); j++) {
                        System.err.println(recordsPosicao.get(j).getId()+ "-" + recordsPosicao.get(j).getDescricao());
                    }     
                }catch(Exception e){
                    System.err.println("Erro: "+e.toString());
                }
                System.err.println("###Teste Posicao FIM.\n");
                break; 
        case 40:
                try {
                    System.err.println("###Teste Pais INICIO:\n");
                    PaisService paisService = new PaisService();
                    ArrayList<Pais> recordsPais =null;

                    // Get Todas Pais     
                    System.err.println("getAll\n");
                    recordsPais = paisService.getAll();
                    for (int j = 0; j < recordsPais.size(); j++) {
                        System.err.println(recordsPais.get(j).getId()+ "-" + recordsPais.get(j).getDescricao());
                    }

                    System.err.println("\ngetByPrimaryKey 41\n");
                    // Obter Pais, pk
                    Pais pais = paisService.getByPrimaryKey(41);
                    System.err.println(pais.getId()+ "-" + pais.getDescricao()); 

                    // Criar Pais        
                    System.err.println("create\n");

                    paisService.create(55,"Brasil");
                    paisService.create(51, "Peru");

                     // Alterar Pais
                    System.err.println("update 51 \n");
                    paisService.update(51, "Peru upd");
                    //
                    System.err.println("getAll\n");
                    recordsPais = paisService.getAll();
                    for (int j = 0; j < recordsPais.size(); j++) {
                        System.err.println(recordsPais.get(j).getId()+ "-" + recordsPais.get(j).getDescricao());
                    }      

                    // Apagar Pais
                    System.err.println("deleteByPrimaryKey 55\n");
                    paisService.deleteByPrimaryKey(55);
                    //
                    System.err.println("getAll\n");
                    recordsPais = paisService.getAll();
                    for (int j = 0; j < recordsPais.size(); j++) {
                        System.err.println(recordsPais.get(j).getId()+ "-" + recordsPais.get(j).getDescricao());
                    }     
                }catch(Exception e){
                    System.err.println("Erro: "+e.toString());
                }
                System.err.println("###Teste Pais FIM.\n");
                break; 
         case 50:
                try {
                    System.err.println("###Teste Localidade INICIO:\n");
                    LocalidadeService localidadeServices = new LocalidadeService();
                    ArrayList<Localidade> recordsLocalidade =null;

                    // Get Todas Localidade     
                    System.err.println("getAll\n");
                    recordsLocalidade = localidadeServices.getAll();
                    for (int j = 0; j < recordsLocalidade.size(); j++) {
                        System.err.println(recordsLocalidade.get(j).getId()+ "-" + recordsLocalidade.get(j).getIdPais()+ "-" + recordsLocalidade.get(j).getDescricao());
                    }

                    System.err.println("\ngetByPrimaryKey 1\n");
                    // Obter Localidade, pk
                    Localidade localidade = localidadeServices.getByPrimaryKey(1);
                    System.err.println(localidade.getId()+ "-" + localidade.getIdPais()+ "-" + localidade.getDescricao()); 

                    // Criar Localidade        
                    System.err.println("create\n");

                    localidadeServices.create(351,"Avintes");
                    localidadeServices.create(351, "Lever");
                    localidadeServices.create(351, "Oliveira do Douro");

                     // Alterar Localidade
                    System.err.println("update 10 \n"); 
                    localidadeServices.update(10, 351,"Crestuma upd");
                    //
                    System.err.println("getAll\n");
                    recordsLocalidade = localidadeServices.getAll();
                    for (int j = 0; j < recordsLocalidade.size(); j++) {
                        System.err.println(recordsLocalidade.get(j).getId()+ "-" + recordsLocalidade.get(j).getIdPais()+ "-" + recordsLocalidade.get(j).getDescricao());
                    }      

                    // Apagar Localidade
                    System.err.println("deleteByPrimaryKey 11\n");
                    localidadeServices.deleteByPrimaryKey(11);
                    //
                    System.err.println("getAll\n");
                    recordsLocalidade = localidadeServices.getAll();
                    for (int j = 0; j < recordsLocalidade.size(); j++) {
                        System.err.println(recordsLocalidade.get(j).getId()+ "-" + recordsLocalidade.get(j).getDescricao());
                    }     
                }catch(Exception e){
                    System.err.println("Erro: "+e.toString());
                }                          
                System.err.println("###Teste Localidade FIM.\n");
                break; 
        case 60:
                try {
                    System.err.println("###Teste Estadio INICIO:\n");
                    EstadioService estadioService = new EstadioService();
                    ArrayList<Estadio> recordsEstadio =null;

                    // Get Todos Estadios     
                    System.err.println("getAll\n");
                    recordsEstadio = estadioService.getAll();
                    for (int j = 0; j < recordsEstadio.size(); j++) {
                        System.err.println(recordsEstadio.get(j).getId()+ "-" + recordsEstadio.get(j).getNome()+ "-" + recordsEstadio.get(j).getIdLocal()+ "-" + recordsEstadio.get(j).getIdPais());
                    }

                    System.err.println("\ngetByPrimaryKey 3\n");
                    // Obter Estadio, pk
                    Estadio estadio = estadioService.getByPrimaryKey(3);
                    System.err.println(estadio.getId()+ "-" + estadio.getNome()+ "-" + estadio.getIdLocal()+ "-" + estadio.getIdPais()); 

                    // Criar Estadio        
                    System.err.println("create\n");

                    estadioService.create("Wankdorf a",7,41);
                    estadioService.create("Wankdorf b",7,41); 

                     // Alterar Estadio
                    System.err.println("update 3 \n"); 
                    estadioService.update(3,"Wankdorf a upd",7,41);
                    //
                    System.err.println("getAll\n");
                    recordsEstadio = estadioService.getAll();
                    for (int j = 0; j < recordsEstadio.size(); j++) {
                        System.err.println(recordsEstadio.get(j).getId()+ "-" + recordsEstadio.get(j).getNome()+ "-" + recordsEstadio.get(j).getIdLocal()+ "-" + recordsEstadio.get(j).getIdPais());
                    }      

                    // Apagar Estadio
                    System.err.println("deleteByPrimaryKey 4\n");
                    estadioService.deleteByPrimaryKey(4);
                    //
                    System.err.println("getAll\n");
                    recordsEstadio = estadioService.getAll();
                    for (int j = 0; j < recordsEstadio.size(); j++) {
                        System.err.println(recordsEstadio.get(j).getId()+ "-" + recordsEstadio.get(j).getNome()+ "-" + recordsEstadio.get(j).getIdLocal()+ "-" + recordsEstadio.get(j).getIdPais());
                    }     
                }catch(Exception e){
                    System.err.println("Erro: "+e.toString());
                }
                System.err.println("###Teste Estadio FIM.\n");
                break; 
        case 70:
                try {
                    System.err.println("###Teste Equipa INICIO:\n");
                    EquipaService equipaService = new EquipaService();
                    ArrayList<Equipa> recordsEquipa =null;
 
                    // Get Todos Equipas     
                    System.err.println("getAll\n");
                    recordsEquipa = equipaService.getAll();
                    for (int j = 0; j < recordsEquipa.size(); j++) {
                        System.err.println(recordsEquipa.get(j).getId()+ "-" + recordsEquipa.get(j).getNome()+ "-" + recordsEquipa.get(j).getSigla()+ "-" + recordsEquipa.get(j).getIdEstadio());
                    }

                    System.err.println("\ngetByPrimaryKey 2\n");
                    // Obter Equipa, pk
                    Equipa equipa = equipaService.getByPrimaryKey(2);
                    System.err.println(equipa.getId()+ "-" + equipa.getNome()+ "-" + equipa.getSigla()+ "-" + equipa.getIdEstadio()); 

                    // Criar Equipa        
                    System.err.println("create\n");

                    equipaService.create("Equipa T a","TA",3);
                    equipaService.create("Equipa T b","TB",3); 

                     // Alterar Equipa
                    System.err.println("update TA \n"); 
                    equipaService.update(4,"Equipa T a UPD ","TA",3);
                    //
                    System.err.println("getAll\n");
                    recordsEquipa = equipaService.getAll();
                    for (int j = 0; j < recordsEquipa.size(); j++) {
                        System.err.println(recordsEquipa.get(j).getId()+ "-" + recordsEquipa.get(j).getNome()+ "-" + recordsEquipa.get(j).getSigla()+ "-" + recordsEquipa.get(j).getIdEstadio());
                    }      
 
                    // Apagar Equipa
                    System.err.println("deleteByPrimaryKey TB\n");
                    equipaService.deleteByPrimaryKey(5);
                    //
                    System.err.println("getAll\n");
                    recordsEquipa = equipaService.getAll();
                    for (int j = 0; j < recordsEquipa.size(); j++) {
                        System.err.println(recordsEquipa.get(j).getId()+ "-" + recordsEquipa.get(j).getNome()+ "-" + recordsEquipa.get(j).getSigla()+ "-" + recordsEquipa.get(j).getIdEstadio());
                    }      
                }catch(Exception e){
                    System.err.println("Erro: "+e.toString());
                }
                System.err.println("###Teste Equipa FIM.\n");
                break; 
        case 80:
                try {
                    System.err.println("###Teste Jornada INICIO:\n");
                    JornadaService jornadaService = new JornadaService();
                    ArrayList<Jornada> recordsJornada =null;
 
                    // Get Todos Jornadas     
                    System.err.println("getAll\n");
                    recordsJornada = jornadaService.getAll();
                    for (int j = 0; j < recordsJornada.size(); j++) {
                        System.err.println(recordsJornada.get(j).getNrEpoca()+ "-" + recordsJornada.get(j).getNrJornada()+ "-" + recordsJornada.get(j).getDataInicio()+ "-" + recordsJornada.get(j).getDataFim());
                    }

                    System.err.println("\ngetByPrimaryKey epoca 2020/2021 e jornada 1:\n");
                    // Obter Jornada, pk
                    Jornada jornada = jornadaService.getByPrimaryKey("2020/2021",1);
                    System.err.println(jornada.getNrEpoca()+ "-" + jornada.getNrJornada()+ "-" + jornada.getDataInicio()+ "-" + jornada.getDataFim()); 

                    // Criar Jornada        
                    System.err.println("create\n");

                    jornadaService.create("2020/2021",5,new Date(), new Date());
                    jornadaService.create("2020/2021",6,new Date(), new Date()); 

                     // Alterar Jornada
                    System.err.println("update epoca 2020/2021 e jornada 5 \n"); 
                      Date dt= null;
                    try {
                     dt= new SimpleDateFormat("ddMMyyyy").parse("31122999");

                    } catch (Exception e) {
                    }
                    jornadaService.update("2020/2021",5,new Date(), dt);
                    //
                    System.err.println("getAll\n");
                    recordsJornada = jornadaService.getAll();
                    for (int j = 0; j < recordsJornada.size(); j++) {
                        System.err.println(recordsJornada.get(j).getNrEpoca()+ "-" + recordsJornada.get(j).getNrJornada()+ "-" + recordsJornada.get(j).getDataInicio()+ "-" + recordsJornada.get(j).getDataFim());
                    }      
 
                    // Apagar Jornada
                    System.err.println("deleteByPrimaryKey epoca 2020/2021 e jornada 6\n");
                    jornadaService.deleteByPrimaryKey("2020/2021",6);
                    //
                    System.err.println("getAll\n");
                    recordsJornada = jornadaService.getAll();
                    for (int j = 0; j < recordsJornada.size(); j++) {
                        System.err.println(recordsJornada.get(j).getNrEpoca()+ "-" + recordsJornada.get(j).getNrJornada()+ "-" + recordsJornada.get(j).getDataInicio()+ "-" + recordsJornada.get(j).getDataFim());
                    }      
                }catch(Exception e){
                    System.err.println("Erro: "+e.toString());
                }
                System.err.println("###Teste Jornada FIM.\n");
                break;           
 
            case 90:
                try {
                    System.err.println("###Teste view_MelhoresMarcadoresGerais INICIO:\n");
                    MelhoresMarcadoresGeraisService mMarcGeraisService = new MelhoresMarcadoresGeraisService();
                    ArrayList<MelhoresMarcadoresGerais> recordsMarcadores =null;
 
                    // Get Todos Melhores Marcadores Gerais     
                    System.err.println("getAll\n");
                    recordsMarcadores = mMarcGeraisService.getAll();
                    for (int j = 0; j < recordsMarcadores.size(); j++) {
                        System.err.println(recordsMarcadores.get(j).getAtletaNr()+ "-" + recordsMarcadores.get(j).getAtletaNome() + "-" + recordsMarcadores.get(j).getEpoca()+ "-" + recordsMarcadores.get(j).getJornada()+ "-" + recordsMarcadores.get(j).getGolos());
                    }

                    System.err.println("\ngetBy epoca 2020/2021 :\n");
                    // Obter por epoca
                    String epoca="2020/2021";
                   recordsMarcadores = mMarcGeraisService.getByEpoca(epoca);
                    for (int j = 0; j < recordsMarcadores.size(); j++) {
                        System.err.println(recordsMarcadores.get(j).getAtletaNr()+ "-" + recordsMarcadores.get(j).getAtletaNome() + "-" + recordsMarcadores.get(j).getEpoca()+ "-" + recordsMarcadores.get(j).getJornada()+ "-" + recordsMarcadores.get(j).getGolos());
                    }
                    System.err.println("\ngetBy epoca e jornada 2020/2021 1 : getByJornada \n");
                    // Obter por epoca e jornada
                    epoca="2020/2021";
                    Integer jornada =1;
                   recordsMarcadores = mMarcGeraisService.getByJornada(epoca,jornada);
                    for (int j = 0; j < recordsMarcadores.size(); j++) {
                        System.err.println(recordsMarcadores.get(j).getAtletaNr()+ "-" + recordsMarcadores.get(j).getAtletaNome() + "-" + recordsMarcadores.get(j).getEpoca()+ "-" + recordsMarcadores.get(j).getJornada()+ "-" + recordsMarcadores.get(j).getGolos());
                    }
 
                    // ###################################### //
                    // Get Todos Melhores Equipas Marcadores
                    ArrayList<MelhoresMarcadoresGeraisEquipas> recordsMarcadoresEquipas =null;
                    System.err.println("Melhores Equipas Marcadores: getAllEquipas\n");
                    recordsMarcadoresEquipas = mMarcGeraisService.getAllEquipas();
                    for (int j = 0; j < recordsMarcadoresEquipas.size(); j++) {
                        System.err.println(recordsMarcadoresEquipas.get(j).getEquipaId()+ "-" + recordsMarcadoresEquipas.get(j).getEquipaNome() + "-" + recordsMarcadoresEquipas.get(j).getEpoca()+ "-" + recordsMarcadoresEquipas.get(j).getJornada()+ "-" + recordsMarcadoresEquipas.get(j).getGolos());
                    }

                    System.err.println("\nMelhores Equipas Marcadores: getByEpocaEquipas epoca 2020/2021 :\n");
                    // Get Todos Melhores Equipas Marcadores Obter por epoca
                    epoca="2020/2021";
                   recordsMarcadoresEquipas = mMarcGeraisService.getByEpocaEquipas(epoca);
                    for (int j = 0; j < recordsMarcadoresEquipas.size(); j++) {
                        System.err.println(recordsMarcadoresEquipas.get(j).getEquipaId()+ "-" + recordsMarcadoresEquipas.get(j).getEquipaNome() + "-" + recordsMarcadoresEquipas.get(j).getEpoca()+ "-" + recordsMarcadoresEquipas.get(j).getJornada()+ "-" + recordsMarcadoresEquipas.get(j).getGolos());
                    }
                    
                    System.err.println("\nMelhores Equipas Marcadores: getByJornadaEquipas epoca e jornada 2020/2021 1 : getByJornada \n");
                    // Obter por epoca e jornada
                    epoca="2020/2021";
                    jornada =1;
                   recordsMarcadoresEquipas = mMarcGeraisService.getByJornadaEquipas(epoca, jornada);
                    for (int j = 0; j < recordsMarcadoresEquipas.size(); j++) {
                        System.err.println(recordsMarcadoresEquipas.get(j).getEquipaId()+ "-" + recordsMarcadoresEquipas.get(j).getEquipaNome() + "-" + recordsMarcadoresEquipas.get(j).getEpoca()+ "-" + recordsMarcadoresEquipas.get(j).getJornada()+ "-" + recordsMarcadoresEquipas.get(j).getGolos());
                    }
                    
                }catch(Exception e){
                    System.err.println("Erro: "+e.toString());
                }
                System.err.println("###Teste view_MelhoresMarcadoresGerais FIM.\n");
                break; 
                
            case 100:
                try {
                    System.err.println("###Teste view_OnzeInicialJogo INICIO:\n");
                    OnzeInicialService onzeInicialService = new OnzeInicialService();
                    ArrayList<OnzeInicial> recordsOnzeI =null;
 
                    // Get Todos Onze Inicial     
                    System.err.println("getAll\n");
                    recordsOnzeI = onzeInicialService.getAll();
                    for (int j = 0; j < recordsOnzeI.size(); j++) {
                        System.err.println(recordsOnzeI.get(j).getEquipa()+ "-" + 
                                recordsOnzeI.get(j).getNumeroAtleta()+ "-" + 
                                recordsOnzeI.get(j).getNome()+ "-" + 
                                recordsOnzeI.get(j).getJogo()+ "-" + 
                                recordsOnzeI.get(j).getDataJogo()+ "-" + 
                                recordsOnzeI.get(j).getPosicao()+ "-" + 
                                recordsOnzeI.get(j).getNomeEquipa()+ "-" + 
                                recordsOnzeI.get(j).getIdJogo()+ "-" + 
                                recordsOnzeI.get(j).getEpoca()+ "-" + 
                                recordsOnzeI.get(j).getJornada()+ "-" 
                                );
                    }

                    System.err.println("\ngetBy epoca 2020/2021 :\n");
                    // Obter por epoca
                    String epoca="2020/2021";
                   recordsOnzeI = onzeInicialService.getByEpoca(epoca);
                    for (int j = 0; j < recordsOnzeI.size(); j++) {
                                System.err.println(recordsOnzeI.get(j).getEquipa()+ "-" + 
                                recordsOnzeI.get(j).getNumeroAtleta()+ "-" + 
                                recordsOnzeI.get(j).getNome()+ "-" + 
                                recordsOnzeI.get(j).getJogo()+ "-" + 
                                recordsOnzeI.get(j).getDataJogo()+ "-" + 
                                recordsOnzeI.get(j).getPosicao()+ "-" + 
                                recordsOnzeI.get(j).getNomeEquipa()+ "-" + 
                                recordsOnzeI.get(j).getIdJogo()+ "-" + 
                                recordsOnzeI.get(j).getEpoca()+ "-" + 
                                recordsOnzeI.get(j).getJornada()+ "-" 
                                );
                    }
                }catch(Exception e){
                    System.err.println("Erro: "+e.toString());
                }
                System.err.println("###Teste view_OnzeInicialJogo FIM.\n");
                break; 
                
            case 110:
                try {
                    System.err.println("###Teste JogoService getAllByEpocaJornada INICIO:\n");
                    JogoService jogoService = new JogoService();
                    ArrayList<Jogo> recordsJogo =null;
 
                    // Get Todos Onze Inicial     
                    System.err.println("getAll\n");
                    recordsJogo = jogoService.getAll();
                    for (int j = 0; j < recordsJogo.size(); j++) {
                        System.err.println(recordsJogo.get(j).getId());
                    }

                    System.err.println("\ngetBy epoca 2020/2021 e Jornada 1:\n");
                    // Obter por epoca
                    String epoca="2020/2021";
                    Integer jornada=1;
                   recordsJogo = jogoService.getAllByEpocaJornada(epoca, jornada);
                    for (int j = 0; j < recordsJogo.size(); j++) {
                                System.err.println(recordsJogo.get(j).getId()
                                );
                    }
                }catch(Exception e){
                    System.err.println("Erro: "+e.toString());
                }
                System.err.println("###Teste JogoService getAllByEpocaJornada FIM.\n");
                break; 
            default:
            // code block
             break;
        }
    }

}
