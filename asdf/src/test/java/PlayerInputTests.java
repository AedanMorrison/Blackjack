import blackjack.GameActions;
import blackjack.PlayerInputs;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
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
        InputStream yesFromTerminal = new ByteArrayInputStream("yes".getBytes());
        System.setIn(yesFromTerminal);
        assertEquals(PlayerInputs.takeGameResolution(), GameActions.GameAction.RESTART_GAME);

        InputStream noFromTerminal = new ByteArrayInputStream("no".getBytes());
        System.setIn(noFromTerminal);
        assertEquals(PlayerInputs.takeGameResolution(), GameActions.GameAction.END_GAME);
    }

    @Test
    public void testEnsureInputIsNumber() {
        assertEquals(123, ensureInputIsNumber("123"));

        System.setIn(new ByteArrayInputStream("no".getBytes()));
        assertEquals(0, ensureInputIsNumber(""));

        System.setIn(new ByteArrayInputStream("1234".getBytes()));
        assertEquals(1234, ensureInputIsNumber(""));
    }

}
