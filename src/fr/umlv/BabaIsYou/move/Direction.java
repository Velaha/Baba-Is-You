package fr.umlv.BabaIsYou.move;

/**
 * Represent the cardinal direction=
 *
 */
public enum Direction {
	/**
	 * Represent the west direction.
	 */
	LEFT(0, -1),
	/**
	 * Represent the east direction.
	 */
	RIGHT(0, 1),
	/**
	 * Represent the north direction.
	 */
	UP(-1 ,0),
	/**
	 * Represent the south direction.
	 */
	DOWN(1, 0);
	
	/**
	 * The fiels which represent the direction in y and x axis.
	 */
	public final int dy, dx;
	
	/**
	 * The builder of Direction.
	 * @param dy direction in the y axis
	 * @param dx direction in the x axis
	 */
	private Direction(int dy, int dx) {
		this.dy = dy;
		this.dx = dx;
	}
}
