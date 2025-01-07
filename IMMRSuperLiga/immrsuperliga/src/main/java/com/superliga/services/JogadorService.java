/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superliga.services;

import com.superliga.models.Jogador;
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
public class JogadorService extends Service {

    /**
     * Retorna uma coletanea de registos de Jogador(es) presentes na base de
     * dados.
     *
     * @return
     */
    public ArrayList<Jogador> getAll() {
        ArrayList<Jogador> records = new ArrayList<>();

        ArrayList<String> returnValues = new ArrayList() {
            {
                add("nr_atleta");
                add("nome");
                add("data_nasc");
            }
        };

        ArrayList< HashMap<String, Object>> data = super.get("SELECT * FROM Jogador", null, returnValues);

        for (HashMap<String, Object> record : data) {

            records.add(
                    mapToJogador(record.get("nr_atleta"),
                            record.get("nome"),
                            record.get("data_nasc"))
            );

        }

        return records;

    }

    /**
     * Retorna registo referenciado pelo nr_atleta do Jogador passado por
     * parametro.
     *
     * @param nrAtleta
     * @return
     */
    public Jogador getByPrimaryKey(Integer nrAtleta) {

        Jogador record = null;

        String query = "SELECT * "
                + "FROM Jogador "
                + "WHERE nr_atleta = ?";

        ArrayList<Object> params = new ArrayList() {
            {
                add(nrAtleta);
            }
        };

        ArrayList<String> returnValues = new ArrayList() {
            {
                add("nr_atleta");
                add("nome");
                add("data_nasc");                
            }
        };

        ArrayList< HashMap<String, Object>> data = super.get(query, params, returnValues);

        // Mapear dados para modelo Jogador
        if (data.size() == 1) {

            HashMap<String, Object> dataValue = data.get(0);

            record = mapToJogador(
                    dataValue.get("nr_atleta"),
                    dataValue.get("nome"),
                    dataValue.get("data_nasc")
            );
        }

        return record;
    }

    public boolean create(Integer nrAtleta, String nome, Date dataNasc) {

        String query = "Jogador (nr_atleta, nome, data_nasc) "
                + "VALUES (?,?,?)";

        ArrayList<Object> params = new ArrayList() {
            {
                add(nrAtleta);
                add(nome);
                add(dataNasc);
            }
        };

        return super.insert(query, params);

    }

    public boolean update(Integer nrAtleta, String nome, Date dataNasc) {

        String query = "Jogador SET nome=?, data_nasc=?"
                + " WHERE nr_atleta= ?";

        ArrayList<Object> params = new ArrayList() {
            {
                add(nome);
                add(dataNasc);
                add(nrAtleta);
            }
        };

        return super.update(query, params);

    }

    public boolean deleteByPrimaryKey(Integer nrAtleta) {

        String query = "Jogador "
                + "WHERE nr_atleta= ?";

        ArrayList<Object> params = new ArrayList() {
            {
                add(nrAtleta);
            }
        };

         return delete(query, params);
    }

    /**
     *
     * @param nrAtleta
     * @param nome
     * @param dataNasc
     * @return
     */
    private Jogador mapToJogador(Object nrAtleta, Object nome, Object dataNasc) {
        Jogador e = new Jogador();
        e.setNrAtleta((Integer) nrAtleta);
        e.setNome((String) nome);
        e.setDataNasc((java.sql.Date) dataNasc);

        return e;
    }

}
