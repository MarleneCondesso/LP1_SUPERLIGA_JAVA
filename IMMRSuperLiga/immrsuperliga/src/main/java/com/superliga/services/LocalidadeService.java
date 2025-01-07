/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superliga.services;

import com.superliga.models.Localidade;
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
public class LocalidadeService extends Service {

    /**
     * Retorna uma coletanea de registos de Localidade presentes na base de
     * dados.
     *
     * @return
     */
    public ArrayList<Localidade> getAll() {
        ArrayList<Localidade> records = new ArrayList<>();

        ArrayList<String> returnValues = new ArrayList() {
            {
                add("id");
                add("id_pais");
                add("descricao");
            }
        };

        ArrayList< HashMap<String, Object>> data = super.get("SELECT * FROM Localidade", null, returnValues);

        for (HashMap<String, Object> record : data) {

            records.add(
                    mapToLocalidade(record.get("id"),
                            record.get("id_pais"),
                            record.get("descricao"))
            );

        }

        return records;

    }

    /**
     * Retorna registo referenciado pelo id da Localidade passado por parametro.
     *
     * @param id
     * @return
     */
    public Localidade getByPrimaryKey(Integer id) {

        Localidade record = null;

        String query = "SELECT * "
                + "FROM Localidade "
                + "WHERE id = ?";

        ArrayList<Object> params = new ArrayList() {
            {
                add(id);
            }
        };

        ArrayList<String> returnValues = new ArrayList() {
            {
                add("id");
                add("id_pais");
                add("descricao");
            }
        };

        ArrayList< HashMap<String, Object>> data = super.get(query, params, returnValues);

        // Mapear dados para modelo Localidade
        if (data.size() == 1) {

            HashMap<String, Object> dataValue = data.get(0);

            record = mapToLocalidade(
                    dataValue.get("id"),
                    dataValue.get("id_pais"),
                    dataValue.get("descricao")
            );
        }

        return record;
    }

    public boolean create(Integer id_pais, String descricao)  {

        String query = "Localidade (id_pais, descricao) "
                + "VALUES (?,?)";

        ArrayList<Object> params = new ArrayList() {
            {
                add(id_pais);
                add(descricao);
            }
        };

        return super.insert(query, params);

    }

    public boolean update(Integer id, Integer id_pais, String descricao) {

        String query = "Localidade SET id_pais=?, descricao=?"
                + " WHERE id= ?";

        ArrayList<Object> params = new ArrayList() {
            {
                add(id_pais);
                add(descricao);
                add(id);
            }
        };

        return super.update(query, params);

    }

    public boolean deleteByPrimaryKey(Integer id)  {

        String query = "Localidade "
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
     * @param idPais
     * @param descricao
     * @return
     */
    private Localidade mapToLocalidade(Object id, Object idPais, Object descricao) {
        Localidade e = new Localidade();
        e.setId((Integer) id);
        e.setIdPais((Integer) idPais);
        e.setDescricao((String) descricao);
        return e;
    }
}
