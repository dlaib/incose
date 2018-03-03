package com.tacstp.model;

import com.google.gson.annotations.Expose;

public class Segmento {

	@Expose private String choice;
	@Expose private String aerolinea;
	@Expose private String numeroVuelo;
	@Expose private String salida; // ver si se adapta

	@Expose private String duracion;
	@Expose private String origen; 
	@Expose private String destino;
	
	public Segmento(String aerolinea, String numeroVuelo, String salida,
			String duracion, String origen, String destino,String choice) {
		super();
		this.aerolinea = aerolinea;
		this.numeroVuelo = numeroVuelo;
		this.salida = salida;
		this.duracion = duracion;
		this.origen = origen;
		this.destino = destino;
		this.choice = choice;
	}
	
	public Segmento(){
	}
	
	public String getAerolinea() {
		return aerolinea;
	}
	public void setAerolinea(String aerolinea) {
		this.aerolinea = aerolinea;
	}
	public String getNumeroVuelo() {
		return numeroVuelo;
	}
	public void setNumeroVuelo(String numeroVuelo) {
		this.numeroVuelo = numeroVuelo;
	}
	public String getSalida() {
		return salida;
	}
	public void setSalida(String salida) {
		this.salida = salida;
	}
	public String getDuracion() {
		return duracion;
	}
	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	public String getDestino() {
		return destino;
	}
	public void setDestino(String destino) {
		this.destino = destino;
	}
	public String getChoice() {
		return choice;
	}
	public void setChoice(String choice) {
		this.choice = choice;
	}
	
	
}
