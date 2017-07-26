package fr.genstar.gospl.input;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import core.metamodel.pop.APopulationAttribute;
import fr.genstar.GoSPTypes;
import gospl.algo.sr.bn.CategoricalBayesianNetwork;
import gospl.io.ReadDictionaryUtils;
import ptolemy.actor.TypedAtomicActor;
import ptolemy.actor.TypedIOPort;
import ptolemy.data.ObjectToken;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

public class ReadDictionnaryFromBayesianNetworkActor extends TypedAtomicActor {

	public static Log log = LogFactory.getLog(ReadDictionnaryFromBayesianNetworkActor.class);

	private TypedIOPort inputBayesianNetwork;

	private TypedIOPort outputDictionary;
	
	public ReadDictionnaryFromBayesianNetworkActor(CompositeEntity container, String name)
			throws NameDuplicationException, IllegalActionException {
		super(container, name);

		inputBayesianNetwork = new TypedIOPort(this, "Bayesian network");
		inputBayesianNetwork.setInput(true);
		inputBayesianNetwork.setTypeEquals(GoSPTypes.GOSPL_BAYESIAN_NETWORK);
		
		outputDictionary = new TypedIOPort(this, "dictionary");
		outputDictionary.setOutput(true);
		outputDictionary.setMultiport(true);
		outputDictionary.setTypeEquals(GoSPTypes.GOSPL_DICTIONARY);

	}
	

	@Override
	public void fire() throws IllegalActionException {

		super.fire();
		
		// get input BN
		CategoricalBayesianNetwork bn = (CategoricalBayesianNetwork) ((ObjectToken)inputBayesianNetwork.get(0)).getValue();
		
		Collection<APopulationAttribute> attributes = ReadDictionaryUtils.readBayesianNetworkAsDictionary(bn);
		
		outputDictionary.broadcast(new ObjectToken(attributes, attributes.getClass()));
		
	}

}
