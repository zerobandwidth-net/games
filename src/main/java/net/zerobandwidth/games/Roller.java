package net.zerobandwidth.games;

import java.text.ParseException;
import java.util.Random;

/**
 * Describes a procedure for rolling dice, and provides methods to execute that
 * roll.
 * @since [NEXT]
 */
public class Roller
{
	/**
	 * Provides details of a dice roll result.
	 * 
	 * The class intentionally provides no publicly-visible constructors or
	 * mutators. It is created by the {@link Roller#roll} method of its parent
	 * {@code Roller} instance.
	 * 
	 * @since [NEXT]
	 */
	public static class Result
	{
		/**
		 * The total roll result. This is either the sum of all dice, or the
		 * count of all successes.
		 */
		protected short m_zValue ;
		
		/** An array of the results from the individual dice. */
		protected short[] m_azDieResults ;
		
		/** A result object <i>must</i> be given an initial size. */
		@SuppressWarnings( "unused" )
		private Result() {}
		
		/**
		 * Constructs the result with an explicit size for the die results
		 * array.
		 * @param nSize the number of dice that were rolled
		 * @throws IllegalArgumentException if the size is less than zero
		 */
		protected Result( int nSize )
		throws IllegalArgumentException
		{
			if( nSize < 0 )
				throw exceptInvalidSize(nSize) ;
			else if( nSize == 0 )
				m_azDieResults = null ;
			else
				m_azDieResults = new short[nSize] ;
			
			m_zValue = 0 ;
		}
		
		/**
		 * Creates an {@link IllegalArgumentException} with consistent text.
		 * Consumed by the constructor.
		 * @param z the invalid initial size
		 * @return "<b>Invalid size specification [</b><i>size</i><b>] for roll
		 *  result array.</b>"
		 */
		protected static IllegalArgumentException exceptInvalidSize( int z )
		{
			StringBuffer sb = new StringBuffer() ;
			sb.append( "Invalid size specification [" )
			  .append( z )
			  .append( "] for roll result array." )
			  ;
			return new IllegalArgumentException( sb.toString() ) ;
		}

		/**
		 * Accessor for the array of individual die results.
		 * @return the array of results for each individual rolled die
		 */
		public short[] getDieResults()
		{ return m_azDieResults ; }

		/**
		 * Accessor for the defined total "value" of the result.
		 * @return the value of the result
		 */
		public short getValue()
		{ return m_zValue ; }
	} // end inner class Result
	
	/** Marks the beginning of a die type specification. */
	public static final char DIE_TYPE_OPERATOR = 'd' ;
	/** Marks the beginning of a bonus specification. */
	public static final char PLUS_OPERATOR = '+' ;
	/** Marks the beginning of a penalty specification. */
	public static final char MINUS_OPERATOR = '-' ;
	/** Marks the beginning of a keep specification. */
	public static final char KEEP_OPERATOR = 'k' ;
	/** Marks the beginning of a success target specification. */
	public static final char SUCCESS_OPERATOR = 's' ;
	/** Marks a roll in which dice "explode". */
	public static final char EXPLODE_OPERATOR = 'x' ;
	
	/**
	 * Magic value indicating that one of the roller's fields is not set.
	 * This is applied only to the fields tracking kept dice and success
	 * targets; the die count, die type, and bonus fields are always
	 * initialized with a value.
	 */
	public static final short NOT_SET = Short.MIN_VALUE ;
	
	/**
	 * Convenient set of constants for controlling the state of the string
	 * parser. Consumed by {@link #parse} but not useful outside this class,
	 * thus the enum remains {@code protected} rather than {@code public}. 
	 */
	protected static enum ParsingState
	{ DICE, DIE_TYPE, BONUS, KEEP, SUCCESS, EXPLODED }
	
	protected static final Random RNG ;
	static { RNG = new Random() ; }
	
