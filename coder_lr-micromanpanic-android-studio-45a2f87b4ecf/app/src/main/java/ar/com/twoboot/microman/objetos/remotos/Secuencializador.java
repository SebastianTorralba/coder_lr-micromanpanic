package ar.com.twoboot.microman.objetos.remotos;

import ar.com.twoboot.microman.objetos.DalusObject;

public class Secuencializador extends DalusObject {
	private Long id;
	private String secuencializador;
	public String getSecuencializador() {
		return secuencializador;
	}
	public Secuencializador(String secuencializador) {
		super();
		this.secuencializador = secuencializador;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	

}
