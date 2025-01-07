/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superliga.tests;

import com.superliga.services.EpocaService;
import com.superliga.models.Epoca;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author superliga
 */
public class EpocaServiceTest {

    public static void getAll() throws Exception {
        EpocaService es = new EpocaService();
        
        ArrayList<Epoca> allEpocas = es.getAll();
        for (int i = 0; i < allEpocas.size(); i++) {
            System.out.println("Época: " + allEpocas.get(i).getNumeroEpoca());
        }
    }
    
    public static void getByPrimaryKey() {
        EpocaService es = new EpocaService();
        
        Epoca e = es.getByPrimaryKey("2020/2021");
        
    }
    
    public static void createEpoca() {
        EpocaService es = new EpocaService();
        es.create("2021/2022", Date.valueOf("2021-01-01"), Date.valueOf("2022-01-01"));
    }
    
    public static void updateEpoca() {
        EpocaService es = new EpocaService();
                
        es.update("2021/2022", Date.valueOf("2021-01-01"), Date.valueOf("2022-12-12"));
    }
    public static void deleteEpoca(){
        EpocaService es = new EpocaService();
        
        es.deleteByPrimaryKey("2021/2022");
    }
    /*
    public Epoca getByPrimaryKey(String nrEpoca) throws Exception {
        Epoca epoca = EpocaServico.getByPrimaryKey(nrEpoca);

        if (null != epoca.getNr_epoca()) {
            System.out.println("Época: " + epoca.getNr_epoca()
                    + "Data Inicio/ Fim: " + epoca.getData_inicio() + "/ " + epoca.getData_fim());
        } else {
            System.out.println("Época Inexistente.");
            epoca = null;
        }
        return epoca;
    }

    public void insert(Epoca epoca) throws Exception {
        try {
            EpocaServico.insertEpoca(epoca.getNr_epoca(), epoca.getData_inicio(), epoca.getData_fim());
            System.out.println("Epoca: " + epoca.getNr_epoca() + " inserida com sucesso");
        } catch (Exception e) {
            System.out.println("ERROR insert epoca: " + e.toString());
        }

    }

    public void update(Epoca epoca) throws Exception {
        try {
            EpocaServico.updateEpoca(epoca.getNr_epoca(), epoca.getData_inicio(), epoca.getData_fim());
            System.out.println("Epoca: " + epoca.getNr_epoca() + " alterada com sucesso");
        } catch (Exception e) {
            System.out.println("ERROR update epoca: " + e.toString());
        }

    }

    public void delete(String nrEpoca) throws Exception {
        try {
            EpocaServico.deleteByPrimaryKey(nrEpoca);
            System.out.println("Epoca: " + nrEpoca + " eliminada");
        } catch (Exception e) {
            System.out.println("ERROR delete epoca " + nrEpoca + " : " + e.toString());
        }

    }
*/
}
