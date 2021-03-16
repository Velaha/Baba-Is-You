package fr.umlv.BabaIsYou.map;

import java.util.Objects;

/**
 * The class which represents the coordinates of every element of the level.
 */
public class Coordinates {
	private final int width;
	private final int height;
	
	/**
	 * Allow to construct new Coordinates with two integer.
	 * @param width of the current window
	 * @param height of the current window
	 */
	public Coordinates(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Allow to construct new Coordinates with Coordinates.
	 * @param coor the previous Coordinates
	 */
	public Coordinates(Coordinates coor) {
		this.width = coor.width;
		this.height = coor.height;
	}
	
	/**
	 * This function is a getter for the width field.
	 * @return the value of the field width
	 */
	public int width() {
		return this.width;
	}
	
	/**
	 * This function is a getter for the height field.
	 * @return the height of the Coordinates
	 */
	public int height() {
		return this.height;
	}
	
	@Override
	public String toString() {
		return "(" + width + ", " + height + ")";
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Coordinates) {
			var c = (Coordinates) o;
			return ((this.width == c.width) && (this.height == c.height));
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(width, height);
	}
}
