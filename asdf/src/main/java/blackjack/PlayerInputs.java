package blackjack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ConcurrentModificationException;

public class PlayerInputs {

    public static int takePlayerBet(Player bettingPlayer) {
        System.out.println(bettingPlayer.name + " should input a number for a bet now. Don't go broke.");
        int desiredBet = ensureInputIsNumber(getInputFromTerminal());
        int attempts = 0;
        while (checkAgainstMinimumBet(desiredBet) || checkAgainstPlayerFunds(desiredBet, bettingPlayer)) { //checks that desired bet passes criteria before continuing
            if (attempts > 2) {
                System.out.println("All in you say? Great!");
                return bettingPlayer.getFunds();
            }
            attempts++;
            desiredBet = ensureInputIsNumber(getInputFromTerminal());
        }
        System.out.println(bettingPlayer.name + " has bet: " + desiredBet);
        return desiredBet;
    }

    public static GameActions.PlayerAction takePlayerAction() {
        System.out.println("Hit, Stand or Double Down.");
        while (true) {
            switch (getInputFromTerminal().toLowerCase()) {
                case "hit":
                    return GameActions.PlayerAction.HIT;
                case "stand":
                    return GameActions.PlayerAction.STAND;
                case "double down":
                    return GameActions.PlayerAction.DOUBLE_DOWN;
                default:
                    System.out.println("You did something stupid, enter what I asked for");
                    break;
            }
        }
    }

    public static GameActions.GameAction takeGameResolution() {
        System.out.println("Would you like to keep playing? Type Yes or No");
        while (true) {
            switch (getInputFromTerminal().toLowerCase()) {
                case "yes":
                    return GameActions.GameAction.RESTART_GAME;
                case "no":
                    return GameActions.GameAction.END_GAME;
                default:
                    System.out.println("You did something stupid, enter what I asked for");
                    break;
            }
        }
    }

    public static int getPlayerReBuyIn(Player player) {
        System.out.println(player.name + " how much ya wanna buy back in with? 0 if you're out.");
        while (true) {
            int playerInput = ensureInputIsNumber(getInputFromTerminal());
            if (playerInput == 0) {
                return playerInput;
            }
            if (playerInput + player.getFunds() > BlackJack.MINIMUM_BET) {
                System.out.println("This better not be rent money.");
                return playerInput;
            } else {
                getPlayerReBuyInElseLoopOccurred = true;
                System.out.println("You need to have more than the minimum bet after buying in. Try again.");
            }
        }
    }

    private static boolean checkAgainstMinimumBet(int desiredBet) {
        if (desiredBet < BlackJack.MINIMUM_BET) {
            System.out.println("The table has a minimum bet of " + BlackJack.MINIMUM_BET + ". Do try and bet more next time");
            return true;
        }
        return false;
    }

    private static boolean checkAgainstPlayerFunds(int desiredBet, Player bettingPlayer) {
        if (desiredBet > bettingPlayer.getFunds()) {
            System.out.println("Did you graduate preschool? You can't bet more than you have. Try again");
            return true;
        }
        return false;
    }

    private static String getInputFromTerminal() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            return reader.readLine();
        } catch (IOException e) {
            System.out.println("I don't get it, come again?");
            return "";
        }
    }

    protected static int ensureInputIsNumber(String inputFromTerminal) {
        try {
            return Integer.parseInt(inputFromTerminal);
        } catch (NumberFormatException e) {
            try {
                System.out.println("In case it wasn't clear, it's gotta be a number. If you fuck this up, you're betting nada.");
                return Integer.parseInt(getInputFromTerminal());
            } catch (NumberFormatException e2) {
                System.out.println("You fucked it up, you bet nada.");
                return 0;
            }
        }
    }

    static boolean getPlayerReBuyInElseLoopOccurred = false;
    protected static boolean didGetPlayerReBuyInElseLoopOccur() {
        return getPlayerReBuyInElseLoopOccurred;
    }

}
