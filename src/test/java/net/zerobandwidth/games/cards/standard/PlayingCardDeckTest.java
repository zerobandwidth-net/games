package net.zerobandwidth.games.cards.standard;

import static org.junit.Assert.*;
import static net.zerobandwidth.games.cards.standard.PlayingCardDeck.* ;

import java.util.List ;
import java.util.ArrayList ;
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
		sb.append( deck.getClass().getSimpleName() )
		  .append( " card dump: " )
		  ;
		while( deck.countRemaining() > 0 )
			sb.append( deck.next().renderCornerText() ).append( " " ) ;
		System.out.println( sb.toString() ) ;
	}

	/**
	 * Exercises the various constructors.
	 * Implies testing of {@link PlayingCardDeck#addRankedCards}.
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
		ArrayList<PlayingCard> aCards = deck.getCards() ;
		for( int i = 0 ; i < 52 ; i++ )
			assertTrue( deck.next().equals( aCards.get(i) ) ) ;
		assertNull( deck.next() ) ;
	}
	
	/**
	 * Exercises {@link PlayingCardDeck#peek}.
	 * Beyond testing the basic operation of {@link AbstractDeck#peek}, this
	 * method verifies that we get the exact card that we expect at each
	 * position in a given peek result set.
	 */
	@Test
	public void testPeek()
	{
		PlayingCardDeck deck = new PlayingCardDeck() ;
		deck.shuffle() ;           // The microprocessor is faster than the eye!
		ArrayList<PlayingCard> aCards = deck.getCards() ;
		List<PlayingCard> aPeeked = deck.peek(55) ;
		for( int i = 0 ; i < 52 ; i++ )
			assertTrue( aPeeked.get(i).equals( aCards.get(i) ) ) ;
		assertNull( aPeeked.get(52) ) ;
		assertNull( aPeeked.get(53) ) ;
		assertNull( aPeeked.get(54) ) ;
	}
}
