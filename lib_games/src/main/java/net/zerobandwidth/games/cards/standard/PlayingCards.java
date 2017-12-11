package net.zerobandwidth.games.cards.standard;

import net.zerobandwidth.games.cards.Suit;

import static net.zerobandwidth.games.cards.Card.RANK_UNDEFINED;

/**
 * Defines constants and utility methods related to standard playing cards.
 * 
 * <h3>Ranks</h3>
 * 
 * <p>Valid ranks include {@link Card#RANK_UNDEFINED}, 0 (joker), and 1-13 (ace
 * through king). The class provides semantic constants {@link JOKER} (0),
 * {@link #ACE} (1), {@link #JACK} (11), {@link #QUEEN} (12), and {@link KING}
 * (13) to allow for more readable code when setting and examining the rank
 * values.</p>
 * 
 * <h3>Suits</h3>
 * 
 * <p>The class provides <i>six</i> canonical {@link Suit} instances: the usual
 * {@link #HEARTS}, {@link #CLUBS}, {@link #DIAMONDS}, and {@link SPADES}, plus
 * a {@link #BLACK_JOKER} and {@link #RED_JOKER}. The {@link PlayingCard#equals}
 * method expects to directly/na&iuml;vely compare a card instance's suit to
 * these canonical static instances when testing for card equivalence.</p>
 * 
 * @since [NEXT] (#1)
 * @see PlayingCardDeck
 * @see PlayingCard
 */
public abstract class PlayingCards
{
	/** Semantic constant for the numeric rank "ace" (1). */
	public static final int ACE = 1 ;
	/** Semantic constant for the numeric rank "jack" (11). */
	public static final int JACK = 11 ;
	/** Semantic constant for the numeric rank "queen" (12). */
	public static final int QUEEN = 12 ;
	/** Semantic constant for the numeric rank "king" (13). */
	public static final int KING = 13 ;
	/**
	 * Semantic constant for the numeric rank "joker" (0).
	 * Red and black jokers should be distinguished by suits. 
	 */
	public static final int JOKER = 0 ;
	
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
		public int getID() { return 1 ; }
		public String getSymbol() { return "\u2665" ; }
		public String getLabel() { return "hearts" ; }
	};
	
	/** Defines the ID, symbol, and label for clubs. */
	public static final Suit CLUBS = new Suit()
	{
		public int getID() { return 2 ; }
		public String getSymbol() { return "\u2663" ; }
		public String getLabel() { return "clubs" ; }
	};
	
	/** Defines the ID, symbol, and label for diamonds. */
	public static final Suit DIAMONDS = new Suit()
	{
		public int getID() { return 3 ; }
		public String getSymbol() { return "\u2666" ; }
		public String getLabel() { return "diamonds" ; }
	};
	
	/** Defines the ID, symbol, and label for spades. */
	public static final Suit SPADES = new Suit()
	{
		public int getID() { return 4 ; }
		public String getSymbol() { return "\u2660" ; }
		public String getLabel() { return "spades" ; }
	};
	
	/** Defines the ID, symbol, and label for the black joker's virtual suit. */
	public static final Suit BLACK_JOKER = new Suit()
	{
		public int getID() { return -1 ; }
		public String getSymbol() { return "B" ; }
		public String getLabel() { return "black" ; }
	};
	
	/** Defines the ID, symbol, and label for the red joker's virtual suit. */
	public static final Suit RED_JOKER = new Suit()
	{
		public int getID() { return -2 ; }
		public String getSymbol() { return "R" ; }
		public String getLabel() { return "red" ; }
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
