package fr.genstar.gospl;

import java.util.List;

import ptolemy.actor.lib.Source;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

public class GoSPSource extends Source {

	public GoSPSource(CompositeEntity container, String name) throws NameDuplicationException, IllegalActionException {
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