	/**
	 * Parses a dice roll specification from a string.
	 * 
	 * The algorithm has four distinct states &mdash; counting dice, parsing
	 * die type, parsing keep count, and parsing the success threshhold. It
	 * defaults to trying to parse the dice count first. Whenever an
	 * operator is encountered, it alters its state according to the
	 * operator, resets any previously-parsed value for that operator, and
	 * parses further digits into that field. Though die roll specifications
	 * are generally written in the form
	 * "<i>DICE</i><b>d</b><i>TYPE</i><b>k</b><i>KEEP</i><b>s</b><i>SUCCESS</i>",
	 * the parsing algorithm will gracefully parse tokens in any order.
	 * @param sSpec a specification of a die roll
	 * @return a {@link Roller} object which can execute that roll
	 * @throws ParseException if the parse operation fails
	 */
	public static Roller parse( String sSpec )
	throws ParseException
	{
		if( sSpec == null ) throw exceptInvalidSpec( sSpec, 0 ) ;
		
		Roller rl = new Roller() ;
		
		ParsingState state = ParsingState.DICE ;
		char[] acSpec = sSpec.toCharArray() ;
		char cOperator = PLUS_OPERATOR ;
		for( int i = 0 ; i < acSpec.length ; i++ )
		{
			char c = acSpec[i] ;
			if( Character.isDigit(c) )
			{
				switch( state )
				{
					case DICE:
						rl.m_nDice = (short)( ( rl.m_nDice * 10 )
							+ Character.digit(c,10) ) ;
						break ;
					case DIE_TYPE:
						rl.m_nType = (short)( ( rl.m_nType * 10 )
							+ Character.digit(c,10) ) ;
						break ;
					case BONUS:
						rl.m_nBonus = (short)( ( rl.m_nBonus * 10 )
							+ Character.digit(c,10) ) ;
						break ;
					case KEEP:
						rl.m_nKeep = (short)( ( rl.m_nKeep * 10 )
							+ Character.digit(c,10) ) ;
						break ;
					case SUCCESS:
						rl.m_nTarget = (short)( (rl.m_nTarget * 10 )
							+ Character.digit(c,10) ) ;
						break ;
					case EXPLODED:
						throw exceptInvalidSpec( sSpec, i ) ;
					default:
						throw new RuntimeException( "Can't happen!" ) ;
				}
			}
			else
			{
				switch( Character.toLowerCase(c) )
				{
					case DIE_TYPE_OPERATOR:
						rl.m_nType = 0 ;
						state = ParsingState.DIE_TYPE ;
						if( i == 0 ) rl.m_nDice = 1 ;
						break ;
					case PLUS_OPERATOR:
					case MINUS_OPERATOR:
						rl.m_nBonus = 0 ;
						cOperator = c ;
						state = ParsingState.BONUS ;
						break ;
					case KEEP_OPERATOR:
						rl.m_nKeep = 0 ;
						state = ParsingState.KEEP ;
						break ;
					case SUCCESS_OPERATOR:
						rl.m_nTarget = 0 ;
						state = ParsingState.SUCCESS ;
						break ;
					case EXPLODE_OPERATOR:
						rl.m_bExplode = true ;
						if( state != ParsingState.DICE || rl.m_nDice != 0 )
							state = ParsingState.EXPLODED ;
						break ;
					default:
						throw exceptInvalidSpec( sSpec, i ) ;
				}
			}
		}
		
		if( cOperator == MINUS_OPERATOR )
			rl.m_nBonus *= -1 ;
		
		if( ! rl.isValid() )
			throw exceptInvalidSpec( sSpec, sSpec.length() ) ;
		
		return rl ;
	}
	
	/**
	 * Creates a {@link ParseException} with consistent text. 
	 * @param sSpec the invalid roll spec
	 * @param zPosition the position at which parsing failed; this will equal
	 *  the string length if the roll spec contained valid individual characters
	 *  but failed final validation
	 * @return "<b>Cannot parse roll spec [</b><i>spec</i><b>]</b>
	 *  [<b>at position [</b><i>position</i><b>]</b>]<b>.</b>"
	 */
	protected static ParseException
	exceptInvalidSpec( String sSpec, int zPosition )
	{
		StringBuffer sb = new StringBuffer() ;
		sb.append( "Cannot parse roll spec [" )
		  .append(( sSpec == null ? "(null)" : sSpec ))
		  .append( "]" )
		  ;
		if( zPosition >= 0 )
		{
			sb.append( " at position [" )
			  .append( zPosition )
			  .append( "]." )
			  ;
		}
		else
			sb.append( "." ) ;
		return new ParseException( sb.toString(), zPosition ) ;
	}
	
