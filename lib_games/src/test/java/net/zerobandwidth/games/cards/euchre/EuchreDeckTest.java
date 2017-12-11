package net.zerobandwidth.games.cards.euchre;

import static org.junit.Assert.*;

import org.junit.Test;

import net.zerobandwidth.games.cards.standard.PlayingCardDeckTest;

import static net.zerobandwidth.games.cards.standard.PlayingCards.ACE ;
import static net.zerobandwidth.games.cards.standard.PlayingCards.KING ;

/**
 * Unit test class which exercises the methods of {@link EuchreDeck} that are
 * distinct from those of its parent class, {@link PlayingCardDeck}.
 * @since [NEXT] (#1)
 */
public class EuchreDeckTest
{
	/**
	 * Exercises {@link EuchreDeck#addRankedCards} by constructing an instance
	 * and verifying that cards with ranks only in the 7-to-king range are
	 * included.
	 */
	@Test
	public void testAddRankedCards()
	{
		EuchreDeck deck = new EuchreDeck() ;
		PlayingCardDeckTest.dumpDeckContents( deck ) ;  // for visual inspection
		while( deck.countRemaining() > 0 )
		{ // Verify that each card is an ace, or is between 7 and king.
			int zRank = deck.next().getRank() ;
			assertTrue( zRank == ACE || ( zRank >= 7 && zRank <= KING ) ) ;
		}
	}
}
