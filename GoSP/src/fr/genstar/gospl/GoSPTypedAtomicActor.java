package fr.genstar.gospl;

import java.util.List;

import ptolemy.actor.TypedAtomicActor;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;
import ptolemy.kernel.util.Workspace;

public class GoSPTypedAtomicActor extends TypedAtomicActor {

	public GoSPTypedAtomicActor() {
	}

	public GoSPTypedAtomicActor(Workspace workspace) {
		super(workspace);
	}

	public GoSPTypedAtomicActor(CompositeEntity container, String name)
			throws IllegalActionException, NameDuplicationException {
		super(container, name);
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List attributeList() {
		return GoSPKeplerUtils.filterVisibility(super.attributeList());
	}

	@Override
	public <T> List<T> attributeList(Class<T> filter) {
		return GoSPKeplerUtils.filterVisibility(super.attributeList(filter));
	}



}
