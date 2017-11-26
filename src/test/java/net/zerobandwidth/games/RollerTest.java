package net.zerobandwidth.games;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Test;
import net.zerobandwidth.games.Roller ;

/**
 * Exercises {@link Roller}. Note that, since this naturally involves some
 * random numbers, sporadic failures may occur. If this happens often, consider
 * bumping up the {@link #TRIALS} constant. 
 * @since 0.01.20151004
 */
public class RollerTest
{
	/**
	 * Convenience method for testing various fields of a {@link Roller}.
	 * @param rl a {@link Roller} object to test
	 * @param nDice expected die count
	 * @param nType expected die type
	 * @param nBonus expected bonus
	 * @param nKeep expected keep count
	 * @param nTarget expected success target
	 * @param bExplodes expected "explodes" flag
	 */
	private static void assertRollerEquals( Roller rl,
		int nDice, int nType, int nBonus, int nKeep, int nTarget,
		boolean bExplodes )
	{
		if( rl == null ) fail( "Testing a null Roller? Stop that!" ) ;
		assertEquals( (short)nDice, rl.getDice() ) ;
		assertEquals( (short)nType, rl.getDieType() ) ;
		assertEquals( (short)nBonus, rl.getBonus() ) ;
		assertEquals( (short)nKeep, rl.getKeep() ) ;
		assertEquals( (short)nTarget, rl.getSuccessTarget() ) ;
		assertEquals( bExplodes, rl.explodes() ) ;
	}
	
	/**
	 * Convenience method for trying a {@link Roller#parse} operation with a
	 * valid string; fails if a {@link ParseException} is ever thrown.
	 * @param s the string to parse
	 * @param nDice expected die count
	 * @param nType expected die type
	 * @param nBonus expected bonus
	 * @param nKeep expected keep count
	 * @param nTarget expected success target
	 * @param bExplodes expected "explodes" flag
	 * @throws ParseException if the parse fails (this fails the test)
	 */
	private static void testParseWithGoodString( String s,
		int nDice, int nType, int nBonus, int nKeep, int nTarget,
		boolean bExplodes )
	throws ParseException
	{
		Roller rlTest = null ;
		rlTest = Roller.parse(s) ;
		assertRollerEquals( rlTest,
			nDice, nType, nBonus, nKeep, nTarget, bExplodes ) ;
	}
	
	/**
	 * Exercises {@link Roller#parse} with known-good strings.
	 * @throws ParseException if the parse fails (this fails the test)
	 */
	@Test
	public void testParseWithGoodStrings()
	throws ParseException
	{
		testParseWithGoodString( "1d6",
			1, 6, 0, Roller.NOT_SET, Roller.NOT_SET, false ) ;
		testParseWithGoodString( "3d8+15",
			3, 8, 15, Roller.NOT_SET, Roller.NOT_SET, false ) ;
		testParseWithGoodString( "4d10-2k3",
			4, 10, -2, 3, Roller.NOT_SET, false ) ;
		testParseWithGoodString( "3d6s4x",
			3, 6, 0, Roller.NOT_SET, 4, true ) ;
		// Show that an omitted die count is set to 1 when first char is 'd'.
		testParseWithGoodString( "d8+2",
			1, 8, 2, Roller.NOT_SET, Roller.NOT_SET, false ) ;
		// Show that the parser can handle segments out of canonical order.
		testParseWithGoodString( "x1s2k1+1d8",
			1, 8, 1, 1, 2, true ) ;
	}
	
	/**
	 * Convenience method for testing {@link Roller#parse} with a known-bad
	 * string. Asserts that a {@link ParseException} is thrown at the expected
	 * position in the invalid string
	 * @param s the string to be parsed
	 * @param nFailPosition the position at which parsing is expected to fail
	 */
	private static void testParseWithBadString( String s, int nFailPosition )
	{
		ParseException pxExpected = null ;
		try { Roller.parse(s) ; }
		catch( ParseException px ) { pxExpected = px ; }
		if( pxExpected == null )
			fail( "Should have failed: [" + s + "]" ) ;
		assertEquals( nFailPosition, pxExpected.getErrorOffset() ) ;
	}
	
	/**
	 * Exercises {@link Roller#parse} with known-bad strings.
	 */
	@Test
	public void testParseWithBadStrings()
	{
		testParseWithBadString( "abcdefg", 0 ) ;                 // Fail on 'a'.
		testParseWithBadString( "1d8x5", 4 ) ;    // Fail on invalid use of 'x'.
		testParseWithBadString( "3d10k5", 6 ) ;       // Fails final validation.
	}

