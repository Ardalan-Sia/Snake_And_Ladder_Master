package ir.sharif.math.bp99_1.snake_and_ladder.model;

import jdk.management.resource.internal.inst.NetRMHooks;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class GameState {
    private final Board board;
    private final Player player1;
    private final Player player2;
    private int turn;

    public GameState(Board board, Player player1, Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        turn = 0;
    }

    public Board getBoard() {
        return board;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getPlayer(int i) {
        if (i == 1) return player1;
        else if (i == 2) return player2;
        else return null;
    }

    public boolean isStarted() {
        return turn != 0;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    /**
     * return null if game is not started.
     * else return a player who's turn is now.
     */
    public Player getCurrentPlayer() {
        if (!isStarted()){
            return null;
        }
        else{
            Player player = getPlayer((turn-1)%2+1);
            return player;
        }

    }


    /**
     * finish current player's turn and update some fields of this class;
     * you can use method "endTurn" in class "Player" (not necessary, but recommanded)
     */
    public void nextTurn() {

        if (isStarted()) {
            getCurrentPlayer().getDice().setPreviousDiceNumber(getCurrentPlayer().getDice().getDiceNumber());

            getCurrentPlayer().endTurn();
            turn++;
        }else {
            turn = 1;
        }
        if(turn>1)
            Save.saveGame(player1.getName(), player2.getName());


    }
    public void cantMove(){
        if (!getCurrentPlayer().hasMove(getBoard(),getCurrentPlayer().getDice().getDiceNumber())){

            getCurrentPlayer().applyOnScore(-3);
            nextTurn();
        }
    }


    @Override
    public String toString() {
        return "GameState{" +
                "board=" + board +
                ", playerOne=" + player1 +
                ", playerTwo=" + player2 +
                ", turn=" + turn +
                '}';
    }

    public void save(){



    }
}
