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
public class MelhoresMarcadoresGerais {

    private Integer atletaNr;
    private String atletaNome;
    private String epoca;
    private Integer jornada;
    private Integer golo;

    public MelhoresMarcadoresGerais() {

    }

    /**
     * carrega valores
     *
     * @param atletaNr
     * @param atletaNome
     * @param epoca
     * @param jornada
     * @param golo
     */
    public MelhoresMarcadoresGerais(Integer atletaNr, String atletaNome, String epoca, Integer jornada, Integer golo) {
        this.atletaNr = atletaNr;
        this.atletaNome = atletaNome;
        this.epoca = epoca;
        this.jornada = jornada;
        this.golo = golo;
    }

    public Integer getAtletaNr() {
        return atletaNr;
    }

    public void setAtletaNr(Integer atletaNr) {
        this.atletaNr = atletaNr;
    }

    public String getAtletaNome() {
        return atletaNome;
    }

    public void setAtletaNome(String atletaNome) {
        this.atletaNome = atletaNome;
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

    public Integer getGolos() {
        return golo;
    }

    public void setGolos(Integer golo) {
        this.golo = golo;
    }

}
