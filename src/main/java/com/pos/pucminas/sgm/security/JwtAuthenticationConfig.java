package com.pos.pucminas.sgm.security;

import org.springframework.beans.factory.annotation.Value;

/**
 * Config JWT.
 * property 'security.jwt.secret' is mandatory.
 *
 */
public class JwtAuthenticationConfig {

    @Value("${security.jwt.url:/login}")
    private String url;

    @Value("${security.jwt.header:Authorization}")
    private String header;

    @Value("${security.jwt.prefix:Bearer}")
    private String prefix;

    @Value("${security.jwt.expiration:#{24*60*60}}")
    private int expiration; // default 24 hours

    @Value("${security.jwt.secret}")
    private String secret;

	public String getUrl() {
		return url;
	}

	public String getHeader() {
		return header;
	}

	public String getPrefix() {
		return prefix;
	}

	public int getExpiration() {
		return expiration;
	}

	public String getSecret() {
		return secret;
	}

	@Override
	public String toString() {
		return "JwtAuthenticationConfig [url=" + url + ", header=" + header + ", prefix=" + prefix + ", expiration="
				+ expiration + ", secret=" + secret + "]";
	}
    
    
}
