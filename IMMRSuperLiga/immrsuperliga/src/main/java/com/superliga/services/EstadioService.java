/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superliga.services;

import com.superliga.models.Estadio;
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
public class EstadioService extends Service {

    /**
     * Retorna uma coletanea de registos de Estadio presentes na base de dados.
     *
     * @return
     */
    public ArrayList<Estadio> getAll() {
        ArrayList<Estadio> records = new ArrayList<>();

        ArrayList<String> returnValues = new ArrayList() {
            {
                add("id");
                add("nome");
                add("id_local");
                add("id_pais");
            }
        };

        ArrayList< HashMap<String, Object>> data = super.get("SELECT * FROM Estadio", null, returnValues);

        for (HashMap<String, Object> record : data) {

            records.add(
                    mapToEstadio(record.get("id"),
                            record.get("nome"),
                            record.get("id_local"),
                            record.get("id_pais")
                    )
            );

        }

        return records;

    }

    /**
     * Retorna registo referenciado pelo id da Estadio passado por parametro.
     *
     * @param id
     * @return
     */
    public Estadio getByPrimaryKey(Integer id) {

        Estadio record = null;

        String query = "SELECT * "
                + "FROM Estadio "
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
                add("id_local");
                add("id_pais");
            }
        };

        ArrayList< HashMap<String, Object>> data = super.get(query, params, returnValues);

        // Mapear dados para modelo Estadio
        if (data.size() == 1) {

            HashMap<String, Object> dataValue = data.get(0);

            record = mapToEstadio(
                    dataValue.get("id"),
                    dataValue.get("nome"),
                    dataValue.get("id_local"),
                    dataValue.get("id_pais")
            );
        }

        return record;
    }

    public boolean create(String nome, Integer idLocal, Integer idPais)  {

        String query = "Estadio (nome, id_local, id_pais) "
                + "VALUES (?,?,?)";

        ArrayList<Object> params = new ArrayList() {
            {
                add(nome);
                add(idLocal);
                add(idPais);
            }
        };

        return super.insert(query, params);

    }

    public boolean update(Integer id, String nome, Integer idLocal, Integer idPais) {

        String query = "Estadio SET nome=?, id_local=?, id_pais=? "
                + " WHERE id= ?";

        ArrayList<Object> params = new ArrayList() {
            {
                add(nome);
                add(idLocal);
                add(idPais);
                add(id);
            }
        };

        return super.update(query, params);

    }

    public boolean deleteByPrimaryKey(Integer id) {

        String query = "Estadio "
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
     * @param idLocal
     * @param idPais
     * @return
     */
    private Estadio mapToEstadio(Object id, Object nome, Object idLocal, Object idPais) {
        Estadio e = new Estadio();
        e.setId((Integer) id);
        e.setNome((String) nome);
        e.setIdLocal((Integer) idLocal);
        e.setIdPais((Integer) idPais);
        return e;
    }
}
