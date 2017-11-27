package net.zerobandwidth.games.cards.sorry;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Unit test class which exercises {@link SorryCardDeck}.
 * @since [NEXT] (#2)
 */
public class SorryCardDeckTest
{
	/**
	 * Exercises the constructor, and by implication,
	 * {@link SorryCardDeck#addCards()}.
	 * 
	 * Also dumps the contents of the deck to standard output for visual
	 * inspection.
	 */
	@Test
	public void testConstruction()
	{
		SorryCardDeck deck = new SorryCardDeck() ;
		assertEquals( 45, deck.size() ) ;
		StringBuilder sb = new StringBuilder() ;
		sb.append( "SorryCardDeck card dump: " ) ;
		while( deck.countRemaining() > 0 )
			sb.append( deck.next().renderCornerText() ).append( " " ) ;
		System.out.println( sb.toString() ) ;
	}

}
