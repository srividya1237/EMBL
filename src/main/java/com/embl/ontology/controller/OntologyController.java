package com.embl.ontology.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.embl.ontology.dto.OntologyDTO;
import com.embl.ontology.entity.OntologyEntity;
import com.embl.ontology.exceptions.OLSException;
import com.embl.ontology.service.OLSService;
import com.embl.ontology.service.OntologyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/ontology")
public class OntologyController {
	
    private static final Logger logger = LoggerFactory.getLogger(OntologyController.class);

	
	@Autowired
	private OntologyService ontologyService;
	
	@Autowired
	private OLSService olsService;
	
	@GetMapping("/getDetails/{OntologyId}")
	public ResponseEntity<Object> getOntology(@PathVariable("OntologyId") String OntologyId ) {
		Map<String, Object> map = new HashMap<String, Object>();
	    try {
			Optional<OntologyEntity>  ontologyData = ontologyService.getOntologyData(OntologyId);
			OntologyDTO ontologyDto = new OntologyDTO();
			if(ontologyData.isEmpty()) {
				JSONObject  ontologyOLSData = olsService.getOntologyOLS(OntologyId);
				OntologyEntity saveontologyData = new OntologyEntity();
				if(null!=ontologyOLSData && ontologyOLSData.has("config"))
				{
					
					saveontologyData.setOntologyId(OntologyId);
					saveontologyData.setOntologyData(ontologyOLSData.getJSONObject("config").toString());				
					OntologyEntity saveObject = ontologyService.saveOntology(saveontologyData);
					 map.put("timestamp", new Date());
			         map.put("status", HttpStatus.OK);
			         map.put("message", "Success");
					
			         JSONObject configdata = new JSONObject(saveObject.getOntologyData());
			         ontologyDto.setOntologyId(OntologyId);
			         ontologyDto.setDescription(configdata.getString("description"));
			         ontologyDto.setTitle(configdata.getString("title"));
			         ObjectMapper objectMapper = new ObjectMapper();
			         TypeReference<List<String>> listType = new TypeReference<List<String>>() {};		        
			         ontologyDto.setDefinitionProperties(objectMapper.readValue(configdata.getJSONArray("definitionProperties").toString(), listType));
			         ontologyDto.setSynonymProperties(objectMapper.readValue(configdata.getJSONArray("synonymProperties").toString(), listType));				
			         map.put("data", ontologyDto);
			         logger.info("Fetching ontology details from OLS and stored in local database" + saveObject);
					 return new ResponseEntity<Object>(map,HttpStatus.OK);
				}
				else {
					 map.put("timestamp", new Date());
			         map.put("status", HttpStatus.NOT_FOUND);
			         map.put("message", "Ontology not available in OLS");
			         map.put("errorMessage","Ontology not available in OLS");
			         logger.error("Ontology not found in OLS");
			         return new ResponseEntity<Object>(map,HttpStatus.NOT_FOUND);
				}
			}
			else {
			
				 map.put("timestamp", new Date());
		         map.put("status", HttpStatus.OK);
		         map.put("message", "Success");   
		         JSONObject configdata = new JSONObject(ontologyData.get().getOntologyData());
		         ontologyDto.setOntologyId(OntologyId);
		         ontologyDto.setDescription(configdata.getString("description"));
		         ontologyDto.setTitle(configdata.getString("title"));
		         ObjectMapper objectMapper = new ObjectMapper();
		         TypeReference<List<String>> listType = new TypeReference<List<String>>() {};	
				 ontologyDto.setDefinitionProperties(objectMapper.readValue(configdata.getJSONArray("definitionProperties").toString(), listType));
				 ontologyDto.setSynonymProperties(objectMapper.readValue(configdata.getJSONArray("synonymProperties").toString(), listType));
	    	     map.put("data", ontologyDto);
		         logger.info("Ontology found in local database"+ ontologyData.get());

	    	     return new ResponseEntity<Object>(map,HttpStatus.OK);
			}
			
		}catch (RestClientException | OLSException e) {
			// TODO Auto-generated catch block
			map.put("timestamp", new Date());
	        map.put("status", HttpStatus.NOT_FOUND);
	        map.put("message", "Ontology not available in OLS");
	        map.put("errorMessage",e.getMessage());
	        logger.error("Error" + e.getMessage());
	        return new ResponseEntity<Object>(map,HttpStatus.NOT_FOUND);
		} 
	    catch (JsonProcessingException | JSONException e) {
			// TODO Auto-generated catch block
			map.put("timestamp", new Date());
	        map.put("status", HttpStatus.NOT_FOUND);
	        map.put("message", "Ontology not available in OLS");
	        map.put("errorMessage",e.getMessage());
	        logger.error("Error" + e.getMessage());
	        return new ResponseEntity<Object>(map,HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			map.put("timestamp", new Date());
	        map.put("status", HttpStatus.NOT_FOUND);
	        map.put("message", "Ontology not available in OLS");
	        map.put("errorMessage",e.getMessage());
	        logger.error("Error" + e.getMessage());
	        return new ResponseEntity<Object>(map,HttpStatus.NOT_FOUND);
		}
	}

}
