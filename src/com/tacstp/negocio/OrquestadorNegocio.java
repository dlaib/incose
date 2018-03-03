package com.tacstp.negocio;

import java.util.Set;

import com.tacstp.dao.UsuarioDAO;
import com.tacstp.model.Amigo;
import com.tacstp.model.Recomendacion;
import com.tacstp.model.Usuario;
import com.tacstp.model.Viaje;
import com.tacstp.rest.model.Itinerario;
import com.tacstp.rest.model.RecomendacionAmigo;
import com.tacstp.services.IServicioDespegar;
import com.tacstp.services.ServicioDespegar;
import com.tacstp.services.ServicioFB;
import com.tacstp.services.ServicioWebservicex;

public class OrquestadorNegocio implements Negocio{
	
	private final UsuarioDAO usuarioDAO;
	private final IServicioDespegar servicioDespegar = new ServicioDespegar();
	private final ServicioWebservicex servicioWebservicex = new ServicioWebservicex();
	private final ServicioFB servicioFB = new ServicioFB();
	
	public OrquestadorNegocio(UsuarioDAO usDAO){
		this.usuarioDAO = usDAO;
	}
	
	@Override
	public Usuario obtenerUsuario(String fbId) {
		return usuarioDAO.obtenerUsuario(fbId);
	}

	@Override
	public long guardarUsuario(Usuario usuario) {
		servicioFB.validarIdentidad(usuario.getFbToken());
		return usuarioDAO.guardarUsuario(usuario);
	}

	@Override
	public Set<Viaje> obtenerViajeDeUsuario(String id) {
		return usuarioDAO.obtenerViajesDeUsuario(id);
	}

	@Override
	public void agregarViajeAUsuario(String id, Itinerario itinerario) {
		servicioFB.validarIdentidad(usuarioDAO.obtenerUsuario(id).getFbToken());
		Viaje viaje = servicioDespegar.obtenerItinerario(itinerario);
		usuarioDAO.agregarViajeAUsuario(id,viaje);
		servicioFB.publicarEnMuro(id,viaje);
	}
	
	@Override
	public void cambiarEstadoRecomendacionDeUsuario(String id,
			Recomendacion recomendacion) {
		servicioFB.validarIdentidad(usuarioDAO.obtenerUsuario(id).getFbToken());
		Viaje viajeRecomendado = usuarioDAO.obtenerViajeRecomendado(id,recomendacion.getIdRecomendacion());
		usuarioDAO.cambiarEstadoRecomendacionDeUsuario(id, recomendacion);
		if (recomendacion.getEstado().equalsIgnoreCase("aprobada")){
			usuarioDAO.agregarViajeAUsuario(id, viajeRecomendado);
			servicioFB.enviarNotificacion(usuarioDAO.obtenerAmigoQueRecomendo(id, recomendacion), "Aceptaron un viaje que recomendaste!!");
			servicioFB.publicarEnMuro(id,viajeRecomendado);
		}
	}

	@Override
	public void agregarRecomendacionDeUsuario(String id, RecomendacionAmigo recomendacionAmigo) {
		servicioFB.validarIdentidad(usuarioDAO.obtenerUsuario(id).getFbToken());
		usuarioDAO.agregarRecomendacionDeUsuario(recomendacionAmigo.getFbId(), new Recomendacion(new Amigo(id, ""), usuarioDAO.obtenerViajeARecomendar(id, recomendacionAmigo), "espera"));
		servicioFB.enviarNotificacion(recomendacionAmigo.getFbId(), "Te Recomendaron un viaje!!");
	}

	@Override
	public Set<Recomendacion> obtenerRecomendacionDeUsuario(String id) {
		return usuarioDAO.obtenerRecomendacionesDeUsuario(id);
	}

	@Override
	public Set<Viaje> obtenerItinerarios(String site,String from, String to,
			String departureDate, String returnDate) {
		return servicioDespegar.listaViajes(site, from, to, departureDate,returnDate);
	}

	@Override
	public String[] obtenerCoordenadas(String codigo){
		return servicioWebservicex.obtenerLatitudYLongitudDeAeropuerto(codigo);
	}

}
