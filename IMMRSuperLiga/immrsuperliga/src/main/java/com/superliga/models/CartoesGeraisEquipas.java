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
public class CartoesGeraisEquipas {

    private String epoca;
    private String equipa;
    private String cartao;
    private int contagem;

    public CartoesGeraisEquipas() {
    }

    /**
     *
     * @param epoca
     * @param equipa
     * @param cartao
     * @param contagem
     */
    public CartoesGeraisEquipas(String epoca, String equipa, String cartao, int contagem) {
        this.epoca = epoca;
        this.equipa = equipa;
        this.cartao = cartao;
        this.contagem = contagem;
    }

    public String getEpoca() {
        return epoca;
    }

    public void setEpoca(String epoca) {
        this.epoca = epoca;
    }

    public String getEquipa() {
        return equipa;
    }

    public void setEquipa(String equipa) {
        this.equipa = equipa;
    }

    public String getCartao() {
        return cartao;
    }

    public void setCartao(String cartao) {
        this.cartao = cartao;
    }

    public int getContagem() {
        return contagem;
    }

    public void setContagem(int contagem) {
        this.contagem = contagem;
    }

}
