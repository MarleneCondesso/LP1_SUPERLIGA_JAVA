/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superliga.models;

import java.sql.Timestamp;

/**
 *
 * @author superliga
 */
public class OnzeInicial {

    private String equipa;
    private Integer numeroAtleta;
    private String nome;
    private Integer jogo;
    private Timestamp dataJogo;
    private String posicao;
    private String nomeEquipa;
    private Integer idJogo;
    private String epoca;
    private Integer jornada;

    public OnzeInicial() {

    }

    /*
'Casa/Fora' 		as 'equipa' 
jogador_jogo.nr_atleta 	as 'numeroAtleta'  
jogador.nome 		as 'nome' 
jogador_jogo.id_jogo 	as 'jogo' 
jogo.data_hora_jogo 	as 'dataJogo' 
P.descricao 		as 'posicao' 
equipa.nome 		as 'nomeEquipa' 
jogo.id 		as 'idJogo' 
jogo.nr_epoca 		as 'Epoca' 
jogo.nr_jornada 	as 'Jornada'
     */
    public OnzeInicial(String equipa, Integer numeroAtleta, String nome, Integer jogo, Timestamp dataJogo, String posicao,
            String nomeEquipa, Integer idJogo, String epoca, Integer jornada) {
        this.equipa = equipa;
        this.numeroAtleta = numeroAtleta;
        this.nome = nome;
        this.jogo = jogo;
        this.dataJogo = dataJogo;
        this.posicao = posicao;
        this.nomeEquipa = nomeEquipa;
        this.idJogo = idJogo;
        this.epoca = epoca;
        this.jornada = jornada;
    }

    public String getEquipa() {
        return equipa;
    }

    public void setEquipa(String equipa) {
        this.equipa = equipa;
    }

    public Integer getNumeroAtleta() {
        return numeroAtleta;
    }

    public void setNumeroAtleta(Integer numeroAtleta) {
        this.numeroAtleta = numeroAtleta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getJogo() {
        return jogo;
    }

    public void setJogo(Integer jogo) {
        this.jogo = jogo;
    }

    public Timestamp getDataJogo() {
        return dataJogo;
    }

    public void setDataJogo(Timestamp dataJogo) {
        this.dataJogo = dataJogo;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }

    public String getNomeEquipa() {
        return nomeEquipa;
    }

    public void setNomeEquipa(String nomeEquipa) {
        this.nomeEquipa = nomeEquipa;
    }

    public Integer getIdJogo() {
        return idJogo;
    }

    public void setIdJogo(Integer idJogo) {
        this.idJogo = idJogo;
    }

    public String getEpoca() {
        return epoca;
    }

    public void setEpoca(String epoca) {
        this.epoca = epoca;
    }

    public Integer getJornada() {
        return jornada;
    }

    public void setJornada(Integer jornada) {
        this.jornada = jornada;
    }

}
