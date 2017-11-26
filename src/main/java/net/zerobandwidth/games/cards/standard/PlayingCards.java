package net.zerobandwidth.games.cards.standard;

import net.zerobandwidth.games.cards.Suit;

import static net.zerobandwidth.games.cards.Card.RANK_UNDEFINED;

/**
 * Defines constants and utility methods related to standard playing cards.
 * @since [NEXT] (#1)
 * @see PlayingCardDeck
 * @see PlayingCard
 */
public abstract class PlayingCards
{
	/** Defines the short symbols for each rank. */
	public static final String[] RANK_SYMBOLS =
	{ "X", "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" } ;
	
	/** Defines the semantic labels for each card rank */
	public static final String[] RANK_LABELS =
	{
		"joker", "ace", "two", "three", "four", "five", "six",
		"seven", "eight", "nine", "ten", "jack", "queen", "king"
	};
	
	/** Defines the ID, symbol, and label for hearts. */
	public static final Suit HEARTS = new Suit()
	{
		public int getID() { return 0 ; }
		public String getSymbol() { return "\u2665" ; }
		public String getLabel() { return "hearts" ; }
	};
	
	/** Defines the ID, symbol, and label for clubs. */
	public static final Suit CLUBS = new Suit()
	{
		public int getID() { return 1 ; }
		public String getSymbol() { return "\u2663" ; }
		public String getLabel() { return "clubs" ; }
	};
	
	/** Defines the ID, symbol, and label for diamonds. */
	public static final Suit DIAMONDS = new Suit()
	{
		public int getID() { return 2 ; }
		public String getSymbol() { return "\u2666" ; }
		public String getLabel() { return "diamonds" ; }
	};
	
	/** Defines the ID, symbol, and label for spades. */
	public static final Suit SPADES = new Suit()
	{
		public int getID() { return 3 ; }
		public String getSymbol() { return "\u2660" ; }
		public String getLabel() { return "spades" ; }
	};
	
	/**
	 * Evaluates whether a given rank specification is valid for playing cards.
	 * This assumes that {@link Card#RANK_UNDEFINED} is used for "undefined",
	 * 0 is used for a joker, and 1-13 define A, 2, 3, 4, 5, 6, 7, 8, 9, 10, J,
	 * Q, and K.
	 * @param zRank the proposed rank
	 * @return {@code true} iff the rank is valid
	 */
	public static final boolean isValidRank( int zRank )
	{
		if( zRank == RANK_UNDEFINED ) return true ;
		if( zRank < 0 || zRank > 13 ) return false ;
		return true ;
	}
	
	/**
	 * Returns the symbol to be used when displaying each of the valid ranks.
	 * @param zRank the rank to be represented
	 * @return the rank's symbol to be displayed
	 */
	public static final String rankSymbol( int zRank )
	{
		if( zRank == RANK_UNDEFINED ) return "?" ;
		else if( ! isValidRank(zRank) ) return "?" ;
		else return RANK_SYMBOLS[zRank] ;
	}
	
	/**
	 * Returns the label to be used when displaying each of the valid ranks.
	 * @param zRank the rank to be represented
	 * @return the rank's label to be displayed
	 */
	public static final String rankLabel( int zRank )
	{
		if( zRank == RANK_UNDEFINED ) return "(undefined)" ;
		else if( ! isValidRank(zRank) ) return "(invalid)" ;
		else return RANK_LABELS[zRank] ;
	}
}
