package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.Main;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.GameState;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;

public class Sniper extends Piece {
    public Sniper(Player player) {
        super(player, Color.BLUE);
    }

    public void useAbilityIfPossible() {
        int x = Main.getLogicalAgent().x;
        int y = Main.getLogicalAgent().y;

        for (Piece piece :
                getPlayer().getRival().getPieces()) {
            if (!piece.isDead())
                if (this.isAbilityOn())
                    if (DistanceOfTwePieces(piece, this) <= getPlayer().getDice().getDiceNumber()) {
                        if (Main.getLogicalAgent().getGameState().getBoard().getCell(x, y) == piece.getCurrentCell()
                                && Main.getLogicalAgent().Target) {
                            piece.setDeath(true);
                            setAbilityOn(false);
                            GameState gameState = Main.getLogicalAgent().getGameState();
//
                            this.setSelected(false);
                            gameState.nextTurn();
                            Main.getLogicalAgent().Target = false;

                        }


                    }
        }
    }
    @Override
    public boolean canUseAbility() {
        int x = Main.getLogicalAgent().x;
        int y = Main.getLogicalAgent().y;
        boolean c = false;
        for (Piece piece :
                getPlayer().getRival().getPieces()) {
            if (!piece.isDead())
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
        if (getPlayer().getDice().getDiceNumber() == 5)
            setAbilityOn(true);
    }
}
