package net.zerobandwidth.games.cards;

import java.util.List;

/**
 * Interface for interacting with a deck of cards.
 * @since [NEXT]
 */
public interface Deck<C extends Card>
{
	/**
	 * Shuffles the entire deck.
	 * @return (fluid)
	 */
	Deck<C> shuffle() ;
	
	/**
	 * Shuffle the portion of the deck that is still unseen; leave alone any
	 * cards already seen since the last full shuffle.
	 * @return (fluid)
	 */
	Deck<C> shuffleRemaining() ;
	
	/**
	 * Deals the next card off the top of the deck.
	 * @return the next card off the top of the deck
	 */
	C next() ;
	
	/**
	 * Peek at the next <i>n</i> cards that would be drawn from the deck.
	 * @param nCards the number of cards to include in the peek
	 * @return a list of the next <i>n</i> cards on top of the deck
	 */
	List<C> peek( int nCards ) ;
	
	/**
	 * Indicates the total size of the deck (dealt and undealt).
	 * @return the total size of the deck
	 */
	int size() ;
	
	/**
	 * Indicates the number of cards remaining undealt from the deck.
	 * @return the number of cards remaining in the deck
	 */
	int countRemaining() ;
}
