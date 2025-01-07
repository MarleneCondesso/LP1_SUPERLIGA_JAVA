/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superliga.services;

import com.superliga.models.Epoca;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;

/**
 *
 * @author superliga
 */
public class EpocaService extends Service {
    
    /**
     * Retorna uma coletanea de registos de Epoca presentes na base de dados.
     * @return 
     */
    public ArrayList<Epoca> getAll(){
        
        ArrayList<Epoca> records = new ArrayList<>();
        
        ArrayList<String> returnValues = new ArrayList(){{
                add("nr_epoca");
                add("data_inicio");
                add("data_fim");
        }};
        
        ArrayList< HashMap<String,Object> > data = super.get("SELECT * FROM Epoca", null, returnValues);
        
        for(HashMap<String,Object> record :data){
            
            records.add( 
                mapToEpoca(record.get("nr_epoca"), 
                           record.get("data_inicio"), 
                           record.get("data_fim"))
            );
           
        }
        
        return records;
    }
    
    /**
     * Retorna registo referenciado pelo número de epoca passado por parametro.
     * @param nrEpoca
     * @return 
     */
    public Epoca getByPrimaryKey(String nrEpoca){
        
        Epoca record = null;
        
        String query = "SELECT * "
                        + "FROM Epoca "
                        + "WHERE nr_epoca = ?";
        
        ArrayList<Object> params = new ArrayList(){{
                add(nrEpoca);
        }};
                  
        ArrayList<String> returnValues = new ArrayList(){{
                add("nr_epoca");
                add("data_inicio");
                add("data_fim");
        }};
        
        
        ArrayList< HashMap<String,Object> > data = super.get(query, params, returnValues);
        
        // Mapear dados para modelo Epoca
        if(data.size() == 1){

            HashMap<String, Object> dataValue = data.get(0);
            
            record = mapToEpoca( 
                    dataValue.get("nr_epoca"), 
                    dataValue.get("data_inicio"), 
                    dataValue.get("data_fim") 
            );
        }
        
        return record;
    }
    
    public boolean create(String nrEpoca, Date dataInicio, Date dataFim) {
        
        String query = "Epoca (nr_epoca, data_inicio, data_fim) "
                       + "VALUES (?,?,?)";
        
        ArrayList<Object> params = new ArrayList(){{
                add(nrEpoca);
                add(dataInicio);
                add(dataFim);
        }};

        return super.insert(query, params);
    }
    
    public boolean update(String nrEpoca, Date dataInicio, Date dataFim) {
        String query = "Epoca SET data_inicio=?,"
                              + " data_fim=? "
                     + "WHERE nr_epoca= ?";
        
        ArrayList<Object> params = new ArrayList(){{
                add(dataInicio);
                add(dataFim);
                add(nrEpoca);
        }};
        
        return super.update(query, params);
    }
    
    public boolean deleteByPrimaryKey(String nrEpoca){
        
        String query = "Epoca "
                     + "WHERE nr_epoca= ?";
        
        ArrayList<Object> params = new ArrayList(){{
                add(nrEpoca);
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
    private Epoca mapToEpoca(Object epoca, Object inicio, Object fim) {
        Epoca e = new Epoca();
            
        e.setNumeroEpoca((String)epoca)
         .setDataInicio( (Date)inicio )
         .setDataFim(    (Date)fim    );
        
        return e;
    }
    
}
