/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superliga.models;

import java.sql.Date;

/**
 *
 * @author superliga
 */
public class Jogador {

    private Integer nrAtleta;
    private String nome;
    private Date dataNasc;

    public Jogador() {

    }

    public Jogador(Integer nrAtleta, String nome, Date dataNasc) {
        this.nrAtleta = nrAtleta;
        this.nome = nome;
        this.dataNasc = dataNasc;
    }

    public Integer getNrAtleta() {
        return nrAtleta;
    }

    public void setNrAtleta(Integer nrAtleta) {
        this.nrAtleta = nrAtleta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(Date dataNasc) {
        this.dataNasc = dataNasc;
    }

}
