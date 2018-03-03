package com.tacstp.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.tacstp.model.Segmento;
import com.tacstp.model.Viaje;
import com.tacstp.services.ServicioFB;

public class FBTest {

	private Viaje unViajeMock;
	private Segmento unSegmentoIdaMock;
	private Segmento unSegmentoVueltaMock;
	private List<Segmento> segmentosIda;
	private List<Segmento> segmentosVuelta;
	
	@Before
	public void setUp(){
		unSegmentoIdaMock = new Segmento("AA", "1", "2015-05-21", "10",  "BUE", "MIA","1");
		unSegmentoVueltaMock = new Segmento("AA", "1", "2015-05-21", "10",  "MIA", "BUE","1");
		
		segmentosIda = new ArrayList<>();
		segmentosIda.add(unSegmentoIdaMock);
		segmentosVuelta = new ArrayList<>();
		segmentosVuelta.add(unSegmentoVueltaMock);
		
		unViajeMock = new Viaje(segmentosIda,segmentosVuelta, new BigDecimal(30),"");
	}
	
	@Test
	public void testEnviarNotifiacion(){
		new ServicioFB().enviarNotificacion("100009649302654", "Notificacion de prueba");
	}
	
	@Test
	public void testPublicarEnMuro(){
		new ServicioFB().publicarEnMuro("100009649302654", unViajeMock);
	}
}
