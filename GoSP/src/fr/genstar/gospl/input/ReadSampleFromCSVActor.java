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
import ptolemy.data.IntToken;
import ptolemy.data.ObjectToken;
import ptolemy.data.StringToken;
import ptolemy.data.expr.ChoiceParameter;
import ptolemy.data.expr.FileParameter;
import ptolemy.data.expr.Parameter;
import ptolemy.data.expr.StringParameter;
import ptolemy.data.type.BaseType;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

public class ReadSampleFromCSVActor extends TypedAtomicActor {

	public static Log log = LogFactory.getLog(ReadSampleFromCSVActor.class);

	public FileParameter paramFileData;
	public StringParameter paramMaxToRead;
	public ChoiceParameter paramSeparator;
	public Parameter paramFirstLineData;
	public Parameter paramFirstColData;

	protected final TypedIOPort inputDictionary;

	protected final TypedIOPort outputPopulation;
	protected final TypedIOPort outputDictionary;


	public enum CSVSeparators {
		
		COMMA 		(','),
		SEMICOLUMN 	(';'),
		COLUMN 		(':'),
		PIPE 		('|'),
		SPACE 		("space", ' '),
		TAB 		("tabulation", '\t'),
		;
		
		public final String display;
		public final char sep;
		private CSVSeparators(String display, char s) {
			this.display = display;
			this.sep = s;
		}
		private CSVSeparators(char s) {
			this.display = ""+s;
			this.sep = s;
		}
		
		@Override
		public final String toString() {
			return this.display;
		}
	}
			
	
	public ReadSampleFromCSVActor(CompositeEntity container, String name)
			throws NameDuplicationException, IllegalActionException {
		super(container, name);
		
		
		// add parameters
		paramFileData = new FileParameter(this, "CSV file", false);
		
		paramMaxToRead = new StringParameter(this, "max to read");
		paramMaxToRead.setExpression("1000"); // TODO remove !
				
		paramSeparator = new ChoiceParameter(this, "separator", CSVSeparators.class);
		
		paramFirstLineData = new Parameter(this, "first row index");
		paramFirstLineData.setTypeEquals(BaseType.INT);
		paramFirstLineData.setExpression("1");
		
		paramFirstColData = new Parameter(this, "first column index");
		paramFirstColData.setTypeEquals(BaseType.INT);
		paramFirstColData.setExpression("0");
		
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
		

		char separator = ((CSVSeparators)paramSeparator.getChosenValue()).sep;
		int firstLine = ((IntToken)paramFirstLineData.getToken()).intValue();
		int firstCol = ((IntToken)paramFirstColData.getToken()).intValue();
		
		// configure the survey factory with the right parameters
		GosplSurveyFactory factory = new GosplSurveyFactory(
				0, 
				separator,
				firstLine,
				firstCol
				);
		IGSSurvey survey;
		try {
			survey = factory.getSurvey(
					fileData.getAbsolutePath(), 
					0,
					separator,
					firstLine,
					firstCol,
					GSSurveyType.Sample,
					GosplSurveyFactory.CSV_EXT
					);
		} catch (InvalidFormatException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (InvalidSurveyFormatException e) {
			throw new IllegalArgumentException("Invalid survey format", e);
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
