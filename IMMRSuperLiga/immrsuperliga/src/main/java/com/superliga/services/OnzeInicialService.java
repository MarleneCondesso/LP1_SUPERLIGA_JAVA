/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superliga.services;

import com.superliga.models.Jogo;
import com.superliga.models.OnzeInicial;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;

/**
 *
 * @author superliga
 */
public class OnzeInicialService extends Service {

    /**
     * Retorna uma coletanea de registos de OnzeInicial presentes na base de
     * dados.
     *
     * @return
     */
    public ArrayList<OnzeInicial> getAll() {
        ArrayList<OnzeInicial> records = new ArrayList<>();

        ArrayList<String> returnValues = new ArrayList() {
            {
                add("equipa");
                add("numeroAtleta");
                add("nome");
                add("jogo");
                add("dataJogo");
                add("posicao");
                add("nomeEquipa");
                add("idJogo");
                add("Epoca");
                add("Jornada");
            }
        };

        ArrayList< HashMap<String, Object>> data = super.get("SELECT * FROM view_OnzeInicialJogo", null, returnValues);

        for (HashMap<String, Object> record : data) {

            records.add(
                    mapToOnzeInicial(record.get("equipa"),
                            record.get("numeroAtleta"),
                            record.get("nome"),
                            record.get("jogo"),
                            record.get("dataJogo"),
                            record.get("posicao"),
                            record.get("nomeEquipa"),
                            record.get("idJogo"),
                            record.get("Epoca"),
                            record.get("Jornada")
                    )
            );

        }

        return records;

    }

    /**
     * Retorna registo referenciado pelo id jogo e nome equipa passado por
     * parametro.
     *
     * @param idJogo
     * @param nomeEquipa
     * @return
     */
    public ArrayList<OnzeInicial> getAllJogoEquipa(int idJogo, String nomeEquipa) {

        ArrayList<OnzeInicial> records = new ArrayList<>();

        String query = "SELECT * "
                + "FROM view_OnzeInicialJogo "
                + "WHERE jogo = ? AND "
                + "nomeEquipa = ?";

        ArrayList<Object> params = new ArrayList() {
            {
                add(idJogo);
                add(nomeEquipa);
            }
        };

        ArrayList<String> returnValues = new ArrayList() {
            {
                add("equipa");
                add("numeroAtleta");
                add("nome");
                add("jogo");
                add("dataJogo");
                add("posicao");
                add("nomeEquipa");
                add("idJogo");
                add("Epoca");
                add("Jornada");
            }
        };

        ArrayList< HashMap<String, Object>> data = super.get(query, params, returnValues);
        for (HashMap<String, Object> record : data) {

            records.add(
                    mapToOnzeInicial(record.get("equipa"),
                            record.get("numeroAtleta"),
                            record.get("nome"),
                            record.get("jogo"),
                            record.get("dataJogo"),
                            record.get("posicao"),
                            record.get("nomeEquipa"),
                            record.get("idJogo"),
                            record.get("Epoca"),
                            record.get("Jornada")
                    )
            );

        }

        return records;

    }

    /**
     * Retorna registo referenciado pelo id da OnzeInicial passado por
     * parametro.
     *
     * @param id
     * @return
     */
    public ArrayList<OnzeInicial> getByEpoca(String epoca) {

        String query = "SELECT * "
                + "FROM view_OnzeInicialJogo "
                + "WHERE Epoca = ?";

        ArrayList<Object> params = new ArrayList() {
            {
                add(epoca);
            }
        };

        ArrayList<String> returnValues = new ArrayList() {
            {
                add("equipa");
                add("numeroAtleta");
                add("nome");
                add("jogo");
                add("dataJogo");
                add("posicao");
                add("nomeEquipa");
                add("idJogo");
                add("Epoca");
                add("Jornada");
            }
        };

        ArrayList< HashMap<String, Object>> data = super.get(query, params, returnValues);

        ArrayList<OnzeInicial> recordsOut = new ArrayList<>();
        for (HashMap<String, Object> record : data) {

            recordsOut.add(
                    mapToOnzeInicial(record.get("equipa"),
                            record.get("numeroAtleta"),
                            record.get("nome"),
                            record.get("jogo"),
                            record.get("dataJogo"),
                            record.get("posicao"),
                            record.get("nomeEquipa"),
                            record.get("idJogo"),
                            record.get("Epoca"),
                            record.get("Jornada")
                    )
            );

        }

        return recordsOut;
    }

    /**
     * @param equipa
     * @param numeroAtleta
     * @param nome
     * @param jogo
     * @param dataJogo
     * @param posicao
     * @param nomeEquipa
     * @param idJogo
     * @param epoca
     * @param jornada
     * @return
     */
    private OnzeInicial mapToOnzeInicial(Object equipa, Object numeroAtleta, Object nome, Object jogo, Object dataJogo, Object posicao,
            Object nomeEquipa, Object idJogo, Object epoca, Object jornada) {
        OnzeInicial m = new OnzeInicial();

        m.setEquipa((String) equipa);
        m.setNumeroAtleta((Integer) numeroAtleta);
        m.setNome((String) nome);
        m.setJogo((Integer) jogo);
        m.setDataJogo((Timestamp) dataJogo);
        m.setPosicao((String) posicao);
        m.setNomeEquipa((String) nomeEquipa);
        m.setIdJogo((Integer) idJogo);
        m.setEpoca((String) epoca);
        m.setJornada((Integer) jornada);
        return m;
    }
}
