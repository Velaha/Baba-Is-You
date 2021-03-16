package fr.umlv.BabaIsYou.display;

import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * Represent the different Operator use in the game
 */
public enum Operator implements Word{
	/**
     * Represents the 'is' operator.
     */
    IS;
	
	@Override
	public Path get_url() {
		StringBuilder tmp = new StringBuilder();
		
		tmp.append("./resources/img/OPERATORS/");
		tmp.append(this.name() + "/");
		tmp.append("Text_" + this.name() + "_0.gif");
		
		return FileSystems.getDefault().getPath(tmp.toString());			
	}
	
	
	@Override
	public boolean is_noun() {
		return false;
	}
	
	/**
	 * Allow to find the Operator which correspond to a String
	 * @param elem represents the String you want to know if it is include in Operator
	 * @return the Operator which correspond to elem, null otherwise
	 */
	public static Operator which_op(String elem) {
		for(var ope : Operator.values()) {
			if(elem.equalsIgnoreCase(ope.name())) {
				return ope;
			}
		}
		return null;
	}

}
