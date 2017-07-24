package fr.genstar;

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

        @Override
        public int getTypeHash() {
            return 1001;
        }

    }
    
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

        @Override
        public int getTypeHash() {
            return 1002;
        }

    }
    
	public static final Type GOSPL_POPULATION = new PopulationType();
	

    public static class BayesianNetworkType extends ObjectType {

		public BayesianNetworkType() {
			super();
		}

		public BayesianNetworkType(Class<?> valueClass) {
			super(valueClass);
		}

		public BayesianNetworkType(Object value, Class<?> valueClass) throws IllegalActionException {
			super(value, valueClass);
		}

        @Override
        public int getTypeHash() {
            return 1003;
        }
        
        

    }
    
	public static final Type GOSPL_BAYESIAN_NETWORK = new PopulationType();
	

 	
    public static class SampleType extends ObjectType {

		public SampleType() {
			super();
		}

		public SampleType(Class<?> valueClass) {
			super(valueClass);
		}

		public SampleType(Object value, Class<?> valueClass) throws IllegalActionException {
			super(value, valueClass);
		}

        @Override
        public int getTypeHash() {
            return 1004;
        }
        
       
    }
    
	public static final Type GOSPL_SAMPLE = new SampleType();
	
	static {
	
		BaseType.addType(GOSPL_DISTRIBUTION_BUILDER, 	"distribution_builder", 	ObjectToken.class);
		BaseType.addType(GOSPL_POPULATION, 				"population", 				ObjectToken.class);
		BaseType.addType(GOSPL_BAYESIAN_NETWORK, 		"Bayesian_network", 		ObjectToken.class);
		BaseType.addType(GOSPL_SAMPLE, 					"sample", 					ObjectToken.class);

	}
	
	private GoSPTypes() {}

}
