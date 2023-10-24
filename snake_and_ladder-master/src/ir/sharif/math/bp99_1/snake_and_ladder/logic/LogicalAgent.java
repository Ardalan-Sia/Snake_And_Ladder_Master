package ir.sharif.math.bp99_1.snake_and_ladder.logic;


import ir.sharif.math.bp99_1.snake_and_ladder.graphic.GraphicalAgent;
import ir.sharif.math.bp99_1.snake_and_ladder.model.*;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;

import java.io.File;
import java.util.*;

/**
 * This class is an interface between logic and graphic.
 * some methods of this class, is called from graphic.
 * DO NOT CHANGE ANY PART WHICH WE MENTION.
 */
public class LogicalAgent {
   protected final ModelLoader modelLoader;
    private final GraphicalAgent graphicalAgent;
    private  final GameState gameState ;
    private static Board board;
    public boolean doubleClick;
    public boolean Target;
    public int x;
    public int y;
    /**
     * DO NOT CHANGE CONSTRUCTOR.
     */
    public LogicalAgent() {
        this.graphicalAgent = new GraphicalAgent(this);
        this.modelLoader = new ModelLoader();
        this.gameState = loadGameState();

    }



    public static Board getBoard() {
        return board;
    }

    public GameState getGameState() {
        return gameState;
    }

    /**
     * NO CHANGES NEEDED.
     */
    private GameState loadGameState() {
        this.board = modelLoader.loadBord();
        Player player1 = modelLoader.loadPlayer(graphicalAgent.getPlayerNames(1), 1);
        Player player2;
        do {
            player2 = modelLoader.loadPlayer(graphicalAgent.getPlayerNames(2), 2);
        } while (player1.equals(player2));
        player1.setRival(player2);
        player2.setRival(player1);
        return new GameState(board, player1, player2);
    }

    public ModelLoader getModelLoader() {
        return modelLoader;
    }

    /**
     * NO CHANGES NEEDED.
     */
    public void initialize() {
        graphicalAgent.initialize(gameState);
    }

    /**
     * Give a number from graphic,( which is the playerNumber of a player
     * who clicks "ReadyButton".) you should somehow change that player state.
     * if both players are ready. then start the game.
     */

    public void readyPlayer(int playerNumber) {
        Player player = gameState.getPlayer(playerNumber);
        if (player.isReady()) {
            player.setReady(false);
        } else {
            player.setReady(true);
        }
        if (gameState.getPlayer1().isReady() && gameState.getPlayer2().isReady()) {

            gameState.nextTurn();
            Board board = gameState.getBoard();
            LinkedHashMap<Cell, Integer> startingCells = board.getStartingCells();
            int count = 0;
            for (Cell cell :
                    startingCells.keySet()) {
//                Piece piece = new Piece(gameState.getPlayer(startingCells.get(cell)), cell.getColor());
                List<Piece> list = gameState.getPlayer(startingCells.get(cell)).getPieces();
                if (count == 4) {
                    count = 0;
                }
//                list.set(count,piece);
//                cell.setPiece();
                cell.setPiece(list.get(count));
                list.get(count).setCurrentCell(cell);
                count++;
            }
            for (Piece piece :
                    gameState.getPlayer1().getPieces()) {

            }
            File file = new File("resources/ir/sharif/math/bp99_1/snake_and_ladder/Data/"+
                    gameState.getPlayer1().getName()+"-"+gameState.getPlayer2().getName()+".txt");
            if(file.exists())
                Save.loadGame(gameState.getPlayer1().getName(), gameState.getPlayer2().getName());
        }
        // dont touch this line
        graphicalAgent.update(gameState);
    }

