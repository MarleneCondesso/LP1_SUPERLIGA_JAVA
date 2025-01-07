/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superliga.tests;

import com.superliga.models.JogadorContrato;
import com.superliga.services.JogadorContratoService;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author mstevz
 */
public class JogadorContratoServiceTest {
    public static void testGetAll(){
        JogadorContratoService jcs = new JogadorContratoService();
        ArrayList<JogadorContrato> jc = jcs.getAll();
        
        for(JogadorContrato contrato : jc){
            System.out.println(contrato.getNrAtleta() + " em " + contrato.getEntrada());
        }
    }
    
    public static void testGetByPrimaryKey(){
        JogadorContratoService jcs = new JogadorContratoService();
        
        JogadorContrato contrato =  jcs.getByPrimaryKey(100, Date.valueOf("2020-10-01"));
        
        System.out.println(contrato.getNrAtleta());
    }
}
