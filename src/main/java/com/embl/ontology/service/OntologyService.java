package com.embl.ontology.service;

import java.util.Optional;

import com.embl.ontology.entity.OntologyEntity;

public interface OntologyService {

	public OntologyEntity saveOntology(OntologyEntity OntologyData);
	
	public Optional<OntologyEntity> getOntologyData(String ontologyId);
}
