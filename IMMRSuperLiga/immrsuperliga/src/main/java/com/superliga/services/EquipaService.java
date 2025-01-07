/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superliga.services;

import com.superliga.models.Equipa;
import com.superliga.models.EquipasComMaisVitorias;
import java.util.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;

/**
 *
 * @author superliga
 */
public class EquipaService extends Service {

    /**
     * Retorna uma coletanea de registos de Equipa presentes na base de dados.
     *
     * @return
     */
    public ArrayList<Equipa> getAll() {
        ArrayList<Equipa> records = new ArrayList<>();

        ArrayList<String> returnValues = new ArrayList() {
            {
                add("id");
                add("nome");
                add("sigla");
                add("id_estadio");
            }
        };

        ArrayList< HashMap<String, Object>> data = super.get("SELECT * FROM Equipa", null, returnValues);

        for (HashMap<String, Object> record : data) {

            records.add(
                    mapToEquipa(record.get("id"),
                            record.get("nome"),
                            record.get("sigla"),
                            record.get("id_estadio")
                    )
            );

        }

        return records;

    }

    /**
     * Retorna registo referenciado pelo id da Equipa passado por parametro.
     *
     * @param id
     * @return
     */
    public Equipa getByPrimaryKey(Integer id) {

        Equipa record = null;

        String query = "SELECT * "
                + "FROM Equipa "
                + "WHERE id = ?";

        ArrayList<Object> params = new ArrayList() {
            {
                add(id);
            }
        };

        ArrayList<String> returnValues = new ArrayList() {
            {
                add("id");
                add("nome");
                add("sigla");
                add("id_estadio");
            }
        };

        ArrayList< HashMap<String, Object>> data = super.get(query, params, returnValues);

        // Mapear dados para modelo Equipa
        if (data.size() == 1) {

            HashMap<String, Object> dataValue = data.get(0);

            record = mapToEquipa(
                    dataValue.get("id"),
                    dataValue.get("nome"),
                    dataValue.get("sigla"),
                    dataValue.get("id_estadio")
            );
        }

        return record;
    }
    
    public ArrayList<EquipasComMaisVitorias> getTeamWithMoreVictories(String nrEpoca){
        
        String query = "SELECT * "
                + "FROM view_EquipasComMaisVitorias "
                + "WHERE nr_epoca = ?";
        
        ArrayList<Object> params = new ArrayList() {
            {
                add(nrEpoca);
            }
        };
        
        ArrayList<String> returnValues = new ArrayList() {
            {
                add("nr_epoca");
                add("equipa");
                add("QtdVitorias");
            }
        };

        ArrayList< HashMap<String, Object>> data = super.get(query, params, returnValues);
        
        ArrayList<EquipasComMaisVitorias> recordsOut = new ArrayList<>();
        for (HashMap<String, Object> record : data) {

            recordsOut.add(
                    mapToTeamWithMoreVictories(record.get("nr_epoca"),
                                                record.get("equipa"),
                                                record.get("QtdVitorias")
                    )
            );

        }
        
        return recordsOut;
        
    } 
    
    /**
     *
     * @param epoca
     * @param jogador
     * @param cartao
     * @param contagem
     * @return
     */
        private EquipasComMaisVitorias mapToTeamWithMoreVictories(Object epoca, Object equipa, Object qtdVitorias) {
        EquipasComMaisVitorias equipaVitorias = new EquipasComMaisVitorias();

        equipaVitorias.setNrEpoca((String) epoca);
        equipaVitorias.setEquipa((Integer) equipa);
        equipaVitorias.setQuantidade((int) qtdVitorias);
        
        return equipaVitorias;
    }
        
        
    public boolean create(String nome, String sigla, Integer idEstadio) throws SQLException {

        String query = "Equipa (nome, sigla, id_estadio) "
                + "VALUES (?,?,?)";

        ArrayList<Object> params = new ArrayList() {
            {
                add(nome);
                add(sigla);
                add(idEstadio);
            }
        };

        return super.insert(query, params);
    }

    public boolean update(Integer id, String nome, String sigla, Integer idEstadio) {

        String query = "Equipa SET nome=?, sigla=?, id_estadio=? "
                + " WHERE id= ?";

        ArrayList<Object> params = new ArrayList() {
            {
                add(nome);
                add(sigla);
                add(idEstadio);
                add(id);
            }
        };

        return super.update(query, params);

    }

    public boolean deleteByPrimaryKey(Integer id) {

        String query = "Equipa "
                + "WHERE id= ?";

        ArrayList<Object> params = new ArrayList() {
            {
                add(id);
            }
        };

        return delete(query, params);
    }

    /**
     *
     * @param id
     * @param nome
     * @param sigla
     * @param idEstadio
     * @return
     */
    private Equipa mapToEquipa(Object id, Object nome, Object sigla, Object idEstadio) {
        Equipa e = new Equipa();
        e.setId((Integer) id);
        e.setNome((String) nome);
        e.setSigla((String) sigla);
        e.setIdEstadio((Integer) idEstadio);
        return e;
    }
}
