package ir.sharif.math.bp99_1.snake_and_ladder.model;

import ir.sharif.math.bp99_1.snake_and_ladder.Main;
import ir.sharif.math.bp99_1.snake_and_ladder.logic.LogicalAgent;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.*;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Save {
    public static void saveCells(PrintStream print) throws FileNotFoundException {
        print.println(LogicalAgent.getBoard().getCells().size());
        for (Cell cell :
                LogicalAgent.getBoard().getCells()) {
            print.println(cell.getX() + " " + cell.getY());
            print.println(cell.getColor());
        }
    }

    public static void savePrize(PrintStream print) throws FileNotFoundException {
        int cellsWithPrize = 0;
        for (Cell cell :
                LogicalAgent.getBoard().getCells()) {
            if (cell.getPrize() != null) {
                cellsWithPrize++;
            }
        }
        print.println(cellsWithPrize);
        for (Cell cell :
                LogicalAgent.getBoard().getCells()) {
            if (cell.getPrize() != null) {
                print.println(cell.getX() + " " + cell.getY() + " " + cell.getPrize().getPoint() + " "
                        + cell.getPrize().getDiceNumber() + " " + cell.getPrize().getChance());
            }
        }

    }

    public static void savePlayer(PrintStream print) {
        for (int i = 1; i <= 2; i++) {
            Player player = Main.getLogicalAgent().getGameState().getPlayer(i);
            print.println(player.getName());
            print.println(player.getScore());
            print.println(player.getDice().getDetails());
            print.println(player.getPieces().size());
            print.println(player.isDicePlayedThisTurn());
            print.println(player.isReady());
            print.println(player.getMoveLeft());
            Thief thief = null;
            for (Piece piece :
                    player.getPieces()) {
                if (piece.getColor() != Color.GREEN) {
                    print.println(piece.getCurrentCell().getX() + " " + piece.getCurrentCell().getY());
                    print.println((piece.getColor()));
                    print.println(piece.isDead());
                    print.println(piece.canUseAbility());
                } else thief = (Thief) piece;
            }
            print.println(thief.getCurrentCell().getX() + " " + thief.getCurrentCell().getY());
            print.println((thief.getColor()));
            print.println(thief.isDead());
            print.println(thief.canUseAbility());
            if(thief.isHasStolen())
                print.println(thief.getPrize().getPoint()+" "+thief.getPrize().getChance()+" "+
                    thief.getPrize().getDiceNumber());
            else print.println("null");
            print.println(thief.isHasStolen());

        }
    }

    public static boolean saveGame(String name1, String name2) {
        try {
            File file = new File("resources/ir/sharif/math/bp99_1/snake_and_ladder/Data/"+name1+"-"+name2+".txt");
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File to save already exists.");
            }
            PrintStream print = new PrintStream(new FileOutputStream(file));
            print.println(Main.getLogicalAgent().getGameState().getTurn());
            saveCells(print);
            savePrize(print);
            savePlayer(print);
            print.flush();
            print.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;

    }

    public static void loadCells(Scanner scan){
        int size = scan.nextInt();
//        System.out.println(size);
        for (int i = 0; i <size ; i++) {
            int x = scan.nextInt();
            int y = scan.nextInt();
            scan.nextLine();
            Color color = Color.valueOf(scan.next());
//            System.out.println(x+" "+ y+" "+ color);
            Main.getLogicalAgent().getGameState().getBoard().getCell(x, y).setColor(color);
        }
    }
    public static void loadPrize(Scanner scan){
        int size = scan.nextInt();
        System.out.println(size);
        for (Cell cell: Main.getLogicalAgent().getGameState().getBoard().getCells()) {
            if(cell.getPrize()!=null){
                cell.setPrize(null);
            }
        }
        for (int i = 0; i <size ; i++) {
            int x = scan.nextInt();
            int y = scan.nextInt();
            int point = scan.nextInt();
            int diceNumber = scan.nextInt();
            int chance = scan.nextInt();
//            System.out.println(x+" "+y+" "+point+" "+diceNumber+" "+chance);
            Prize prize = new Prize(Main.getLogicalAgent().getGameState().getBoard().getCell(x, y), point,
                    chance, diceNumber);
            Main.getLogicalAgent().getGameState().getBoard().getCell(x, y).setPrize(prize);

        }
    }

    public static boolean loadPlayer(Scanner scan, String name1, String name2){
        for (int i = 1; i <= 2; i++) {
            Player player = Main.getLogicalAgent().getGameState().getPlayer(i);
            scan.nextLine();
            String name = scan.next();
            if(i==1)
                if(!name1.equals(name)) return false;
            if(i==2)
                if(!name2.equals(name)) return false;
            System.out.println(name);
            int score = scan.nextInt();
            System.out.println(score);
            player.setName(name);
            player.setScore(score);
            scan.nextLine();
            for (int j = 1; j <=6 ; j++) {

                String num = scan.nextLine().split("#")[1].replace("\n", "").replace("\r", "");
                System.out.println(num);
                int chance = Integer.parseInt(num);
                Main.getLogicalAgent().getGameState().getPlayer(i).getDice()
                        .addChance(j, chance-1);
            }

            int pieceSize = scan.nextInt();
            scan.nextLine();
            player.setDicePlayedThisTurn("true".equals(scan.next()));
            scan.nextLine();
            player.setReady("true".equals(scan.next()));
            player.setMoveLeft(scan.nextInt());
            for (Piece piece:
                    Main.getLogicalAgent().getGameState().getPlayer(i).getPieces()) {
                piece.getCurrentCell().setPiece(null);

            }
            Main.getLogicalAgent().getGameState().getPlayer(i).getPieces().clear();
            for (int j = 0; j <pieceSize ; j++) {
                int x = scan.nextInt();
                int y = scan.nextInt();
                scan.nextLine();
                Color color = Color.valueOf(scan.next());
                scan.nextLine();
                boolean isDead = "true".equals(scan.next());
                scan.nextLine();
                boolean canUseAbility = "true".equals(scan.next());
                if(color == Color.BLUE){
                    Sniper sniper = new Sniper(player);
                    sniper.setCurrentCell(Main.getLogicalAgent().getGameState().getBoard().getCell(x,y));
                    sniper.setDeath(isDead);
                    sniper.setPlayer(player);
                    sniper.setAbilityOn(canUseAbility);
                    Main.getLogicalAgent().getGameState().getBoard().getCell(x, y).setPiece(sniper);
                    Main.getLogicalAgent().getGameState().getPlayer(i).getPieces().add(sniper);
                }else if(color == Color.YELLOW){
                    Healer healer = new Healer(player);
                    healer.setCurrentCell(Main.getLogicalAgent().getGameState().getBoard().getCell(x,y));
                    healer.setDeath(isDead);
                    healer.setPlayer(player);
                    healer.setAbilityOn(canUseAbility);
                    Main.getLogicalAgent().getGameState().getBoard().getCell(x, y).setPiece(healer);
                    Main.getLogicalAgent().getGameState().getPlayer(i).getPieces().add(healer);
                }else if(color == Color.RED){
                    Bomber bomber = new Bomber(player);
                    bomber.setCurrentCell(Main.getLogicalAgent().getGameState().getBoard().getCell(x,y));
                    bomber.setDeath(isDead);
                    bomber.setPlayer(player);
                    bomber.setAbilityOn(canUseAbility);
                    Main.getLogicalAgent().getGameState().getBoard().getCell(x, y).setPiece(bomber);
                    Main.getLogicalAgent().getGameState().getPlayer(i).getPieces().add(bomber);
                }
                else if(color == Color.GREEN){
                    Thief thief = new Thief(player);
                    thief.setCurrentCell(Main.getLogicalAgent().getGameState().getBoard().getCell(x,y));
                    thief.setDeath(isDead);
                    thief.setPlayer(player);
                    thief.setAbilityOn(canUseAbility);
                    scan.nextLine();
                    String tmp = scan.next();
                    int point = 0, chance=0, diceNumber=0;
                    if(!tmp.equals("null")){
                        point = Integer.parseInt(tmp);
                        chance = scan.nextInt();
                        diceNumber = scan.nextInt();
                        scan.nextLine();
                    }
                    Prize prize = new Prize(Main.getLogicalAgent().getGameState().getBoard().getCell(x,y),
                            point, chance, diceNumber);
                    thief.setPrize(prize);
                    thief.setHasStolen("true".equals(scan.next()));
                    Main.getLogicalAgent().getGameState().getBoard().getCell(x, y).setPiece(thief);
                    Main.getLogicalAgent().getGameState().getPlayer(i).getPieces().add(thief);
                }

            }

        }
        return true;
    }

    public static boolean loadGame(String name1, String name2) {
        try {
            File file = new File("resources/ir/sharif/math/bp99_1/snake_and_ladder/Data/"+name1+"-"+name2+".txt");
            if (file.createNewFile()) {
                System.out.println("File does not exist");
                return false;
            } else {
                System.out.println("File to load exists");
            }
            Scanner scan = new Scanner(file);
            int turn = scan.nextInt();
            Main.getLogicalAgent().getGameState().setTurn(turn);
            System.out.println(turn);
            loadCells(scan);
            loadPrize(scan);
            if(!loadPlayer(scan, name1, name2)) return false;
            scan.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

}
