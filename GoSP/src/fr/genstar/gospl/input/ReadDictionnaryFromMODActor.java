package fr.genstar.gospl.input;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import core.configuration.GenstarConfigurationFile;
import core.configuration.GenstarXmlSerializer;
import core.metamodel.pop.APopulationAttribute;
import fr.genstar.GoSPTypes;
import gospl.io.insee.ReadINSEEDictionaryUtils;
import ptolemy.actor.lib.Source;
import ptolemy.data.ObjectToken;
import ptolemy.data.expr.FileParameter;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

public class ReadDictionnaryFromMODActor extends Source {

	public static Log log = LogFactory.getLog(ReadDictionnaryFromMODActor.class);

	public FileParameter paramFileData;

	public ReadDictionnaryFromMODActor(CompositeEntity container, String name)
			throws NameDuplicationException, IllegalActionException {
		super(container, name);

		// add parameters
		paramFileData = new FileParameter(this, "paramFileData", false);
		
		output.setTypeEquals(GoSPTypes.GOSPL_DICTIONARY);

	}
	

	@Override
	public void fire() throws IllegalActionException {

		super.fire();
		
		
		// open the file 
		File fileData = paramFileData.asFile();
		log.info("opening as a configuration file: "+fileData.getName());

		Collection<APopulationAttribute> attributes = ReadINSEEDictionaryUtils.readDictionnaryFromMODFile(
																fileData
																);

		
		
		output.send(0, new ObjectToken(attributes, attributes.getClass()));
		
	}

}
