package fr.umlv.BabaIsYou.move;

import java.util.ArrayList;

import fr.umlv.BabaIsYou.Object.*;
import fr.umlv.BabaIsYou.display.*;
import fr.umlv.BabaIsYou.map.*;

/**
 * Methods related for moving are in this class.
 *
 */
public class Move {	
	
	/**
	 * Move the entity of a given coordinates to a new one with the given direction.
	 * ObjectState s is used to know what entity the game is moving.
	 * @param l current level the game is playing 
	 * @param tile the starting tile 
	 * @param d the direction we move on 
	 * @param s current state of object in the level
	 * @return true if the move has been done, false otherwise
	 */
	public static boolean move(Level l, Coordinates tile, Direction d, ObjectState s) {
		Coordinates c;
		
		if (!outOfBound(l, c  = new Coordinates(tile.width() + d.dx, tile.height() + d.dy))) {
			if (frontTile(l, c, d, s)) {
				ArrayList<WordInfo> tmpTile = l.level().get(tile);
				ArrayList<WordInfo> newTile = new ArrayList<>();
				WordInfo w;
				
				w = l.level().get(tile).get(l.level().get(tile).size() - 1);
				tmpTile.remove(tmpTile.size() - 1);
			
				if (l.level().get(c) != null)
					newTile = l.level().get(c);
				
				newTile.add(w);
				
				l.level().put(tile, tmpTile);
				l.level().put(c, newTile);	
				
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Return a boolean if the given coordinates is out of bond of the level board.
	 * @param l current level the game is playing 
	 * @param c coordinates the program check on
	 * @return true if the coord is out of bond
	 */
	public static boolean outOfBound(Level l, Coordinates c) {
		if  ((c.height() < 0) 
				| (c.width() < 0 )
				| (c.height() > l.size().height() - 1) 
				| (c.width() > l.size().width() - 1))
			return true;
		return false;
	}
	
	/**
	 * Check if the move can be done in the front tile.
	 * @param l current level the game is playing 
	 * @param tile coord of the tile 
	 * @param d the direction we move on 
	 * @param s current state of object in the level
	 * @return true if the move can be done, false otherwise
	 */
	public static boolean frontTile(Level l, Coordinates tile, Direction d, ObjectState s) {
		
		ArrayList<Coordinates> textCoord = Search.findText(l);
		for (var coord : textCoord) {
			if (coord.equals(tile)) {				
				return ApplyEffect.Push(l, tile, d, s);
			}
		}
		
		Property w = tileProperty(l, tile, s);
		if (w != null) {
			switch ((Property) w) {
			case STOP: return false;
			case PUSH: return ApplyEffect.Push(l, tile, d, s);
			case DEFEAT: return ApplyEffect.Defeat(l, tile, d, s);
			default: return true;
			}
		}
		return true;
	}
	
	
	/**
	 * Return the Property of the given tile.
	 * @param l current level the game is playing 
	 * @param tile coord of the tile 
	 * @param s current state of object in the level
	 * @return the Property of the given tile
	 */
	public static Property tileProperty(Level l, Coordinates tile, ObjectState s) {		
		Property p = null;
		if (l.level().get(tile) != null) {			
			for (var word : l.level().get(tile)) {
				if (s.state().get(word.word()) != null){				
					for (var prop : s.state().get(word.word())) {
						p = prop;
					}
				}
			}
		}
		return p;
	}
}
