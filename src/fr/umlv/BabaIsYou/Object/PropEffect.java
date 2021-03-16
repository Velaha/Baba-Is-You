package fr.umlv.BabaIsYou.Object;

import java.util.ArrayList;

import fr.umlv.BabaIsYou.display.Noun;
import fr.umlv.BabaIsYou.display.Property;
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
public class PropEffect {
	
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
	 * Transform all src in level into dest.
	 * @param l current level the game is playing 
	 * @param src the noun to be changed
	 * @param dest the new noun
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
	 * Check if the current level is solved.
	 * @param l current level the is playing
	 * @param s current state of object in the level
	 * @return true if the level is solved, false otherwise
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
}
