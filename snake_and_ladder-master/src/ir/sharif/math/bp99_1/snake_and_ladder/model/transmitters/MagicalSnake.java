package ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;

public class MagicalSnake extends Transmitter{
    public MagicalSnake(Cell firstCell, Cell lastCell) {
        super(firstCell, lastCell);
        this.setColor(Color.WHITE);
    }

    @Override
    public void transmit(Piece piece) {

        if (piece.getColor()==Color.GREEN)
            piece.setDeath(true);
        piece.setDeath(false);
        if (getLastCell().canEnter(piece)){
            piece.moveTo(getLastCell());
            piece.setAbilityOn(true);
        }
        piece.getPlayer().applyOnScore(3);
    }
}
