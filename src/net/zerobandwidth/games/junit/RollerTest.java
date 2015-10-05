package net.zerobandwidth.games.junit;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Test;
import net.zerobandwidth.games.Roller ;

/**
 * Exercises {@link Roller}.
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
}
