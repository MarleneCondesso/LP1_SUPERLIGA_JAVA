/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superliga.services;

import com.superliga.models.Jornada;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author superliga
 */
public class JornadaService extends Service {

    /**
     * Retorna uma coletanea de registos de Jornada presentes na base de dados.
     *
     * @return
     */
    public ArrayList<Jornada> getAll() {
        ArrayList<Jornada> records = new ArrayList<>();

        ArrayList<String> returnValues = new ArrayList() {
            {
                add("nr_epoca");
                add("nr_jornada");
                add("data_inicio");
                add("data_fim");
            }
        };

        ArrayList< HashMap<String, Object>> data = super.get("SELECT * FROM Jornada", null, returnValues);

        for (HashMap<String, Object> record : data) {

            records.add(
                    mapToJornada(record.get("nr_epoca"),
                            record.get("nr_jornada"),
                            record.get("data_inicio"),
                            record.get("data_fim")
                    )
            );

        }

        return records;

    }

    /**
     * Retorna registo referenciado pelo número de época passado por parametro.
     *
     * @param nrEpoca
     * @return
     */
    public ArrayList<Jornada> getAllByEpoca(String nrEpoca) {

        ArrayList<Jornada> records = new ArrayList<>();

        String query = "SELECT * "
                + "FROM Jornada "
                + "WHERE nr_epoca = ?";

        ArrayList<Object> params = new ArrayList() {
            {
                add(nrEpoca);
            }
        };

        ArrayList<String> returnValues = new ArrayList() {
            {
                add("nr_epoca");
                add("nr_jornada");
                add("data_inicio");
                add("data_fim");
            }
        };

        ArrayList< HashMap<String, Object>> data = super.get(query, params, returnValues);
        for (HashMap<String, Object> record : data) {

            records.add(
                    mapToJornada(record.get("nr_epoca"),
                            record.get("nr_jornada"),
                            record.get("data_inicio"),
                            record.get("data_fim")
                    )
            );

        }

        return records;
    }

    /**
     * Retorna registo referenciado pelo id da Jornada passado por parametro.
     *
     * @param nrEpoca
     * @param nrJornada
     * @return
     */
    public Jornada getByPrimaryKey(String nrEpoca, Integer nrJornada) {

        Jornada record = null;

        String query = "SELECT * "
                + "FROM Jornada "
                + "WHERE nr_epoca = ? and nr_jornada=? ";

        ArrayList<Object> params = new ArrayList() {
            {
                add(nrEpoca);
                add(nrJornada);
            }
        };

        ArrayList<String> returnValues = new ArrayList() {
            {
                add("nr_epoca");
                add("nr_jornada");
                add("data_inicio");
                add("data_fim");
            }
        };

        ArrayList< HashMap<String, Object>> data = super.get(query, params, returnValues);

        // Mapear dados para modelo Jornada
        if (data.size() == 1) {

            HashMap<String, Object> dataValue = data.get(0);

            record = mapToJornada(
                    dataValue.get("nr_epoca"),
                    dataValue.get("nr_jornada"),
                    dataValue.get("data_inicio"),
                    dataValue.get("data_fim")
            );
        }

        return record;
    }

    public boolean create(String nrEpoca, Integer nrJornada, Date dataInicio, Date dataFim) {

        String query = "Jornada (nr_epoca, nr_jornada, data_inicio,data_fim) "
                + "VALUES (?,?,?,?)";

        ArrayList<Object> params = new ArrayList() {
            {
                add(nrEpoca);
                add(nrJornada);
                add(dataInicio);
                add(dataFim);
            }
        };

        return super.insert(query, params);

    }

    public boolean update(String nrEpoca, Integer nrJornada, Date dataInicio, Date dataFim) {

        String query = "Jornada SET data_inicio=?, data_fim=?"
                + " WHERE nr_epoca= ? and nr_jornada=?";

        ArrayList<Object> params = new ArrayList() {
            {
                add(dataInicio);
                add(dataFim);
                add(nrEpoca);
                add(nrJornada);
            }
        };

        return super.update(query, params);

    }

    public boolean deleteByPrimaryKey(String nrEpoca, Integer nrJornada) {

        String query = "Jornada "
                + "WHERE nr_epoca= ? and nr_jornada=?";

        ArrayList<Object> params = new ArrayList() {
            {
                add(nrEpoca);
                add(nrJornada);
            }
        };

        return delete(query, params);
    }

    /**
     *
     * @param nrEpoca
     * @param nrJornada
     * @param dataInicio
     * @param dataFim
     * @return
     */
    private Jornada mapToJornada(Object nrEpoca, Object nrJornada, Object dataInicio, Object dataFim) {
        Jornada e = new Jornada();
        e.setNrEpoca((String) nrEpoca);
        e.setNrJornada((Integer) nrJornada);
        e.setDataInicio((java.sql.Date) dataInicio);
        e.setDataFim((java.sql.Date) dataFim);

        return e;
    }
}
