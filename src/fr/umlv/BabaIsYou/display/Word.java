package fr.umlv.BabaIsYou.display;

import java.nio.file.Path;
/**
 * Represent the Word interface
 */
public interface Word {
	
	/**
	 * Search the Path of the text representation of the Word
	 * @return the Path
	 */
	public Path get_url();	
	
	/**
	 * Allow to know if the current word is a noun
	 * @return true is the word is a noun, false otherwise
	 */
	public boolean is_noun();
}
