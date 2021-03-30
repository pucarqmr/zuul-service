package com.pos.pucminas.sgm.config;

import javax.servlet.http.HttpServletResponse;

import com.pos.pucminas.sgm.security.JwtAuthenticationConfig;
import com.pos.pucminas.sgm.security.JwtTokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@EnableWebSecurity
@Configuration
public class ZullSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private JwtAuthenticationConfig config;

    @Bean
    public JwtAuthenticationConfig jwtConfig() {
        return new JwtAuthenticationConfig();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .logout().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .anonymous()
                .and()
                    .exceptionHandling().authenticationEntryPoint(
                            (req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                    .addFilterAfter(new JwtTokenAuthenticationFilter(config),
                            UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                    .antMatchers(config.getUrl()).permitAll()
                    .antMatchers("/api/servicos/iptus").hasAnyRole("ADMIN")
                    .antMatchers(HttpMethod.GET, "/api/servicos/iptus/segundavia/**").hasAnyRole("ADMIN", "CIDADAO")
                    
                    .antMatchers(HttpMethod.GET, "/api/servicos/escolas").hasAnyRole("ADMIN", "CIDADAO")
                    
                    .antMatchers(HttpMethod.POST, "/api/servicos/alunos").hasAnyRole("ADMIN", "CIDADAO")
                    .antMatchers(HttpMethod.GET, "/api/servicos/alunos").hasAnyRole("ADMIN")
                    .antMatchers(HttpMethod.GET, "/api/servicos/alunos/**").hasAnyRole("ADMIN", "CIDADAO")
                    .antMatchers(HttpMethod.PUT, "/api/servicos/alunos/**").hasAnyRole("ADMIN", "CIDADAO")
                    .antMatchers(HttpMethod.DELETE, "/api/servicos/alunos/**").hasAnyRole("ADMIN")
                    
                    .antMatchers(HttpMethod.GET, "/api/geo/indicadores/pesquisas/**").hasAnyRole("ADMIN")
                    ;
    }

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        config.setExposedHeaders(Arrays.asList("Authorization"));
        return bean;
    }

}
