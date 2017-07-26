package fr.genstar;

import java.util.Collection;

import gospl.GosplPopulation;
import gospl.algo.sr.bn.CategoricalBayesianNetwork;
import ptolemy.data.ObjectToken;
import ptolemy.data.type.BaseType;
import ptolemy.data.type.ObjectType;
import ptolemy.data.type.Type;
import ptolemy.kernel.util.IllegalActionException;

/**
 * declares workflow types that can exist in a Kepler workflow 
 * 
 * @author Samuel Thiriot
 *
 */
public class GoSPTypes {


    public static class PopulationType extends ObjectType {

		public PopulationType() {
			super(GosplPopulation.class);
		}
		public PopulationType(Object value) throws IllegalActionException {
			super(value, GosplPopulation.class);
		}
    }
	public static final Type GOSPL_POPULATION = new PopulationType();
	

	/**
	 * A Bayesian network loaded in memory 
	 */
    public static class BayesianNetworkType extends ObjectType {
		public BayesianNetworkType() {
			super(CategoricalBayesianNetwork.class);
		}
		public BayesianNetworkType(Object value) throws IllegalActionException {
			super(value, CategoricalBayesianNetwork.class);
		}
    }
	public static final Type GOSPL_BAYESIAN_NETWORK = new BayesianNetworkType();
	

 	
    public static class SampleType extends ObjectType {

		public SampleType() {
			super(GosplPopulation.class);
		}

		public SampleType(Object value) throws IllegalActionException {
			super(value, GosplPopulation.class);
		}

    }
	public static final Type GOSPL_SAMPLE = new SampleType();
	

 	/**
 	 * A dictionary of data loaded in memory (a collection of Attribute)
 	 */
    public static class DictionaryType extends ObjectType {
		public DictionaryType() {
			super(Collection.class);
		}
		public DictionaryType(Object value) throws IllegalActionException {
			super(value, Collection.class);
		}
    }
	public static final Type GOSPL_DICTIONARY = new DictionaryType();
	
	
	static {
	
		BaseType.addType(GOSPL_POPULATION, 				"population", 				ObjectToken.class);
		BaseType.addType(GOSPL_BAYESIAN_NETWORK, 		"Bayesian_network", 		ObjectToken.class);
		BaseType.addType(GOSPL_SAMPLE, 					"sample", 					ObjectToken.class);
		BaseType.addType(GOSPL_DICTIONARY, 				"dictionary", 				ObjectToken.class);

	}
	
	private GoSPTypes() {}

}
