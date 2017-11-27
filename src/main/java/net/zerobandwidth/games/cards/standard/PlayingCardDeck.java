package net.zerobandwidth.games.cards.standard;

import java.util.ArrayList;
//import java.util.Collections;

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
	 * The complete deck of cards. A pointer into the deck indicates the "next"
	 * card to be dealt.
	 */
//	protected ArrayList<PlayingCard> m_aCards = null ;
	
	/** The "next" card to be dealt from the deck. */
//	protected int m_nNext = 0 ;
	
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
	
	/*
	 * Fully randomizes the cards by shuffling seven times. They say it takes
	 * seven shuffles to fully randomize a physical deck of cards, so why not
	 * apply the same here?
	 //
	@Override
	public PlayingCardDeck shuffle()
	{
		for( int i = 0 ; i < 7 ; i++ )
			Collections.shuffle( m_aCards ) ;
		m_nNext = 0 ;
		return this ;
	}*/

	/**
	 * @throws UnsupportedOperationException <i>(not yet implemented)</i>
	 */
	@Override
	public PlayingCardDeck shuffleRemaining()
	{ throw new UnsupportedOperationException( "Not yet implemented." ) ; }

	/*
	 * @return the next unseen card from the deck, or {@code null} if we've run
	 *  off the bottom of the deck
	 //
	@Override
	public PlayingCard next()
	{
		if( m_nNext >= m_aCards.size() ) // We've run off the bottom of the deck.
			return null ;
		return m_aCards.get( m_nNext++ ) ;
	}*/
/*
	@Override
	public PlayingCard[] peek( int nCards )
	{
		PlayingCard aCards[] = new PlayingCard[nCards] ;
		for( int i = 0 ; i < nCards ; i++ )
		{
			if( m_nNext + i < m_aCards.size() )
				aCards[i] = m_aCards.get( m_nNext + i ) ;
			else
				aCards[i] = null ;
		}
		return aCards ;
	}

	@Override
	public int size()
	{ return m_aCards.size() ; }

	@Override
	public int countRemaining()
	{ return m_aCards.size() - m_nNext ; }
*/
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
