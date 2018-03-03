package com.dl.incose.model;

import java.util.HashSet;
import java.util.Set;


import com.google.gson.annotations.Expose;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

@Entity
public class Usuario {
	@Expose @Id private String fbId;
	@Index @Load private Set<Ref<Viaje>> viajesDS;
	@Expose @Ignore private Set<Viaje> viajes;
	@Expose @Ignore private long userId;
	@Expose private String fbToken;
	@Expose @Ignore private Set<Recomendacion> recomendaciones;
	@Index @Load private Set<Ref<Recomendacion>> recomendacionesDS;

	public Usuario(){
	}
	
	public Usuario(String fbId) {
		super();
		this.fbId = fbId;
		this.viajesDS = new HashSet<Ref<Viaje>>();
		this.recomendacionesDS = new HashSet<Ref<Recomendacion>>();
		this.recomendaciones = new HashSet<Recomendacion>();
		this.viajes = new HashSet<Viaje>();
	}
	
	public Set<Ref<Viaje>> getViajesDS() {
		return viajesDS;
	}
	
	public void setViajesDS(Set<Ref<Viaje>> viajesDS) {
		this.viajesDS = viajesDS;
	}
	
	public Set<Ref<Recomendacion>> getRecomendacionesDS() {
		return recomendacionesDS;
	}

	public void setRecomendacionesDS(Set<Ref<Recomendacion>> recomendacionesDS) {
		this.recomendacionesDS = recomendacionesDS;
	}

	public void setViajes(Set<Viaje> viajes) {
		this.viajes = viajes;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public void setFbToken(String fbToken) {
		this.fbToken = fbToken;
	}
	
	public void setFbId(String fbId) {
		this.fbId = fbId;
	}
	
	public Set<Viaje> getViajes() {
		return viajes;
	}

	public void setRecomendaciones(Set<Recomendacion> recomendaciones) {
		this.recomendaciones = recomendaciones;
	}
	
	public long getUserId() {
		return userId;
	}

	public String getFbToken() {
		return fbToken;
	}

	public String getFbId() {
		return fbId;
	}
	
	public Set<Recomendacion> getRecomendaciones() {
		return recomendaciones;
	}
}
