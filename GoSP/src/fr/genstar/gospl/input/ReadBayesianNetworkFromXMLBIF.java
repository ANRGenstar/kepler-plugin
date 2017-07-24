package fr.genstar.gospl.input;

import java.io.File;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.genstar.GoSPTypes;
import gospl.algo.sr.bn.CategoricalBayesianNetwork;
import ptolemy.actor.lib.Source;
import ptolemy.data.ObjectToken;
import ptolemy.data.expr.FileParameter;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

/**
 * A reader able to parse Bayesian networks from the XMLBIF file format
 * 
 * @author Samuel Thiriot
 *
 */
public class ReadBayesianNetworkFromXMLBIF extends Source {

	public static Log log = LogFactory.getLog(ReadBayesianNetworkFromXMLBIF.class);

	public FileParameter paramFileData;

	public ReadBayesianNetworkFromXMLBIF(CompositeEntity container, String name)
			throws NameDuplicationException, IllegalActionException {
		super(container, name);

		// set output type
		output.setTypeEquals(GoSPTypes.GOSPL_BAYESIAN_NETWORK);
		
		// add parameters
		paramFileData = new FileParameter(this, "paramFileData", false);

	}
	

	@Override
	public void fire() throws IllegalActionException {

		super.fire();
		
		// open the file 
		File fileData = paramFileData.asFile();
		log.info("opening as a Bayesian network: "+fileData.getName());

		CategoricalBayesianNetwork bn = CategoricalBayesianNetwork.loadFromXMLBIF(fileData);
		
		log.info("parsed a Bayesian network with "+
				bn.getNodes().size()+" variables: "+
				bn.getNodes().stream().map(v->v.getName()).collect(Collectors.joining(","))
				);
		
		output.send(0, new ObjectToken(bn, bn.getClass()));
		
	}

}
