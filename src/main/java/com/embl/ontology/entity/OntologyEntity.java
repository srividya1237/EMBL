package com.embl.ontology.entity;

import java.util.HashMap;
import java.util.Map;

import org.bson.json.JsonObject;
import org.json.JSONArray;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Document(collection = "Ontology")
public class OntologyEntity {

	 @Id 
	 private String ontologyId;
	 private String title;		
	 private String description;
   	 private JSONArray definitionProperties;
	 private JSONArray synonymProperties;
	 private String ontologyData;
	 
}
