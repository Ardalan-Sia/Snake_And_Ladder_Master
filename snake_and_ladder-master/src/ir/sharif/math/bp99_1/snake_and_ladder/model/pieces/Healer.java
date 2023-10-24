package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.Main;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.GameState;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;

public class Healer extends Piece {



    public Healer(Player player) {
        super(player, Color.YELLOW);
        this.isDead = false;
    }



    public void useAbilityIfPossible() {
        int x = Main.getLogicalAgent().x;
        int y = Main.getLogicalAgent().y;
        for (Piece piece :
                getPlayer().getPieces()) {
            if (piece.isDead())
                if (this.isAbilityOn())
                    if (DistanceOfTwePieces(piece, this) <= getPlayer().getDice().getDiceNumber()) {
                        if (Main.getLogicalAgent().getGameState().getBoard().getCell(x,y)==piece.getCurrentCell()
                        && Main.getLogicalAgent().Target) {
                            piece.setDeath(false);
                            setAbilityOn(false);
                            GameState gameState = Main.getLogicalAgent().getGameState();
//
                            this.setSelected(false);
                            gameState.nextTurn();
                            Main.getLogicalAgent().Target = false;

                        }
                    } else if (DistanceOfTwePieces(piece, this) == 4) {
                        piece.setDeath(false);
                        setAbilityOn(false);

                    }
        }

    }

    @Override
    public boolean canUseAbility() {
        int x = Main.getLogicalAgent().x;
        int y = Main.getLogicalAgent().y;
        boolean c = false;
        for (Piece piece :
                getPlayer().getPieces()) {
            if (piece.isDead())
                if (this.isAbilityOn())
                    if (DistanceOfTwePieces(piece, this) <= getPlayer().getDice().getDiceNumber()) {
                        if (Main.getLogicalAgent().getGameState().getBoard().getCell(x, y) == piece.getCurrentCell()
                                && Main.getLogicalAgent().Target) {
                            c =true;

                        }
                    }

        }
        return c;
    }


    @Override
    public void TurnOnAbility() {
        if (getPlayer().getDice().getDiceNumber()==1)
        setAbilityOn(true);
    }


}
