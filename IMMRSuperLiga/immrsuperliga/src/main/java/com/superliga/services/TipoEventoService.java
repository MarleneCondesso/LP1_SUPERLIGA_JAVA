/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superliga.services;

import com.superliga.models.TipoEvento;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;

/**
 *
 * @author superliga
 */
public class TipoEventoService extends Service {

    /**
     * Retorna uma coletanea de registos de Tipo Evento presentes na base de
     * dados.
     *
     * @return
     */
    public ArrayList<TipoEvento> getAll() {
        ArrayList<TipoEvento> records = new ArrayList<>();

        ArrayList<String> returnValues = new ArrayList() {
            {
                add("id");
                add("nome");
            }
        };

        ArrayList< HashMap<String, Object>> data = super.get("SELECT * FROM Tipo_Evento", null, returnValues);

        for (HashMap<String, Object> record : data) {

            records.add(
                    mapToTipoEvento(record.get("id"),
                            record.get("nome"))
            );

        }

        return records;

    }

    /**
     * Retorna registo referenciado pelo id do Tipo Evento passado por
     * parametro.
     *
     * @param id
     * @return
     */
    public TipoEvento getByPrimaryKey(String id) {

        TipoEvento record = null;

        String query = "SELECT * "
                + "FROM Tipo_Evento "
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
            }
        };

        ArrayList< HashMap<String, Object>> data = super.get(query, params, returnValues);

        // Mapear dados para modelo Tipo Evento
        if (data.size() == 1) {

            HashMap<String, Object> dataValue = data.get(0);

            record = mapToTipoEvento(
                    dataValue.get("id"),
                    dataValue.get("nome")
            );
        }

        return record;
    }

    public boolean create(String id, String nome) {

        String query = "Tipo_Evento (id, nome) "
                + "VALUES (?,?)";

        ArrayList<Object> params = new ArrayList() {
            {
                add(id);
                add(nome);
            }
        };

        return super.insert(query, params);
    }

    public boolean update(String id, String nome) {
        String query = "Tipo_Evento SET nome=? "
                + "WHERE id= ?";

        ArrayList<Object> params = new ArrayList() {
            {
                add(nome);
                add(id);
            }
        };

        return super.update(query, params);
    }

    public boolean deleteByPrimaryKey(String id) {
        String query = "Tipo_Evento "
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
     * @param tipoEvento
     * @param nome
     * @return
     */
    private TipoEvento mapToTipoEvento(Object tipoEvento, Object nome) {
        TipoEvento e = new TipoEvento();
        e.setId((String) tipoEvento);
        e.setNome((String) nome);
        return e;
    }

}
