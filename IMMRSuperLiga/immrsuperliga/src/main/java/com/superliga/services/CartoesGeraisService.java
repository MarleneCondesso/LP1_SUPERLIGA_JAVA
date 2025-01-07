/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superliga.services;

import com.superliga.models.CartoesGeraisEquipas;
import com.superliga.models.CartoesGeraisJogadores;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author 35191
 */
public class CartoesGeraisService extends Service {

    /**
     * Retorna uma coletanea de registos de MelhoresMarcadoresGerais presentes
     * na base de dados.
     *
     * @return
     */
    public ArrayList<CartoesGeraisJogadores> getAllJogadores() {
        ArrayList<CartoesGeraisJogadores> records = new ArrayList<>();

        ArrayList<String> returnValues = new ArrayList() {
            {
                add("Época");
                add("Jogador");
                add("Cartão");
                add("Contagem");
            }
        };

        ArrayList< HashMap<String, Object>> data = super.get("SELECT * FROM view_CartoesGeraisJogador", null, returnValues);

        for (HashMap<String, Object> record : data) {

            records.add(
                    mapToCartoesGeraisJogadores(record.get("Época"),
                            record.get("Jogador"),
                            record.get("Cartão"),
                            record.get("Contagem")
                    )
            );

        }

        return records;

    }

    /**
     * Retorna registo referenciado pelo id da MelhoresMarcadoresGerais passado
     * por parametro.
     *
     * @param id
     * @return
     */
    public ArrayList<CartoesGeraisJogadores> getByEpoca(String epoca) {

        String query = "SELECT * "
                + "FROM view_CartoesGeraisJogador "
                + "WHERE Época = ?";

        ArrayList<Object> params = new ArrayList() {
            {
                add(epoca);
            }
        };

        ArrayList<String> returnValues = new ArrayList() {
            {
                add("Época");
                add("Jogador");
                add("Cartão");
                add("Contagem");
            }
        };

        ArrayList< HashMap<String, Object>> data = super.get(query, params, returnValues);

        ArrayList<CartoesGeraisJogadores> recordsOut = new ArrayList<>();
        for (HashMap<String, Object> record : data) {

            recordsOut.add(
                    mapToCartoesGeraisJogadores(record.get("Época"),
                            record.get("Jogador"),
                            record.get("Cartão"),
                            record.get("Contagem")
                    )
            );

        }

        return recordsOut;
    }

    /**
     *
     * @param epoca
     * @param jogador
     * @param cartao
     * @param contagem
     * @return
     */
    private CartoesGeraisJogadores mapToCartoesGeraisJogadores(Object epoca, Object jogador, Object cartao, Object contagem) {
        CartoesGeraisJogadores c = new CartoesGeraisJogadores();

        c.setNrEpoca((String) epoca);
        c.setNomeJogador((String) jogador);
        c.setTipoEvento((String) cartao);
        c.setQuantidade((Integer) contagem);
        return c;
    }

    /**
     * Retorna uma coletanea de registos de MelhoresMarcadoresGerais presentes
     * na base de dados.
     *
     * @return
     */
    public ArrayList<CartoesGeraisEquipas> getAllEquipas() {
        ArrayList<CartoesGeraisEquipas> records = new ArrayList<>();

        ArrayList<String> returnValues = new ArrayList() {
            {
                add("Época");
                add("Equipa");
                add("Cartão");
                add("Contagem");
            }
        };

        ArrayList< HashMap<String, Object>> data = super.get("SELECT * FROM view_CartoesGeraisEquipa", null, returnValues);

        for (HashMap<String, Object> record : data) {

            records.add(
                    mapToCartoesGeraisEquipas(record.get("Época"),
                            record.get("Equipa"),
                            record.get("Cartão"),
                            record.get("Contagem")
                    )
            );

        }

        return records;

    }

    /**
     * Retorna registo referenciado pelo id da MelhoresMarcadoresGerais passado
     * por parametro.
     *
     * @param id
     * @return
     */
    public ArrayList<CartoesGeraisEquipas> getByEpocaE(String epoca) {

        String query = "SELECT * "
                + "FROM view_CartoesGeraisEquipa "
                + "WHERE Época = ?";

        ArrayList<Object> params = new ArrayList() {
            {
                add(epoca);
            }
        };

        ArrayList<String> returnValues = new ArrayList() {
            {
                add("Época");
                add("Equipa");
                add("Cartão");
                add("Contagem");
            }
        };

        ArrayList< HashMap<String, Object>> data = super.get(query, params, returnValues);

        ArrayList<CartoesGeraisEquipas> recordsOut = new ArrayList<>();
        for (HashMap<String, Object> record : data) {

            recordsOut.add(
                    mapToCartoesGeraisEquipas(record.get("Época"),
                            record.get("Equipa"),
                            record.get("Cartão"),
                            record.get("Contagem")
                    )
            );

        }

        return recordsOut;
    }

    /**
     *
     * @param epoca
     * @param equipa
     * @param cartao
     * @param contagem
     * @return
     */
    private CartoesGeraisEquipas mapToCartoesGeraisEquipas(Object epoca, Object equipa, Object cartao, Object contagem) {
        CartoesGeraisEquipas cEquipas = new CartoesGeraisEquipas();

        cEquipas.setEpoca((String) epoca);
        cEquipas.setEquipa((String) equipa);
        cEquipas.setCartao((String) cartao);
        cEquipas.setContagem((Integer) contagem);
        return cEquipas;
    }
}
