package fr.umlv.BabaIsYou.display;

import java.nio.file.FileSystems;
import java.nio.file.Path;
/**
 * Represent the different Property use in the game
 */
public enum Property implements Word{
	/**
     * Represents the 'defeat' property.
     */
    DEFEAT,
    /**
     * Represents the 'hot' property.
     */
    HOT,
    /**
     * Represents the 'melt' property.
     */
    MELT,
    /**
     * Represents the 'push' property.
     */
    PUSH,
    /**
     * Represents the 'sink' property.
     */
    SINK,
    /**
     * Represents the 'stop' property.
     */
    STOP,
    /**
     * Represents the 'win' property.
     */
    WIN,
    /**
     * Represents the 'you' property.
     */
    YOU,
    /**
     * Represents the 'follow' property.
     */
    FOLLOW;
	
	@Override
	public Path get_url() {
		StringBuilder tmp = new StringBuilder();
		
		tmp.append("./resources/img/PROPERTIES/");
		tmp.append(this.name() + "/");
		tmp.append("Text_" + this.name() + "_0.gif");
		
		return FileSystems.getDefault().getPath(tmp.toString());
	}
	
	
	@Override
	public boolean is_noun() {
		return false;
	}
	
	/**
	 * Allow to find the Property which correspond to a String
	 * @param elem represents the String you want to know if it is include in Property
	 * @return the Property which correspond to elem, null otherwise
	 */
	public static Property which_prop(String elem) {
		for(var prop : Property.values()) {
			if(elem.equalsIgnoreCase(prop.name())) {
				return prop;
			}
		}
		return null;
	}
	
	

}
