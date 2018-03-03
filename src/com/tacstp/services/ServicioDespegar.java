package com.tacstp.services;

import java.util.Set;

import com.tacstp.model.Viaje;
import com.tacstp.rest.model.Itinerario;

public class ServicioDespegar extends HTTPService implements IServicioDespegar {

	private static final String API_KEY = "457519f1de2d4a0fb6ea48e1c493b49d";

	@Override
	public Set<Viaje> listaViajes(String site, String from, String to,
			String departureDate, String returnDate) {
		// Ejemplo de llamada exitosa.
		// http://localhost:8080/tacstp/rest/viajes?site=ar&from=BUE&to=MIA&departure_date=2015-05-21
		StringBuilder sb = super.hacerRequestHTTP(
				this.armarURL(site, from, to, departureDate, returnDate),
				API_KEY, true);
		return new RespuestaDespegarParseada(sb.toString()).comoViajes();
	}

	@Override
	public Viaje obtenerItinerario(Itinerario nuevoItinerario) {
		StringBuilder sb = super.hacerRequestHTTP(String.format(
				"https://api.despegar.com/v3/flights/itineraries/%s",
				nuevoItinerario.getIdItinerario()), API_KEY, true);
		return new RespuestaDespegarParseada(sb.toString(), nuevoItinerario)
				.comoViaje();
	}

	private String armarURL(String site, String from, String to,
			String departureDate, String returnDate) {
		return String
				.format("https://api.despegar.com/v3/flights/itineraries?site=%s&from=%s&to=%s&departure_date=%s&return_date=%s&adults=1",
						site, from, to, departureDate, returnDate);
	}

	@Override
	public String autoComplete(String ciudad) {
		StringBuilder sb = super.hacerRequestHTTP(String.format(
				"https://api.despegar.com/v3/autocomplete?query=%s&channel=default&product=hotels&locale=es-AR&airport_result=5",
				ciudad),API_KEY, true);
		
		return sb.toString(); 
	}

}
