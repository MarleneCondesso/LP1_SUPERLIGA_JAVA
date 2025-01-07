/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superliga.services;

import com.superliga.models.MelhoresMarcadoresGerais;
import com.superliga.models.MelhoresMarcadoresGeraisEquipas;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author superliga
 */
public class MelhoresMarcadoresGeraisService extends Service {

    /**
     * Retorna uma coletanea de registos de MelhoresMarcadoresGerais presentes
     * na base de dados.
     *
     * @return
     */
    public ArrayList<MelhoresMarcadoresGerais> getAll() {
        ArrayList<MelhoresMarcadoresGerais> records = new ArrayList<>();

        ArrayList<String> returnValues = new ArrayList() {
            {
                add("AtletaNr");
                add("AtletaNome");
                add("Epoca");
                add("Jornada");
                add("Golos");
            }
        };

        ArrayList< HashMap<String, Object>> data = super.get("SELECT * FROM view_MelhoresMarcadoresGerais Order by golos desc", null, returnValues);

        for (HashMap<String, Object> record : data) {

            records.add(
                    mapToMelhoresMarcadoresGerais(record.get("AtletaNr"),
                            record.get("AtletaNome"),
                            record.get("Epoca"),
                            record.get("Jornada"),
                            record.get("Golos")
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
    public ArrayList<MelhoresMarcadoresGerais> getByEpoca(String epoca) {

        String query = "SELECT * "
                + "FROM view_MelhoresMarcadoresGerais "
                + "WHERE Epoca = ? Order by golos desc"; 

        ArrayList<Object> params = new ArrayList() {
            {
                add(epoca);
            }
        };

        ArrayList<String> returnValues = new ArrayList() {
            {
                add("AtletaNr");
                add("AtletaNome");
                add("Epoca");
                add("Jornada");
                add("Golos");
            }
        };

        ArrayList< HashMap<String, Object>> data = super.get(query, params, returnValues);

        ArrayList<MelhoresMarcadoresGerais> recordsOut = new ArrayList<>();
        for (HashMap<String, Object> record : data) {

            recordsOut.add(
                    mapToMelhoresMarcadoresGerais(record.get("AtletaNr"),
                            record.get("AtletaNome"),
                            record.get("Epoca"),
                            record.get("Jornada"),
                            record.get("Golos")
                    )
            );

        }

        return recordsOut;
    }

    /**
     * Retorna registo referenciado pelo id da MelhoresMarcadoresGerais passado
     * por parametro.
     *
     * @param epoca
     * @param jornada
     * @return
     */
    public ArrayList<MelhoresMarcadoresGerais> getByJornada(String epoca, Integer jornada) { 

        String query = "SELECT * "
                + "FROM view_MelhoresMarcadoresGerais "
                + "WHERE Epoca = ? and Jornada = ? Order by golos desc";

        ArrayList<Object> params = new ArrayList() {
            {
                add(epoca);
                add(jornada);
            }
        };

        ArrayList<String> returnValues = new ArrayList() {
            {
                add("AtletaNr");
                add("AtletaNome");
                add("Epoca");
                add("Jornada");
                add("Golos");
            }
        };

        ArrayList< HashMap<String, Object>> data = super.get(query, params, returnValues);

        ArrayList<MelhoresMarcadoresGerais> recordsOut = new ArrayList<>();
        for (HashMap<String, Object> record : data) {

            recordsOut.add(
                    mapToMelhoresMarcadoresGerais(record.get("AtletaNr"),
                            record.get("AtletaNome"),
                            record.get("Epoca"),
                            record.get("Jornada"),
                            record.get("Golos")
                    )
            );

        }
        System.out.println(recordsOut);
        return recordsOut;
    }

    /**
     *
     * @param atletaNr
     * @param atletaNome
     * @param epoca
     * @param jornada
     * @param golos
     * @return
     */
    private MelhoresMarcadoresGerais mapToMelhoresMarcadoresGerais(Object atletaNr, Object atletaNome, Object epoca, Object jornada, Object golos) {
        MelhoresMarcadoresGerais m = new MelhoresMarcadoresGerais();

        m.setAtletaNr((Integer) atletaNr);
        m.setAtletaNome((String) atletaNome);
        m.setEpoca((String) epoca);
        m.setJornada((Integer) jornada);
        m.setGolos((Integer) golos);
        return m;
    }

    /**
     * Retorna uma coletanea de registos de MelhoresMarcadoresGeraisEquipas
     * presentes na base de dados.
     *
     * @return
     */
    public ArrayList<MelhoresMarcadoresGeraisEquipas> getAllEquipas() {
        ArrayList<MelhoresMarcadoresGeraisEquipas> records = new ArrayList<>();

        ArrayList<String> returnValues = new ArrayList() {
            {
                add("EquipaId");
                add("EquipaNome");
                add("Epoca");
                add("Jornada");
                add("Golos");
            }
        };

        ArrayList< HashMap<String, Object>> data = super.get("SELECT * FROM view_MelhoresEquipasMarcadores Order by golos desc", null, returnValues);

        for (HashMap<String, Object> record : data) {

            records.add(
                    mapToMelhoresMarcadoresGeraisEquipas(record.get("EquipaId"),
                            record.get("EquipaNome"),
                            record.get("Epoca"),
                            record.get("Jornada"),
                            record.get("Golos")
                    )
            );

        }

        return records;

    }

    /**
     * Retorna registo referenciado pelo id da MelhoresMarcadoresGeraisEquipas
     * passado por parametro.
     *
     * @param id
     * @return
     */
    public ArrayList<MelhoresMarcadoresGeraisEquipas> getByEpocaEquipas(String epoca) {

        String query = "SELECT * "
                + "FROM view_MelhoresEquipasMarcadores "
                + "WHERE Epoca = ? Order by golos desc";

        ArrayList<Object> params = new ArrayList() {
            {
                add(epoca);
            }
        };

        ArrayList<String> returnValues = new ArrayList() {
            {
                add("EquipaId");
                add("EquipaNome");
                add("Epoca");
                add("Jornada");
                add("Golos");
            }
        };

        ArrayList< HashMap<String, Object>> data = super.get(query, params, returnValues);

        ArrayList<MelhoresMarcadoresGeraisEquipas> recordsOut = new ArrayList<>();
        for (HashMap<String, Object> record : data) {

            recordsOut.add(
                    mapToMelhoresMarcadoresGeraisEquipas(record.get("EquipaId"),
                            record.get("EquipaNome"),
                            record.get("Epoca"),
                            record.get("Jornada"),
                            record.get("Golos")
                    )
            );

        }

        return recordsOut;
    }

    /**
     * Retorna registo referenciado pelo id da MelhoresMarcadoresGerais passado
     * por parametro.
     *
     * @param epoca
     * @param jornada
     * @return
     */
    public ArrayList<MelhoresMarcadoresGeraisEquipas> getByJornadaEquipas(String epoca, Integer jornada) {

        String query = "SELECT * "
                + "FROM view_MelhoresEquipasMarcadores "
                + "WHERE Epoca = ? and Jornada = ? Order by golos desc";

        ArrayList<Object> params = new ArrayList() {
            {
                add(epoca);
                add(jornada);
            }
        };

        ArrayList<String> returnValues = new ArrayList() {
            {
                add("EquipaId");
                add("EquipaNome");
                add("Epoca");
                add("Jornada");
                add("Golos");
            }
        };

        ArrayList< HashMap<String, Object>> data = super.get(query, params, returnValues);

        ArrayList<MelhoresMarcadoresGeraisEquipas> recordsOut = new ArrayList<>();
        for (HashMap<String, Object> record : data) {

            recordsOut.add(
                    mapToMelhoresMarcadoresGeraisEquipas(record.get("EquipaId"),
                            record.get("EquipaNome"),
                            record.get("Epoca"),
                            record.get("Jornada"),
                            record.get("Golos")
                    )
            );

        }
        System.out.println(recordsOut);
        return recordsOut;
    }

    /**
     *
     * @param equipaId
     * @param equipaNome
     * @param epoca
     * @param jornada
     * @param golos
     * @return
     */
    private MelhoresMarcadoresGeraisEquipas mapToMelhoresMarcadoresGeraisEquipas(Object equipaId, Object equipaNome, Object epoca, Object jornada, Object golos) {
        MelhoresMarcadoresGeraisEquipas m = new MelhoresMarcadoresGeraisEquipas();

        m.setEquipaId((Integer) equipaId);
        m.setEquipaNome((String) equipaNome);
        m.setEpoca((String) epoca);
        m.setJornada((Integer) jornada);
        m.setGolos((Integer) golos);
        return m;
    }
}
