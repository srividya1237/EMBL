package com.embl.ontology;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class OntologyApplication {

	public static void main(String[] args) {
		SpringApplication.run(OntologyApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		
		
		RestTemplate rest=new RestTemplate(factory);
		//rest.setErrorHandler(new SicRestErrorHandler());
		
		return rest;
	}
}
