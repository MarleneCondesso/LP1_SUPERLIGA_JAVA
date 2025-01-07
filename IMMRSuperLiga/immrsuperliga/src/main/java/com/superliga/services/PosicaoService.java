/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superliga.services;

import com.superliga.models.Posicao;
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
public class PosicaoService extends Service {

    /**
     * Retorna uma coletanea de registos de Posicao presentes na base de dados.
     *
     * @return
     */
    public ArrayList<Posicao> getAll() {
        ArrayList<Posicao> records = new ArrayList<>();

        ArrayList<String> returnValues = new ArrayList() {
            {
                add("id");
                add("descricao");
            }
        };

        ArrayList< HashMap<String, Object>> data = super.get("SELECT * FROM Posicao", null, returnValues);

        for (HashMap<String, Object> record : data) {

            records.add(
                    mapToPosicao(record.get("id"),
                            record.get("descricao"))
            );

        }

        return records;

    }

    /**
     * Retorna registo referenciado pelo id da Posicao passado por parametro.
     *
     * @param id
     * @return
     */
    public Posicao getByPrimaryKey(String id) {

        Posicao record = null;

        String query = "SELECT * "
                + "FROM Posicao "
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

        // Mapear dados para modelo Posicao
        if (data.size() == 1) {

            HashMap<String, Object> dataValue = data.get(0);

            record = mapToPosicao(
                    dataValue.get("id"),
                    dataValue.get("descricao")
            );
        }

        return record;
    }
    
    /**
     * Retorna registo referenciado pela descrição da Posicao passado por parametro.
     *
     * @param description
     * @return
     */
    public Posicao getByDescription(String description) {

        Posicao record = null;

        String query = "SELECT * "
                + "FROM Posicao "
                + "WHERE descricao = ?";

        ArrayList<Object> params = new ArrayList() {
            {
                add(description);
            }
        };

        ArrayList<String> returnValues = new ArrayList() {
            {
                add("id");
                add("descricao");
            }
        };

        ArrayList< HashMap<String, Object>> data = super.get(query, params, returnValues);

        // Mapear dados para modelo Posicao
        if (data.size() == 1) {

            HashMap<String, Object> dataValue = data.get(0);

            record = mapToPosicao(
                    dataValue.get("id"),
                    dataValue.get("descricao")
            );
        }

        return record;
    }

    public boolean create(String id, String descricao) {

        String query = "Posicao (id, descricao) "
                + "VALUES (?,?)";

        ArrayList<Object> params = new ArrayList() {
            {
                add(id);
                add(descricao);
            }
        };

        return super.insert(query, params);

    }

    public boolean update(String id, String descricao)   {

        String query = "Posicao SET descricao=?"
                + " WHERE id= ?";

        ArrayList<Object> params = new ArrayList() {
            {
                add(descricao);
                add(id);
            }
        };

        return super.update(query, params);

    }

    public boolean deleteByPrimaryKey(String id)  {

        String query = "Posicao "
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
    private Posicao mapToPosicao(Object id, Object descricao) {
        Posicao e = new Posicao();
        e.setId((String) id);
        e.setDescricao((String) descricao);
        return e;
    }
}
