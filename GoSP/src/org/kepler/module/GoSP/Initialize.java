package org.kepler.module.GoSP;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kepler.sms.OntologyCatalog;

/**
 * Called by Kepler at the initialization of the module.
 * 
 * @author Samuel Thiriot
 */
public class Initialize implements org.kepler.module.ModuleInitializer {

	private static final Log log = LogFactory.getLog(Initialize.class);


	public Initialize() {
	}

	
	@Override
	public void initializeModule() {

		// create ontology elements 
		
		GoSPOntologyCatalog catalog = GoSPOntologyCatalog.instance();

		catalog.addConcept("Disciplines","gosp_root","GoSP");
		
			catalog.addConcept("Disciplines","gosp_input","input");
			catalog.assignSuperConcept("Disciplines","gosp_input","gosp_root");

				catalog.addConcept("Disciplines","gosp_input_dict","dictionary");
				catalog.assignSuperConcept("Disciplines","gosp_input_dict","gosp_input");
		
				catalog.addConcept("Disciplines","gosp_input_bn","Bayesian network");
				catalog.assignSuperConcept("Disciplines","gosp_input_bn","gosp_input");
		
				catalog.addConcept("Disciplines","gosp_input_sample","Sample");
				catalog.assignSuperConcept("Disciplines","gosp_input_sample","gosp_input");
		
			catalog.addConcept("Disciplines","gosp_output","output");
			catalog.assignSuperConcept("Disciplines","gosp_output","gosp_root");
			
				catalog.addConcept("Disciplines","gosp_output_sample","sample");
				catalog.assignSuperConcept("Disciplines","gosp_output_sample","gosp_output");
			
			catalog.addConcept("Disciplines","gosp_sampling","sampling");
			catalog.assignSuperConcept("Disciplines","gosp_sampling","gosp_root");
			
	}

}
