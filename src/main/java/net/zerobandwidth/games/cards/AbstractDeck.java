package net.zerobandwidth.games.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Provides a canonical partial implementation of the {@link Deck} interface.
 * @param <D> the concrete descendant class; this must provide all the methods
 *  that {@code AbstractDeck} does not. The descendant class is referenced here
 *  such that any fluid methods defined in {@code AbstractDeck} and inherited by
 *  the descendant will directly return that descendant type, not {@code Deck}
 *  or {@code AbstractDeck}. The descendant class <i>must</i> declard itself
 *  thusly:
 *  <pre>
 *  public class DeckImpl
 *  extends AbstractDeck&lt;DeckImpl,CardImpl&gt;
 *  implements Deck&lt;CardImpl&gt;
 *  </pre>
 * @param <C> the implementation of {@link Card} which is managed by the deck
 * @since [NEXT] (#2)
 */
public abstract class AbstractDeck<D extends Deck<C>, C extends Card>
implements Deck<C>
{
/// Member fields //////////////////////////////////////////////////////////////
	
	/**
	 * The complete deck of cards. The {@link #m_nNext} member points to an
	 * index within this list.
	 */
	protected ArrayList<C> m_aCards = null ;
	
	/** The index of the "next" card to be dealt from the deck. */
	protected int m_nNext = 0 ;
	
/// Initializers ///////////////////////////////////////////////////////////////
	
	/**
	 * Initializes the deck by creating the list which contains the deck,
	 * calling the instance's {@link #addCards} method, and setting the pointer
	 * to the top of the deck.
	 * @return (fluid)
	 */
	@SuppressWarnings("unchecked") // Will be guaranteed by a valid declaration.
	protected D init()
	{
		m_aCards = new ArrayList<>() ;
		this.addCards() ;
		m_nNext = 0 ;
		return ((D)this) ;
	}
	
	/**
	 * Override this method to add cards to the deck at initialization.
	 * @return (fluid)
	 */
	protected abstract D addCards() ;

/// net.zerobandwidth.games.cards.Deck /////////////////////////////////////////

	/**
	 * Fully randomizes the cards by shuffling them seven times.
	 * This is based on the widespread assertion that a physical deck of
	 * standard playing cards is most efficiently randomized when shuffled seven
	 * times.
	 * @return (fluid) 
	 */
	@Override
	@SuppressWarnings("unchecked") // Will be guaranteed by a valid declaration.
	public D shuffle()
	{
		for( int i = 0 ; i < 7 ; i++ )
			Collections.shuffle( m_aCards ) ;
		m_nNext = 0 ;
		return ((D)this) ;
	}
	
	// There will eventually be a canonical implementation of shuffleRemaining()
	// when we figure out how best to do it...
	
	/**
	 * @return the next unseen card from the deck, or {@code null} if we've run
	 *  off the bottom of the deck and need to reshuffle
	 */
	@Override
	public C next()
	{
		if( m_nNext >= m_aCards.size() ) // We've run off the bottom of the deck
			return null ;
		return m_aCards.get( m_nNext++ ) ;
	}
	
	@Override
	public List<C> peek( int nCards )
	{
		ArrayList<C> aPeeked = new ArrayList<>(nCards) ;
		for( int i = 0 ; i < nCards ; i++ )
		{
			if( m_nNext + i < m_aCards.size() )
				aPeeked.add( m_aCards.get( m_nNext + i ) ) ;
			else
				aPeeked.add( ((C)null) ) ;
		}
		return aPeeked ;
	}
	
	@Override
	public int size()
	{ return m_aCards.size() ; }
	
	@Override
	public int countRemaining()
	{ return m_aCards.size() - m_nNext ; }
	
/// Other methods //////////////////////////////////////////////////////////////
	
	/**
	 * Forces the "next card" pointer to a specific position in the deck.
	 * 
	 * Should not be consumed in general use; it is added here only for testing
	 * purposes.
	 * 
	 * @param nIndex the new "next card" position
	 * @return (fluid)
	 * @throws ArrayIndexOutOfBoundsException if the specified index is out of
	 *  bounds of the deck
	 */
	@SuppressWarnings("unchecked") // Will be guaranteed by a valid declaration.
	protected final D moveTo( int nIndex )
	throws ArrayIndexOutOfBoundsException
	{
		if( nIndex < 0 || nIndex > m_aCards.size() )
			throw new ArrayIndexOutOfBoundsException( nIndex ) ;
		m_nNext = nIndex ;
		return ((D)this) ;
	}
}
