package com.tacstp.services;

import java.util.Set;

import com.tacstp.exceptions.TacsException;
import com.tacstp.model.Viaje;
import com.tacstp.rest.model.Itinerario;

/**
 * @author tacstp
 * 
 * Servicio que interactua con despegar.
 *
 */
public interface IServicioConstructoras.java {
	/**
	 * Dados los siguientes parametros obtenemos los itinerarios disponibles. 
	 * @param site Ubicacion geografica del sitio (p.ej:ar)
	 * @param from Codigo Origen
	 * @param to Codigo Destino
	 * @param departureDate Fecha de Partida(AAAA/MM/DD)
	 * @param returnDate Fecha de Retorno (AAAA/MM/DD)
	 * 
	 * @throws En caso que despegar devuelva un error o haya un problema con la conexion {@link TacsException}.
	 * @return itinerarios Lista de los itinerarios
	 *
	 */
	public Set<Viaje> listaViajes(String site,String from,String to,String departureDate,String returnDate);
	/**
	 * Dado un itinerario de despegar devuelve un viaje de nuestro sistema. 
	 * @param  nuevoItinerario {@link Itinerario}
	 * 
	 * @return Viaje {@link Viaje}
	 *
	 */
	public Viaje obtenerItinerario(Itinerario nuevoItinerario);
	/**
	 * Se obtienen los aeropuertos para una ciudad en particular. 
	 * @param ciudad Query asociada a una ciudad (p.ej: BUE)
	 * 
	 * @return JSONString respuesta identica a la obtenida por Despegar.
	 *
	 */
	public String autoComplete(String ciudad);
}
