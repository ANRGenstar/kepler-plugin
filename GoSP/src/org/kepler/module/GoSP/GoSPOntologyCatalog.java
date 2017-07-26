package org.kepler.module.GoSP;

import java.net.URI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kepler.sms.NamedOntModel;
import org.kepler.sms.OntologyCatalog;
import org.semanticweb.owl.model.AddAxiom;
import org.semanticweb.owl.model.OWLAxiom;
import org.semanticweb.owl.model.OWLAxiomAnnotationAxiom;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLDataFactory;
import org.semanticweb.owl.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owl.model.OWLDataType;
import org.semanticweb.owl.model.OWLDeclarationAxiom;
import org.semanticweb.owl.model.OWLEntity;
import org.semanticweb.owl.model.OWLLabelAnnotation;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyChangeException;
import org.semanticweb.owl.model.OWLOntologyManager;
import org.semanticweb.owl.model.OWLOntologyStorageException;
import org.semanticweb.owl.vocab.XSDVocabulary;

/**
 * An extension of the Kepler default ontology catalog. 
 * Just created to be able to access protected fields, else simple calls 
 * would have been enough.
 * 
 * @author Samuel Thiriot
 */
public class GoSPOntologyCatalog extends OntologyCatalog {

	private static final Log log = LogFactory.getLog(GoSPOntologyCatalog.class);

	private static GoSPOntologyCatalog instance = null;
	
	//public static String DEFAULT_NSPREFIX = "http://genstar.org/ontology#";
	private static String DEFAULT_NSPREFIX =  "#";//"http://seek.ecoinformatics.org/ontology#";


	
	
	public static GoSPOntologyCatalog instance() {
		if (instance == null)
			instance = new GoSPOntologyCatalog();
		return instance;
	}
	
	public GoSPOntologyCatalog(boolean initialize) {
		super(initialize);
	}

	public GoSPOntologyCatalog() {
	}


	/**
	 * Adds a concept in a given ontology. 
	 * @param defaultOnt
	 * @param conceptName
	 * @param conceptLabel
	 */
	public void addConcept(String ontologyName, String conceptName, String conceptLabel) {

		final NamedOntModel ontModel = getNamedOntModel(ontologyName);
		if (ontModel == null) {
			log.warn("unable to find ontology named: "+ontologyName+", aborting the creation of concept "+conceptName);
			return;
		}
		final OWLOntology ont = ontModel.getOntology();
					
		final OWLOntologyManager manager = getManager();
		
		final URI createdClassURI = URI.create(DEFAULT_NSPREFIX+conceptName);
		
		OWLDataFactory factory = manager.getOWLDataFactory();
		OWLClass c = factory.getOWLClass(createdClassURI);

		OWLAxiom axiom = factory.getOWLDeclarationAxiom(c);
		OWLLabelAnnotation annotation = factory.getOWLLabelAnnotation(conceptLabel);
		OWLAxiom axiom2 = factory.getOWLEntityAnnotationAxiom(c, annotation);
		
		/*
		OWLDataType stringString = factory.getOWLDataType(XSDVocabulary.STRING.getURI());

		factory.getOWLDataPropertyRangeAxiom(property, stringString);
		 	
		 	*/
		  
		//factory.getOWLLabelAnnotation(arg0, arg1)
		//rdf:datatype="&xsd;string"
		try {
			manager.applyChange(new AddAxiom(ont, axiom));
			manager.applyChange(new AddAxiom(ont, axiom2));
		} catch (OWLOntologyChangeException ex) {
			log.error("Error adding class declaration axiom to ontology", ex);
		}
		
		try {
			manager.saveOntology(ont);
		} catch (OWLOntologyStorageException ex) {
			log.error("Error saving ontology "+ontologyName, ex);
		}
	}
	
	public void assignSuperConcept(String ontologyName, String subConceptName, String superConceptName) {
		
		final NamedOntModel ontModel = getNamedOntModel(ontologyName);
		if (ontModel == null) {
			log.warn("unable to find ontology named: "+ontologyName+", aborting the linkage of concept "+subConceptName);
			return;
		}
		final OWLOntology ont = ontModel.getOntology();
					
		
		final OWLOntologyManager manager = getManager();

		OWLDataFactory factory = manager.getOWLDataFactory();

		// check if sub and sup have been created already
		OWLClass sub = factory.getOWLClass(URI.create(DEFAULT_NSPREFIX
				+ subConceptName));
		OWLClass sup = factory.getOWLClass(URI.create(DEFAULT_NSPREFIX
				+ superConceptName));
		OWLAxiom axiom = factory.getOWLSubClassAxiom(sub, sup);
		try {
			manager.applyChange(new AddAxiom(ont, axiom));
		} catch (OWLOntologyChangeException ex) {
			log.error("Error adding subclass axiom to ontology", ex);
		}
		
		try {
			manager.saveOntology(ont);
		} catch (OWLOntologyStorageException ex) {
			log.error("Error saving ontology "+ontologyName, ex);
		}
	}
	
}