	/**
	 * Verifies that the constructor creates an object with the default values.
	 */
	@Test
	public void testRoller()
	{
		assertRollerEquals( new Roller(),
			0, 0, 0, Roller.NOT_SET, Roller.NOT_SET, false ) ;
	}

	/**
	 * Exercises {@link Roller#setFrom(Roller)}.
	 * @throws ParseException if the parse fails (this fails the test)
	 */
	@Test
	public void testSetFrom()
	throws ParseException
	{
		Roller rlSource = Roller.parse( "5d4+3k2s1x" ) ;
		Roller rlTarget = new Roller() ;
		rlTarget.setFrom( rlSource ) ;
		assertRollerEquals( rlTarget, 5, 4, 3, 2, 1, true ) ;
	}

	/**
	 * Exercises {@link Roller#set} with a known-good string.
	 * @throws ParseException if the parse fails (this fails the test)
	 */
	@Test
	public void testSet()
	throws ParseException
	{
		Roller rl = new Roller() ;
		rl.set( "10d8x+6k4s2" ) ;
		assertRollerEquals( rl, 10, 8, 6, 4, 2, true ) ;
	}
	
	/**
	 * Exercises {@link Roller#isValidDieCount} with good and bad values.
	 */
	@Test
	public void testIsValidDieCount()
	{
		assertTrue( Roller.isValidDieCount(0) ) ;
		assertTrue( Roller.isValidDieCount(1) ) ;
		assertFalse( Roller.isValidDieCount(-1) ) ;
		assertFalse( Roller.isValidDieCount( Integer.MAX_VALUE ) ) ;
		assertFalse( Roller.isValidDieCount( ((int)Short.MAX_VALUE) + 1 ) ) ;
		assertFalse( Roller.isValidDieCount( ((int)Short.MAX_VALUE) + 1 ) ) ;
		assertFalse( Roller.isValidDieCount( ((int)Short.MIN_VALUE) - 1 ) ) ;
	}
	
	/**
	 * Exercises {@link Roller#isValidDieType} with good and bad values.
	 */
	@Test
	public void testIsValidDieType()
	{
		assertTrue( Roller.isValidDieType(0) ) ;
		assertTrue( Roller.isValidDieType(1) ) ;
		assertFalse( Roller.isValidDieType(-1) ) ;
		assertFalse( Roller.isValidDieType( Integer.MAX_VALUE ) ) ;
		assertFalse( Roller.isValidDieType( ((int)Short.MAX_VALUE) + 1 ) ) ;
		assertFalse( Roller.isValidDieType( ((int)Short.MAX_VALUE) + 1 ) ) ;
		assertFalse( Roller.isValidDieType( ((int)Short.MIN_VALUE) - 1 ) ) ;		
	}
	
	/**
	 * Exercises {@link Roller#isValidKeep} with good and bad values.
	 */
	@Test
	public void testIsValidKeep()
	{
		assertFalse( Roller.isValidKeep(0) ) ;
		assertTrue( Roller.isValidKeep(1) ) ;
		assertFalse( Roller.isValidKeep(-1) ) ;
		assertFalse( Roller.isValidKeep( Integer.MAX_VALUE ) ) ;
		assertFalse( Roller.isValidKeep( ((int)Short.MAX_VALUE) + 1 ) ) ;
		assertFalse( Roller.isValidKeep( ((int)Short.MAX_VALUE) + 1 ) ) ;
		assertFalse( Roller.isValidKeep( ((int)Short.MIN_VALUE) - 1 ) ) ;		
	}
	
	/**
	 * Exercises {@link Roller#isValidTarget} with good and bad values.
	 */
	@Test
	public void testIsValidTarget()
	{
		assertTrue( Roller.isValidTarget(0) ) ;
		assertTrue( Roller.isValidTarget(1) ) ;
		assertFalse( Roller.isValidTarget(-1) ) ;
		assertFalse( Roller.isValidTarget( Integer.MAX_VALUE ) ) ;
		assertFalse( Roller.isValidTarget( ((int)Short.MAX_VALUE) + 1 ) ) ;
		assertFalse( Roller.isValidTarget( ((int)Short.MAX_VALUE) + 1 ) ) ;
		assertFalse( Roller.isValidTarget( ((int)Short.MIN_VALUE) - 1 ) ) ;				
	}
	
