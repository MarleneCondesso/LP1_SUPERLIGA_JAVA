/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superliga.models;

/**
 *
 * @author 35191
 */
public class MelhoresMarcadoresGeraisEquipas {

    private int equipaId;
    private String equipaNome;
    private String epoca;
    private int jornada;
    private int golo;

    public MelhoresMarcadoresGeraisEquipas() {
    }

    public MelhoresMarcadoresGeraisEquipas(int equipaId, String equipaNome, String epoca, int jornada, int golo) {
        this.equipaId = equipaId;
        this.equipaNome = equipaNome;
        this.epoca = epoca;
        this.jornada = jornada;
        this.golo = golo;
    }

    public int getEquipaId() {
        return equipaId;
    }

    public void setEquipaId(int equipaId) {
        this.equipaId = equipaId;
    }

    public String getEquipaNome() {
        return equipaNome;
    }

    public void setEquipaNome(String equipaNome) {
        this.equipaNome = equipaNome;
    }

    public String getEpoca() {
        return epoca;
    }

    public void setEpoca(String epoca) {
        this.epoca = epoca;
    }

    public int getJornada() {
        return jornada;
    }

    public void setJornada(int jornada) {
        this.jornada = jornada;
    }

    public int getGolos() {
        return golo;
    }

    public void setGolos(int golo) {
        this.golo = golo;
    }

}