	/**
	 * Wrapper for {@link #parse} which eats any {@link ParseException} and
	 * dumps it to the system's error log.
	 * @param sSpec a specification of a die roll
	 * @return
	 */
	public static Roller parseCarelessly( String sSpec )
	{
		Roller rl = null ;
		try { rl = parse(sSpec) ; }
		catch( ParseException px )
		{ System.err.println(px.getMessage()) ; }
		return rl ;
	}

	/**
	 * Convenience method to verify that an integer value can be stuffed into
	 * one of the short-sized fields.
	 * @param n an integer value
	 * @return true if the value can be used as a short
	 */
	protected static boolean valueCanFit( int n )
	{
		if( n > (int)(Short.MAX_VALUE) ) return false ;
		if( n < (int)(Short.MIN_VALUE) ) return false ;
		return true ;
	}
	
	/**
	 * Validates a die count.
	 * @param nDice number of dice to be rolled
	 * @return true if the number is valid
	 */
	public static boolean isValidDieCount( short nDice )
	{ return( nDice >= 0 ) ; }
	
	/**
	 * Validates a die count.
	 * @param nDice number of dice to be rolled
	 * @return true if the number is valid
	 */
	public static boolean isValidDieCount( int nDice )
	{ return( valueCanFit(nDice) && isValidDieCount((short)nDice) ) ; }
	
	/**
	 * Validates a die type.
	 * @param nType the die type
	 * @return true if the number is valid
	 */
	public static boolean isValidDieType( short nType )
	{ return( nType >= 0 ) ; }
	
	/**
	 * Validates a die type.
	 * @param nType the die type
	 * @return true if the number is valid
	 */
	public static boolean isValidDieType( int nType )
	{ return( valueCanFit(nType) && isValidDieType((short)nType) ) ; }

	/**
	 * Validates a keep count.
	 * @param nKeep the number of dice to be kept
	 * @return true if the number is valid
	 */
	public static boolean isValidKeep( short nKeep )
	{ return( nKeep > 0 || nKeep == NOT_SET ) ; }
	
	/**
	 * Validates a keep count.
	 * @param nKeep the number of dice to be kept
	 * @return true if the number is valid
	 */
	public static boolean isValidKeep( int nKeep )
	{ return( valueCanFit(nKeep) && isValidKeep((short)nKeep) ) ; }

	/**
	 * Validates a success target.
	 * @param nTarget the number of successes to match
	 * @return true if the number is valid
	 */
	public static boolean isValidTarget( short nTarget )
	{ return( nTarget >= 0 || nTarget == NOT_SET ) ; }
	
	/**
	 * Validates a success target.
	 * @param nTarget the minimum number for a die to "succeed"
	 * @return true if the number is valid
	 */
	public static boolean isValidTarget( int nTarget )
	{ return( valueCanFit(nTarget) && isValidTarget((short)nTarget) ) ; }
	
	/** Roll how many dice? */
	protected short m_nDice ;
	/** Roll which die? (How many faces on the die?) */
	protected short m_nType ;
	/** Apply what modifier after the roll? */
	protected short m_nBonus ;
	/** Keep how many highest dice? */
	protected short m_nKeep ;
	/** What is the success target? */
	protected short m_nTarget ;
	/** Do individual dice "explode"? */
	protected boolean m_bExplode ;
	
	/**
	 * Default constructor. Die count, die type, and bonus are zero. This will
	 * always roll a zero result.
	 */
	public Roller()
	{ this.init() ; }
	
	/**
	 * Constructor based on a string specification; uses {@link #parse} to set
	 * the object's values.
	 * @param sSpec a specification of a die roll
	 * @throws ParseException if the parse operation fails
	 */
	public Roller( String sSpec )
	throws ParseException
	{ this.init().set(sSpec) ; }
	
	/**
	 * Copy constructor.
	 * @param that the {@link Roller} from which to copy values
	 */
	public Roller( Roller that )
	{ this.init().setFrom(that) ; }
	
