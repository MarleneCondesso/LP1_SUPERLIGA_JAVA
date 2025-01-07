/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superliga.services;

import com.superliga.models.JogadorJogo;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author mstevz
 */
public class JogadorJogoService extends Service {
    /**
     * Retorna uma coletanea de registos de Epoca presentes na base de dados.
     * @return 
     */
    public ArrayList<JogadorJogo> getAll(){
        
        ArrayList<JogadorJogo> records = new ArrayList<>();
        
        ArrayList<String> returnValues = new ArrayList(){{
                add("nr_atleta");
                add("id_jogo");
                add("posicao");
        }};
        
        ArrayList< HashMap<String,Object> > data = super.get("SELECT * FROM Jogador_Jogo", null, returnValues);
        
        for(HashMap<String,Object> record :data){
            
            records.add( 
                mapToObject(record.get("nr_atleta"), 
                           record.get("id_jogo"), 
                           record.get("posicao"))
            );
           
        }
        
        return records;
    }
    
    /**
     * Retorna registos referenciado pelo id jogo
     * passado por parametro.
     *
     * @param idJogo
     * @return
     */
    public ArrayList<JogadorJogo> getAllByJogo(int idJogo) {

        ArrayList<JogadorJogo> records = new ArrayList<>();

        String query = "SELECT * "
                + "FROM Jogador_Jogo "
                + "WHERE id_jogo = ?";

        ArrayList<Object> params = new ArrayList() {
            {
                add(idJogo);
            }
        };

        ArrayList<String> returnValues = new ArrayList() {
            {
                add("nr_atleta");
                add("id_jogo");
                add("posicao");
            }
        };

        ArrayList< HashMap<String, Object>> data = super.get(query, params, returnValues);
        for (HashMap<String, Object> record : data) {

            records.add( 
                mapToObject(record.get("nr_atleta"), 
                           record.get("id_jogo"), 
                           record.get("posicao"))
            );

        }

        return records;
    }

    /**
     * Retorna registo referenciado pelo número de atleta e id jogo passado por parametro.
     * @param nrAtleta
     * @param idJogo
     * @return 
     */
    public JogadorJogo getByPrimaryKey(int nrAtleta, int idJogo){
        
        JogadorJogo record = null;
        
        String query = "SELECT * "
                        + "FROM Jogador_Jogo "
                        + "WHERE nr_atleta = ? AND "
                              + "id_jogo = ?";
        
        ArrayList<Object> params = new ArrayList(){{
                add(nrAtleta);
                add(idJogo);
        }};
                  
        ArrayList<String> returnValues = new ArrayList(){{
                add("nr_atleta");
                add("id_jogo");
                add("posicao");
        }};
        
        
        ArrayList< HashMap<String,Object> > data = super.get(query, params, returnValues);
        
        // Mapear dados para modelo Epoca
        if(data.size() == 1){

            HashMap<String, Object> dataValue = data.get(0);
            
            record = mapToObject( 
                    dataValue.get("nr_atleta"), 
                    dataValue.get("id_jogo"), 
                    dataValue.get("posicao") 
            );
        }
        
        return record;
    }
    
    public boolean create(int nrAtleta, int idJogo, String posicao) {
        
        String query = "EXEC usp_RegistarJogadorJogo @nr_atleta = ?, @id_jogo = ?, @posicao = ?";
        
        ArrayList<Object> params = new ArrayList(){{
                add(nrAtleta);
                add(idJogo);
                add(posicao);
        }};

        return super.insert(query, params);
    }
    
    public boolean update(int nrAtleta, int idJogo, String posicao) {
        String query = "Jogador_Jogo SET posicao=? "
                     + "WHERE nr_atleta = ? AND id_jogo = ?";
        
        ArrayList<Object> params = new ArrayList(){{
                add(posicao);
                add(nrAtleta);
                add(idJogo);
        }};
        
        return super.update(query, params);
    }
    
    public boolean deleteByPrimaryKey(int nrAtleta, int idJogo){
        
        String query = "Jogador_Jogo "
                     + "WHERE nr_atleta = ? AND id_jogo = ?";
        
        ArrayList<Object> params = new ArrayList(){{
                add(nrAtleta);
                add(idJogo);
        }};
        
        return delete(query, params);
    }
    
    /** 
     * 
     * @param epoca
     * @param inicio
     * @param fim
     * @return 
     */
    private JogadorJogo mapToObject(Object nrAtleta, Object idJogo, Object posicao) {
        JogadorJogo e = new JogadorJogo();
            
        e.setNrAtleta((int) nrAtleta)
         .setIdJogo((int) idJogo)
         .setPosicao((String) posicao);
        
        return e;
    }
}
