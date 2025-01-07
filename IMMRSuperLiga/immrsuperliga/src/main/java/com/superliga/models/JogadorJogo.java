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
public class JogadorJogo {
    private int nrAtleta;
    private int idJogo;
    private String posicao;
    
    public JogadorJogo() { }

    public int getNrAtleta() {
        return nrAtleta;
    }

    public int getIdJogo() {
        return idJogo;
    }

    public String getPosicao() {
        return posicao;
    }

    public JogadorJogo setNrAtleta(int nrAtleta) {
        this.nrAtleta = nrAtleta;
        return this;
    }

    public JogadorJogo setIdJogo(int idJogo) {
        this.idJogo = idJogo;
        return this;
    }

    public JogadorJogo setPosicao(String posicao) {
        
        if(posicao.length() > 10)
            throw new IllegalArgumentException("O valor de \"Posição\" não pode conter mais que 10 caractéres.");
        
        this.posicao = posicao;
        
        return this;
    }
    
    
}