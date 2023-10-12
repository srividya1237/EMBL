package com.embl.ontology;

import com.embl.ontology.exceptions.OLSException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import com.embl.ontology.service.OLSService;
import com.embl.ontology.service.OntologyService;
import com.embl.ontology.controller.OntologyController;
import com.embl.ontology.dto.OntologyDTO;
import com.embl.ontology.entity.OntologyEntity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OntologyController.class)
public class OntologyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OntologyService ontologyService;

    @MockBean
    private OLSService olsService;
    
    @MockBean
    private RestTemplate restTempate;

    @Test
    public void testGetOntology_found() throws Exception {
        String ontologyId = "efo";

        // Prepare the mock service response
        OntologyEntity ontologyDTO = new OntologyEntity();
        ontologyDTO.setOntologyId(ontologyId);
        ontologyDTO.setOntologyData("{\"versionIri\":\"http://www.ebi.ac.uk/efo/releases/v3.58.0/efo.owl\",\"creators\":[],\"description\":\"The Experimental Factor Ontology (EFO) provides a systematic description of many experimental variables available in EBI databases, and for external projects such as the NHGRI GWAS catalogue. It combines parts of several biological ontologies, such as anatomy, disease and chemical compounds. The scope of EFO is to support the annotation, analysis and visualization of data handled by many groups at the EBI and as the core ontology for OpenTargets.org\",\"annotations\":null,\"oboSlims\":false,\"labelProperty\":\"http://www.w3.org/2000/01/rdf-schema#label\",\"fileLocation\":\"http://www.ebi.ac.uk/efo/efo.owl\",\"title\":\"Experimental Factor Ontology\",\"version\":\"3.58.0\",\"isSkos\":false,\"preferredPrefix\":\"EFO\",\"hiddenProperties\":[\"http://www.ebi.ac.uk/efo/has_flag\"],\"hierarchicalProperties\":[\"http://purl.obolibrary.org/obo/BFO_0000050\",\"http://purl.obolibrary.org/obo/RO_0002202\"],\"definitionProperties\":[\"http://purl.obolibrary.org/obo/IAO_0000115\",\"http://www.ebi.ac.uk/efo/definition\"],\"preferredRootTerms\":[],\"namespace\":\"efo\",\"tracker\":null,\"synonymProperties\":[\"http://www.ebi.ac.uk/efo/alternative_term\",\"http://www.geneontology.org/formats/oboInOwl#hasExactSynonym\"],\"logo\":null,\"id\":\"efo\",\"baseUris\":[\"http://www.ebi.ac.uk/efo/EFO_\"],\"mailingList\":\"efo-users@lists.sourceforge.net\",\"allowDownload\":false,\"homepage\":\"http://www.ebi.ac.uk/efo\"}");

        when(ontologyService.getOntologyData(any())).thenReturn(java.util.Optional.of(ontologyDTO));
        mockMvc.perform(get("/ontology/getDetails/{OntologyId}", ontologyId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.ontologyId").value(ontologyId));
    }
    
    @Test
    public void testGetOntology_notFound() throws Exception {
        String ontologyId = "xyz";

        when(ontologyService.getOntologyData(any())).thenReturn(java.util.Optional.empty());
        when(olsService.getOntologyOLS(any())).thenThrow(new OLSException("Ontology not available in OLS", null));

        mockMvc.perform(get("/ontology/getDetails/{OntologyId}", ontologyId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.name()))
                .andExpect(jsonPath("$.message").value("Ontology not available in OLS"))
                .andExpect(jsonPath("$.errorMessage").value("Ontology not available in OLS"));
    }

    @Test
    public void testGetOntology_exception() throws Exception {
        String ontologyId = "null";

        when(ontologyService.getOntologyData(any())).thenThrow(new RuntimeException("An unexpected error occurred"));

        mockMvc.perform(get("/ontology/getDetails/{OntologyId}", ontologyId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.name()))
                .andExpect(jsonPath("$.message").value("Ontology not available in OLS"))
                .andExpect(jsonPath("$.errorMessage").value("An unexpected error occurred"));
    }
}