	/**
	 * Initializes the object with zero values. Such an object will always roll
	 * a zero result.
	 * @return the {@code Roller} itself, for chained invocation
	 */
	protected Roller init()
	{
		m_nDice = 0 ;
		m_nType = 0 ;
		m_nBonus = 0 ;
		m_nKeep = NOT_SET ;
		m_nTarget = NOT_SET ;
		m_bExplode = false ;
		return this ;
	}
	
	/**
	 * Copies values from the specified source into this instance.
	 * @param that the source object
	 * @return the {@code Roller} itself, for chained invocation
	 */
	public Roller setFrom( Roller that )
	{
		return this.setDice(that.m_nDice).setDieType(that.m_nType)
			.setBonus(that.m_nBonus).setKeep(that.m_nKeep)
			.setSuccessTarget(that.m_nTarget).setExplode(that.m_bExplode)
			;
	}
	
	/**
	 * Sets the values of this instance based on a roll specification string.
	 * Consumes {@link #parse}.
	 * @param sSpec a specification of a die roll
	 * @return the {@code Roller} itself, for chained invocation
	 * @throws ParseException if the parse operation fails
	 */
	public Roller set( String sSpec )
	throws ParseException
	{ return this.setFrom( parse(sSpec) ) ; }
	
	/**
	 * Validates the various fields of the object to indicate whether it is
	 * usable as a die roller.
	 * @return true if the fields of the object are mutually valid
	 */
	public boolean isValid()
	{
		if( ! isValidDieCount(m_nDice) ) return false ;
		if( ! isValidDieType(m_nType) ) return false ;
		if( ! isValidKeep(m_nKeep) ) return false ;
		if( ! isValidTarget(m_nTarget) ) return false ;
		if( m_nKeep > m_nDice ) return false ;
		return true ;
	}

	/**
	 * Accessor for the die count.
	 * @return the number of dice to be rolled
	 */
	public short getDice()
	{ return m_nDice ; }
	
	/**
	 * Mutator for the die count.
	 * @param nDice the number of dice to be rolled
	 * @return the {@code Roller} itself, for chained invocation
	 */
	public Roller setDice( short nDice )
	{
		if( isValidDieCount(nDice) )
			m_nDice = nDice ;
		return this ;
	}
	
	/**
	 * Mutator for the die count.
	 * @param nDice the number of dice to be rolled
	 * @return the {@code Roller} itself, for chained invocation
	 */
	public Roller setDice( int nDice )
	{ return this.setDice( (short)nDice ) ; }

	/**
	 * Accessor for the die type.
	 * @return the die type (the number of faces on the die)
	 */
	public short getDieType()
	{ return m_nType ; }

	/**
	 * Mutator for the die type.
	 * @param nType the die type (the number of faces on the die)
	 * @return the {@code Roller} itself, for chained invocation
	 */
	public Roller setDieType( short nType )
	{
		if( isValidDieType(nType) )
			m_nType = nType ;
		return this ;
	}
	
	/**
	 * Mutator for the die type.
	 * @param nType the die type (the number of faces on the die)
	 * @return the {@code Roller} itself, for chained invocation
	 */
	public Roller setDieType( int nType )
	{ return this.setDieType( (short)nType ) ; }
	
	/**
	 * Accessor for the roll bonus/penalty modifier.
	 * @return the bonus/penalty to be applied
	 */
	public short getBonus()
	{ return m_nBonus ; }
	
	/**
	 * Mutator for the roll bonus/penalty modifier.
	 * @param nBonus the bonus/penalty to be applied
	 * @return the {@code Roller} itself, for chained invocation
	 */
	public Roller setBonus( short nBonus )
	{ m_nBonus = nBonus ; return this ; }
	
	/**
	 * Mutator for the roll bonus/penalty modifier.
	 * @param nBonus the bonus/penalty to be applied
	 * @return the {@code Roller} itself, for chained invocation
	 */
	public Roller setBonus( int nBonus )
	{ return this.setBonus( (short)nBonus ) ; }

	/**
	 * Accessor for the number of dice to be kept from the roll pool.
	 * @return the number of dice to be kept
	 */
	public short getKeep()
	{ return m_nKeep ; }
	
