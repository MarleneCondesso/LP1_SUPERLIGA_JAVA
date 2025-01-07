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
public class Epoca {

    private String nrEpoca;
    private Date inicio;
    private Date fim;

    public Epoca() {

    }

    public Epoca(String nrEpoca, Date dataInicio, Date dataFim) {
        this.nrEpoca = nrEpoca;
        this.inicio = dataInicio;
        this.fim = dataFim;
    }

    public String getNumeroEpoca() {
        return nrEpoca;
    }
    
    public Date getDataInicio() {
        return inicio;
    }
    
    public Date getDataFim() {
        return fim;
    }
    
    public Epoca setNumeroEpoca(String nrEpoca) {
        this.nrEpoca = nrEpoca;
        return this;
    }
    
    public Epoca setDataInicio(Date dataInicio) {
        this.inicio = dataInicio;
        return this;
    }

    public Epoca setDataFim(Date dataFim) {
        this.fim = dataFim;
        return this;
    }

}
