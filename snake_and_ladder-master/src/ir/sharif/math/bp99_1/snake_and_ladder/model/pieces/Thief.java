package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.Main;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;

public class Thief extends Piece {
    private Prize prize;
    private boolean HasStolen;

    public Thief(Player player) {
        super(player, Color.GREEN);
        this.isAbilityOn = true;
        this.isThief = true;
    }

    public void setPrize(Prize prize) {
        this.prize = prize;
    }

    public void setHasStolen(boolean hasStolen) {
        HasStolen = hasStolen;
    }

    public Prize getPrize() {
        return prize;
    }

    public boolean isHasStolen() {
        return HasStolen;
    }

    @Override
    public void setDeath(boolean kill){
        if (kill)
            HasStolen = false;
            setPrize(null);

        isDead = kill;
    }


    @Override
    public void TurnOnAbility() {
        setAbilityOn(true);
    }


    @Override
    public void useAbilityIfPossible() {
        int x1 = Main.getLogicalAgent().x;
        int y1 = Main.getLogicalAgent().y;
        if (Main.getLogicalAgent().doubleClick
                && this.getCurrentCell() == Main.getLogicalAgent().getGameState().getBoard().getCell(x1, y1)) {
            if (!HasStolen && this.getCurrentCell().getPrize() != null) {
                this.prize = this.getCurrentCell().getPrize();
                this.getCurrentCell().setPrize(null);
                Main.getLogicalAgent().doubleClick = false;
                System.out.println("pp");
                HasStolen = true;
                Main.getLogicalAgent().getGameState().nextTurn();
            } else if (HasStolen) {
                System.out.println("ss");
                Main.getLogicalAgent().getGameState().getBoard().getCell(x1, y1).setPrize(prize);
                prize.setCell(this.getCurrentCell());
                HasStolen = false;
                Main.getLogicalAgent().doubleClick = false;
                Main.getLogicalAgent().getGameState().nextTurn();
            }
        }

    }

    @Override
    public boolean canUseAbility() {
        boolean c = false;
        int x1 = Main.getLogicalAgent().x;
        int y1 = Main.getLogicalAgent().y;
        if (Main.getLogicalAgent().doubleClick
                && this.getCurrentCell() == Main.getLogicalAgent().getGameState().getBoard().getCell(x1, y1)) {
            if (!HasStolen && this.getCurrentCell().getPrize() != null) {
                this.prize = this.getCurrentCell().getPrize();
                this.getCurrentCell().setPrize(null);
                Main.getLogicalAgent().doubleClick = false;
                System.out.println("pp");
                HasStolen = true;
                c = true;
                Main.getLogicalAgent().getGameState().nextTurn();
            } else if (HasStolen) {
                System.out.println("ss");
                Main.getLogicalAgent().getGameState().getBoard().getCell(x1, y1).setPrize(prize);
                prize.setCell(this.getCurrentCell());
                HasStolen = false;
                c = true;
                Main.getLogicalAgent().doubleClick = false;
                Main.getLogicalAgent().getGameState().nextTurn();
            }
        }
        return c;
    }
}



