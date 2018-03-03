package com.dl.incose.model;

import java.math.BigDecimal;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.google.gson.annotations.Expose;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;
@Entity
public class Viaje {
	@Expose @Id private String idViaje;
	@Parent private Ref<Usuario> parent;
	@Expose @Index private String idItinerario;
	@Expose private List<Segmento> segmentosIda;
	@Expose private List<Segmento> segmentosVuelta;
	@Expose private BigDecimal precio;
		
	public Viaje(List<Segmento> segmentosIda,List<Segmento> segmentosVuelta,BigDecimal precio,String idItinerario) {
		super();
		this.segmentosIda = segmentosIda;
		this.segmentosVuelta = segmentosVuelta;
		this.precio = precio;
		this.idItinerario = idItinerario;
	}
	public Viaje() {
	}
	
	@JsonProperty("idViaje")
	public void setIdViaje(String idViaje) {
		this.idViaje = idViaje;
	}

	public List<Segmento> getSegmentosIda() {
		return segmentosIda;
	}
	
	public void setSegmentosIda(List<Segmento> segmentosIda) {
		this.segmentosIda = segmentosIda;
	}
	
	public List<Segmento> getSegmentosVuelta() {
		return segmentosVuelta;
	}
	public void setSegmentosVuelta(List<Segmento> segmentosVuelta) {
		this.segmentosVuelta = segmentosVuelta;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
		
	public String getIdViaje() {
		return idViaje;
	}

	public BigDecimal getPrecio() {
		return precio;
	}
	public String getIdItinerario() {
		return idItinerario;
	}
	public void setIdItinerario(String idItinerario) {
		this.idItinerario = idItinerario;
	}
	public Usuario getParent() {
		return parent.get();
	}
	public void setParent(Usuario parent) {
		this.parent = Ref.create(parent);
	}
	
}
