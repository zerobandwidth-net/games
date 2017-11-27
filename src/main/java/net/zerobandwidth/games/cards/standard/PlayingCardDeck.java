package net.zerobandwidth.games.cards.standard;

import java.util.ArrayList;

import net.zerobandwidth.games.cards.AbstractDeck;
import net.zerobandwidth.games.cards.Deck;

import static net.zerobandwidth.games.cards.standard.PlayingCards.* ;

/**
 * Implements a standard deck of playing cards.
 * @since [NEXT] (#1)
 */
public class PlayingCardDeck
extends AbstractDeck<PlayingCardDeck,PlayingCard>
implements Deck<PlayingCard>
{
/// Constants: creation control flags //////////////////////////////////////////
	
	/** Pass to the constructor to add a black joker to the deck. */
	public static final long WITH_BLACK_JOKER = 1L ;
	/** Pass to the constructor to add a red joker to the deck. */
	public static final long WITH_RED_JOKER = 2L ;
	/** Pass to the constructor to make a "double deck" of ranked cards. */
	public static final long DOUBLE_DECK = 4L ;
	
/// Member fields //////////////////////////////////////////////////////////////
	
	/**
	 * Flags used at creation time to dictate special behavior in the various
	 * initializers. 
	 */
	protected long m_bmFlags = 0L ;

/// Constructors and initializers //////////////////////////////////////////////
	
	/**
	 * Default constructor. Adds all the usual cards to the deck, but doesn't
	 * add either of the jokers.
	 */
	public PlayingCardDeck()
	{ m_bmFlags = 0L ; this.init() ; }
	
	/**
	 * Constructs an instance with one or both jokers.
	 * @param zFlags various flags that control what goes into the deck
	 */
	public PlayingCardDeck( long zFlags )
	{ m_bmFlags = zFlags ; this.init() ; }
	
	/**
	 * Used by {@link #addCards} to add a set of 52 ranked cards to the deck.
	 * @return
	 */
	protected PlayingCardDeck addRankedCards()
	{
		for( int zRank = 1 ; zRank <= 13 ; zRank++ )
		{
			m_aCards.add( new PlayingCard( HEARTS, zRank ) ) ;
			m_aCards.add( new PlayingCard( CLUBS, zRank ) ) ;
			m_aCards.add( new PlayingCard( DIAMONDS, zRank ) ) ;
			m_aCards.add( new PlayingCard( SPADES, zRank ) ) ;
		}
		return this ;
	}
	
/// net.zerobandwidth.games.cards.Deck /////////////////////////////////////////

	/**
	 * @throws UnsupportedOperationException <i>(not yet implemented)</i>
	 */
	@Override
	public PlayingCardDeck shuffleRemaining()
	{ throw new UnsupportedOperationException( "Not yet implemented." ) ; }

/// net.zerobandwidth.games.cards.AbstractDeck /////////////////////////////////
	
	/**
	 * Examines the flags sent in via the constructor to determine which cards
	 * to add, and how many.
	 * @see #addRankedCards()
	 */
	@Override
	protected PlayingCardDeck addCards()
	{
		this.addRankedCards() ;
		if( ( m_bmFlags & DOUBLE_DECK ) == DOUBLE_DECK )
			this.addRankedCards() ; // again
		if( ( m_bmFlags & WITH_BLACK_JOKER ) == WITH_BLACK_JOKER )
			m_aCards.add( new PlayingCard( BLACK_JOKER, JOKER ) ) ;
		if( ( m_bmFlags & WITH_RED_JOKER ) == WITH_RED_JOKER )
			m_aCards.add( new PlayingCard( RED_JOKER, JOKER ) ) ;
		return this ;
	}
	
/// Other methods //////////////////////////////////////////////////////////////
	
	/**
	 * Concealed accessor for the actual collection of card instances.
	 * Should not be consumed generally; intended only for unit tests.
	 * @return the collection of cards
	 */
	protected ArrayList<PlayingCard> getCards()
	{ return m_aCards ; }
}
