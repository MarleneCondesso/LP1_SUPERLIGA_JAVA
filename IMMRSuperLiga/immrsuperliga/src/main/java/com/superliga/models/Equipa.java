/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superliga.models;

/**
 *
 * @author superliga
 */
public class Equipa {

    private Integer id;
    private String nome;
    private String sigla;
    private Integer idEstadio;

    public Equipa() {

    }

    public Equipa(Integer id, String nome, String sigla, Integer idEstadio) {
        this.id = id;
        this.nome = nome;
        this.sigla = sigla;
        this.idEstadio = idEstadio;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Integer getIdEstadio() {
        return idEstadio;
    }

    public void setIdEstadio(Integer idEstadio) {
        this.idEstadio = idEstadio;
    }

}
