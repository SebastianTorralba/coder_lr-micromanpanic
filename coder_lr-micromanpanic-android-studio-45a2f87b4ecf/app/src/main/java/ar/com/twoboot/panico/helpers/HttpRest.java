package ar.com.twoboot.panico.helpers;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;

public class HttpRest {

	private CookieManager cookies;
	private Headers.Builder headers = new Headers.Builder();
	protected OkHttpClient clientehttp;

	public HttpRest() {
		clientehttp = new OkHttpClient();
		clientehttp.setConnectTimeout(5, TimeUnit.SECONDS);
		clientehttp.setReadTimeout(5, TimeUnit.SECONDS);
		cookies = new CookieManager();
		cookies.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
		headers.add("User-Agent", "Micriopanic Cliente");
	}

	public OkHttpClient getClientehttp() {
		return clientehttp;
	}

	public void setClientehttp(OkHttpClient clientehttp) {
		this.clientehttp = clientehttp;
	}

	public Headers.Builder getHeaders() {
		return headers;
	}

	public void setHeaders(Headers.Builder headers) {
		this.headers = headers;
	}
}
