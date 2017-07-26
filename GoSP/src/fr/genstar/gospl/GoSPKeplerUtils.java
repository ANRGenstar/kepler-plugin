package fr.genstar.gospl;

import java.util.List;

import ptolemy.kernel.util.Settable;

public class GoSPKeplerUtils {


	/**
	 * make invisible the parameters which are ugly and useless to end users
	 * including class, or id, or firing events even
	 * @param attributes
	 * @return
	 */
	public static <T> List<T> filterVisibility(List<T> attributes) {
		
		for (T a: attributes) {
			if (a instanceof Settable) {
				Settable s = (Settable)a;
				if (s.getDisplayName().equals("class"))
					s.setVisibility(Settable.EXPERT);
			}
		}
		
		return attributes;
	}
	
	private GoSPKeplerUtils() {}

}
