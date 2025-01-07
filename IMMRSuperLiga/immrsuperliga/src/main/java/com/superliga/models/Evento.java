/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superliga.models;

import java.math.BigDecimal;

/**
 *
 * @author superliga
 */
public class Evento {
    private int idJogadorJogo;
    private int idJogo;
    private BigDecimal tempo;
    private String tipo;
    
    public Evento(){}

    public Evento(int idJogadorJogo, int idJogo, BigDecimal tempo, String tipo) {
        setIdJogadorJogo(idJogadorJogo);
        setIdJogo(idJogo);
        setTempo(tempo);
        setTipo(tipo);
    }
    
    public int getIdJogadorJogo() {
        return idJogadorJogo;
    }

    public int getIdJogo() {
        return idJogo;
    }

    public BigDecimal getTempo() {
        return tempo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setIdJogadorJogo(int idJogadorJogo) {
        this.idJogadorJogo = idJogadorJogo;
    }

    public void setIdJogo(int idJogo) {
        this.idJogo = idJogo;
    }

    public void setTempo(BigDecimal tempo) {
        this.tempo = tempo;
    }

    public void setTipo(String tipo) {
        if(tipo.length() > 2)
            throw new IllegalArgumentException("O tipo de evento não pode ter mais que dois caractéres.");
        
        this.tipo = tipo;
    }
    
    
    
    


}
/*

REATE TABLE Evento (
    id_jogador_jogo 	INTEGER NOT NULL,
	id_jogo 			INTEGER NOT NULL,
	tempo 				DECIMAL(4,2) NOT NULL,
	tipo 				NVARCHAR(5) NOT NULL,
CONSTRAINT fk_evento_jogador_jogo FOREIGN KEY (id_jogador_jogo, id_jogo) REFERENCES jogador_jogo(nr_atleta, id_jogo),
CONSTRAINT fk_evento_tipo_evento FOREIGN KEY (tipo) REFERENCES tipo_evento(id),
CONSTRAINT pk_evento PRIMARY KEY (id_jogador_jogo asc, id_jogo asc, tempo asc)	
)
*/