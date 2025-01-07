/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superliga.services;

import com.superliga.models.Pais;
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
public class PaisService extends Service {

    /**
     * Retorna uma coletanea de registos de Pais presentes na base de dados.
     *
     * @return
     */
    public ArrayList<Pais> getAll() {
        ArrayList<Pais> records = new ArrayList<>();

        ArrayList<String> returnValues = new ArrayList() {
            {
                add("id");
                add("descricao");
            }
        };

        ArrayList< HashMap<String, Object>> data = super.get("SELECT * FROM Pais", null, returnValues);

        for (HashMap<String, Object> record : data) {

            records.add(
                    mapToPais(record.get("id"),
                            record.get("descricao"))
            );

        }

        return records;

    }

    /**
     * Retorna registo referenciado pelo id da Pais passado por parametro.
     *
     * @param id
     * @return
     */
    public Pais getByPrimaryKey(Integer id) {

        Pais record = null;

        String query = "SELECT * "
                + "FROM Pais "
                + "WHERE id = ?";

        ArrayList<Object> params = new ArrayList() {
            {
                add(id);
            }
        };

        ArrayList<String> returnValues = new ArrayList() {
            {
                add("id");
                add("descricao");
            }
        };

        ArrayList< HashMap<String, Object>> data = super.get(query, params, returnValues);

        // Mapear dados para modelo Pais
        if (data.size() == 1) {

            HashMap<String, Object> dataValue = data.get(0);

            record = mapToPais(
                    dataValue.get("id"),
                    dataValue.get("descricao")
            );
        }

        return record;
    }

    public boolean create(Integer id, String descricao) {

        String query = "Pais (id, descricao) "
                + "VALUES (?,?)";

        ArrayList<Object> params = new ArrayList() {
            {
                add(id);
                add(descricao);
            }
        };

        return super.insert(query, params);

    }

    public boolean update(Integer id, String descricao) {

        String query = "Pais SET descricao=?"
                + " WHERE id= ?";

        ArrayList<Object> params = new ArrayList() {
            {
                add(descricao);
                add(id);
            }
        };

        return super.update(query, params);

    }

    public boolean deleteByPrimaryKey(Integer id)  {

        String query = "Pais "
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
     * @param descricao
     * @return
     */
    private Pais mapToPais(Object id, Object descricao) {
        Pais e = new Pais();
        e.setId((Integer) id);
        e.setDescricao((String) descricao);
        return e;
    }
}
