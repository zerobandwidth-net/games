package net.zerobandwidth.games.cards;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Random;

import org.junit.Test;

/**
 * Unit test class which exercises canonical methods provided by
 * {@link AbstractDeck}.
 * @since [NEXT] (#2)
 */
public class AbstractDeckTest
{
	/** Random number generator for the unit test class. */
	private static final Random RNG = new Random() ;
	
	/**
	 * Trivial implementation of a {@link Card} for these unit tests.
	 * @since [NEXT] (#2)
	 */
	private static class TestCardImpl
	implements Card
	{
		private Suit m_suit = null ;
		private int m_zRank = RANK_UNDEFINED ;
		
		public TestCardImpl( Suit suit, int zRank )
		{ m_suit = suit ; m_zRank = zRank ; }
		
		@Override
		public int getRank()
		{ return m_zRank ; }

		@Override
		public TestCardImpl setRank( int zRank )
		{ m_zRank = zRank ; return this ; }

		@Override
		public Suit getSuit()
		{ return m_suit ; }

		@Override
		public TestCardImpl setSuit( Suit suit )
		{ m_suit = suit ; return this ; }
	}
	
	/**
	 * Trivial implementation of a {@link Suit} for these unit tests.
	 * @since [NEXT] (#2)
	 */
	private static class TestSuitImpl
	implements Suit
	{
		@Override
		public int getID() { return 0 ; }

		@Override
		public String getSymbol() { return "S" ; }

		@Override
		public String getLabel() { return "suit" ; }
	}
	
	/**
	 * Trivial extension of {@link AbstractDeck} for these unit tests.
	 * @since [NEXT] (#2)
	 */
	private static class TestDeckImpl
	extends AbstractDeck<TestDeckImpl,TestCardImpl>
	implements Deck<TestCardImpl>
	{
		public int m_nCreationSize = 0 ;
		
		protected TestDeckImpl( int nSize )
		{ m_nCreationSize = nSize ; this.init() ; }
		
		@Override
		public TestDeckImpl shuffleRemaining()
		{ return null ; }

		@Override
		protected TestDeckImpl addCards()
		{
			Suit suit = new TestSuitImpl() ;
			for( int i = 0 ; i < m_nCreationSize ; i++ )
				m_aCards.add( new TestCardImpl( suit, RNG.nextInt(1000000) ) ) ;
			return this ;
		}
	}
	
	/**
	 * Exercises {@link AbstractDeck#init}, which implies a test of the test
	 * implementation's {@code addCards()}. 
	 */
	@Test
	public void testInit()
	{
		final int nExpectedSize = RNG.nextInt(100) ;
		TestDeckImpl deck = new TestDeckImpl( nExpectedSize ) ;
		assertEquals( nExpectedSize, deck.size() ) ;
		assertEquals( 0, deck.m_nNext ) ;
	}

	/**
	 * Trivially exercises {@link AbstractDeck#shuffle} by executing the method
	 * and hoping it doesn't die. Descendant classes should include a unit test
	 * that is more meaningful, if possible.
	 */
	@Test
	public void testShuffle()
	{ (new TestDeckImpl(10)).shuffle() ; }

	/** Exercises {@link AbstractDeck#next}. */
	@Test
	public void testNext()
	{
		TestDeckImpl deck = new TestDeckImpl(3) ;
		for( int i = 0 ; i < 3 ; i++ )
		{
			TestCardImpl card = deck.next() ;
			assertEquals( deck.m_aCards.get(i).getRank(), card.getRank() ) ;
		}
		assertNull( deck.next() ) ;
		assertNull( deck.next() ) ;   // Verify sane behavior after end of deck.
	}

	/**
	 * Exercises {@link AbstractDeck#peek} by peeking at a number of cards that
	 * is greater than the size of the deck. This verifies sane behavior both
	 * within the bounds of the deck, and beyond.
	 */
	@Test
	public void testPeek()
	{
		TestDeckImpl deck = new TestDeckImpl(3) ;
		List<TestCardImpl> aPeeked = deck.peek(5) ;
		for( int i = 0 ; i < 3 ; i++ )
		{
			assertEquals( deck.m_aCards.get(i).getRank(),
					aPeeked.get(i).getRank() ) ;
		}
		assertNull( aPeeked.get(3) ) ;
		assertNull( aPeeked.get(4) ) ;
	}

	/** Exercises {@link AbstractDeck#size}. */
	@Test
	public void testSize()
	{
		final int nExpectedSize = RNG.nextInt(50) ;
		TestDeckImpl deck = new TestDeckImpl(nExpectedSize) ;
		assertEquals( nExpectedSize, deck.size() ) ;
	}

	/** Exercises {@link AbstractDeck#countRemaining}. */
	@Test
	public void testCountRemaining()
	{
		TestDeckImpl deck = new TestDeckImpl(3) ;
		assertEquals( 3, deck.countRemaining() ) ;
		deck.next() ; assertEquals( 2, deck.countRemaining() ) ;
		deck.next() ; assertEquals( 1, deck.countRemaining() ) ;
		deck.next() ; assertEquals( 0, deck.countRemaining() ) ;
		// Verify continued good behavior after running off the end of the deck.
		deck.next() ; assertEquals( 0, deck.countRemaining() ) ;
	}

	/** Exercises {@link AbstractDeck#moveTo} with both good and bad inputs. */
	@Test
	public void testMoveTo()
	{
		TestDeckImpl deck = new TestDeckImpl(3) ;
		deck.moveTo(2) ;
		assertEquals( 2, deck.m_nNext ) ;
		
		ArrayIndexOutOfBoundsException xOut = null ;
		try { deck.moveTo( -1 ) ; }
		catch( ArrayIndexOutOfBoundsException x ) { xOut = x ; }
		assertNotNull(xOut) ;
		assertEquals( 2, deck.m_nNext ) ;                      // Didn't change.
		
		xOut = null ;
		try { deck.moveTo( 999 ) ; }
		catch( ArrayIndexOutOfBoundsException x ) { xOut = x ; }
		assertNotNull(xOut) ;
		assertEquals( 2, deck.m_nNext ) ;                      // Didn't change.
	}

}
