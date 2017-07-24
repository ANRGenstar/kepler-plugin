package fr.genstar.gospl.output;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import core.metamodel.IPopulation;
import core.metamodel.pop.APopulationAttribute;
import core.metamodel.pop.APopulationEntity;
import core.metamodel.pop.APopulationValue;
import core.metamodel.pop.io.GSSurveyType;
import fr.genstar.GoSPTypes;
import fr.genstar.gospl.AbstractWriter;
import gospl.io.GosplSurveyFactory;
import gospl.io.exception.InvalidSurveyFormatException;
import ptolemy.data.ObjectToken;
import ptolemy.data.expr.StringParameter;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

public class CSVSampleWriterActor extends AbstractWriter {

	protected StringParameter sep; 
	
	public CSVSampleWriterActor(CompositeEntity container, String name)
			throws IllegalActionException, NameDuplicationException {
		super(container, name);

		portInput.setName("population");
		portInput.setTypeEquals(GoSPTypes.GOSPL_POPULATION);
		
		sep = new StringParameter(this, "separator");
		sep.setExpression(";");
		
		
	}
	
	@Override
	public void fire() throws IllegalActionException {

		super.fire();
		
		// retrieve parameters
		File fileOutput = paramFile.asFile();
		String paramSep = sep.getValueAsString();

		// retrieve inputs
		IPopulation<APopulationEntity, APopulationAttribute, APopulationValue> population = (IPopulation)((ObjectToken)portInput.get(0)).getValue();	

		// TODO char sep
		final GosplSurveyFactory sf = new GosplSurveyFactory(0, paramSep.charAt(0), 1, 1);

		try {
			sf.createSummary(fileOutput, GSSurveyType.Sample, population);
		} catch (InvalidFormatException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (InvalidSurveyFormatException e) {
			throw new RuntimeException(e);
		}

	}

}
