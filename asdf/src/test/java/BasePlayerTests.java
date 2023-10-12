import blackjack.BasePlayer;
import blackjack.Deck;
import blackjack.Player;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class BasePlayerTests {

    @Test
    public void testGetHandValueNoAces() {
        BasePlayer player = new Player(100);
        player.addCardToHand(new Deck.Card(Deck.Suit.HEARTS, Deck.Face.FIVE));
        player.addCardToHand(new Deck.Card(Deck.Suit.HEARTS, Deck.Face.TWO));
        assertEquals("Is hand value working?", 7, player.getHandValue());
    }

    @Test
    public void testGetHandValueOneAceBelowTwentyOne() {
        BasePlayer player = new Player(100);
        player.addCardToHand(new Deck.Card(Deck.Suit.HEARTS, Deck.Face.FIVE));
        player.addCardToHand(new Deck.Card(Deck.Suit.HEARTS, Deck.Face.ACE));
        assertEquals("Is hand value working?", 16, player.getHandValue());
    }

    @Test
    public void testGetHandValueOneAceAboveTwentyOne() {
        BasePlayer player = new Player(100);
        player.addCardToHand(new Deck.Card(Deck.Suit.HEARTS, Deck.Face.FIVE));
        player.addCardToHand(new Deck.Card(Deck.Suit.HEARTS, Deck.Face.KING));
        player.addCardToHand(new Deck.Card(Deck.Suit.HEARTS, Deck.Face.ACE));
        assertEquals("Is hand value working?", 16, player.getHandValue());
    }

    @Test
    public void testGetHandValueTwoAcesBelowTwentyOne() {
        BasePlayer player = new Player(100);
        player.addCardToHand(new Deck.Card(Deck.Suit.HEARTS, Deck.Face.FIVE));
        player.addCardToHand(new Deck.Card(Deck.Suit.HEARTS, Deck.Face.ACE));
        player.addCardToHand(new Deck.Card(Deck.Suit.HEARTS, Deck.Face.ACE));
        assertEquals("Is hand value working?", 17, player.getHandValue());
    }
}
