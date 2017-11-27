package net.zerobandwidth.games.cards.standard;

import net.zerobandwidth.games.cards.Card;
import net.zerobandwidth.games.cards.Suit;

import static net.zerobandwidth.games.cards.standard.PlayingCards.BLACK_JOKER;
import static net.zerobandwidth.games.cards.standard.PlayingCards.RED_JOKER;
import static net.zerobandwidth.games.cards.standard.PlayingCards.JOKER;

import static net.zerobandwidth.games.cards.standard.PlayingCards.RANK_SYMBOLS;
import static net.zerobandwidth.games.cards.standard.PlayingCards.RANK_LABELS;

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
	public PlayingCard setRank( int zRank )
	throws IllegalArgumentException
	{
		if( ! PlayingCards.isValidRank(zRank) )
		{
			throw new IllegalArgumentException( (new StringBuilder())
					.append( "Cannot set invalid rank [" )
					.append( zRank )
					.append( "] to a card." )
					.toString()
				);
		}
		if( this.isJoker() && zRank != JOKER )
		{
			throw new IllegalArgumentException(
				"Must set a joker's rank to 0." ) ;
		}
		m_zRank = zRank ;
		return this ;
	}

	@Override
	public Suit getSuit()
	{ return m_suit ; }

	@Override
	public PlayingCard setSuit( Suit suit )
	{
		m_suit = suit ;
		if( this.isJoker() ) this.setRank( JOKER ) ;
		return this ;
	}
	
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
	
	/**
	 * Indicates whether this card belongs to one of the "joker" suits,
	 * {@link PlayingCards#BLACK_JOKER} or {@link PlayingCards#RED_JOKER}.
	 * @return {@code true} iff the card belongs to a joker suit.
	 */
	public boolean isJoker()
	{ return ( this.m_suit == BLACK_JOKER || this.m_suit == RED_JOKER ) ; }
	
	/**
	 * Renders the rank and suit of the text as it might appear in the corner
	 * of the card; for example, "Q&#2660;".
	 * @return the corner text of the card
	 */
	public String renderCornerText()
	{
		StringBuilder sb = new StringBuilder() ;
		if( ! this.isJoker() )
			sb.append( RANK_SYMBOLS[m_zRank] ).append( m_suit.getSymbol() ) ;
		else
			sb.append( m_suit.getSymbol() ).append( RANK_SYMBOLS[JOKER] ) ;
		return sb.toString() ;
	}
	
	/**
	 * Renders a full textual description of the card's rank and suit; for
	 * example, "queen of spades".
	 * @return a full description of the card
	 */
	public String describe()
	{
		StringBuilder sb = new StringBuilder() ;
		if( ! this.isJoker() )
		{ // Describe a normal card.
			sb.append( RANK_LABELS[m_zRank] )
			  .append( " of " )
			  .append( m_suit.getLabel() )
			  ;
		}
		else
		{ // Describe a joker.
			sb.append( m_suit.getLabel() )
			  .append( " " )
			  .append( RANK_LABELS[JOKER] )
			  ;
		}
		return sb.toString() ;
	}
}