	/**
	 * Verifies reciprocity of {@link Roller#parse} and {@link Roller#toString}. 
	 * @throws ParseException if the parse fails (this fails the test)
	 */
	@Test
	public void testToString()
	throws ParseException
	{
		final String sSpec = "5d4-3k2s1x" ;
		assertEquals( sSpec, Roller.parse(sSpec).toString() ) ;
	}

	/**
	 * Makes some of the protected methods visible to the test class.
	 */
	protected class InspectableRoller extends Roller
	{
		public InspectableRoller()
		throws ParseException
		{ super() ; }
		
		@Override
		public InspectableRoller set( String sSpec )
		throws ParseException
		{ super.set(sSpec) ; return this ; }
		
		@Override
		public short rollOneDie()
		{ return super.rollOneDie() ; }
		
		@Override
		public short[] getKeptDice( short[] azAllDice )
		{ return super.getKeptDice( azAllDice ) ; }
		
		@Override
		public short evaluate( short[] azDieResults )
		{ return super.evaluate( azDieResults ) ; }
	}
	
	/** How many times to try testing randomized numbers. */
	private static final int TRIALS = 1000 ;
	
	/** Exercises {@link Roller#rollOneDie}. */
	@Test
	public void testRollOneDie()
	throws ParseException
	{
		InspectableRoller roll = new InspectableRoller() ;
		assertRollOneDie( roll.set( "3d8" ) ) ;
		assertRollOneDie( roll.set( "4d10k3" ) ) ;
		assertRollOneDie( roll.set( "3d6s4" ) ) ;
	}
	
	/** Consumed by {@link #testRollOneDie} */
	private static void assertRollOneDie( InspectableRoller roll )
	{
		for( int i = 0 ; i < TRIALS ; i++ )
		{
			short zDie = roll.rollOneDie() ;
			if( roll.getSuccessTarget() != Roller.NOT_SET )
			{ // Expect the bonus to be applied.
				assertTrue( roll.getBonus() <= zDie ) ;
				assertTrue( zDie <= roll.getDieType() + roll.getBonus() ) ;
			}
			else
			{ // Don't expect the bonus to be applied.
				assertTrue( 0 < zDie && zDie <= roll.getDieType() ) ;
			}
		}
	}
	
	/** Exercises {@link Roller#rollOneDie} with exploding dice. */
	@Test
	public void testRollOneExplodingDie()
	throws ParseException
	{
		InspectableRoller roll = new InspectableRoller() ;
		roll.set( "1d8x" ) ;
		boolean bGotExplodedDie = false ;
		for( int i = 0 ; i < TRIALS ; i++ )
		{
			if( roll.rollOneDie() > 8 )
			{
				bGotExplodedDie = true ;
				break ;
			}
		}
		if( !bGotExplodedDie )
			fail( "Got no exploded dice in any trial." ) ;
		
		// Now try with a target number that requires exploding to reach it.
		roll.set( Integer.toString(TRIALS) + "d8xs10" ) ;
		Roller.Result res = roll.roll() ;
		assertTrue( res.getValue() > 0 ) ;
	}
	
	/** Exercises {@link Roller#getKeptDice} */
	@Test
	public void testGetKeptDice()
	throws ParseException
	{
		short[] azAllDice = { 8, 6, 7, 9, 3, 0, 9 } ;        // Not quite Jenny.
		short[] azExpected = { 9, 9, 8, 7, 6, 3, 0 } ;
		InspectableRoller roll = new InspectableRoller() ;
		roll.set( "7d10k7" ) ;
		short[] azKeptDice = roll.getKeptDice( azAllDice ) ;
		assertEquals( azExpected.length, azKeptDice.length ) ;
		for( int i = 0 ; i < azExpected.length ; i++ )
			assertEquals( azExpected[i], azKeptDice[i] ) ;
	}
	
	/** Exercises {@link Roller#evaluate}. */
	@Test
	public void testEvaluate()
	throws ParseException
	{
		short[] azRolls = { 8, 6, 7, 5, 3, 0, 9 } ;      // There you go, Jenny.
		InspectableRoller roll = new InspectableRoller() ;
		roll.set( "7d10" ) ;
		assertEquals( 8+6+7+5+3+9, roll.evaluate(azRolls) ) ;
		roll.setBonus(3) ;
		assertEquals( 8+6+7+5+3+9+3, roll.evaluate(azRolls) ) ;
		roll.setSuccessTarget(5) ; // Bonus wasn't applied to array; testing raw
		assertEquals( 5, roll.evaluate(azRolls) ) ;      // Five rolls are >= 5.
	}
}
