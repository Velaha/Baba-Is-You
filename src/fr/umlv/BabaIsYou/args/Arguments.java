package fr.umlv.BabaIsYou.args;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

import fr.umlv.BabaIsYou.Object.*;
import fr.umlv.BabaIsYou.display.*;

/**
 * This is the class for read and deal with the arguments of the program.
 *
 */
public class Arguments {
	
	/**
	 * Search if the option "--level" is in the arguments. 
	 * @param args are the arguments in the main program
	 * @return the list of level you want to play with
	 */
	private static ArrayList<String> level(String[] args) {
		int i;
		ArrayList<String> list = new ArrayList<>();
		
		for(i = 0; i < args.length - 1; i++) {
			if(args[i].equals("--level")) {
				list.add(args[i + 1]);
			}
		}
		return list;
	}
	
	/**
	 * Search if the option "--levels" is in the arguments. 
	 * @param args are the arguments in the main program
	 * @return the list of directory you want to play with
	 */
	private static ArrayList<String> levels(String[] args) {
		int i;
		ArrayList<String> list = new ArrayList<>();
		
		for(i = 0; i < args.length - 1; i++) {
			if(args[i].equals("--levels")) {
				list.add(args[i + 1]);
			}
		}
		return list;
	}
	
	/**
	 * Search if the option "--execute" is in the arguments. 
	 * @param args are the arguments in the main program
	 * @return the list of condition you want to play with
	 */
	private static ArrayList<String> execute(String[] args) {
		int i;
		ArrayList<String> list = new ArrayList<>();
		
		for(i = 0; i < args.length - 3; i++) {
			if(args[i].equals("--execute")) {
				list.add(args[i + 1]);
				list.add(args[i + 2]);
				list.add(args[i + 3]);
			}
		}
		return list;
	}
	
	/**
	 * Read the arguments of the program.
	 * @param args are the arguments of the program
	 * @return an HashMap were every option is classified
	 */
	public static HashMap<String, ArrayList<String>> readArgs(String[] args){
		HashMap<String, ArrayList<String>> map = new HashMap<>();
		
		map.put("level", level(args));
		map.put("levels", levels(args));
		map.put("execute", execute(args));
		
		return map;
	}
	
	/**
	 * Find every levels you want to play inside directories.
	 * @param levels are the directories
	 * @return a list of path to the levels
	 * @throws IOException if an I/O error of some sort has occurred
	 */
	private static ArrayList<Path> getAllPath(String levels) throws IOException{
		Path folder = FileSystems.getDefault().getPath(levels);
		var visitor = new FilesWalk();
		Files.walkFileTree(folder, new HashSet<>(), 1, visitor);
		
		return new ArrayList<Path>(visitor.getPath().stream().sorted().collect(Collectors.toList()));
	}
	
	/**
	 * Add all the path of levels, from directories you selected, to a list of path.
	 * @param list_path is the current list of path
	 * @param levels are the directories
	 * @throws IOException if an I/O error of some sort has occurred
	 */
	private static void addAllPath(ArrayList<Path> list_path, ArrayList<String> levels) throws IOException{
		ArrayList<Path> allPath;
		
		for(var dir : levels) {
			allPath = getAllPath(dir);
			allPath.forEach(path -> {
				list_path.add(path);
			});
		}		
	}
	
	/**
	 * Create a list of path to the levels you want to play with.
	 * @param args are the arguments of the main program
	 * @param map is the HashMap which represents the different options
	 * @return the list of path to levels
	 * @throws IOException if an I/O error of some sort has occurred
	 */
	public static ArrayList<Path> createListPath(String[] args, HashMap<String, ArrayList<String>> map) throws IOException{
        ArrayList<Path> list_path = new ArrayList<>();
        
        if(!map.get("level").isEmpty()) {
            for(var path : map.get("level")) {
                list_path.add(FileSystems.getDefault().getPath(path));            
            }
        }
        else if(!map.get("levels").isEmpty()) {
            Arguments.addAllPath(list_path, map.get("levels"));
        }
        else {
            list_path.add(FileSystems.getDefault().getPath("./resources/LEVELS/default-level.txt"));
        }
        
        return list_path;
    }
	
	/**
	 * Find every properties associate to the Noun noun.
	 * @param noun is the noun we want to associate
	 * @param execute is a list of noun, property and operator
	 * @param index is the index of noun in execute
	 * @return the list of association to the Noun noun
	 */
	private static ArrayList<Property> findProp(Noun noun, ArrayList<String> execute, int index){
		ArrayList<Property> list = new ArrayList<>();
		int i;

		for(i = index; i < execute.size() - 2; i++) {
			if(execute.get(i+1).equalsIgnoreCase("is")) {
				Property prop = Property.which_prop(execute.get(i+2));
				if(prop != null) {
					list.add(prop);
				}
			}
		}		
		return list;
	}
	
	/**
	 * Create the first properties rules of the levels the player asked with the option 'execute'
	 * @param args are the arguments of the main program
	 * @param map is the HashMap which represents the different options
	 * @return the rules
	 */
	public static ObjectState createObjectState(String[] args, HashMap<String, ArrayList<String>> map) {
		ObjectState rule = new ObjectState();
		ArrayList<String> list_execute = map.get("execute");
		int i = 0;		
		
		if(!list_execute.isEmpty() && list_execute.size() >= 3) {
			for(i = 0; i < list_execute.size(); i++) {
				Noun noun = Noun.which_noun(list_execute.get(i));
				if(noun != null) {
					ArrayList<Property> list_rule = findProp(noun, list_execute, i);
					rule.setState(noun, list_rule);
				}
			}			
		}		
		return rule;
	}	
	
	/**
	 * Find every nouns associate to the Noun noun.
	 * @param noun is the noun we want to associate
	 * @param execute is a list of noun, property and operator
	 * @param index is the index of noun in execute
	 * @return the list of association to the Noun noun
	 */
	private static ArrayList<Noun> findNoun(Noun noun, ArrayList<String> execute, int index){
		ArrayList<Noun> list = new ArrayList<>();
		int i;

		for(i = index; i < execute.size() - 2; i++) {
			if(execute.get(i+1).equalsIgnoreCase("is")) {
				Noun findNoun = Noun.which_noun(execute.get(i+2));
				if(findNoun != null) {
					list.add(findNoun);
				}
			}
		}		
		return list;
	}
	
	/**
	 * Create the first nouns rules of the levels the player asked with the option 'execute'
	 * @param args are the arguments of the main program
	 * @param map is the HashMap which represents the different options
	 * @return the rules
	 */
	public static HashMap<Noun, ArrayList<Noun>> createFirstNounsRules(String[] args, HashMap<String, ArrayList<String>> map){
		HashMap<Noun, ArrayList<Noun>> noun_rule = new HashMap<>();
		ArrayList<String> list_execute = map.get("execute");
		int i = 0;		
		
		if(!list_execute.isEmpty() && list_execute.size() >= 3) {
			for(i = 0; i < list_execute.size(); i++) {
				Noun noun = Noun.which_noun(list_execute.get(i));
				if(noun != null) {
					ArrayList<Noun> list_noun = findNoun(noun, list_execute, i);
					noun_rule.put(noun, list_noun);
				}
			}
		}		
		return noun_rule;
	}
}
