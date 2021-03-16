package fr.umlv.BabaIsYou.display;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;
import fr.umlv.BabaIsYou.map.*;
/**
 * Represent the class which allow the different displays of the game 
 */
public class Draw {
	
	/** 
	 * Allow to print an image which represents the word with text.
	 * @param graphics is were the image is printed
	 * @param word you want to represents
	 * @param x is the width coordinate
	 * @param y is the height coordinate
	 * @param width of the current window
	 * @param height of the current window
	 * @throws MalformedURLException if the URL is malformed
	 */
	public static void draw_text(Graphics2D graphics, Word word, int x, int y, float width, float height) throws MalformedURLException {
		ImageIcon tmp = new ImageIcon(word.get_url().toUri().toURL());
		graphics.drawImage(tmp.getImage(), (int)(x * width), (int)(y * height), (int)width, (int)height, tmp.getImageObserver());
	}
	
	/**
	 * Allow to print an image which represents the word with an image.
	 * @param graphics is were the image is printed
	 * @param noun you want to represents
	 * @param x is the width coordinate
	 * @param y is the height coordinate
	 * @param width of the current window
	 * @param height of the current window
	 * @throws MalformedURLException if the URL is malformed
	 */
	public static void draw_img(Graphics2D graphics, Noun noun, int x, int y, float width, float height) throws MalformedURLException {
		ImageIcon tmp = new ImageIcon(noun.get_url_img().toUri().toURL());
		graphics.drawImage(tmp.getImage(), (int)(x * width), (int)(y * height), (int)width, (int)height, tmp.getImageObserver());
	}
	
	/**
	 * Allow to draw one specific word in graphics.
	 * @param graphics where the word we display the word
	 * @param word which word we want to display
	 * @param x is the width coordinate
	 * @param y is the height coordinate
	 * @param width of the current window
	 * @param height of the current window
	 * @throws MalformedURLException if the URL is malformed
	 */
	public static void draw_elem(Graphics2D graphics, WordInfo word, int x, int y, int width, int height) throws MalformedURLException {
		if(word.state() == State.Img) {
			draw_img(graphics, (Noun)word.word(), x, y, width, height);
		}else {
			draw_text(graphics, word.word(), x, y, width, height);
		}
	}
	
	/**
	 * Allow to represents a level of the game
	 * @param graphics is were the level is represented
	 * @param level is the level you want to represent
	 * @param width of the current window
	 * @param height of the current window
	 * @throws MalformedURLException if the URL is malformed
	 */
	public static void draw_level(Graphics2D graphics, Level level, float width, float height) throws MalformedURLException{
		level.level().forEach((k, v) -> {
			v.forEach(elem -> {
				try {
					draw_elem(graphics, elem, k.width(), k.height(), (int)width, (int)height);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		});	
	}
	
	/**
	 * Allow to clear the display of the current window
	 * @param graphics is what you need to clear
	 * @param width of the current window
	 * @param height of the current window
	 */
	public static void clear_window(Graphics2D graphics, float width, float height) {
        graphics.setColor(Color.BLACK);
        graphics.fill(new  Rectangle2D.Float(0, 0, width, height));
	}

}
