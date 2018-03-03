package com.tacstp.model;

import com.google.gson.annotations.Expose;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Recomendacion {

	@Expose @Id private long idRecomendacion;
	@Parent Ref<Usuario> parent;
	@Expose private Amigo amigo;
	@Expose private Viaje viaje;
	@Expose private String estado;
	
	
	public Recomendacion(Amigo amigo, Viaje viaje, String estado) {
		super();
		this.amigo = amigo;
		this.viaje = viaje;
		this.estado = estado;
	}
	
	public Recomendacion() {
	}
	
	public long getIdRecomendacion() {
		return idRecomendacion;
	}

	public void setIdRecomendacion(long idRecomendacion) {
		this.idRecomendacion = idRecomendacion;
	}
	
	public Amigo getAmigo() {
		return amigo;
	}
	public void setAmigo(Amigo amigo) {
		this.amigo = amigo;
	}
	public Viaje getViaje() {
		return viaje;
	}
	public void setViaje(Viaje viaje) {
		this.viaje = viaje;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Usuario getParent() {
		return parent.get();
	}

	public void setParent(Usuario parent) {
		this.parent = Ref.create(parent);
	}
}
