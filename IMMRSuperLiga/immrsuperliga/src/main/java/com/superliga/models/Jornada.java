package com.superliga.models;

import java.sql.Date;

/**
 *
 * @author superliga
 */
public class Jornada {

    private String nrEpoca;
    private Integer nrJornada;
    private Date dataInicio;
    private Date dataFim;

    public Jornada() {

    }

    public Jornada(String nrEpoca, Integer nrJornada, Date dataInicio, Date dataFim) {
        this.nrEpoca = nrEpoca;
        this.nrJornada = nrJornada;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public String getNrEpoca() {
        return nrEpoca;
    }

    public void setNrEpoca(String nrEpoca) {
        this.nrEpoca = nrEpoca;
    }

    public Integer getNrJornada() {
        return nrJornada;
    }

    public void setNrJornada(Integer nrJornada) {
        this.nrJornada = nrJornada;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

}
