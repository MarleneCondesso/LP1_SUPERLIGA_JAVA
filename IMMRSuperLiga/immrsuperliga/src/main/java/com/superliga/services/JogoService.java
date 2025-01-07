/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superliga.services;

import com.superliga.models.Jogo;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDateTime;

/**
 *
 * @author mstevz
 */
public class JogoService extends Service {

    /**
     * Retorna uma coletanea de registos de Epoca presentes na base de dados.
     *
     * @return
     */
    public ArrayList<Jogo> getAll() {

        ArrayList<Jogo> records = new ArrayList<>();

        ArrayList<String> returnValues = new ArrayList() {
            {
                add("id");
                add("id_equipa_casa");
                add("id_equipa_fora");
                add("nr_epoca");
                add("nr_jornada");
                add("data_hora_jogo");
                add("minutos_intervalo");
                add("duracao");
                add("id_estadio");
            }
        };

        ArrayList< HashMap<String, Object>> data = super.get("SELECT * FROM Jogo", null, returnValues);

        for (HashMap<String, Object> record : data) {

            records.add(
                    mapToObject(record.get("id"),
                            record.get("id_equipa_casa"),
                            record.get("id_equipa_fora"),
                            record.get("nr_epoca"),
                            record.get("nr_jornada"),
                            record.get("data_hora_jogo"),
                            record.get("minutos_intervalo"),
                            record.get("duracao"),
                            record.get("id_estadio"))
            );

        }

        return records;
    }

    /**
     * Retorna registo referenciado pelo id de jogo passado por parametro.
     *
     * @param id
     * @return
     */
    public Jogo getByPrimaryKey(int id) {

        Jogo record = null;

        String query = "SELECT * "
                + "FROM Jogo "
                + "WHERE id = ?";

        ArrayList<Object> params = new ArrayList() {
            {
                add(id);
            }
        };

        ArrayList<String> returnValues = new ArrayList() {
            {
                add("id");
                add("id_equipa_casa");
                add("id_equipa_fora");
                add("nr_epoca");
                add("nr_jornada");
                add("data_hora_jogo");
                add("minutos_intervalo");
                add("duracao");
                add("id_estadio");
            }
        };

        ArrayList< HashMap<String, Object>> data = super.get(query, params, returnValues);

        // Mapear dados para modelo Jogo
        if (data.size() == 1) {

            HashMap<String, Object> dataValue = data.get(0);

            record = mapToObject(dataValue.get("id"),
                    dataValue.get("id_equipa_casa"),
                    dataValue.get("id_equipa_fora"),
                    dataValue.get("nr_epoca"),
                    dataValue.get("nr_jornada"),
                    dataValue.get("data_hora_jogo"),
                    dataValue.get("minutos_intervalo"),
                    dataValue.get("duracao"),
                    dataValue.get("id_estadio")
            );
        }

        return record;
    }

    /**
     * Retorna registo referenciado pelo número de época e número de jornada
     * passado por parametro.
     *
     * @param nrEpoca
     * @param nrJornada
     * @return
     */
    public ArrayList<Jogo> getAllByEpocaJornada(String nrEpoca, int nrJornada) {

        ArrayList<Jogo> records = new ArrayList<>();

        String query = "SELECT * "
                + "FROM Jogo "
                + "WHERE nr_epoca = ? AND "
                + "nr_jornada = ?";

        ArrayList<Object> params = new ArrayList() {
            {
                add(nrEpoca);
                add(nrJornada);
            }
        };

        ArrayList<String> returnValues = new ArrayList() {
            {
                add("id");
                add("id_equipa_casa");
                add("id_equipa_fora");
                add("nr_epoca");
                add("nr_jornada");
                add("data_hora_jogo");
                add("minutos_intervalo");
                add("duracao");
                add("id_estadio");
            }
        };

        ArrayList< HashMap<String, Object>> data = super.get(query, params, returnValues);
        for (HashMap<String, Object> record : data) {

            records.add(
                    mapToObject(record.get("id"),
                            record.get("id_equipa_casa"),
                            record.get("id_equipa_fora"),
                            record.get("nr_epoca"),
                            record.get("nr_jornada"),
                            record.get("data_hora_jogo"),
                            record.get("minutos_intervalo"),
                            record.get("duracao"),
                            record.get("id_estadio"))
            );

        }

        return records;
    }

    public boolean create(int idEquipaCasa, int idEquipaFora, String nrEpoca, String nrJornada, Timestamp dataHora, int intervalo, int duracao, int idEstadio) {

        String query = "Jogo (id_equipa_casa, id_equipa_fora, nr_epoca, nr_jornada, data_hora_jogo, minutos_intervalo, duracao, id_estadio) "
                + "VALUES (?,?,?,?,?,?,?,?)";

        ArrayList<Object> params = new ArrayList() {
            {
                add(idEquipaCasa);
                add(idEquipaFora);
                add(nrEpoca);
                add(nrJornada);
                add(dataHora);
                add(intervalo);
                add(duracao);
                add(idEstadio);
            }
        };

        return super.insert(query, params);
    }

    public boolean update(int id, int idEquipaCasa, int idEquipaFora, String nrEpoca, String nrJornada, Timestamp dataHora, int intervalo, int duracao, int idEstadio) {
        String query = "Jogo SET id_equipa_casa=?, "
                + "id_equipa_fora=?, "
                + "nr_epoca=?, "
                + "nr_jornada=?, "
                + "data_hora_jogo=?, "
                + "minutos_intervalo=?, "
                + "duracao=?, "
                + "id_estadio=? "
                + "WHERE id= ?";

        ArrayList<Object> params = new ArrayList() {
            {
                add(id);
                add(idEquipaCasa);
                add(idEquipaFora);
                add(nrEpoca);
                add(nrJornada);
                add(dataHora);
                add(intervalo);
                add(duracao);
                add(idEstadio);
            }
        };

        return super.update(query, params);
    }

    public boolean deleteByPrimaryKey(int id) {

        String query = "Jogo "
                + "WHERE id = ?";

        ArrayList<Object> params = new ArrayList() {
            {
                add(id);
            }
        };

        return delete(query, params);
    }

    /**
     *
     * @param epoca
     * @param inicio
     * @param fim
     * @return
     */
    private Jogo mapToObject(Object id, Object equipaCasa, Object equipaFora, Object nrEpoca, Object nrJornada, Object dataHoraJogo, Object intervalo, Object duracao, Object estadio) {
        Jogo j = new Jogo();

        j.setId((Integer) id);
        j.setIdEquipaCasa((Integer) equipaCasa);
        j.setIdEquipaFora((Integer) equipaFora);
        j.setNrEpoca((String) nrEpoca);
        j.setNrJornada((Integer) nrJornada);
        j.setDataHora((Timestamp) dataHoraJogo);
        j.setIntervalo((Integer) intervalo);
        j.setDuracao((Integer) duracao);
        j.setIdEstadio((Integer) estadio);

        return j;
    }
}
