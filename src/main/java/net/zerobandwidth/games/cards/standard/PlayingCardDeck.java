package net.zerobandwidth.games.cards.standard;

import java.util.ArrayList;

import net.zerobandwidth.games.cards.Deck;

/**
 * Implements a standard deck of playing cards.
 * @since [NEXT] (#1)
 */
public class PlayingCardDeck
implements Deck<PlayingCard>
{
	public static final int NOT_STARTED = -1 ;
	
/// Member fields //////////////////////////////////////////////////////////////
	
	/**
	 * The complete deck of cards. A pointer into the deck indicates the "next"
	 * card to be dealt.
	 */
	protected ArrayList<PlayingCard> m_cards = null ;
	
	/**
	 * The "next" card to be dealt from the deck.
	 */
	protected int m_nNext = NOT_STARTED ;

/// net.zerobandwidth.games.cards.Deck /////////////////////////////////////////
	
	@Override
	public Deck<PlayingCard> shuffle()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Deck<PlayingCard> shuffleRemaining()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayingCard next()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayingCard[] peek( int nCards )
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countRemaining()
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
/// Other methods //////////////////////////////////////////////////////////////
	
}