	/**
	 * Mutator for the number of dice to be kept from the roll pool.
	 * @param nKeep the number of dice to be kept
	 * @return the {@code Roller} itself, for chained invocation
	 */
	public Roller setKeep( short nKeep )
	{
		if( isValidKeep(nKeep) && nKeep <= m_nDice )
			m_nKeep = nKeep ;
		return this ;
	}
	
	/**
	 * Mutator for the number of dice to be kept from the roll pool.
	 * @param nKeep the number of dice to be kept
	 * @return the {@code Roller} itself, for chained invocation
	 */
	public Roller setKeep( int nKeep )
	{ return this.setKeep( (short)nKeep ) ; }

	/**
	 * Accessor for the success target to be checked for each die.
	 * @return the success target
	 */
	public short getSuccessTarget()
	{ return m_nTarget ; }
	
	/**
	 * Mutator for the success target to be checked for each die.
	 * @param nTarget the success target
	 * @return the {@code Roller} itself, for chained invocation
	 */
	public Roller setSuccessTarget( short nTarget )
	{
		if( isValidTarget(nTarget) )
			m_nTarget = nTarget ;
		return this ;
	}
	
	/**
	 * Mutator for the success target to be checked for each die.
	 * @param nTarget the success target
	 * @return the {@code Roller} itself, for chained invocation
	 */
	public Roller setSuccessTarget( int nTarget )
	{ return this.setSuccessTarget( (short)nTarget ) ; }
	
	/**
	 * Accessor for the flag indicating whether dice "explode".
	 * @return true if dice explode
	 */
	public boolean explodes()
	{ return this.m_bExplode ; }
	
	/**
	 * Mutator for the flag indicating whether dice "explode".
	 * @param bExplode specifies whether dice explode
	 * @return the {@code Roller} itself, for chained invocation
	 */
	public Roller setExplode( boolean bExplode )
	{ m_bExplode = bExplode ; return this ; }
	
	/**
	 * Executes the roll specified by this object's fields.
	 * @return the result of the die roll
	 */
	public Result roll()
	{
		Result res = new Result(m_nDice) ;
		if( m_nDice == 0 ) return res ;
		if( m_nType == 0 ) return res ;
		if( m_nKeep == 0 ) return res ;
		
		for( int i = 0 ; i < m_nDice ; i++ )
			res.m_azDieResults[i] = this.rollOneDie() ;
		
		// Set result value based on kept dice or all dice.
		res.m_zValue = this.evaluate(( m_nKeep != NOT_SET
						? this.getKeptDice( res.m_azDieResults )
						: res.m_azDieResults
					)) ;
		
		return res ;
	}
	
	/**
	 * Rolls one die. If the {@link Roller} is set with a target number for each
	 * die, then the bonus is applied to the die immediately. If the
	 * {@code Roller} is set to explode dice, then keep rolling this die until
	 * it stops exploding.
	 * 
	 * Consumed by {@link #roll}.
	 * 
	 * @return the total result for a single die roll
	 */
	protected short rollOneDie()
	{
		short zDie = 0 ;
		if( m_bExplode )
		{
			short zRoll ;
			do
			{
				zRoll = (short)( RNG.nextInt(m_nType) + 1 ) ;
				zDie += zRoll ;
			} while( zRoll == m_nType ) ;
		}
		else
			zDie = (short)( RNG.nextInt(m_nType) + 1 ) ;
		if( m_nTarget != NOT_SET )
		{ // Bonus should influence each die vs. target, not total.
			zDie += m_nBonus ;
		}
		return zDie ;
	}
	
