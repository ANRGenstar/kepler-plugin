package fr.genstar;

import java.util.Collection;

import core.metamodel.pop.APopulationAttribute;
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


    public static class DistributionBuilderType extends ObjectType {

		public DistributionBuilderType() {
			super();
		}

		public DistributionBuilderType(Class<?> valueClass) {
			super(valueClass);
		}

		public DistributionBuilderType(Object value, Class<?> valueClass) throws IllegalActionException {
			super(value, valueClass);
		}
    }
    
    
    // TODO
	public static final Type GOSPL_DISTRIBUTION_BUILDER = new DistributionBuilderType();

    public static class PopulationType extends ObjectType {

		public PopulationType() {
			super();
		}
		public PopulationType(Class<?> valueClass) {
			super(valueClass);
		}
		public PopulationType(Object value, Class<?> valueClass) throws IllegalActionException {
			super(value, valueClass);
		}
    }
	public static final Type GOSPL_POPULATION = new PopulationType();
	

	/**
	 * A Bayesian network loaded in memory 
	 */
    public static class BayesianNetworkType extends ObjectType {
		public BayesianNetworkType() {
			super(GosplPopulation.class);
		}
		public BayesianNetworkType(Object value, Class<?> valueClass) throws IllegalActionException {
			super(value, GosplPopulation.class);
		}
    }
	public static final Type GOSPL_BAYESIAN_NETWORK = new PopulationType();
	

 	
    public static class SampleType extends ObjectType {

		public SampleType() {
			super(CategoricalBayesianNetwork.class);
		}

		public SampleType(Object value) throws IllegalActionException {
			super(value, CategoricalBayesianNetwork.class);
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
	
		BaseType.addType(GOSPL_DISTRIBUTION_BUILDER, 	"distribution_builder", 	ObjectToken.class);
		BaseType.addType(GOSPL_POPULATION, 				"population", 				ObjectToken.class);
		BaseType.addType(GOSPL_BAYESIAN_NETWORK, 		"Bayesian_network", 		ObjectToken.class);
		BaseType.addType(GOSPL_SAMPLE, 					"sample", 					ObjectToken.class);
		BaseType.addType(GOSPL_DICTIONARY, 				"dictionary", 				ObjectToken.class);

	}
	
	private GoSPTypes() {}

}
