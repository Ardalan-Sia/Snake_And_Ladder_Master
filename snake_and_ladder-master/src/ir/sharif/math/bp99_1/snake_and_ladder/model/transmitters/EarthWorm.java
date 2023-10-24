package ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters;

import ir.sharif.math.bp99_1.snake_and_ladder.logic.LogicalAgent;
import ir.sharif.math.bp99_1.snake_and_ladder.logic.ModelLoader;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Board;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;

import java.util.Random;


public class EarthWorm extends Transmitter {


    public EarthWorm(Cell firstCell, Cell lastCell) {
        super(firstCell, lastCell);
        this.setColor(Color.BLACK);
    }

    @Override
    public void transmit(Piece piece) {

        if (piece.getColor()==Color.GREEN)
            piece.setDeath(true);
        piece.setDeath(false);
        Random random = new Random();;
        Board board = LogicalAgent.getBoard();
        int xr = random.nextInt(7)+1;
        int yr = random.nextInt(16)+1;
        if (board.getCell(xr,yr).canEnter(piece)){

            piece.moveTo(board.getCell(xr,yr));

        }
        piece.getPlayer().applyOnScore(-3);
    }
}
