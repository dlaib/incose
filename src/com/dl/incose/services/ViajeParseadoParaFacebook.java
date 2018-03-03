package com.dl.incose.services;

import java.util.Iterator;
import java.util.List;

import com.dl.incose.model.Segmento;
import com.dl.incose.model.Viaje;

public class ViajeParseadoParaFacebook {
	
	private Viaje viaje;
	
	public ViajeParseadoParaFacebook(Viaje viaje) {
		super();
		this.viaje = viaje;
	}

	public String comoString(){
		return "He Creado el siguiente vuelo en tacs.com.ar:\nEscalas IDA\n"+armarSegmentos(this.viaje.getSegmentosIda())+"Escalas VUELTA\n"+armarSegmentos(this.viaje.getSegmentosVuelta()); 
	}

	private String armarSegmentos(List<Segmento> segmentos) {
		StringBuilder sb = new StringBuilder();
		for (Iterator<Segmento> iterator = segmentos.iterator(); iterator.hasNext();) {
			Segmento segmento = (Segmento) iterator.next();
			sb.append(String.format("Numero de Vuelo:%s aerolinea:%s\n origen:%s destino:%s duracion:%s\n\n", segmento.getNumeroVuelo(),segmento.getAerolinea(),segmento.getOrigen(),segmento.getDestino(),segmento.getDuracion()));
		}
		return sb.toString();
	}
}
