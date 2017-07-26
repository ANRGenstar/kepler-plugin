package fr.genstar.gospl;

import java.io.File;

import fr.genstar.GoSPTypes;
import ptolemy.actor.TypedIOPort;
import ptolemy.data.expr.FileParameter;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

public abstract class AbstractWriter extends GoSPTypedAtomicActor {

	protected final TypedIOPort portInput;

	protected final FileParameter paramFile;
		

	public AbstractWriter(CompositeEntity container, String name)
			throws IllegalActionException, NameDuplicationException {
		super(container, name);

		portInput = new TypedIOPort(this, "population");
		portInput.setInput(true);
		portInput.setMultiport(false);
		portInput.setTypeEquals(GoSPTypes.GOSPL_POPULATION);
		
		paramFile = new FileParameter(this, "output file");
		paramFile.setExpression(System.getProperty("java.io.tmpdir")+File.separator+"result.csv");
		
	}

}
