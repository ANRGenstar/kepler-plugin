package fr.genstar.gospl.input;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import core.metamodel.pop.APopulationAttribute;
import gospl.io.insee.ReadINSEEDictionaryUtils;
import ptolemy.actor.lib.Source;
import ptolemy.data.ObjectToken;
import ptolemy.data.expr.FileParameter;
import ptolemy.data.expr.StringParameter;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

public class ReadDictionnaryFromINSEEWebsiteActor extends Source {

	public static Log log = LogFactory.getLog(ReadDictionnaryFromINSEEWebsiteActor.class);

	public StringParameter paramURL;

	public ReadDictionnaryFromINSEEWebsiteActor(CompositeEntity container, String name)
			throws NameDuplicationException, IllegalActionException {
		super(container, name);

		// add parameters
		paramURL = new FileParameter(this, "url", false);
		paramURL.setExpression("https://www.insee.fr/fr/statistiques/2863607?sommaire=2867825#dictionnaire");

	}
	

	@Override
	public void fire() throws IllegalActionException {

		super.fire();
		
		
		// open the file 
		String urlStr = paramURL.getValueAsString();
		log.info("opening as a configuration file: "+urlStr);

		Collection<APopulationAttribute> attributes = ReadINSEEDictionaryUtils.readFromWebsite(urlStr);
		
		output.send(0, new ObjectToken(attributes));
		
	}

}
