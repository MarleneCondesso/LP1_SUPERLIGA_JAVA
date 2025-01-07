/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superliga.tests;

import com.superliga.models.Evento;
import com.superliga.services.EventoService;
import java.util.ArrayList;

/**
 *
 * @author mstevz
 */
public class EventoServiceTest {
    
    public static void testGetAll(){
        EventoService es = new EventoService();
        
        ArrayList<Evento> eventos = es.getAll();
        
        eventos.forEach((evento) -> {
            System.out.println(evento.getIdJogadorJogo());
        });
        
    }
    
    
}
