package com.pos.pucminas.sgm.exception;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.MultiValueMap;

public class GatewayClientResponse implements ClientHttpResponse {
	
	private HttpStatus statusCode;
	
	private String errorMessage;
	
	public GatewayClientResponse(HttpStatus statusCode, String errorMessage) {
		super();
		this.statusCode = statusCode;
		this.errorMessage = errorMessage;
	}

	@Override
	public InputStream getBody() throws IOException {
		return new ByteArrayInputStream(this.errorMessage.getBytes());
	}

	@Override
	public HttpHeaders getHeaders() {
		return new HttpHeaders();
	}

	@Override
	public HttpStatus getStatusCode() throws IOException {
		return this.statusCode;
	}

	@Override
	public int getRawStatusCode() throws IOException {
		return this.statusCode.value();
	}

	@Override
	public String getStatusText() throws IOException {
		return this.errorMessage;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}
