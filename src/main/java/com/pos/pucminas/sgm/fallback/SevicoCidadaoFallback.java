package com.pos.pucminas.sgm.fallback;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import com.netflix.hystrix.exception.HystrixTimeoutException;
import com.pos.pucminas.sgm.exception.GatewayClientResponse;

@Component
public class SevicoCidadaoFallback implements FallbackProvider {

	private static final String DEFAULT_MESSAGE = "Serviço ao cidação está indisponível! Tente novamente mais tarde.";

	@Override
	public String getRoute() {
		return "servicos-cidadao-service";
	}

	@Override
	public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
		System.out.println("fallbackResponse --> " + route);
		cause.printStackTrace();
		if (cause instanceof HystrixTimeoutException) {
            return new GatewayClientResponse(HttpStatus.GATEWAY_TIMEOUT, DEFAULT_MESSAGE);
        } else {
            return new GatewayClientResponse(HttpStatus.INTERNAL_SERVER_ERROR, DEFAULT_MESSAGE);
        }
	}
	
	
	
}
