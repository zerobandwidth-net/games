package net.zerobandwidth.games.cards.standard;

import static org.junit.Assert.*;

import static net.zerobandwidth.games.cards.standard.PlayingCards.HEARTS;
import static net.zerobandwidth.games.cards.standard.PlayingCards.CLUBS;
import static net.zerobandwidth.games.cards.standard.PlayingCards.DIAMONDS;
import static net.zerobandwidth.games.cards.standard.PlayingCards.SPADES;
import static net.zerobandwidth.games.cards.standard.PlayingCards.BLACK_JOKER;
import static net.zerobandwidth.games.cards.standard.PlayingCards.RED_JOKER;

import static net.zerobandwidth.games.cards.standard.PlayingCards.ACE;
import static net.zerobandwidth.games.cards.standard.PlayingCards.JACK;
import static net.zerobandwidth.games.cards.standard.PlayingCards.QUEEN;
import static net.zerobandwidth.games.cards.standard.PlayingCards.KING;
import static net.zerobandwidth.games.cards.standard.PlayingCards.JOKER;

import org.junit.Test;

/**
 * Unit test class which exercises {@link PlayingCard}.
 * @since [NEXT] (#1)
 */
public class PlayingCardTest
{
	/** Exercises the "full" constructor of {@link PlayingCard}. */
	@Test
	public void testConstructor()
	{
		PlayingCard card = new PlayingCard( SPADES, QUEEN ) ;
		assertEquals( "spades", card.m_suit.getLabel() ) ;
		assertEquals( QUEEN, card.m_zRank ) ;
	}

	/** Exercises getters and setters with positive tests. */
	@Test
	public void testGetSet()
	{
		PlayingCard card = new PlayingCard() ;
		
		assertEquals( "clubs",
			card.setSuit(CLUBS).getSuit().getLabel() ) ;
		assertEquals( 7, card.setRank(7).getRank() ) ;
		
		// Verify that setting suit to "black joker" will overwrite rank.
		card.setSuit( BLACK_JOKER ) ;
		assertEquals( "black", card.getSuit().getLabel() ) ;
		assertEquals( JOKER, card.getRank() ) ;     // Suit change overwrote it.
		
		// Verify that setting suit to "red joker" will overwrite rank.
		card = new PlayingCard( HEARTS, 10 ) ;
		card.setSuit( RED_JOKER ) ;
		assertEquals( "red", card.getSuit().getLabel() ) ;
		assertEquals( JOKER, card.getRank() ) ;     // Suit change overwrote it.
	}
	
	/** Exercises {@link PlayingCard#setRank} with negative tests. */
	@Test
	public void testBadSet()
	{
		PlayingCard card = new PlayingCard() ;

		// Test rank too high.
		IllegalArgumentException xArg = null ;
		try { card.setRank( 999 ) ; }
		catch( IllegalArgumentException x ) { xArg = x ; }
		assertNotNull(xArg) ;
		
		// Test rank too low.
		xArg = null ;
		try { card.setRank( -999 ) ; }
		catch( IllegalArgumentException x ) { xArg = x ; }
		assertNotNull(xArg) ;
		
		// Test assigning non-joker rank to a black joker.
		xArg = null ;
		card.setSuit( BLACK_JOKER ) ;
		try { card.setRank( ACE ) ; }
		catch( IllegalArgumentException x ) { xArg = x ; }
		assertNotNull(xArg) ;
		
		// Test assigning non-joker rank to a red joker.
		xArg = null ;
		card.setSuit( RED_JOKER ) ;
		try { card.setRank( JACK ) ; }
		catch( IllegalArgumentException x ) { xArg = x ; }
		assertNotNull(xArg) ;
	}
	
	/** Exercises {@link PlayingCard#equals}. */
	@Test
	public void testEquals()
	{
		PlayingCard cardThis = new PlayingCard( SPADES, ACE ) ;
		
		assertFalse( cardThis.equals( null ) ) ; // Ensure null input is handled
		
		PlayingCard cardThat = new PlayingCard( SPADES, ACE ) ;
		assertTrue( cardThis.equals( cardThat ) ) ;
		
		cardThat.setRank( QUEEN ) ;                // Same suit, different rank.
		assertFalse( cardThis.equals( cardThat ) ) ;
		
		cardThat.setSuit( HEARTS ).setRank( ACE ) ; // Same rank different suit.
		assertFalse( cardThis.equals( cardThat ) ) ;
		
		cardThat.setSuit( DIAMONDS ).setRank( JACK ) ; // Different rank & suit.
		assertFalse( cardThis.equals( cardThat ) ) ;
	}
	
	/** Exercises {@link PlayingCard#isJoker}. */
	@Test
	public void testIsJoker()
	{
		assertTrue( (new PlayingCard( BLACK_JOKER, JOKER )).isJoker() ) ;
		assertTrue( (new PlayingCard( RED_JOKER, JOKER )).isJoker() ) ;
		
		assertFalse( (new PlayingCard( HEARTS, ACE )).isJoker() ) ;
		assertFalse( (new PlayingCard( CLUBS, 2 )).isJoker() ) ;
		assertFalse( (new PlayingCard( DIAMONDS, JACK )).isJoker() ) ;
		assertFalse( (new PlayingCard( SPADES, QUEEN )).isJoker() ) ;
	}
	
	/** Exercises {@link PlayingCard#renderCornerText()}. */
	@Test
	public void testRenderCornerText()
	{
		assertEquals( "A\u2660",
			(new PlayingCard( SPADES, ACE )).renderCornerText() ) ;
		assertEquals( "Q\u2660",
			(new PlayingCard( SPADES, QUEEN )).renderCornerText() ) ;
		assertEquals( "J\u2666",
			(new PlayingCard( DIAMONDS, JACK )).renderCornerText() ) ;
		assertEquals( "7\u2663",
			(new PlayingCard( CLUBS, 7 )).renderCornerText() ) ;
		assertEquals( "K\u2665",
			(new PlayingCard( HEARTS, KING )).renderCornerText() ) ;
		assertEquals( "BX",
			(new PlayingCard( BLACK_JOKER, JOKER )).renderCornerText() ) ;
		assertEquals( "RX",
			(new PlayingCard( RED_JOKER, JOKER )).renderCornerText() ) ;
	}
	
	/** Exercises {@link PlayingCard#describe}. */
	@Test
	public void testDescribe()
	{
		assertEquals( "ace of spades",
			(new PlayingCard( SPADES, ACE )).describe() ) ;
		assertEquals( "queen of spades",
			(new PlayingCard( SPADES, QUEEN )).describe() ) ;
		assertEquals( "jack of diamonds",
			(new PlayingCard( DIAMONDS, JACK )).describe() ) ;
		assertEquals( "seven of clubs",
			(new PlayingCard( CLUBS, 7 )).describe() ) ;
		assertEquals( "king of hearts",
			(new PlayingCard( HEARTS, KING )).describe() ) ;
		assertEquals( "black joker",
			(new PlayingCard( BLACK_JOKER, JOKER )).describe() ) ;
		assertEquals( "red joker",
			(new PlayingCard( RED_JOKER, JOKER )).describe() ) ;		
	}
}
