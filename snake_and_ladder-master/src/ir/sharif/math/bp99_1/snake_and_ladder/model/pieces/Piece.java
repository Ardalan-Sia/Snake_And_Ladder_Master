package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.logic.ModelLoader;
import ir.sharif.math.bp99_1.snake_and_ladder.model.*;

public abstract class Piece extends Board implements Ability {
    private Cell currentCell;
    private final Color color;
    private Player player;
    private boolean isSelected;
    protected boolean isAbilityOn;
    protected boolean isDead;
    public boolean isThief;

    public Piece(Player player, Color color) {
        this.color = color;
        this.player = player;
    }

    public boolean IsThief(){
        return isThief;
    }

    public abstract void useAbilityIfPossible();
    public abstract boolean canUseAbility();

    public int DistanceOfTwePieces(Piece firstPiece,Piece secondPiece){
        if (firstPiece.getCurrentCell().getY() == secondPiece.getCurrentCell().getY()){
            int d = firstPiece.getCurrentCell().getX()-secondPiece.getCurrentCell().getX();
            return Math.abs(d);
        }else if (firstPiece.getCurrentCell().getX() == secondPiece.getCurrentCell().getX()){
            int d = firstPiece.getCurrentCell().getY()-secondPiece.getCurrentCell().getY();
            return Math.abs(d);
        }
        return 1000000;
    }

    public Player getPlayer() {
        return player;
    }

    public Color getColor() {
        return color;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isDead(){
        return isDead;
    }

    public boolean isAbilityOn(){
        return isAbilityOn;
    }

    public void setAbilityOn(boolean hasAbility){
        isAbilityOn = hasAbility;
    }

    public void setDeath(boolean kill){
        isDead = kill;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    /**
     * @return "true" if your movement is valid  , else return " false"
     * <p>
     * In this method, you should check if the movement is valid of not.
     * <p>
     * You can use some methods ( they are recommended )
     * <p>
     * 1) "canEnter" method in class "Cell"
     * <p>
     * if your movement is valid, return "true" , else return " false"
     */
    public boolean isValidMove(Cell destination, int diceNumber) {
        ModelLoader modelLoader = new ModelLoader();
        Board board = modelLoader.loadBord();
        if ((destination.canEnter(this)) &&
                !this.isDead
                &&((Math.abs(destination.getY()-this.currentCell.getY())==diceNumber) &&(destination.getX()==this.currentCell.getX())
                ||(Math.abs(destination.getX()-this.currentCell.getX())==diceNumber &&(destination.getY()==this.currentCell.getY())))){

            if((Math.abs(destination.getY()-this.currentCell.getY())==diceNumber) &&(destination.getX()==this.currentCell.getX())){

                for (int i = Math.min(destination.getY(),this.currentCell.getY());
                     i < Math.max(destination.getY(),this.currentCell.getY()) ; i++) {


                    for (Wall wall : board.getWalls()) {
                        if (wall.getCell1()==board.getCell(destination.getX(),i)
                                && wall.getCell2()==board.getCell(destination.getX(),i+1) )
                            if (IsThief())
                                return true;
                            else
                                return false;
                    }

                }return true;
            }
            if(Math.abs(destination.getX()-this.currentCell.getX())==diceNumber &&(destination.getY()==this.currentCell.getY())){
                for (int i = Math.min(destination.getX(),this.currentCell.getX());
                     i < Math.max(destination.getX(),this.currentCell.getX()) ; i++) {
                    for (Wall wall : board.getWalls()) {
                        if (wall.getCell1()==board.getCell(i,destination.getY())
                            && wall.getCell2()==board.getCell(i+1,destination.getY()) )
                            if (IsThief())
                                return true;
                            else
                                return false;

                    }

                }return true;
            }
        }

        return false;
    }

    /**
     * @param destination move selected piece from "currentCell" to "destination"
     */
    public void moveTo(Cell destination) {
        this.getCurrentCell().setPiece(null);
        destination.setPiece(this);
        this.setCurrentCell(destination);
    }
}
