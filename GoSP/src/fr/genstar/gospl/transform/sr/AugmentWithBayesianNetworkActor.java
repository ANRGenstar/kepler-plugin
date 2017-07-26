package fr.genstar.gospl.transform.sr;

import core.metamodel.pop.APopulationEntity;
import core.util.excpetion.GSIllegalRangedData;
import fr.genstar.GoSPTypes;
import gospl.GosplPopulation;
import gospl.algo.sr.bn.BayesianNetworkCompletionSampler;
import gospl.algo.sr.bn.CategoricalBayesianNetwork;
import ptolemy.actor.TypedAtomicActor;
import ptolemy.actor.TypedIOPort;
import ptolemy.data.ObjectToken;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

public class AugmentWithBayesianNetworkActor extends TypedAtomicActor {
	
	protected final TypedIOPort portBN;
	protected final TypedIOPort inputPopulation;
	
	protected final TypedIOPort outputPopulation;
	
	public AugmentWithBayesianNetworkActor(CompositeEntity container, String name) throws NameDuplicationException, IllegalActionException {
		super(container, name);
		
		inputPopulation = new TypedIOPort(this, "population");
		inputPopulation.setInput(true);
		inputPopulation.setMultiport(false);
		inputPopulation.setTypeEquals(GoSPTypes.GOSPL_POPULATION);
		
		portBN = new TypedIOPort(this, "Bayesian_network");
		portBN.setInput(true);
		portBN.setMultiport(false);
		portBN.setTypeEquals(GoSPTypes.GOSPL_BAYESIAN_NETWORK);
		
		outputPopulation = new TypedIOPort(this, "augmented population");
		outputPopulation.setOutput(true);
		outputPopulation.setMultiport(true);
		outputPopulation.setTypeEquals(GoSPTypes.GOSPL_POPULATION);
		
	}
	
	@Override
	public void fire() throws IllegalActionException {

		super.fire();
		
		
		// retrieve inputs
		// ... the Bayesian network
		CategoricalBayesianNetwork bn = (CategoricalBayesianNetwork)((ObjectToken)portBN.get(0)).getValue();;
		GosplPopulation inputPop = (GosplPopulation)((ObjectToken)inputPopulation.get(0)).getValue();;

		// retrieve parameters input

		BayesianNetworkCompletionSampler sampler ;
		try {
			sampler = new BayesianNetworkCompletionSampler(bn);
		} catch (GSIllegalRangedData e) {
			throw new IllegalActionException(e.getLocalizedMessage());
		}

		// BUILD THE POPULATION
		GosplPopulation augmentedPopulation = new GosplPopulation();

		for (APopulationEntity e: inputPop) {
			
			APopulationEntity novelEntity = sampler.complete(e);			
			augmentedPopulation.add(novelEntity);
			
		}
		outputPopulation.broadcast(new ObjectToken(augmentedPopulation, augmentedPopulation.getClass()));
		
	}

}
