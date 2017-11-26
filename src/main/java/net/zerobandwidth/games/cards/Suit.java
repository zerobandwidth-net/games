package net.zerobandwidth.games.cards;

/**
 * Interface for interacting with a "suit" of cards.
 * @since [NEXT] (#1)
 */
public interface Suit
{
	/**
	 * Accesses the suit's ID, which identifies it uniquely within the context
	 * of the deck. This <i>is not</i> required to be globally unique across all
	 * decks or contexts, and should not be considered a UUID for the suit.
	 * @return the suit's ID in the context of the game
	 */
	int getID() ;
	
	/**
	 * Accesses the suit's short (usually single-character) symbol.
	 * @return the suit's symbol
	 */
	String getSymbol() ;
	
	/**
	 * Accesses the suit's full label.
	 * @return the suit's label
	 */
	String getLabel() ;
}
