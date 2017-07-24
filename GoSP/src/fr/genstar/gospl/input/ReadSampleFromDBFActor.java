package fr.genstar.gospl.input;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import core.metamodel.pop.APopulationAttribute;
import core.metamodel.pop.io.GSSurveyType;
import core.metamodel.pop.io.IGSSurvey;
import fr.genstar.GoSPTypes;
import gospl.GosplPopulation;
import gospl.distribution.GosplInputDataManager;
import gospl.io.GosplSurveyFactory;
import gospl.io.exception.InvalidSurveyFormatException;
import ptolemy.actor.TypedAtomicActor;
import ptolemy.actor.TypedIOPort;
import ptolemy.data.ObjectToken;
import ptolemy.data.StringToken;
import ptolemy.data.expr.FileParameter;
import ptolemy.data.expr.StringParameter;
import ptolemy.data.type.BaseType;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

public class ReadSampleFromDBFActor extends TypedAtomicActor {

	public static Log log = LogFactory.getLog(ReadSampleFromDBFActor.class);

	public FileParameter paramFileData;
	public StringParameter paramMaxToRead;


	protected final TypedIOPort inputDictionary;

	protected final TypedIOPort outputPopulation;
	protected final TypedIOPort outputDictionary;

	public ReadSampleFromDBFActor(CompositeEntity container, String name)
			throws NameDuplicationException, IllegalActionException {
		super(container, name);
		
		
		// add parameters
		paramFileData = new FileParameter(this, "dbf file", false);
		
		paramMaxToRead = new StringParameter(this, "max to read");
		paramMaxToRead.setExpression("1000"); // TODO remove !
		
		// define input
		inputDictionary = new TypedIOPort(this, "dictionary");
		inputDictionary.setInput(true);
		inputDictionary.setMultiport(false); // TODO why false ? make it true !
		inputDictionary.setTypeEquals(GoSPTypes.GOSPL_DICTIONARY);
		
		// define outputs
		outputPopulation = new TypedIOPort(this, "population");
		outputPopulation.setOutput(true);
		outputPopulation.setMultiport(false);
		outputPopulation.setTypeEquals(GoSPTypes.GOSPL_POPULATION);
		
		outputDictionary = new TypedIOPort(this, "updated dictionary");
		outputDictionary.setOutput(true);
		outputDictionary.setMultiport(false); // TODO why false ? make it true !
		outputDictionary.setTypeEquals(GoSPTypes.GOSPL_DICTIONARY);
		
	}


	@Override
	public void fire() throws IllegalActionException {

		super.fire();
		
		
		// retrieve inputs
		Collection<APopulationAttribute> attributes = (Collection<APopulationAttribute>) ((ObjectToken)inputDictionary.get(0)).getValue();
		
		// retrieve parameters
		File fileData = paramFileData.asFile();
		log.info("opening as a configuration file: "+fileData.getName());

		String maxToReadString = ((StringToken)paramMaxToRead.getToken()).stringValue().trim();
		Integer maxToRead = null;
		if (!maxToReadString.isEmpty())
			maxToRead = Integer.parseInt(maxToReadString);
		
		// TODO add the user parameter for number format !!!
		// DecimalFormatSymbols decimalFormatSymbols, 
		// DecimalFormat decimalFormat
		
		GosplSurveyFactory gsf = new GosplSurveyFactory();

		IGSSurvey survey;
		try {
			survey = gsf.getSurvey(fileData, GSSurveyType.Sample);
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (InvalidSurveyFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		
		GosplPopulation pop = null;
		
		Set<APopulationAttribute> updatedAttributes = new HashSet<>(attributes);

		try {
			//Map<String,String> keepOnlyEqual = new HashMap<>();
			//keepOnlyEqual.put("DEPT", "75");
			//keepOnlyEqual.put("NAT13", "Marocains");
			
			
			pop = GosplInputDataManager.getSample(
					survey, 
					updatedAttributes, 
					maxToRead,
					Collections.emptyMap() // TODO parameters for that
					);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (InvalidSurveyFormatException e) {
			throw new RuntimeException(e);
		}
		
		// send the data
		outputPopulation.send(0, new ObjectToken(pop, pop.getClass()));
		outputDictionary.send(0, new ObjectToken(updatedAttributes, updatedAttributes.getClass()));
		
	}
}
