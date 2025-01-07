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
public class JogadorContrato {
    private int nrAtleta;
    private int idEquipa;
    private Date entrada;
    private Date saida;
    private int nrCamisola;

    public JogadorContrato(){
        
    }
    
    public JogadorContrato(int athleteNumber, int teamId, Date entrada, Date saida, int shirtNumber) {
        
        setNrAtelta(athleteNumber);
        setIdEquipa(teamId);
        setEntrada(entrada);
        setSaida(saida);
        setNrCamisola(shirtNumber);
        this.nrAtleta = athleteNumber;
        this.idEquipa = teamId;
        this.entrada = entrada;
        this.saida = saida;
        this.nrCamisola = shirtNumber;
    }
    
    

    public int getNrAtleta() {
        return nrAtleta;
    }

    public int getIdEquipa() {
        return idEquipa;
    }

    public Date getEntrada() {
        return entrada;
    }

    public Date getSaida() {
        return saida;
    }

    public int getNrCamisola() {
        return nrCamisola;
    }

    public JogadorContrato setNrAtelta(int athleteNumber) {
        this.nrAtleta = athleteNumber;
        return this;
    }

    public JogadorContrato setIdEquipa(int teamId) {
        this.idEquipa = teamId;
        return this;
    }

    public JogadorContrato setEntrada(Date entrada) {
        this.entrada = entrada;
        return this;
    }

    public JogadorContrato setSaida(Date saida) {
        this.saida = saida;
        return this;
    }

    public JogadorContrato setNrCamisola(int shirtNumber) {
        this.nrCamisola = shirtNumber;
        return this;
    }
        
}