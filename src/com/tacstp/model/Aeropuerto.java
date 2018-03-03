package com.tacstp.model;

import com.google.gson.annotations.Expose;

public class Aeropuerto {

	@Expose private String codigo;
	@Expose private String descripcion;

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;

	}

	public String getDescripcion() {
		return this.descripcion;

	}

	public Aeropuerto(String codigo, String descripcion) {
		this.codigo = codigo;
		this.descripcion = descripcion;
	}

}
