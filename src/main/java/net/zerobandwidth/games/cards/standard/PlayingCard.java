package net.zerobandwidth.games.cards.standard;

import net.zerobandwidth.games.cards.Card;
import net.zerobandwidth.games.cards.Suit;

/**
 * Implements a standard playing card.
 * @since [NEXT] (#1)
 */
public class PlayingCard
implements Card
{
/// Member fields //////////////////////////////////////////////////////////////
	
	/** The card's rank in its suit. */
	protected int m_zRank = RANK_UNDEFINED ;
	/** The card's suit (hearts, clubs, diamonds, or spades) */
	protected Suit m_suit = null ;

/// Constructors ///////////////////////////////////////////////////////////////
	
	/**
	 * Trivial constructor; leaves all attributes undefined. Should not be used
	 * generally; intended only for various testing scenarios.
	 */
	protected PlayingCard()
	{ this.setSuit( null ).setRank( RANK_UNDEFINED ) ; }
	
	/** Full constructor; allows both parameters to be specified. */
	public PlayingCard( Suit suit, int zRank )
	{ this.setSuit(suit).setRank(zRank) ; }
	
/// net.zerobandwidth.games.cards.Card /////////////////////////////////////////
	
	@Override
	public int getRank()
	{ return m_zRank ; }

	/**
	 * @throws IllegalArgumentException if the rank is invalid
	 * @see PlayingCards#isValidRank(int)
	 */
	@Override
	public Card setRank( int zRank )
	throws IllegalArgumentException
	{
		if( ! PlayingCards.isValidRank(zRank) )
		{
			throw new IllegalArgumentException( (new StringBuilder())
					.append( "Cannot construct card with invalid rank [" )
					.append( zRank )
					.append( "]." )
					.toString()
				);
		}
		m_zRank = zRank ;
		return this ;
	}

	@Override
	public Suit getSuit()
	{ return m_suit ; }

	@Override
	public Card setSuit( Suit suit )
	{ m_suit = suit ; return this ; }
	
/// Other methods //////////////////////////////////////////////////////////////

	/**
	 * Compares two cards for equivalence.
	 * The algorithm uses the na&iuml;ve test of equality for the suit object,
	 * because we expect the caller to have been using the final suit constants
	 * that are defined in the {@link PlayingCards} utility class, and thus the
	 * two {@code PlayingCard} objects really would be using <i>the same</i>
	 * suit instance, not merely equivalent ones. 
	 * @param that the other card to compare to this one
	 * @return {@code true} iff the cards are equivalent
	 */
	public boolean equals( Card that )
	{
		if( that == null ) return false ;
		return ( this.m_suit == that.getSuit()
			&&   this.m_zRank == that.getRank()
			);
	}
	
}
