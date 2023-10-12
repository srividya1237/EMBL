package com.embl.ontology.service;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.embl.ontology.exceptions.OLSException;
import com.embl.ontology.exceptions.OLSRestErrorHandler;

@Service
public class OLSService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${embl.service.olsurl}")
	String olsurl;
	
	public JSONObject getOntologyOLS(String ontologyId) throws RestClientException,OLSException{
		
		HttpHeaders headers = new HttpHeaders();		
		//final HttpEntity<String> entity = new HttpEntity<String>(headers);
		String url = String.format(olsurl, ontologyId);
//		Object result = restTemplate.getForObject(url, Object.class);
    	restTemplate.setErrorHandler(new OLSRestErrorHandler());

		ResponseEntity<String> result = restTemplate.getForEntity(url,String.class );
		//ResponseEntity<JSONObject> result = restTemplate.exchange(url, HttpMethod.GET, entity, JSONObject.class);   
		JSONObject configdata = new JSONObject(result.getBody());
		return configdata;
		
	}

}
