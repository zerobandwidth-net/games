package net.zerobandwidth.games.cards.sorry;

import net.zerobandwidth.games.cards.AbstractDeck;
import net.zerobandwidth.games.cards.Deck;

import static net.zerobandwidth.games.cards.sorry.SorryCards.*;

/**
 * Implements a deck of movement cards from <i>Sorry!</i>.
 * @since [NEXT] (#2)
 */
public class SorryCardDeck
extends AbstractDeck<SorryCardDeck,SorryCard>
implements Deck<SorryCard>
{
/// Constructors and initializers //////////////////////////////////////////////
	
	/**
	 * Default constructor. Adds the game-standard set of cards to the deck.
	 */
	public SorryCardDeck()
	{ this.init() ; }
	
	/**
	 * Adds all cards to the deck, but does not shuffle it.
	 * @return (fluid)
	 */
	@Override
	protected SorryCardDeck addCards()
	{
		// Add the extra rank 1 card first.
		m_aCards.add( new SorryCard(
			SUIT_FORWARD, 1, ATTR_OR_MOVE_FROM_START ) ) ;
		
		for( int i = 0 ; i < 4 ; i++ )
		{ // Add four copies of each card.
			m_aCards.add( new SorryCard(
				SUIT_FORWARD, 1, ATTR_OR_MOVE_FROM_START ) ) ;
			m_aCards.add( new SorryCard(
				SUIT_FORWARD, 2, ATTR_OR_MOVE_FROM_START | ATTR_DRAW_AGAIN ) ) ;
			m_aCards.add( new SorryCard(
				SUIT_FORWARD, 3 ) ) ;
			m_aCards.add( new SorryCard(
				SUIT_BACKWARD, 4 ) ) ;
			m_aCards.add( new SorryCard(
				SUIT_FORWARD, 5 ) ) ;
			m_aCards.add( new SorryCard(
				SUIT_FORWARD, 7, ATTR_SPLIT_MOVEMENT ) ) ;
			m_aCards.add( new SorryCard(
				SUIT_FORWARD, 8 ) ) ;
			m_aCards.add( new SorryCard(
				SUIT_FORWARD, 10, ATTR_OR_BACKWARD_ONE ) ) ;
			m_aCards.add( new SorryCard(
				SUIT_FORWARD, 11, ATTR_OR_CHANGE_PLACES ) ) ;
			m_aCards.add( new SorryCard(
				SUIT_FORWARD, 12 ) ) ;
			m_aCards.add( new SorryCard(
				SUIT_SORRY, RANK_SORRY ) ) ;
		}
		return this ;
	}
	
/// net.zerobandwidth.games.cards.Deck /////////////////////////////////////////
	
	/**
	 * @throws UnsupportedOperationException <i>(not yet implemented)</i>
	 */
	@Override
	public SorryCardDeck shuffleRemaining()
	{ throw new UnsupportedOperationException( "Not yet implemented." ) ; }
}