	/**
	 * Returns an array of only the kept dice from a larger array of all die
	 * results.
	 * 
	 * Consumed by {@link #roll}.
	 * 
	 * @param azAllDice an array of all die results
	 * @return an array of only the <i>n</i> highest results
	 */
	protected short[] getKeptDice( short[] azAllDice )
	{
		short[] azHighest = new short[m_nKeep] ;
		for( int i = 0 ; i < m_nKeep ; i++ )
			azHighest[i] = Short.MIN_VALUE ;
		for( int iDie = 0 ; iDie < m_nDice ; iDie++ )
		{ // Determine whether each value is "highest" and should be kept.
			short zCurrent = azAllDice[iDie] ;
			for( int iHighest = 0 ; iHighest < m_nKeep ; iHighest++ )
			{ // Push the value through the array.
				if( zCurrent > azHighest[iHighest] )
				{ // Swap the current value for the formerly-high value.
					short zTemp = azHighest[iHighest] ;
					azHighest[iHighest] = zCurrent ;
					zCurrent = zTemp ;
				}
			}
		}
		return azHighest ;
	}

	/**
	 * Evaluates an array of individual die results based on the other fields of
	 * the {@link Roller} object. This is a separate method because the results
	 * being evaluated might not be the entire array in a {@link Result} object;
	 * for example, it could be only the subset of <i>n</i> highest results.
	 * 
	 * Consumed by {@link #roll}.
	 * 
	 * @param azDieResults an array of die results
	 * @return a sum of all results, or a count of successful results
	 */
	protected short evaluate( short[] azDieResults )
	{
		short zValue = 0 ;
		if( m_nTarget != NOT_SET )
		{ // Count successes from the array; bonus was applied to each die.
			for( short zDie : azDieResults )
				if( zDie >= m_nTarget )
					++zValue ;
		}
		else
		{ // Sum the elements of the array; add bonus at the end.
			for( short zDie : azDieResults )
				zValue += zDie ;
			zValue += m_nBonus ;
		}
		return zValue ;
	}
	
	/**
	 * Compares two {@link Roller} instances, verifying that each field is
	 * equal between the two.
	 * @param that the instance to be compared to this instance
	 * @return true only if each field is equal between instances
	 */
	public boolean equals( Roller that )
	{
		if( m_nDice != that.m_nDice ) return false ;
		if( m_nType != that.m_nType ) return false ;
		if( m_nBonus != that.m_nBonus ) return false ;
		if( m_nKeep != that.m_nKeep ) return false ;
		if( m_nTarget != that.m_nTarget ) return false ;
		if( m_bExplode != that.m_bExplode ) return false ;
		return true ;
	}
	
	/**
	 * Compares the {@link Roller} instance to a roll specification string. A
	 * temporary {@code Roller} is parsed from the string and compared to this
	 * instance. If the string is null, or cannot be parsed, then the method
	 * always returns {@code false}.
	 * @param sSpec a specification of a die roll
	 * @return true only if the string and object represent the same roll
	 *  specification
	 */
	public boolean equals( String sSpec )
	{
		if( sSpec == null ) return false ;
		try { return this.equals( Roller.parse(sSpec) ) ; }
		catch( ParseException px ) { return false ; }
	}
	
	/**
	 * Serializes the {@link Roller} object as a string.
	 * <p>Because the algorithm used by {@link #parse} does not enforce a
	 * canonical order for the segments of the roll spec, there is no guarantee
	 * that the following will be true for any valid string {@code sSpec}:</p>
	 * <pre>sSpec.equals( Roller.parse(sSpec).toString() )</pre>
	 * <p>However, for a given {@code Roller} instance {@code roll}, the
	 * following test <i>is</i> guaranteed:</p>
	 * <pre>roll.equals( Roller.parse(roll.toString()) )</pre>
	 * @return a string representation of the roll
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer() ;
		sb.append( m_nDice )
		  .append( DIE_TYPE_OPERATOR )
		  .append( m_nType )
		  ;
		
		if( m_nBonus > 0 )
			sb.append( PLUS_OPERATOR ).append( m_nBonus ) ;
		else if( m_nBonus < 0 )
			sb.append( MINUS_OPERATOR ).append( m_nBonus * -1 ) ;
		
		if( m_nKeep != NOT_SET )
			sb.append( KEEP_OPERATOR ).append( m_nKeep ) ;
		
		if( m_nTarget != NOT_SET )
			sb.append( SUCCESS_OPERATOR ).append( m_nTarget ) ;
		
		if( m_bExplode )
			sb.append( EXPLODE_OPERATOR ) ;
		
		return sb.toString() ;
	}
}
