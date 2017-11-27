package net.zerobandwidth.games.cards.sorry;

import static org.junit.Assert.*;
import static net.zerobandwidth.games.cards.sorry.SorryCards.*;

import org.junit.Test;

/**
 * Unit test class which exercises {@link SorryCard}.
 * @since [NEXT] (#2)
 */
public class SorryCardTest
{
	/** Exercises the various constructors. */
	@Test
	public void testConstructors()
	{
		assertEquals( "5\u25b6",
			(new SorryCard( SUIT_FORWARD, 5 )).renderCornerText() ) ;
		assertEquals( "\u25c04",
			(new SorryCard( SUIT_BACKWARD, 4 )).renderCornerText() ) ;
		assertEquals( "S",
			(new SorryCard( SUIT_SORRY, RANK_SORRY )).renderCornerText() ) ;
	}

	/** Exercises various accessors/mutators. */
	@Test
	public void testGetSet()
	{
		SorryCard card = new SorryCard() ;
		assertEquals( "forward",
			card.setSuit( SUIT_FORWARD ).getSuit().getLabel() ) ;
		assertEquals( 7, card.setRank(7).getRank() ) ;
		assertEquals( 7L, card
				.setAttrs( ATTR_OR_MOVE_FROM_START 
					| ATTR_DRAW_AGAIN 
					| ATTR_SPLIT_MOVEMENT )
				.getAttrs()
			);
	}
	
	/** Exercises {@link SorryCard#equals}. */
	@Test
	public void testEquals()
	{
		SorryCard cardThis =
				new SorryCard( SUIT_FORWARD, 7, ATTR_SPLIT_MOVEMENT ) ;
		
		SorryCard cardThat = null ;
		assertFalse( cardThis.equals( cardThat ) ) ;
		
		cardThat = (new SorryCard()).setSuit( SUIT_FORWARD ) ;
		assertFalse( cardThis.equals( cardThat ) ) ;
		
		cardThat.setRank( 7 ) ;
		assertFalse( cardThis.equals( cardThat ) ) ;
		
		cardThat.setAttrs( ATTR_SPLIT_MOVEMENT ) ;
		assertTrue( cardThis.equals( cardThat ) ) ;
	}
	
	/** Exercises {@link SorryCard#renderCornerText}. */
	@Test
	public void testRenderCornerText()
	{
		assertEquals( "\u25c04",
			(new SorryCard( SUIT_BACKWARD, 4 )).renderCornerText() ) ;
		assertEquals( "12\u25b6",
			(new SorryCard( SUIT_FORWARD, 12 )).renderCornerText() ) ;
		assertEquals( "S",
			(new SorryCard( SUIT_SORRY, 999 )).renderCornerText() ) ;
	}
}
