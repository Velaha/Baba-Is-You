package fr.umlv.BabaIsYou.map;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

import fr.umlv.BabaIsYou.display.*;
/**
 * The class which allow to read a file.
 */
public class Parser {
	
	/**
	 * Allow to read a file line by line.
	 * @param level is the file we want to read
	 * @return a line from the file level
	 * @throws IOException if an I/O error of some sort has occurred
	 */
	public static String read_line(Reader level) throws IOException {
		StringBuilder line = new StringBuilder();
		int c;		
		while((c = level.read()) != '\n') {
			line.append((char) c);
		}		
		return line.toString();		
	}
	
	/**
	 * Allow to convert a String to an integer.
	 * @param str is the String we want to convert into an integer
	 * @return the String as an integer
	 */
	public static int str_to_int(String str) {
		return Integer.parseInt(str);
	}
		
	/**
	 * This function allow to know if the word we read is a Word of the game.
	 * @param elem is the String we want to know if it is include in the Word of Baba Is You
	 * @return the word which correspond to the String elem, null otherwise
	 */
	private static Word elem_in_word(String elem) {
		Word word;
		
		if((word = Noun.which_noun(elem)) == null) {
			if((word = Operator.which_op(elem)) == null) {
				if((word = Property.which_prop(elem)) == null) {
					return null;
				}
			}
		}
		return word;
	}
	
	/**
	 * This function allow to create new WordInfo thanks to the String elem.
	 * @param elem is the String we currently read
	 * @return a new WordInfo which contains the information of the Word the function find
	 */
	private static WordInfo parse_elem(String elem) {
		if(elem.equals("")) {
			return null;
		}
		
		Word word = elem_in_word(elem);
		if(word == null) {
			return null;
		}
		
		State state = State.Text;
		if(word.is_noun()) {
			if(Character.isUpperCase(elem.charAt(0))) {
				state = State.Img;
			}
		}
		
		return new WordInfo(word, state);
	}
	
	/**
	 * This function use the previous function to read
	 * a file and put all the information we need in a HashMap
	 * to represents the current level.
	 * @param text_level the path of the level
	 * @return the level Level
	 * @throws IOException if an I/O error of some sort has occurred
	 */
	public static Level parser(Path text_level) throws IOException {
		Level map = new Level(new Coordinates(0, 0));
		HashMap<Coordinates, ArrayList<WordInfo>> level = new HashMap<>();
		WordInfo tmp;
		Reader file = Files.newBufferedReader(text_level);
		
		int width = str_to_int(read_line(file));
		int height = str_to_int(read_line(file));
		map.set_size(new Coordinates(width, height));
		
		for(int i = 0; i < height; i++) {
			String line = read_line(file);
			String[] elem = line.split(";", -1);
			
			for(int j = 0; j < width; j++) {
				ArrayList<WordInfo> info = new ArrayList<>();
				if((tmp = parse_elem(elem[j])) != null) {
					info.add(tmp);
					level.put(new Coordinates(j, i), info);
				}
			}
		}
		
		map.set_level(level);
		return map;
	}
}
