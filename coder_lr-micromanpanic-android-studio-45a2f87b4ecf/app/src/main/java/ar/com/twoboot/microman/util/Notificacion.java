package ar.com.twoboot.microman.util;

public class Notificacion {

	protected String cuerpo;
	private String estado;

	public Notificacion() {
		super();
	}

	public String getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}