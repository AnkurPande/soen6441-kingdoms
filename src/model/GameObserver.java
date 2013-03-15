package model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * This class is to implement the observer pattern in the game.
 * 
 * @author Team B
 *
 */
public class GameObserver implements PropertyChangeListener {
	
	/**
	 * Constructor.
	 * 
	 * @param gi The game instance to observe.
	 */
	public GameObserver(GameInstance gi) {
		gi.addChangeListener(this);
	}
	
	/**
	 * This method triggers when the observed property is changed.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		System.out.println("Changed property: " + event.getPropertyName() + " old:"
				+ event.getOldValue() + " new: " + event.getNewValue());
	}
} 
