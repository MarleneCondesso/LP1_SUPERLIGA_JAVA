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
public class CartoesGeraisJogadores {

    private String epoca;
    private String jogador;
    private String cartao;
    private int contagem;

    public CartoesGeraisJogadores() {
    }

    /**
     *
     * @param nrEpoca
     * @param nomeJogador
     * @param tipoEvento
     * @param quantidade
     */
    public CartoesGeraisJogadores(String nrEpoca, String nomeJogador, String tipoEvento, int quantidade) {
        this.epoca = nrEpoca;
        this.jogador = nomeJogador;
        this.cartao = tipoEvento;
        this.contagem = quantidade;
    }

    public String getNrEpoca() {
        return epoca;
    }

    public void setNrEpoca(String nrEpoca) {
        this.epoca = nrEpoca;
    }

    public String getNomeJogador() {
        return jogador;
    }

    public void setNomeJogador(String nomeJogador) {
        this.jogador = nomeJogador;
    }

    public String getTipoEvento() {
        return cartao;
    }

    public void setTipoEvento(String tipoEvento) {
        this.cartao = tipoEvento;
    }

    public int getQuantidade() {
        return contagem;
    }

    public void setQuantidade(int quantidade) {
        this.contagem = quantidade;
    }

}
