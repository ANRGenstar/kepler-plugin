package fr.genstar.gospl.transform.sr;

import core.util.excpetion.GSIllegalRangedData;
import fr.genstar.GoSPTypes;
import gospl.GosplPopulation;
import gospl.algo.sr.bn.BayesianNetworkFromScratchSampler;
import gospl.algo.sr.bn.CategoricalBayesianNetwork;
import gospl.entity.GosplEntity;
import ptolemy.actor.TypedAtomicActor;
import ptolemy.actor.TypedIOPort;
import ptolemy.data.ObjectToken;
import ptolemy.data.expr.StringParameter;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

public class SampleFromBayesianNetworkActor extends TypedAtomicActor {
	
	protected final TypedIOPort portBN;
	
	protected final StringParameter paramPopSize;
	
	protected final TypedIOPort outputPopulation;
	
	public SampleFromBayesianNetworkActor(CompositeEntity container, String name) throws NameDuplicationException, IllegalActionException {
		super(container, name);
		
		portBN = new TypedIOPort(this, "Bayesian_network");
		portBN.setInput(true);
		portBN.setMultiport(false);
		portBN.setTypeEquals(GoSPTypes.GOSPL_BAYESIAN_NETWORK);
		
		outputPopulation = new TypedIOPort(this, "population");
		outputPopulation.setOutput(true);
		outputPopulation.setMultiport(false);
		outputPopulation.setTypeEquals(GoSPTypes.GOSPL_POPULATION);
		
		paramPopSize = new StringParameter(this, "population size");
		paramPopSize.setExpression("1000");
		
	}
	
	@Override
	public void fire() throws IllegalActionException {

		super.fire();
		
		
		// retrieve inputs
		// ... the Bayesian network
		Object receivedDataRaw = ((ObjectToken)portBN.get(0)).getValue();
		CategoricalBayesianNetwork bn = null;
		try {
			bn = (CategoricalBayesianNetwork)receivedDataRaw;
		} catch (ClassCastException e) {
			throw new IllegalArgumentException("invalid data: a GoSPL data is expected");
		}
		
		// retrieve parameters input
		// ... the population size
		Integer popSize = Integer.parseInt(paramPopSize.getValueAsString());

		BayesianNetworkFromScratchSampler sampler;
		try {
			sampler = new BayesianNetworkFromScratchSampler(bn);
		} catch (GSIllegalRangedData e) {
			throw new IllegalActionException(e.getLocalizedMessage());
		}

		// BUILD THE POPULATION
		GosplPopulation population = new GosplPopulation();
		for (int i=0; i<popSize; i++)
			population.add(new GosplEntity(sampler.draw().getMap()));
		
		outputPopulation.send(0, new ObjectToken(population, population.getClass()));
		
	}

}
