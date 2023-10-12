package com.embl.ontology.exceptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;


public class OLSRestErrorHandler extends DefaultResponseErrorHandler {

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getBody()))) {
				String httpBodyResponse = reader.lines().collect(Collectors.joining(""));

				String errorMessage = httpBodyResponse;
                     
				//System.out.println( "SicRestErrorHandler.handleError body=" + errorMessage + ", status=" + response.getStatusText() + ", code=" + response.getStatusCode() );
				throw new OLSException( errorMessage, response.getStatusCode() );
			}
		}
	}
}