package com.tacstp.model;

import com.google.gson.annotations.Expose;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
@Entity
public class Amigo {
	
	@Expose @Id private String fbId;
	@Expose private String nombre;
	
	public Amigo(){
	}
	
	public Amigo(String fbId, String nombre){
		this.fbId = fbId;
		this.nombre = nombre;
	}
	
	public String getFbId() {
		return fbId;
	}
	public void setFbId(String fbId) {
		this.fbId = fbId;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
