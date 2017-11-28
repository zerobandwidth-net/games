package net.zerobandwidth.games.cards.sorry;

import net.zerobandwidth.games.cards.Card;
import net.zerobandwidth.games.cards.Suit;

import static net.zerobandwidth.games.cards.sorry.SorryCards.RANK_SORRY;

import static net.zerobandwidth.games.cards.sorry.SorryCards.SUIT_FORWARD;
import static net.zerobandwidth.games.cards.sorry.SorryCards.SUIT_BACKWARD;
import static net.zerobandwidth.games.cards.sorry.SorryCards.SUIT_SORRY;

import static net.zerobandwidth.games.cards.sorry.SorryCards.ATTR_NONE;

/**
 * Represents one of the movement cards in the game <i>Sorry!</i>.
 * @since [NEXT] (#2)
 */
public class SorryCard implements Card
{
/// Member fields //////////////////////////////////////////////////////////////
	
	/** The card's rank. */
	protected int m_zRank = RANK_UNDEFINED ;
	
	/** The card's "suit" (forward, backward, Sorry!) */
	protected Suit m_suit = null ;
	
	/** The card's secondary attributes. */
	protected long m_bmAttrs = 0L ;
	
/// Constructors ///////////////////////////////////////////////////////////////
	
	/**
	 * Trivial constructor; leaves all attributes undefined. Should not be used
	 * generally; intended only for various testing scenarios.
	 */
	protected SorryCard()
	{ this.setSuit( null ).setRank( RANK_UNDEFINED ).setAttrs( ATTR_NONE ) ; }
	
	/**
	 * Constructs the card with rank and "suit" but no special attributes.
	 * @param suit the suit
	 * @param zRank the rank &mdash; that is, the number of spaces moved
	 */
	public SorryCard( Suit suit, int zRank )
	{ this.setSuit( suit ).setRank( zRank ).setAttrs( ATTR_NONE ) ; }
	
	/**
	 * Constructs the card with rank, "suit", and special attributes.
	 * @param suit the suit
	 * @param zRank the rank &mdash; that is, the number of spaces moved
	 * @param bmAttrs any special attributes that the card also carries
	 */
	public SorryCard( Suit suit, int zRank, long bmAttrs )
	{ this.setSuit( suit ).setRank( zRank ).setAttrs( bmAttrs ) ; }
	
/// net.zerobandwidth.games.cards.Card /////////////////////////////////////////
	
	@Override
	public int getRank()
	{ return m_zRank ; }

	@Override
	public SorryCard setRank( int zRank )
	{ m_zRank = zRank ; return this ; }

	@Override
	public Suit getSuit()
	{ return m_suit ; }

	@Override
	public SorryCard setSuit( Suit suit )
	{
		m_suit = suit ;
		if( suit == SUIT_SORRY ) this.setRank( RANK_SORRY ) ;
		return this ;
	}
	
/// Secondary Attribute Accessors/Mutators /////////////////////////////////////
	
	/**
	 * Returns the entire bitmask of special attributes.
	 * Not generally useful; use {@link #hasAttr} instead to check specific
	 * logical bits.
	 * @return the entire set of special attributes on this card
	 */
	public long getAttrs()
	{ return m_bmAttrs ; }
	
	/**
	 * Indicates whether the card instance has the specified attribute flag.
	 * @param bmAttrs the attribute flag(s) to be examined
	 * @return {@code true} iff the card has the attribute(s) specified
	 */
	public boolean hasAttr( long bmAttrs )
	{ return ( ( m_bmAttrs & bmAttrs ) == bmAttrs ) ; } 

	/**
	 * Sets the card's secondary attribute flags.
	 * @param bmAttrs the attribute flags to be set
	 * @return (fluid)
	 */
	public SorryCard setAttrs( long bmAttrs )
	{ m_bmAttrs = bmAttrs ; return this ; }
	
/// Other Methods //////////////////////////////////////////////////////////////
	
	/**
	 * Compares two cards for equivalence.
	 * The cards must have the same rank, suit, and attributes.
	 * @param that the other card to compare to this one
	 * @return {@code true} iff the cards are equivalent
	 */
	public boolean equals( SorryCard that )
	{
		if( that == null ) return false ;
		return ( this.m_suit == that.getSuit()
			&&   this.m_zRank == that.getRank()
			&&   this.m_bmAttrs == that.m_bmAttrs ) ;
	}
	
	/**
	 * Renders the rank and "suit" of the card as it might appear in the corner
	 * of the card; for example, "10&#9654;" or "&#9664;4". 
	 * @return the text to be shown in the corner of the card
	 */
	public String renderCornerText()
	{
		if( m_suit == SUIT_FORWARD )
		{
			return (new StringBuilder())
				.append( m_zRank )
				.append( m_suit.getSymbol() )
				.toString()
				;
		}
		else if( m_suit == SUIT_BACKWARD )
		{
			return (new StringBuilder())
				.append( m_suit.getSymbol() )
				.append( m_zRank )
				.toString()
				;
		}
		else if( m_suit == SUIT_SORRY )
			return "S" ;
		else
			return "???" ;
	}
}
