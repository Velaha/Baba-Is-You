package fr.umlv.BabaIsYou.Object;

import java.util.ArrayList;

import fr.umlv.BabaIsYou.display.Noun;
import fr.umlv.BabaIsYou.display.Property;
import fr.umlv.BabaIsYou.display.Word;
import fr.umlv.BabaIsYou.map.Coordinates;
import fr.umlv.BabaIsYou.map.Level;
import fr.umlv.BabaIsYou.map.State;
import fr.umlv.BabaIsYou.map.WordInfo;
import fr.umlv.BabaIsYou.move.Direction;
import fr.umlv.BabaIsYou.move.Move;

/**
 * Methods used for each Property effects are in this class
 *
 */
public class ApplyEffect {
	
	/**
	 * Recursive method for moving Push entity.
	 * @param l current level the game is playing 
	 * @param tile the starting tile 
	 * @param d the direction we move on 
	 * @param s current state of object in the level
	 * @return true if the move has been done, false otherwise
	 */
	public static boolean Push(Level l, Coordinates tile, Direction d, ObjectState s) {		
		return Move.move(l, tile, d, s);
	}
	
	/**
	 * Transform the given source noun to the destination one.
	 * @param l current level the game is playing 
	 * @param src noun that will change
	 * @param dest noun it change for 
	 */
	public static void transform(Level l, Noun src, Noun dest) {
		ArrayList<Coordinates> coord = Search.findEntity(l, src);
		
		for (var c : coord) {
			ArrayList<WordInfo> tile = l.level().get(c);
			
			for (int i = 0; i < l.level().get(c).size(); i++) {
				if (l.level().get(c).get(i).word() == src) {
					tile.remove(i);
					tile.add(new WordInfo(dest, State.Img));
				}
			}
			l.level().put(c, tile);
		}
	}
	
	/**
	 * Check if the game is in a winning condition.
	 * @param l current level the game is playing 
	 * @param s current state of object in the level
	 * @return true if the game is winable, false otherwise
	 */
	public static boolean Win(Level l, ObjectState s) {
		ArrayList<Coordinates> coordW = Search.findEntityByProp(l, Property.WIN, s);
		ArrayList<Coordinates> coordP = Search.findPlayer(l, s);
		
		for (var cW : coordW) {
			for (var cP : coordP) {
				if (cW.equals(cP)) {					
					for (var w : l.level().get(cP)) {
						if (s.state().get(w.word()) != null){
							for (var prop : s.state().get(w.word())){
								if (prop == Property.WIN)
									return true;
							}							
						}
					}
				}
			}
		}
		
		return false; 
	}
	
	/**
	 * Represent the sink Property. Remove all the entities on the tile if there is more than 1 entity.
	 * @param l current level the game is playing 
	 * @param s current state of object in the level
	 */
	public static void Sink(Level l, ObjectState s) {
		ArrayList<Coordinates> coord = Search.findEntityByProp(l, Property.SINK, s);
		
		for (var c : coord) {
			if (l.level().get(c).size() > 1) {
				ArrayList<WordInfo> tmpTile = new ArrayList<>();

				
				l.level().put(c, tmpTile);
			}
		}
	}
	
	/**
	 * Represent the melt Property. Remove the hot entity on the tile.
	 * @param l current level the game is playing 
	 * @param s current state of object in the level
	 */
	public static void Melt(Level l, ObjectState s) {
		ArrayList<Word> words = Search.findMeltEntity(s);
		ArrayList<Coordinates> coordH = Search.findEntityByProp(l, Property.HOT, s);
		ArrayList<Coordinates> coordM = Search.findEntityByProp(l, Property.MELT, s);
		
		for (var cH : coordH) {
			for (var cM : coordM) {
				if (cH.equals(cM)) {
					ArrayList<WordInfo> tmpTile = l.level().get(cM);
					for (var w : words) {
						tmpTile.remove(new WordInfo(w, State.Img));
					}
					l.level().put(cM, tmpTile);
				}
			}
		}
	}
	
	/**
	 * Represent the defeat Property. Remove the entity that move on this tile
	 * @param l current level the game is playing 
	 * @param tile the defeat tile 
	 * @param d the direction we move on 
	 * @param s current state of object in the level
	 * @return false if the entity is removed (to prevent the method move to remove entity that don't exist), true otherwise
	 */
	public static boolean Defeat(Level l, Coordinates tile, Direction d, ObjectState s) {
		Coordinates c = new Coordinates(tile.width() - d.dx, tile.height() - d.dy);
		ArrayList<WordInfo> tmpTile = l.level().get(c);
		ArrayList<Coordinates> coord = Search.findEntityByProp(l, Property.YOU, s);
		
		for (var cY : coord) {
			if (tmpTile.get(tmpTile.size() - 1).state() != State.Text && cY.equals(c)) {
				tmpTile.remove(tmpTile.get(tmpTile.size() - 1));
				l.level().put(c, tmpTile);	
				return false;
			}			
		}
		return true;
	}
}
