package net.zerobandwidth.games.cards.standard;

import static org.junit.Assert.*;
import static net.zerobandwidth.games.cards.standard.PlayingCardDeck.* ;

import org.junit.Test;

/**
 * Unit test class which exercises {@link PlayingCardDeck}.
 * @since [NEXT] (#1)
 */
public class PlayingCardDeckTest
{
	/**
	 * Used by various tests to dump the contents of a deck to standard output
	 * for visual inspection.
	 * @param deck the deck to be rendered
	 */
	public static void dumpDeckContents( PlayingCardDeck deck )
	{
		StringBuilder sb = new StringBuilder() ;
		for( PlayingCard card : deck.m_aCards )
			sb.append( card.renderCornerText() ).append( " " ) ;
		System.out.println( sb.toString() ) ;
	}

	/**
	 * Exercises the various constructors.
	 * Implies testing of {@link PlayingCardDeck#init},
	 * {@link PlayingCardDeck#addRankedCards}, and {@link PlayingCardDeck#size}.
	 */
	@Test
	public void testConstructors()
	{
		PlayingCardDeck deck = new PlayingCardDeck() ;
		assertEquals( 52, deck.size() ) ;
		deck = new PlayingCardDeck( WITH_BLACK_JOKER ) ;
		assertEquals( 53, deck.size() ) ;
		deck = new PlayingCardDeck( WITH_RED_JOKER ) ;
		assertEquals( 53, deck.size() ) ;
		deck = new PlayingCardDeck( WITH_BLACK_JOKER | WITH_RED_JOKER ) ;
		assertEquals( 54, deck.size() ) ;
		deck = new PlayingCardDeck( DOUBLE_DECK ) ;
		assertEquals( 104, deck.size() ) ;
		deck = new PlayingCardDeck(
					WITH_BLACK_JOKER | WITH_RED_JOKER | DOUBLE_DECK ) ;
		assertEquals( 106, deck.size() ) ;
	}

	/**
	 * "Exercises" {@link PlayingCardDeck#shuffle}. There's no straightforward
	 * way to test whether a deck has been sufficiently randomized; the test
	 * will simply execute the shuffle directive, then dump the contents of the
	 * deck to standard output for visual inspection.
	 */
	@Test
	public void testShuffle()
	{
		PlayingCardDeck deck = new PlayingCardDeck() ;
		deck.shuffle() ;
		dumpDeckContents( deck ) ;
	}
	
	/**
	 * "Exercises" {@link PlayingCardDeck#shuffleRemaining}.
	 * That is, verifies that, sure enough, the method isn't implemented yet.
	 */
	@Test
	public void testShuffleRemaining()
	{
		UnsupportedOperationException xNope = null ;
		try { (new PlayingCardDeck()).shuffleRemaining() ; }
		catch( UnsupportedOperationException x ) { xNope = x ; }
		assertNotNull(xNope) ;
	}
	
	/** Exercises {@link PlayingCardDeck#next}. */
	@Test
	public void testNext()
	{
		PlayingCardDeck deck = new PlayingCardDeck() ;
		deck.shuffle() ;              // Observe, there is nothing up my sleeve!
		for( int i = 0 ; i < 52 ; i++ )
			assertTrue( deck.next().equals( deck.m_aCards.get(i) ) ) ;
		assertNull( deck.next() ) ;
	}
	
	/** Exercises {@link PlayingCardDeck#peek}. */
	@Test
	public void testPeek()
	{
		PlayingCardDeck deck = new PlayingCardDeck() ;
		deck.shuffle() ;           // The microprocessor is faster than the eye!
		PlayingCard aPeeked[] = deck.peek(5) ;
		for( int i = 0 ; i < 5 ; i++ )
			assertTrue( aPeeked[i].equals( deck.m_aCards.get(i) ) ) ;
		deck.m_nNext = 50 ;      // Force the pointer forward to almost the end.
		aPeeked = deck.peek(5) ;
		assertTrue( aPeeked[0].equals( deck.m_aCards.get(50) ) ) ;
		assertTrue( aPeeked[1].equals( deck.m_aCards.get(51) ) ) ;
		assertNull( aPeeked[2] ) ;
		assertNull( aPeeked[3] ) ;
		assertNull( aPeeked[4] ) ;
	}
	
	/** Exercises {@link PlayingCardDeck#countRemaining}. */
	@Test
	public void testCountRemaining()
	{
		PlayingCardDeck deck = new PlayingCardDeck() ;
		assertEquals( 52, deck.countRemaining() ) ;
		deck.next() ; assertEquals( 51, deck.countRemaining() ) ;
		deck.next() ; assertEquals( 50, deck.countRemaining() ) ;
		deck.m_nNext = 50 ;      // Force the pointer forward to almost the end.
		assertEquals( 2, deck.countRemaining() ) ;
		deck.next() ; assertEquals( 1, deck.countRemaining() ) ;
		deck.next() ; assertEquals( 0, deck.countRemaining() ) ;
		deck.next() ; assertEquals( 0, deck.countRemaining() ) ;
	}
}
