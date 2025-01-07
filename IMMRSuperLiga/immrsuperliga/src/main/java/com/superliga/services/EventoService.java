/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superliga.services;

import com.superliga.models.Evento;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author mstevz
 */
public class EventoService extends Service {

    /**
     * Retorna uma coletanea de registos de Epoca presentes na base de dados.
     *
     * @return
     */
    public ArrayList<Evento> getAll() {

        ArrayList<Evento> records = new ArrayList<>();

        ArrayList<String> returnValues = new ArrayList() {
            {
                add("id_jogador_jogo");
                add("id_jogo");
                add("tempo");
                add("tipo");
            }
        };

        ArrayList< HashMap<String, Object>> data = super.get("SELECT * FROM Evento", null, returnValues);

        for (HashMap<String, Object> record : data) {

            records.add(
                    mapToObject(record.get("id_jogador_jogo"),
                            record.get("id_jogo"),
                            record.get("tempo"),
                            record.get("tipo"))
            );

        }

        return records;
    }

    /**
     * Retorna registo referenciado pelo id jogo passado por parametro.
     *
     * @param idJogo
     * @return
     */
    public ArrayList<Evento> getAllByJogo(int idJogo) {

        ArrayList<Evento> records = new ArrayList<>();

        String query = "SELECT * "
                + "FROM Evento "
                + "WHERE id_jogo = ?";

        ArrayList<Object> params = new ArrayList() {
            {
                add(idJogo);
            }
        };

        ArrayList<String> returnValues = new ArrayList() {
            {
                add("id_jogador_jogo");
                add("id_jogo");
                add("tempo");
                add("tipo");
            }
        };

        ArrayList< HashMap<String, Object>> data = super.get(query, params, returnValues);

        for (HashMap<String, Object> record : data) {

            records.add(
                    mapToObject(record.get("id_jogador_jogo"),
                            record.get("id_jogo"),
                            record.get("tempo"),
                            record.get("tipo"))
            );

        }

        return records;
    }

    public Evento getByPrimaryKey(int idJogadorJogo, int idJogo, BigDecimal tempo) {

        Evento record = null;

        String query = "SELECT * "
                + "FROM Evento "
                + "WHERE id_jogador_jogo = ? AND "
                + "id_jogo = ? AND "
                + "tempo = ?";

        ArrayList<Object> params = new ArrayList() {
            {
                add(idJogadorJogo);
                add(idJogo);
                add(tempo);
            }
        };

        ArrayList<String> returnValues = new ArrayList() {
            {
                add("id_jogador_jogo");
                add("id_jogo");
                add("tempo");
                add("tipo");
            }
        };

        ArrayList< HashMap<String, Object>> data = super.get(query, params, returnValues);

        // Mapear dados para modelo Epoca
        if (data.size() == 1) {

            HashMap<String, Object> dataValue = data.get(0);

            record = mapToObject(dataValue.get("id_jogador_jogo"),
                    dataValue.get("id_jogo"),
                    dataValue.get("tempo"),
                    dataValue.get("tipo"));
        }

        return record;
    }

    public boolean create(int idJogadorJogo, int idJogo, BigDecimal tempo, String tipo) {

        String query = "Evento (id_jogador_jogo, id_jogo, tempo, tipo) "
                + "VALUES (?,?,?,?)";

        ArrayList<Object> params = new ArrayList() {
            {
                add(idJogadorJogo);
                add(idJogo);
                add(tempo);
                add(tipo);
            }
        };

        return super.insert(query, params);
    }

    public boolean update(int idJogadorJogo, int idJogo, BigDecimal tempo, String tipo) {
        String query = "Evento SET tipo=?"
                + "WHERE id_jogador_jogo=? AND "
                + "id_jogo=? AND "
                + "tempo=?";

        ArrayList<Object> params = new ArrayList() {
            {
                add(tipo);
                add(idJogadorJogo);
                add(idJogo);
                add(tempo);
            }
        };

        return super.update(query, params);
    }

    public boolean deleteByPrimaryKey(int idJogadorJogo, int idJogo, BigDecimal tempo) {
        String query = "Evento "
                + "WHERE id_jogador_jogo=? AND "
                + "id_jogo=? AND "
                + "tempo=?";

        ArrayList<Object> params = new ArrayList() {
            {
                add(idJogadorJogo);
                add(idJogo);
                add(tempo);
            }
        };

        return delete(query, params);
    }

    private Evento mapToObject(Object idJogadorJogo, Object idJogo, Object tempo, Object tipo) {
        return new Evento((int) idJogadorJogo, (int) idJogo, (BigDecimal) tempo, (String) tipo);
    }

}
