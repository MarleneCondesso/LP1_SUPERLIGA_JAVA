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
public class EquipasComMaisVitorias {
    
    private String nrEpoca;
    private Integer equipa;
    private int quantidade;

    public EquipasComMaisVitorias() {
    }

    public EquipasComMaisVitorias(String nrEpoca, Integer equipa, int quantidade) {
        this.nrEpoca = nrEpoca;
        this.equipa = equipa;
        this.quantidade = quantidade;
    }

    public String getNrEpoca() {
        return nrEpoca;
    }

    public void setNrEpoca(String nrEquipa) {
        this.nrEpoca = nrEquipa;
    }

    public Integer getEquipa() {
        return equipa;
    }

    public void setEquipa(Integer equipa) {
        this.equipa = equipa;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
}
