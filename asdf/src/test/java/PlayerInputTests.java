import blackjack.GameActions;
import blackjack.Player;
import blackjack.PlayerInputs;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class PlayerInputTests extends PlayerInputs {

    static InputStream originalSystemIn;

    @BeforeClass
    public static void runBeforeTest() {
        originalSystemIn = System.in;
    }

    @After
    public void runAfterTest() {
        System.setIn(originalSystemIn);
    }

    @Test
    public void testTakeGameResolution() {
        prepTerminal("yes");
        assertEquals(GameActions.GameAction.RESTART_GAME, PlayerInputs.takeGameResolution());

        prepTerminal("no");
        assertEquals(GameActions.GameAction.END_GAME, PlayerInputs.takeGameResolution());
    }

    @Test
    public void testGetPlayerReBuyIn() {
        Player testPlayer = new Player(0);

        prepTerminal("7");
        getPlayerReBuyIn(testPlayer);
        assertTrue(didGetPlayerReBuyInElseLoopOccur());

        prepTerminal("0");
        assertEquals(0, getPlayerReBuyIn(testPlayer));

        prepTerminal("40");
        assertEquals(40, getPlayerReBuyIn(testPlayer));
    }

    @Test
    public void testEnsureInputIsNumber() {
        assertEquals(123, ensureInputIsNumber("123"));

        System.setIn(new ByteArrayInputStream("no".getBytes()));
        assertEquals(0, ensureInputIsNumber(""));

        System.setIn(new ByteArrayInputStream("1234".getBytes()));
        assertEquals(1234, ensureInputIsNumber(""));
    }

    private void prepTerminal(String terminalEntry) {
        InputStream fromTerminal = new ByteArrayInputStream(terminalEntry.getBytes());
        System.setIn(fromTerminal);
    }

    @Test
    public void testTakePlayerAction() {
        prepTerminal("HIT");
        assertEquals(GameActions.PlayerAction.HIT, takePlayerAction());

        prepTerminal("stand");
        assertEquals(GameActions.PlayerAction.STAND, takePlayerAction());

        prepTerminal("dOuBLe DoWn");
        assertEquals(GameActions.PlayerAction.DOUBLE_DOWN, takePlayerAction());

        prepTerminal("incorrect answer");
        try {
            PlayerInputs.takePlayerAction();
            fail();
        } catch (NullPointerException e) {
            //test Pass scenario
        }
    }

    @Test
    public void testTakePlayerBet() {
        Player testPlayer = new Player(100);

        prepTerminal("0");
        assertEquals(100, takePlayerBet(new Player(100)));

        prepTerminal("1000");
        assertEquals(100, takePlayerBet(new Player(100)));

        prepTerminal("50");
        assertEquals(50, takePlayerBet(new Player(100)));
    }


}
