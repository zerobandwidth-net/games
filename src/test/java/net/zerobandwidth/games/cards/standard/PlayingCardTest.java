package net.zerobandwidth.games.cards.standard;

import static org.junit.Assert.*;

import static net.zerobandwidth.games.cards.standard.PlayingCards.HEARTS;
import static net.zerobandwidth.games.cards.standard.PlayingCards.CLUBS;
import static net.zerobandwidth.games.cards.standard.PlayingCards.DIAMONDS;
import static net.zerobandwidth.games.cards.standard.PlayingCards.SPADES;

import org.junit.Test;

public class PlayingCardTest
{
	/** Exercises the "full" constructor of {@link PlayingCard}. */
	@Test
	public void testConstructor()
	{
		PlayingCard card = new PlayingCard( SPADES, 12 ) ;
		assertEquals( "spades", card.m_suit.getLabel() ) ;
		assertEquals( 12, card.m_zRank ) ;
	}

	/** Exercises getters and setters with positive tests. */
	@Test
	public void testGetSet()
	{
		PlayingCard card = new PlayingCard() ;
		assertEquals( "clubs",
			card.setSuit(CLUBS).getSuit().getLabel() ) ;
		assertEquals( 7, card.setRank(7).getRank() ) ;
	}
	
	/** Exercises {@link PlayingCard#setRank} with negative tests. */
	@Test
	public void testBadSet()
	{
		IllegalArgumentException xArg = null ;
		PlayingCard card = new PlayingCard() ;
		try { card.setRank( 999 ) ; }
		catch( IllegalArgumentException x ) { xArg = x ; }
		assertNotNull(xArg) ;
		
		xArg = null ;
		try { card.setRank( -999 ) ; }
		catch( IllegalArgumentException x ) { xArg = x ; }
		assertNotNull(xArg) ;
	}
	
	/** Exercises {@link PlayingCard#equals}. */
	@Test
	public void testEquals()
	{
		PlayingCard cardThis = new PlayingCard( SPADES, 1 ) ;
		
		assertFalse( cardThis.equals( null ) ) ; // Ensure null input is handled
		
		PlayingCard cardThat = new PlayingCard( SPADES, 1 ) ;
		assertTrue( cardThis.equals( cardThat ) ) ;
		
		cardThat.setRank( 12 ) ;                   // Same suit, different rank.
		assertFalse( cardThis.equals( cardThat ) ) ;
		
		cardThat.setSuit( HEARTS ).setRank( 1 ) ;  // Same rank, different suit.
		assertFalse( cardThis.equals( cardThat ) ) ;
		
		cardThat.setSuit( DIAMONDS ).setRank( 11 ) ; // Different rank and suit.
		assertFalse( cardThis.equals( cardThat ) ) ;
	}
}
