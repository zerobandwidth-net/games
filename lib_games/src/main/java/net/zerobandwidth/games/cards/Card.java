package net.zerobandwidth.games.cards;

/**
 * Interface for interacting with one card from a {@link Deck}.
 * @since [NEXT] (#1)
 */
public interface Card
{
	/**
	 * A placeholder value for the card's "rank" before it is defined in a given
	 * instance. Implementation classes should initialize their "rank" member
	 * with this value.
	 */
	static final int RANK_UNDEFINED = Integer.MIN_VALUE ;
	
	/**
	 * Accesses the card's "rank".
	 * This may be paired with a "suit" to determine the value of the card.
	 * @return the card's rank
	 */
	int getRank() ;
	
	/**
	 * Mutates the card's "rank".
	 * @param zRank the new rank to set
	 * @return (fluid)
	 */
	Card setRank( int zRank ) ;
	
	/**
	 * Accesses the card's "suit".
	 * This may be paired with a "rank" to determine the value of the card.
	 * @return the card's suit
	 */
	Suit getSuit() ;
	
	/**
	 * Mutates the card's "suit".
	 * @param suit the new suit to set
	 * @return (fluid)
	 */
	Card setSuit( Suit suit ) ;
}
