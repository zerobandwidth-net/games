package net.zerobandwidth.games.cards.sorry;

import net.zerobandwidth.games.cards.Suit;

/**
 * Defines constants and utility methods related to movement cards in the game
 * <i>Sorry!</i>.
 * 
 * <h3>Ranks</h3>
 * 
 * <p>The concept of card "rank" is used in this context to specify the number
 * of spaces that the card allows the player to move. The canonical card deck
 * for <i>Sorry!</i> includes four copies of each of ranks 2, 3, 4, 5, 7, 8, 10,
 * 11, 12, and "Sorry!", and five copies of rank 1. The "Sorry!" cards are
 * assigned rank 0.</p>
 * 
 * <h3>Suits</h3>
 *
 * <p>This implementation exploits the concept of "suits" to classify cards
 * according to the primary directionality of their movement. In a standard deck
 * of movement cards, only rank 4 primarily moves backward. Rank 10 allows the
 * player to choose to move backward 1 instead, but that is handled as a special
 * "attribute" (see below) as the rank 10 card's primary use is to move forward
 * 10 spaces. The "Sorry!" cards belong to their own special suit.</p> 
 * 
 * <h3>Attributes</h3>
 * 
 * <p>In addition to directionality of the card's primary movement, cards in
 * <i>Sorry!</i> have various attributes that produce additional effects in the
 * game. These are defined as flags which may be assigned to a given card when
 * it is created. The special attributes are as follows:</p>
 * 
 * <table>
 *  <thead>
 *   <tr>
 *    <th>Semantic Constant</th>
 *    <th>In-Game Effect</th>
 *    <th>Usual Ranks</th>
 *   </tr>
 *  </thead>
 *  <tbody>
 *   <tr>
 *    <td>{@link #ATTR_OR_MOVE_FROM_START}</td>
 *    <td>Player may move a pawn off of the START square instead of moving.</td>
 *    <td>1, 2</td>
 *   </tr><tr>
 *    <td>{@link #ATTR_DRAW_AGAIN}</td>
 *    <td>Player draws another movement card after resolving this card.</td>
 *    <td>2</td>
 *   </tr><tr>
 *    <td>{@link #ATTR_SPLIT_MOVEMENT}</td>
 *    <td>Player may split the movement allotment among two pawns.</td>
 *    <td>7</td>
 *   </tr><tr>
 *    <td>{@link #ATTR_OR_BACKWARD_ONE}</td>
 *    <td>
 *     Player may move a pawn 1 space backward instead of using the card's
 *     normal movement.
 *    </td>
 *    <td>10</td>
 *   </tr><tr>
 *    <td>{@link #ATTR_OR_CHANGE_PLACES}</td>
 *    <td>
 *     Player may swap a pawn for an opponent's instead of using the card's
 *     normal movement.
 *    </td>
 *    <td>11</td>
 *   </tr>
 *  </tbody>
 * </table>
 * 
 * @since [NEXT] (#2)
 * @see SorryCard
 */
public abstract class SorryCards
{
/// Card Ranks /////////////////////////////////////////////////////////////////
	
	/** Semantic constant for the rank of a "Sorry!" card. */
	public static final int RANK_SORRY = 0 ;

/// Card Suits /////////////////////////////////////////////////////////////////
	
	/** Defines the suit of cards which move pawns forward. */
	public static final Suit SUIT_FORWARD = new Suit()
	{
		public int getID() { return 1 ; }
		public String getSymbol() { return "\u25b6" ; }
		public String getLabel() { return "forward" ; }
	};
	
	/** Defines the suit of cards which move pawns backward. */
	public static final Suit SUIT_BACKWARD = new Suit()
	{
		public int getID() { return -1 ; }
		public String getSymbol() { return "\u25c0" ; }
		public String getLabel() { return "backward" ; }
	};
	
	/** Defines the special suit for "Sorry!" cards. */
	public static final Suit SUIT_SORRY = new Suit()
	{
		public int getID() { return 0 ; }
		public String getSymbol() { return "S" ; }
		public String getLabel() { return "Sorry!" ; }
	};
	
/// Card Attributes ////////////////////////////////////////////////////////////
	
	/** Indicates that the card has no special attributes. */
	public static final long ATTR_NONE = 0L ;
	/**
	 * The player who draws this card may choose to move a pawn off of the START
	 * space instead of using the card's normal movement.
	 */
	public static final long ATTR_OR_MOVE_FROM_START = 1L ;
	/**
	 * The player who draws this card may draw another card after this one is
	 * resolved.
	 */
	public static final long ATTR_DRAW_AGAIN = 2L ;
	/**
	 * The player who draws this card may split the card's movement among two
	 * pawns.
	 */
	public static final long ATTR_SPLIT_MOVEMENT = 4L ;
	/**
	 * The player who draws this card may choose to move a pawn backward by one
	 * space instead of using the card's normal movement.
	 */
	public static final long ATTR_OR_BACKWARD_ONE = 8L ;
	/**
	 * The player who draws this card may choose to swap a pawn with an
	 * opponent's instead of using the card's normal movement.
	 */
	public static final long ATTR_OR_CHANGE_PLACES = 16L ;
	
/// Static utility methods /////////////////////////////////////////////////////
	
	
}
