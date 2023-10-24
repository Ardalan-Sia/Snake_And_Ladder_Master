package ir.sharif.math.bp99_1.snake_and_ladder.model;
import java.util.LinkedList;
import java.util.Random;


public class Dice {
    LinkedList<Integer> chanceOfEachDiceNumber;
    GameState gameState;
    Player player1;
    Player player2;
    private int DiceNumber;
    private int PreviousDiceNumber;
    static  LinkedList<Integer> DefaultDice = new LinkedList<>();
    /**
     * add some fields to store :
     * 1) chance of each dice number ( primary chance of each number, should be 1 )
     * currently our dice has 1 to 6.
     * 2) generate a random number
     * <p>
     * initialize these fields in constructor.
     */
    public Dice() {
    chanceOfEachDiceNumber = new LinkedList<>();
        for (int i = 0; i < 6; i++) {
            DefaultDice.add(1);
            chanceOfEachDiceNumber.add(1);
        }
    }

    public int getDiceNumber() {
        return DiceNumber;
    }

    public int getPreviousDiceNumber() {
        return PreviousDiceNumber;
    }

    public void setPreviousDiceNumber(int previousDiceNumber) {
        PreviousDiceNumber = previousDiceNumber;
    }

    /**
     * create an algorithm generate a random number(between 1 to 6) according to the
     * chance of each dice number( you store them somewhere)
     * return the generated number
     */
    public int roll() {

        Random random = new Random();
        LinkedList<Integer> list = new LinkedList<>();
        int count = 1;
        for (int x :
                chanceOfEachDiceNumber) {
            for (int i = 0; i < x; i++) {
                list.add(count);
            }count++;
        }

        int a = random.nextInt(list.size());
        DiceNumber = list.get(a);
        if (DiceNumber == 2){
            chanceOfEachDiceNumber.clear();
            for (int i = 0; i < 6; i++) {
                chanceOfEachDiceNumber.add(1);
            }

        }
        return list.get(a);

    }

    /**
     * give a dice number and a chance, you should UPDATE chance
     * of that number.
     * pay attention chance of none of the numbers must not be negative(it can be zero)
     */
    public void addChance(int number, int chance) {
//        int a = chanceOfEachDiceNumber.get(number-1) - chance;
//        if (a>=0){
        if (chance + chanceOfEachDiceNumber.get(number - 1) <= 8) {
            if (number != 0) {
                chanceOfEachDiceNumber.set(number - 1, chance + chanceOfEachDiceNumber.get(number - 1));
            }
        }
    }
//        else {
//            chanceOfEachDiceNumber.set(number-1,0);
//        }
//    }



    /**
     * you should return the details of the dice number.
     * sth like:
     * "1 with #1 chance.
     * 2 with #2 chance.
     * 3 with #3 chance
     * .
     * .
     * . "
     * where #i is the chance of number i.
     */
    public String getDetails() {

        String gd ="";
        int count = 1;
        for (int i = 0; i < chanceOfEachDiceNumber.size(); i++) {
            if(gd.equals(""))
                gd = String.valueOf(count)+ " : #" + String.valueOf(chanceOfEachDiceNumber.get(i));
            else
                gd =gd + System.lineSeparator() + String.valueOf(count)+ " : #" + String.valueOf(chanceOfEachDiceNumber.get(i));
            count++;
        }
        return gd;
    }
}
