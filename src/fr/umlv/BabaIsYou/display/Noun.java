package fr.umlv.BabaIsYou.display;

import java.nio.file.FileSystems;
import java.nio.file.Path;
/**
 * Represent the different Noun use in the game
 */
public enum Noun implements Word{
	/**
     * Represents the 'baba' noun.
     */
    BABA,
    /**
     * Represents the 'brick' noun.
     */
    BRICK,
    /**
     * Represents the 'flag' noun.
     */
    FLAG,
    /**
     * Represents the 'flower' noun.
     */
    FLOWER,
    /**
     * Represents the 'grass' noun.
     */
    GRASS,
    /**
     * Represents the 'lava' noun.
     */
    LAVA,
    /**
     * Represents the 'rock' noun.
     */
    ROCK,
    /**
     * Represents the 'skull' noun.
     */
    SKULL,
    /**
     * Represents the 'tile' noun.
     */
    TILE,
    /**
     * Represents the 'wall' noun.
     */
    WALL,
    /**
     * Represents the 'water' noun.
     */
    WATER,
    /**
     * Represents the 'dog' noun.
     */
    DOG;

	@Override
	public Path get_url() {
		StringBuilder tmp = new StringBuilder();
		
		tmp.append("./resources/img/NOUNS/");
		tmp.append(this.name() + "/");
		tmp.append("Text_" + this.name() + "_0.gif");
		
		return FileSystems.getDefault().getPath(tmp.toString());			
	}
	
	/**
	 * Search the Path of the image representation of the Noun
	 * @return the Path
	 */
	public Path get_url_img() {
		StringBuilder tmp = new StringBuilder();
		
		tmp.append("./resources/img/NOUNS/");
		tmp.append(this.name() + "/");
		tmp.append(this.name() + "_0.gif");
		
		return FileSystems.getDefault().getPath(tmp.toString());		
	}

	
	@Override
	public boolean is_noun() {
		return true;
	}
	
	/**
	 * Allow to find the Noun which correspond to a String
	 * @param elem represents the String you want to know if it is include in Noun
	 * @return the Noun which correspond to elem, null otherwise
	 */
	public static Noun which_noun(String elem) {
		for(var noun : Noun.values()) {
			if(elem.equalsIgnoreCase(noun.name())) {
				return noun;
			}
		}
		return null;
	}
}
