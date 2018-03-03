package com.dl.incose.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.dl.incose.model.Aeropuerto;
import com.dl.incose.model.Segmento;
import com.dl.incose.model.Viaje;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tacstp.rest.model.Itinerario;

public class RespuestaDespegarParseada {
	
	private static final JsonParser jsonParser = new JsonParser();
	
	private String respuestaDespegar; 
	private Itinerario itinerario;
	
	public RespuestaDespegarParseada(String respuestaDespegar) {
		super();
		this.respuestaDespegar = respuestaDespegar;
		this.itinerario = null;
	}
	
	public RespuestaDespegarParseada(String respuestaDespegar,Itinerario itinerario) {
		super();
		this.respuestaDespegar = respuestaDespegar;
		this.itinerario = itinerario;
	}

	public Set<Viaje> comoViajes() {
		Set<Viaje> viajes = new HashSet<>();
		JsonObject jo = jsonParser.parse(this.respuestaDespegar).getAsJsonObject();
		JsonArray jArray = jo.get("items").getAsJsonArray();
		for (Iterator<JsonElement> iterator = jArray.iterator(); iterator.hasNext();) {
			JsonElement je = (JsonElement) iterator.next();
			viajes.add(setearViaje(je.getAsJsonObject()));
		}
		
		return viajes;
	}
	
	public Viaje comoViaje() {
		return setearViaje(this.itinerario, jsonParser.parse(this.respuestaDespegar).getAsJsonObject());
	}

	private Viaje setearViaje(Itinerario nuevoItinerario, JsonObject jo) {
		List<Segmento> segmentosIda = setearSegmentos(nuevoItinerario.getOutBoundChoice(), "outbound_choices", jo);
		List<Segmento> segmentosVuelta = setearSegmentos(nuevoItinerario.getInboundChoice(), "inbound_choices", jo);
		return new Viaje(segmentosIda, segmentosVuelta, jo.get("price_detail").getAsJsonObject().get("total").getAsBigDecimal(),jo.get("id").getAsString());
	}
	
	private Viaje setearViaje(JsonObject jsonObject) {
		List<Segmento> segmentosIda = setearSegmentos(jsonObject, "outbound_choices");
		List<Segmento> segmentosVuelta = setearSegmentos(jsonObject,"inbound_choices");
		return new Viaje(segmentosIda, segmentosVuelta, jsonObject.get("price_detail").getAsJsonObject().get("total").getAsBigDecimal(),jsonObject.get("id").getAsString()); 
	}

	private List<Segmento> setearSegmentos(String choice, String tipoDeChoice,
			JsonObject jo) {
		List<Segmento> segmentosIda = new ArrayList<>();
		for (Iterator<JsonElement> itChoices = jo.get(tipoDeChoice).getAsJsonArray().iterator(); itChoices.hasNext();) {
			JsonElement jeOC = (JsonElement) itChoices.next();
			if (jeOC.getAsJsonObject().get("choice").getAsString().equalsIgnoreCase(choice)){
				for (Iterator<JsonElement> itSegments = jeOC.getAsJsonObject().get("segments").getAsJsonArray().iterator(); itSegments.hasNext();) {
					segmentosIda.add(crearSegmento(jeOC, itSegments));
				}
			}
		}
		return segmentosIda;
	}

	private List<Segmento> setearSegmentos(JsonObject jsonObject,String choice) {
		List<Segmento> segmentos = new ArrayList<>();
		for (Iterator<JsonElement> itChoices = jsonObject.get(choice).getAsJsonArray().iterator(); itChoices.hasNext();) {
			JsonElement jeIC = (JsonElement) itChoices.next();
			for (Iterator<JsonElement> itSegments = jeIC.getAsJsonObject().get("segments").getAsJsonArray().iterator(); itSegments.hasNext();) {
				segmentos.add(crearSegmento(jeIC, itSegments));
			}
		}
		return segmentos;
	}
	
	private Segmento crearSegmento(JsonElement je,
			Iterator<JsonElement> itSegments) {
		Segmento segmento = new Segmento();
		JsonElement jeSeg = (JsonElement) itSegments.next();
		segmento.setChoice(je.getAsJsonObject().get("choice").getAsString());
		segmento.setAerolinea(jeSeg.getAsJsonObject().get("airline").getAsString());
		segmento.setDestino(jeSeg.getAsJsonObject().get("to").getAsString());
		segmento.setOrigen(jeSeg.getAsJsonObject().get("from").getAsString());
		segmento.setNumeroVuelo(jeSeg.getAsJsonObject().get("flight_id").getAsString());
		segmento.setSalida(jeSeg.getAsJsonObject().get("departure_datetime").getAsString());
		segmento.setDuracion(jeSeg.getAsJsonObject().get("duration").getAsString());
		return segmento;
	}
	
	public Aeropuerto getAeropuerto(){
		JsonObject jsonObject = jsonParser.parse(this.respuestaDespegar).getAsJsonObject();
		return new Aeropuerto(jsonObject.get("code").getAsString(),jsonObject.get("description").getAsString());
	}

}
