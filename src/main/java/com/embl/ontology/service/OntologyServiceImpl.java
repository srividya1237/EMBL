package com.embl.ontology.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.embl.ontology.entity.OntologyEntity;
import com.embl.ontology.repository.OntologyRepo;

@Service
public class OntologyServiceImpl implements OntologyService {

	@Autowired
	private OntologyRepo ontologyRepo;
	
	@Override
	public OntologyEntity saveOntology(OntologyEntity OntologyData) {
		// TODO Auto-generated method stub
		OntologyEntity ontologyData = ontologyRepo.save(OntologyData);
		return ontologyData;
	}

	@Override
	public Optional<OntologyEntity> getOntologyData(String ontologyId) {
		// TODO Auto-generated method stub
		Optional<OntologyEntity> ontologyData = ontologyRepo.findById(ontologyId);
		
		return ontologyData;
	}

}
