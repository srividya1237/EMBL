package com.embl.ontology.dto;

import java.util.List;

import org.json.JSONArray;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OntologyDTO {

	
	private String ontologyId;	
	
	private String title;
	
	private String description;
	
	private List<String> definitionProperties;
	
	private List<String> synonymProperties;

}
