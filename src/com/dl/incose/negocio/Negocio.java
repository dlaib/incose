package com.dl.incose.negocio;

import java.util.Set;

import com.dl.incose.model.Recomendacion;
import com.dl.incose.model.Usuario;
import com.dl.incose.model.Viaje;
import com.tacstp.rest.model.Itinerario;
import com.tacstp.rest.model.RecomendacionAmigo;
/**
 * @author tacstp
 * 
 * Interface que define todas las reglas de negocio que expondra el BE.
 *
 */
public interface Negocio {
	/**
	 * Obtengo el usuario. 
	 * @param fbId id de Facebook del usuario.
	 * 
	 * @return Usuario {@link Usuario} 
	 */
	public Usuario obtenerUsuario(String fbId);
	/**
	 * guardar usuario. 
	 * @param usuario {@link Usuario}.
	 * 
	 * @return id Generado por el sistema, para identificar al usuario.
	 */
	public long guardarUsuario(Usuario usuario);
	/**
	 * Dado un usuario obtengo su lista de viajes. 
	 * @param id Facebook id.
	 * 
	 * @return Set<Viaje> Listado de viajes del usuario.
	 */
	public Set<Viaje> obtenerViajeDeUsuario(String id);
	/**
	 * Dado un usuario creo un nuevo viaje. 
	 * @param id Facebook id.
	 * @param Itinerario {@link Itinerario} itinerario seleccionado de despegar para guardar.
	 * 
	 */
	public void agregarViajeAUsuario(String id, Itinerario Itinerario);
	/**
	 * Modifico el estado de una recomendacion ya creada.
	 * @param id Facebook id.
	 * @param recomendacion {@link Recomendacion} Recomendacion modificada.
	 * 
	 */
	public void cambiarEstadoRecomendacionDeUsuario(String id, Recomendacion recomendacion);
	/**
	 * Creo recomendacion para un usuario.
	 * @param id Facebook id.
	 * @param recomendacionAmigo {@link RecomendacionAmigo}.
	 * 
	 */
	public void agregarRecomendacionDeUsuario(String id, RecomendacionAmigo recomendacionAmigo);
	/**
	 * Obtener Recomendacion.
	 * @param id Facebook id.
	 * @return Set<Recomendacion> Listado de las recomendaciones que recibio el usuario.
	 */
	public Set<Recomendacion> obtenerRecomendacionDeUsuario(String id);
	/**
	 * Obtengo los itinerarios posibles de Despegar dada una busqueda.
	 * @param site Ubicacion geografica del sitio (p.ej:ar)
	 * @param from Codigo Origen
	 * @param to Codigo Destino
	 * @param departureDate Fecha de Partida(AAAA/MM/DD)
	 * @param returnDate Fecha de Retorno (AAAA/MM/DD)
	 * 
	 * @return Set<Viaje> Lista de los itinerarios.
	 * 
	 */
	public Set<Viaje> obtenerItinerarios(String site, String from, String to,String departureDate,String returnDate);
	/**
	 * Obtengo los itinerarios posibles de Despegar dada una busqueda.
	 * @param codigo Codigo de aeropuerto.
	 * 
	 * @return String[] latitud y longitud([0]-lat,[1]-long)
	 * 
	 */
	public String[] obtenerCoordenadas(String codigo);

}
