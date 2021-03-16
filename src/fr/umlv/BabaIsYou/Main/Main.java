package fr.umlv.BabaIsYou.Main;

import fr.umlv.BabaIsYou.Object.*;
import fr.umlv.BabaIsYou.display.*;
import fr.umlv.BabaIsYou.map.*;
import fr.umlv.BabaIsYou.move.*;
import fr.umlv.BabaIsYou.args.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import fr.umlv.zen5.*;
import fr.umlv.zen5.Event.Action;

/**
 * The main class of the program
 *
 */
public class Main {
	
	/**
	 * Give the size of the screen.
	 * @param context the ApplicationContext that display the level
	 * @return the size of the screen
	 */
	public static Coordinates scr_size(ApplicationContext context) {
		Coordinates scr;
		
		ScreenInfo screenInfo = context.getScreenInfo();
	    float screen_width = screenInfo.getWidth();
	    float screen_height = screenInfo.getHeight();
	    
	    scr = new Coordinates((int)screen_width, (int)screen_height);		
		return scr;
	}
	
	/**
	 * Give the size of the current level.
	 * @param context the ApplicationContext that display the level
	 * @param level the current level
	 * @return the size of the level
	 */
	public static Coordinates game_size(ApplicationContext context, Level level) {
		Coordinates game;
		
		ScreenInfo screenInfo = context.getScreenInfo();
	    float screen_width = screenInfo.getWidth();
	    float screen_height = screenInfo.getHeight();
	    
	    int lvl_width = level.size().width();
	    int lvl_height = level.size().height();	      
	      
	    float width = screen_width / lvl_width;
        float height = screen_height / lvl_height;
        
        game = new Coordinates((int)width, (int)height);
        return game;
	}
	
	/**
	 * Refresh the display of the level.
	 * @param graphics the frame of the level
	 * @param game the size of the current level
	 * @param scr the size of the screen
	 * @param level the current level
	 */
	public static void refresh_scr(Graphics2D graphics, Coordinates game, Coordinates scr, Level level) {
		Draw.clear_window(graphics, scr.width(), scr.height());
		  try {
			Draw.draw_level(graphics, level, game.width(), game.height());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Allow the player to move.
	 * @param context the ApplicationContext that display the level
	 * @param level the current level
	 * @param player the noun which is link to the property 'You'
	 * @param s the HashMap which represent the different property rules of the level
	 */
	public static void doAction(ApplicationContext context, Level level, ArrayList<Coordinates> player, ObjectState s) {
		Event event = context.pollOrWaitEvent(200);
    	if (event == null) {  // no event
    		return;
    	}
    
    	Action action = event.getAction();		        
    	if (action == Action.KEY_PRESSED) { 	
    		switch(event.getKey()) {
    			case UP : for (var c : player) 
    				Move.move(level, c, Direction.UP, s); break;
    			case DOWN : for (var c : player) 
    				Move.move(level, c, Direction.DOWN, s); break;
    			case LEFT : for (var c : player) 
    				Move.move(level, c, Direction.LEFT, s); break;
    			case RIGHT : for (var c : player) 
    				Move.move(level, c, Direction.RIGHT, s); break;
    			case Q : context.exit(0); return;
    			
    			default:;
    		}
        }
    	return;
	}
	
	/**
	 * Allow the player to play the current level.
	 * @param context the ApplicationContext that display the level
	 * @param current_lvl the path of the level we are playing
	 * @param level the current level
	 * @param executeRule the property rules of the level
	 * @param noun_rule the noun rules of the level
	 */
	public static void game(ApplicationContext context, Iterator<Path> current_lvl, Level level, ObjectState executeRule, HashMap<Noun, ArrayList<Noun>> noun_rule) {
		for(;;) {
	    	ObjectState s = ObjectState.setLevelProperty(level, executeRule);
	    	ObjectState.transformNounArgs(level, noun_rule);	
	    	ArrayList<Coordinates> player = Search.findPlayer(level, s);
	    	ApplyEffect.Sink(level, s);
	    	ApplyEffect.Melt(level, s);
	    	
	    	context.renderFrame(graphics -> {
	    		refresh_scr(graphics, game_size(context, level), scr_size(context), level);	    		  	
	    	});
	    	if(player.isEmpty()) {
	    		System.out.println("Defeat");
	    		context.exit(0); return;	    		
	    	}
	    	if (ApplyEffect.Win(level, s)) {
	    		System.out.println("Win");
	    		if(current_lvl.hasNext()) {
	    			break;
	    		}
	    		else {
	    			context.exit(0); return;
	    		}
	    	}
	    	doAction(context, level, player, s);
	    }		
	}
	
	/**
	 * The main method of the program. Allow to play the game.
	 * @param args arguments of the program
	 * @throws IOException if an I/O error of some sort has occurred
	 */
	public static void main(String[] args) throws IOException {
		HashMap<String, ArrayList<String>> map = Arguments.readArgs(args);
		ArrayList<Path> levels = Arguments.createListPath(args, map);
		ObjectState executeRule = Arguments.createObjectState(args, map);
		HashMap<Noun, ArrayList<Noun>> noun_rule = Arguments.createFirstNounsRules(args, map);
	    Application.run(Color.BLACK, context -> {
	    	Iterator<Path> current_lvl = levels.iterator();
	    	while(current_lvl.hasNext()) {
	    		Level level;
				try {
					level = Parser.parser(current_lvl.next());
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
				game(context, current_lvl, level, executeRule, noun_rule);	 
	    	}		    
	    });
	  }
}
