package com.embl.ontology.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.embl.ontology.entity.OntologyEntity;

public interface OntologyRepo extends MongoRepository<OntologyEntity, String> {

}
