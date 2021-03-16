package fr.umlv.BabaIsYou.map;

import java.util.Objects;

import fr.umlv.BabaIsYou.display.*;

/**
 * The class which represents the information we need to represent the level and display it.
 */
public class WordInfo {
	private final Word word;
	private final State state;
	
	/**
	 * This is a builder for WordInfo.
	 * @param word is the word
	 * @param state is the state of the word
	 */
	public WordInfo(Word word, State state) {
		this.word = word;
		this.state = state;
	}
	
	/**
	 * This is a getter for WordInfo.
	 * @return the word.
	 */
	public Word word() {
		return this.word;
	}
	
	/**
	 * This is a getter for WordInfo.
	 * @return the state of the word.
	 */
	public State state() {
		return this.state;
	}
	
	@Override
	public String toString() {
		return word.toString() + ", State : " + state.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof WordInfo) {
			var w = (WordInfo) o;
			return ((this.word.equals(w.word)) && (this.state.equals(w.state)));
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(word, state);
	}
}
