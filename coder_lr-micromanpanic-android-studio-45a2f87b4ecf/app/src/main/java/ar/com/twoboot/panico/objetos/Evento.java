package ar.com.twoboot.panico.objetos;

import java.util.Date;

public class Evento {
	private Usuario usuario;
	private Double lat;
	private Double lon;
	private Integer tipo;
	private Float precision;
	private Date fecha;

	public Evento() {
		super();
	}

	public Evento(Usuario usuario, Double lat, Double lon, Integer tipo, Float precision, Date fecha) {
		super();
		this.usuario = usuario;
		this.lat = lat;
		this.lon = lon;
		this.tipo = tipo;
		this.precision = precision;
		this.fecha = fecha;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public Float getPrecision() {
		return precision;
	}

	public void setPrecision(Float precision) {
		this.precision = precision;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}
