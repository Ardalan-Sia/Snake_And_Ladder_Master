package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.Main;
import ir.sharif.math.bp99_1.snake_and_ladder.logic.LogicalAgent;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;

import java.util.LinkedList;

public class Bomber extends Piece {
    public Bomber(Player player) {
        super(player, Color.RED);
    }
    public void useAbilityIfPossible(){
        int x1  = Main.getLogicalAgent().x;
        int y1 = Main.getLogicalAgent().y;
        boolean t= this.getCurrentCell() == Main.getLogicalAgent().getGameState().getBoard().getCell(x1,y1);
//        System.out.println("1"+Main.getLogicalAgent().doubleClick);
//        System.out.println("2"+this.isAbilityOn);
//        System.out.println("3"+ t);
//        System.out.println("4"+!isDead());
//        System.out.println(getCurrentCell().getX());
//        System.out.println(Main.getLogicalAgent().getGameState().getBoard().getCell(x1,y1).getX());
        if (Main.getLogicalAgent().doubleClick && this.isAbilityOn
                && this.getCurrentCell() == Main.getLogicalAgent().getGameState().getBoard().getCell(x1,y1)
        && !isDead()) {
            int y = getCurrentCell().getY();
            int x = getCurrentCell().getX();
            LinkedList<Integer> X = new LinkedList<>();
            if (x-1 >= 1){
                X.add(x-1);
            }
            X.add(x);
            if (x+1 <= 7){
                X.add(x+1);
            }
            LinkedList<Integer> Y = new LinkedList<>();
            if (y-1 >= 1){
                Y.add(y-1);
            }
            Y.add(y);
            if (y+1 <= 16){
                Y.add(y+1);
            }
            for (Piece piece :
                    getPlayer().getRival().getPieces()) {
                if (X.contains(piece.getCurrentCell().getX())&&Y.contains(piece.getCurrentCell().getY())){
                    piece.setDeath(true);
                }
            }
            for (Piece piece :
                    getPlayer().getPieces()) {
                if (X.contains(piece.getCurrentCell().getX())&&Y.contains(piece.getCurrentCell().getY())){
                    piece.setDeath(true);
                }
            }
            for (Integer xt :
                    X) {
                for (Integer yt:
                     Y) {
                    LogicalAgent.getBoard().getCell(xt, yt).setPrize(null);
                }
            }

            Main.getLogicalAgent().doubleClick = false;
            this.setAbilityOn(false);
            this.getCurrentCell().setColor(Color.BLACK);
            Main.getLogicalAgent().getGameState().nextTurn();
        }
    }

    @Override
    public boolean canUseAbility() {
        boolean c = false;
        if (this.isAbilityOn){
            c = true;
    }

       return c;

    }



    @Override
    public void TurnOnAbility() {
        if (getPlayer().getDice().getDiceNumber() == 3)
            setAbilityOn(true);

    }


}
