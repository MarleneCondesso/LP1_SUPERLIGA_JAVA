/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superliga.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author superliga
 */
public class Jogo {
    private int id;
    private int idEquipaCasa;
    private int idEquipaFora;
    private String nrEpoca;
    private Integer nrJornada;
    private Timestamp dataHora;
    private int intervalo = 45;
    private int duracao = 90;
    private int idEstadio;
    
    
    public Jogo() { }

    public Jogo(int idEquipaCasa, int idEquipaFora, String nrEpoca, int nrJornada, Timestamp dataHora, int intervalo, int duracao, int idEstadio) {
        setIdEquipaCasa(idEquipaCasa);
        setIdEquipaFora(idEquipaFora);
        setNrEpoca(nrEpoca);
        setNrJornada(nrJornada);
        setDataHora(dataHora);
        setIntervalo(intervalo);
        setDuracao(duracao);
        setIdEstadio(idEstadio);
    }
    
    public int getId() {
        return id;
    }

    public int getIdEquipaCasa() {
        return idEquipaCasa;
    }

    public int getIdEquipaFora() {
        return idEquipaFora;
    }

    public String getNrEpoca() {
        return nrEpoca;
    }

    public int getNrJornada() {
        return nrJornada;
    }

    public Timestamp getDataHora() {
        return dataHora;
    }

    public int getIntervalo() {
        return intervalo;
    }

    public int getDuracao() {
        return duracao;
    }

    public int getIdEstadio() {
        return idEstadio;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdEquipaCasa(int idEquipaCasa) {
        this.idEquipaCasa = idEquipaCasa;
    }

    public void setIdEquipaFora(int idEquipaFora) {
        this.idEquipaFora = idEquipaFora;
    }

    public void setNrEpoca(String nrEpoca) {
        this.nrEpoca = nrEpoca;
    }

    public void setNrJornada(int nrJornada) {
        this.nrJornada = nrJornada;
    }

    public void setDataHora(Timestamp dataHora) {
        this.dataHora = dataHora;
    }

    public void setIntervalo(int intervalo) {
        this.intervalo = intervalo;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public void setIdEstadio(int idEstadio) {
        this.idEstadio = idEstadio;
    }
    
}