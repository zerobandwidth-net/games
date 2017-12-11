package net.zerobandwidth.games.cards.euchre;

import static net.zerobandwidth.games.cards.standard.PlayingCards.CLUBS;
import static net.zerobandwidth.games.cards.standard.PlayingCards.DIAMONDS;
import static net.zerobandwidth.games.cards.standard.PlayingCards.HEARTS;
import static net.zerobandwidth.games.cards.standard.PlayingCards.SPADES;
import static net.zerobandwidth.games.cards.standard.PlayingCards.ACE;

import net.zerobandwidth.games.cards.Deck;
import net.zerobandwidth.games.cards.standard.PlayingCard;
import net.zerobandwidth.games.cards.standard.PlayingCardDeck;

/**
 * A variant of {@link PlayingCardDeck} which provides the subset of cards used
 * in Euchre.
 * @since [NEXT] (#1)
 */
public class EuchreDeck
extends PlayingCardDeck
implements Deck<PlayingCard>
{
	public EuchreDeck()
	{ super() ; }
	
	public EuchreDeck( long zFlags )
	{ super(zFlags) ; }
	
	/**
	 * Overrides the standard {@link PlayingCardDeck#addRankedCards} such that
	 * only the cards from seven to king from each suit are added to the deck.
	 */
	@Override
	protected EuchreDeck addRankedCards()
	{
		for( int zRank = 7 ; zRank <= 13 ; zRank++ )
		{
			m_aCards.add( new PlayingCard( HEARTS, zRank ) ) ;
			m_aCards.add( new PlayingCard( CLUBS, zRank ) ) ;
			m_aCards.add( new PlayingCard( DIAMONDS, zRank ) ) ;
			m_aCards.add( new PlayingCard( SPADES, zRank ) ) ;
		}
		m_aCards.add( new PlayingCard( HEARTS, ACE ) ) ;
		m_aCards.add( new PlayingCard( CLUBS, ACE ) ) ;
		m_aCards.add( new PlayingCard( DIAMONDS, ACE ) ) ;
		m_aCards.add( new PlayingCard( SPADES, ACE ) ) ;
		return this ;
	}
}
