/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superliga.services;

import com.superliga.models.JogadorContrato;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author mstevz
 */
public class JogadorContratoService extends Service {
/**
     * Retorna uma coletanea de registos de Epoca presentes na base de dados.
     * @return 
     */
    public ArrayList<JogadorContrato> getAll(){
        
        ArrayList<JogadorContrato> records = new ArrayList<>();
        
        ArrayList<String> returnValues = new ArrayList(){{
                add("nr_atleta");
                add("id_equipa");
                add("data_entrada_atleta");
                add("data_saida_atleta");
                add("nr_camisola");
        }};
        
        ArrayList< HashMap<String,Object> > data = super.get("SELECT * FROM Jogador_Contrato", null, returnValues);
        
        for(HashMap<String,Object> record :data){
            
            records.add( 
                mapToObject(
                        record.get("nr_atleta"),
                        record.get("id_equipa"),
                        record.get("data_entrada_atleta"),
                        record.get("data_saida_atleta"),
                        record.get("nr_camisola") 
                )
            );
           
        }
        
        return records;
    }
    
    public ArrayList<JogadorContrato> getByTeamId(int idEquipa){
        
        ArrayList<JogadorContrato> records = new ArrayList<>();
        
        String query = "SELECT * FROM Jogador_Contrato WHERE id_equipa = ?";
        
//        if(entrada != null){
//            query += " AND data_entrada_atleta <= ? AND (data_saida_atleta >= ? OR data_saida_atleta IS NULL";
//        }
        
        ArrayList<Object> params = new ArrayList(){{
                add(idEquipa);
        }};
        
        ArrayList<String> returnValues = new ArrayList(){{
                add("nr_atleta");
                add("id_equipa");
                add("data_entrada_atleta");
                add("data_saida_atleta");
                add("nr_camisola");
        }};
        
        ArrayList< HashMap<String,Object> > data = super.get(query, params, returnValues);
        
        for(HashMap<String,Object> record :data){
            
            records.add( 
                mapToObject(
                        record.get("nr_atleta"),
                        record.get("id_equipa"),
                        record.get("data_entrada_atleta"),
                        record.get("data_saida_atleta"),
                        record.get("nr_camisola") 
                )
            );
           
        }
        
        return records;
    }
    
    /**
     * Retorna registo referenciado pelo n�mero de epoca passado por parametro.
     * @param nrEpoca
     * @return 
     */
    public JogadorContrato getByPrimaryKey(int nrAtleta, Date entrada){
        
        JogadorContrato record = null;
        
        String query = "SELECT * "
                        + "FROM Jogador_Contrato "
                        + "WHERE nr_atleta = ? AND "
                              + "data_entrada_atleta = ?";
        
        ArrayList<Object> params = new ArrayList(){{
                add(nrAtleta);
                add(entrada);
        }};
                  
        ArrayList<String> returnValues = new ArrayList(){{
                add("nr_atleta");
                add("id_equipa");
                add("data_entrada_atleta");
                add("data_saida_atleta");
                add("nr_camisola");
        }};
        
        
        ArrayList< HashMap<String,Object> > data = super.get(query, params, returnValues);
        
        // Mapear dados para modelo Epoca
        if(data.size() == 1){

            HashMap<String, Object> dataValue = data.get(0);
            
            record = mapToObject( dataValue.get("nr_atleta"),
                                  dataValue.get("id_equipa"),
                                  dataValue.get("data_entrada_atleta"),
                                  dataValue.get("data_saida_atleta"),
                                  dataValue.get("nr_camisola") );
        }
        
        return record;
    }
    
    public boolean create(int nrAtleta, int idEquipa, Date entrada, Date saida, int nrCamisola) {        
        String query = "Jogador_Contrato (nr_atleta, id_equipa, data_entrada_atleta, data_saida_atleta, nr_camisola)"
                       + "VALUES (?,?,?,?,?)";
        
        ArrayList<Object> params = new ArrayList(){{
                add(nrAtleta);
                add(idEquipa);
                add(entrada);
                add(saida);
                add(nrCamisola);
        }};

        return super.insert(query, params);
    }
    
    public boolean update(int nrAtleta, int idEquipa, Date entrada, Date saida, int nrCamisola) {
        String query = "Epoca SET id_equipa=?, "
                              + " data_entrada_atleta=?, "
                              + " data_saida_atleta=?, "
                              + " nr_camisola=? "
                     + "WHERE nr_atleta=? AND "
                           + "data_entrada_atleta=?";
        
        ArrayList<Object> params = new ArrayList(){{
                add(idEquipa);
                add(entrada);
                add(saida);
                add(nrCamisola);
                add(nrAtleta);
                add(entrada);
        }};
        
        return super.update(query, params);
    }
    
    public boolean deleteByPrimaryKey(int nrAtleta, Date entrada){
        
        String query = "Jogador_Contrato "
                     + "WHERE nr_atleta=? AND "
                           + "data_entrada_atleta=?";
        
        ArrayList<Object> params = new ArrayList(){{
                add(nrAtleta);
                add(entrada);
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
    private JogadorContrato mapToObject(Object athleteNumber, Object teamId, Object dateStart, Object dateEnd, Object shirtNumber) {
        return new JogadorContrato((int)athleteNumber, (int)teamId, (Date)dateStart, (Date)dateEnd, (int)shirtNumber);
    }
}
