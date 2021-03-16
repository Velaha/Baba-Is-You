package fr.umlv.BabaIsYou.map;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * The class which represents the levels of the game.
 */
public class Level {
	private Coordinates size;
	private HashMap <Coordinates, ArrayList<WordInfo>> level;
	
	/**
	 * Allow to construct new Level with one Coordinates.
	 * @param size represents the width and the height of the current level
	 */
	public Level(Coordinates size) {
		this.size = size;
		this.level = new HashMap <>();
	}
	
	/**
	 * This function is a setter for the size of the level.
	 * @param new_size represents the new size of the level
	 */
	public void set_size(Coordinates new_size) {
		this.size = new Coordinates(new_size);		
	}
	
	/**
	 * This function is a getter for the size field of Level.
	 * @return the value of the field size
	 */
	public Coordinates size() {
		return this.size;
	}
	
	/**
	 * This function is a setter for the HashMap of the level field of Level.
	 * @param new_level is the new level
	 */
	public void set_level(HashMap <Coordinates, ArrayList<WordInfo>> new_level) {
		this.level = new_level;		
	}
	
	/**
	 * This function is a getter for the level field of Level.
	 * @return the HashMap of the level field
	 */
	public HashMap <Coordinates, ArrayList<WordInfo>> level(){
		return this.level;
	}
}