    /**
     * give x,y (coordinates of a cell) :
     * you should handle if user want to select a piece
     * or already selected a piece and now want to move it to a new cell
     */
    // ***
    public void selectCell(int x, int y) {
        this.x = x;
        this.y = y;

        Target =false;


        Dice dice = gameState.getCurrentPlayer().getDice();

        boolean anySelected = false;

        int temp = 0;
        for (Piece piece :
                gameState.getCurrentPlayer().getPieces()) {
            if (piece.isSelected()) {
                anySelected = true;
                temp = gameState.getCurrentPlayer().getPieces().indexOf(piece);
            }
        }
        if (!anySelected) {
            if (gameState.getBoard().getCell(x, y).getPiece() != null
                    && gameState.getCurrentPlayer().getPieces().contains(gameState.getBoard().getCell(x, y).getPiece())) {
                if (!gameState.getBoard().getCell(x, y).getPiece().isSelected()) {
                    gameState.getBoard().getCell(x, y).getPiece().setSelected(true);


                }

            }

        } else {
            if (gameState.getBoard().getCell(x, y).getPiece() == null) {

                if (gameState.getCurrentPlayer().getPieces().get(temp).isValidMove(gameState.getBoard().getCell(x, y), dice.getDiceNumber())) {
                    gameState.getCurrentPlayer().getPieces().get(temp).moveTo(gameState.getBoard().getCell(x, y));
//                    if (gameState.getCurrentPlayer().getPieces().get(temp).IsThief())
//                        gameState.getCurrentPlayer().getPieces().get(temp).useAbilityIfPossible();


                    if (gameState.getCurrentPlayer().getPieces().get(temp).getColor() == gameState.getBoard().getCell(x, y).getColor()) {
                        gameState.getCurrentPlayer().applyOnScore(4);
                    }
                    if (gameState.getBoard().getCell(x, y).getPrize() != null) {
//                        gameState.getBoard().getCell(x,y).getPrize().using(gameState.getCurrentPlayer().getPieces().get(temp));
                        gameState.getCurrentPlayer().usePrize(gameState.getBoard().getCell(x, y).getPrize());
                    }
                    if (gameState.getBoard().getCell(x, y).getTransmitter() != null) {
                        gameState.getBoard().getCell(x, y).getTransmitter().transmit(gameState.getCurrentPlayer().getPieces().get(temp));
                    }
                    gameState.getCurrentPlayer().getPieces().get(temp).setSelected(false);
                    gameState.nextTurn();
                }
//
            } else {
                if (gameState.getCurrentPlayer().getPieces().get(temp) == gameState.getBoard().getCell(x, y).getPiece()) {
                    doubleClick = true;
//                    if (!gameState.getCurrentPlayer().getPieces().get(temp).getIsThief())
                        gameState.getCurrentPlayer().getPieces().get(temp).setSelected(false);


                }else if (gameState.getBoard().getCell(x,y).getPiece()!=null)
                    Target = true;

//
            }
            for (Piece piece :
                    gameState.getCurrentPlayer().getPieces()) {

                piece.useAbilityIfPossible();
            }
        }


//        // dont touch this line
        graphicalAgent.update(gameState);
        checkForEndGame();
    }

    /**
     * check for endgame and specify winner
     * if player one in winner set winner variable to 1
     * if player two in winner set winner variable to 2
     * If the game is a draw set winner variable to 3
     */
    private void checkForEndGame() {
        int winner = 0;
        // game ends
        // your code
        if (gameState.getTurn() == 41) {

            if (gameState.getPlayer1().getScore() > gameState.getPlayer2().getScore()) {
                winner = 1;
            }
            if (gameState.getPlayer1().getScore() < gameState.getPlayer2().getScore()) {
                winner = 2;
            }
            if (gameState.getPlayer2().getScore() == gameState.getPlayer1().getScore()) {
                winner = 3;
            }
            File file = new File("resources/ir/sharif/math/bp99_1/snake_and_ladder/Data/"+gameState.getPlayer1().getName()+"-"+gameState.getPlayer2().getName()+".txt");
            file.delete();


            // dont touch it
            graphicalAgent.playerWin(winner);
            /* save players*/
            modelLoader.savePlayer(gameState.getPlayer1());
            modelLoader.savePlayer(gameState.getPlayer2());
            modelLoader.archive(gameState.getPlayer1(), gameState.getPlayer2());
            // LogicalAgent logicalAgent = new LogicalAgent();
            // logicalAgent.initialize();
        }
    }


    /**
     * Give a number from graphic,( which is the playerNumber of a player
     * who left clicks "dice button".) you should roll his/her dice
     * and update *****************
     */

    public void rollDice(int playerNumber) {
        int a = gameState.getPlayer(playerNumber).getDice().roll();
        if (!gameState.getPlayer(playerNumber).isDicePlayedThisTurn() && gameState.getPlayer(playerNumber) == gameState.getCurrentPlayer()) {
            gameState.getPlayer(playerNumber).setMoveLeft(a);
            if (a == 6) {
                gameState.getPlayer(playerNumber).applyOnScore(4);
            }
            if (gameState.getPlayer(playerNumber).getDice().getPreviousDiceNumber() == gameState.getPlayer(playerNumber).getDice().getDiceNumber())
                gameState.getPlayer(playerNumber).getDice().addChance(gameState.getPlayer(playerNumber).getDice().getDiceNumber(),1);
            gameState.getPlayer(playerNumber).getDice().setPreviousDiceNumber(0);


//            if (temp == gameState.getCurrentPlayer().getDice().getDiceNumber()){
//                count++;
//                if (count == 2 ){
//                    gameState.getCurrentPlayer().getDice().addChance(temp,1);
//
//                }
//            }else{
//                count = 0;
//                temp = gameState.getCurrentPlayer().getDice().getDiceNumber();
//            }

            for (Piece piece :
                    gameState.getCurrentPlayer().getPieces()) {
                piece.TurnOnAbility();

            }

            gameState.getPlayer(playerNumber).setDicePlayedThisTurn(true);
//            Save.saveGame(gameState.getPlayer1().getName(), gameState.getPlayer2().getName());
            gameState.cantMove();
        }



        // dont touch this line
        graphicalAgent.update(gameState);
        checkForEndGame();
    }


    public String getCellDetails(int x, int y) {
        return "cell at" + x + "," + y;
    }


    /**
     * Give a number from graphic,( which is the playerNumber of a player
     * who right clicks "dice button".) you should return the dice detail of that player.
     * you can use method "getDetails" in class "Dice"(not necessary, but recommended )
     */
    public String getDiceDetail(int playerNumber) {
        return gameState.getPlayer(playerNumber).getDice().getDetails();
    }
}
