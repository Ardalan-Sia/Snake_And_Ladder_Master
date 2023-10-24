package ir.sharif.math.bp99_1.snake_and_ladder.logic;

import ir.sharif.math.bp99_1.snake_and_ladder.model.*;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.DeadlySnake;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.EarthWorm;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.MagicalSnake;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Transmitter;
import ir.sharif.math.bp99_1.snake_and_ladder.util.Config;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class ModelLoader {
    private final File boardFile, playersDirectory, archiveFile;
    static int x =0;
    static int y =0;

    /**
     * DO NOT CHANGE ANYTHING IN CONSTRUCTOR.
     */
    public ModelLoader() {
        boardFile = Config.getConfig("mainConfig").getProperty(File.class, "board");
        playersDirectory = Config.getConfig("mainConfig").getProperty(File.class, "playersDirectory");
        archiveFile = Config.getConfig("mainConfig").getProperty(File.class, "archive");
        if (!playersDirectory.exists()) playersDirectory.mkdirs();
    }


    /**
     * read file "boardFile" and craete a Board
     * <p>
     * you can use "BoardBuilder" class for this purpose.
     * <p>
     * pay attention add your codes in "try".
     */
    public Board loadBord() {
        try {
            Scanner scanner = new Scanner(boardFile);
            Board board = new Board();
            /**
             * 1
             */

            while (scanner.hasNext()){
                if (scanner.hasNextInt()) {
                    x = scanner.nextInt();
                    y = scanner.nextInt();
                    if (x != 0 && y != 0){
                        break;
                    }
                }else {
                    scanner.next();
                }
            }
            scanner.nextLine();

            for (int i = 1; i <= x; i++) {
                for (int j = 1; j <= y ; j++) {
                    Color color = Color.valueOf(scanner.next());
                    Cell cell = new Cell(color,i,j);
                    board.getCells().add(cell);


                }

            }
            /**
             * 2
             */
            int ns = 0;
            while (scanner.hasNext()){
                if (scanner.hasNextInt()){
                    ns = scanner.nextInt();
                    if (ns!=0){
                        break;
                    }
                }else{scanner.next();}

            }
            scanner.nextLine();
            for (int i = 0; i < ns; i++) {
                int xs = scanner.nextInt();
                int ys = scanner.nextInt();
                int pn = scanner.nextInt();
                board.getStartingCells().put(board.getCell(xs,ys),pn);
            }
            /**
             * 3
             */
            int nw = 0;
            while (scanner.hasNext()){
                if (scanner.hasNextInt()){
                    nw = scanner.nextInt();
                    if (nw!=0){
                        break;
                    }
                }else{scanner.next();}

            }
            scanner.nextLine();
            for (int i = 0; i < nw; i++) {
                int x1 = scanner.nextInt();
                int y1 = scanner.nextInt();
                int x2 = scanner.nextInt();
                int y2 = scanner.nextInt();
                Wall wall = new Wall(board.getCell(x1,y1),board.getCell(x2,y2));
                board.getWalls().add(wall);
//                test????
//                Wall wall1 = new Wall(board.getCell(x2,y2),board.getCell(x1,y1));
//                board.getWalls().add(wall1);
            }
            /**
             * 4
             */
            int nt = 0;
            while (scanner.hasNext()){
                if (scanner.hasNextInt()){
                    nt = scanner.nextInt();
                    if (nt!=0){
                        break;
                    }
                }else{scanner.next();}

            }
            scanner.nextLine();
            for (int i = 0; i < nt; i++) {
                int x1 = scanner.nextInt();
                int y1 = scanner.nextInt();
                int x2 = scanner.nextInt();
                int y2 = scanner.nextInt();

                    String type = scanner.next();

                    switch (type){
                        case "E" :
                            EarthWorm ew = new EarthWorm(board.getCell(x1, y1),board.getCell(x1, y1));
                            board.getTransmitters().add(ew);
                            board.getCell(x1, y1).setTransmitter(ew);
                            break;
                        case "D" :
                            DeadlySnake ds = new DeadlySnake(board.getCell(x1, y1), board.getCell(x2, y2));
                            board.getTransmitters().add(ds);
                            board.getCell(x1, y1).setTransmitter(ds);
                            break;
                        case "M" :
                            MagicalSnake ms = new MagicalSnake(board.getCell(x1, y1), board.getCell(x2, y2));
                            board.getTransmitters().add(ms);
                            board.getCell(x1, y1).setTransmitter(ms);
                            break;
                        case "N" :
                            Transmitter snake = new Transmitter(board.getCell(x1, y1), board.getCell(x2, y2));
                            board.getTransmitters().add(snake);
                            board.getCell(x1, y1).setTransmitter(snake);
                            break;
                    }


            }



            /**
             * 5
             */
            int np = 0;
            while (scanner.hasNext()){
                if (scanner.hasNextInt()){
                    np = scanner.nextInt();
                    if (np!=0){
                        break;
                    }
                }else{scanner.next();}

            }
            scanner.nextLine();

            for (int i = 0; i < np; i++) {
                int xp = scanner.nextInt();
                int yp = scanner.nextInt();
                int s = scanner.nextInt();
                int c = scanner.nextInt();
                int n = scanner.nextInt();
                Prize prize = new Prize(board.getCell(xp,yp),s,c,n);
                board.getCell(xp,yp).setPrize(prize);
            }
            scanner.close();

//            for (Cell cell :
//                    board.getCells()) {
//                Wall wall1 = new Wall(cell,board.getCell(cell.getX()+1,cell.getY()));
//                if (cell.getX()+1 > x){
//                    wall1=null;
//                }
//                Wall wall2 = new Wall(cell,board.getCell(cell.getX(),cell.getY()+1));
//                if (cell.getY()+1 > y){
//                    wall1=null;
//                }
//                Wall wall3 = new Wall(cell, board.getCell(cell.getX() - 1, cell.getY()));
//                if( (cell.getX()-1) <= 0) {
//                    wall3 = null;
//                }
//                Wall wall4 = new Wall(cell, board.getCell(cell.getX(), cell.getY() - 1));
//                if( (cell.getY()-1) <= 0) {
//                    wall4 = null;
//                }
//                if (wall1 !=null ){
//                    if (!board.getWalls().contains(wall1)){
//                        cell.getAdjacentOpenCells().add(wall1.getCell1());
//                    }
//                }
//                if (wall2 !=null ){
//                    if (!board.getWalls().contains(wall2)){
//                        cell.getAdjacentOpenCells().add(wall2.getCell2());
//                    }
//                }
//                if (wall3 !=null ){
//                    if (!board.getWalls().contains(wall3)){
//                        cell.getAdjacentOpenCells().add(wall3.getCell1());
//                    }
//                }
//                if (wall4 !=null ){
//                    if (!board.getWalls().contains(wall4)){
//                        cell.getAdjacentOpenCells().add(wall4.getCell2());
//                    }
//                }
//            }
            return board;

            // Code Here



        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("could not find board file");
            System.exit(-2);
        }
        return null;
    }

    /**
     * load player.
     * if no such a player exist, create an account(file) for him/her.
     * <p>
     * you can use "savePlayer" method of this class for that purpose.
     * <p>
     * add your codes in "try" block .
     */
    public Player loadPlayer(String name, int playerNumber) {
        try {

            File playerFile = getPlayerFile(name);
            Scanner scanner = new Scanner(playerFile);
            Player player  = new Player(name,0,0,playerNumber);

            return  player ;


            // Code in this part

        } catch (FileNotFoundException | IllegalArgumentException e) {
            e.printStackTrace();
            System.err.println("could not find player file");
            System.exit(-2);
        }
        return null;
    }

    /**
     * if player does not have a file, create one.
     * <p>
     * else update his/her file.
     * <p>
     * add your codes in "try" block .
     */
    public void savePlayer(Player player) {
        try {
            // add your codes in this part
            File file = getPlayerFile(player.getName());
            int score = player.getScore();
            int total =0;
            Scanner scanner = new Scanner(file);
            PrintStream printStream = new PrintStream(new FileOutputStream(file,true));
           if (scanner.hasNext()){
               while (scanner.hasNext()) {
                   if (scanner.hasNextInt()) {
                      total = scanner.nextInt() + score;
//                      clearing the text
                       FileOutputStream clearing = new FileOutputStream(file);
                       printStream.println("Total Score = " + total);
                       printStream.close();
                       printStream.flush();
                       break;
                   } else scanner.next();
               }
           }else{

               printStream.println("Total Score = "+score);
               printStream.close();
               printStream.flush();
           }
           scanner.close();








        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("could not find player file");
            System.exit(-2);
        }
    }

    /**
     * give you a name (player name), search for its file.
     * return the file if exist.
     * return null if not.
     */
    private File getPlayerFile(String name) {
        File file = new File(playersDirectory,name+".txt");
        if (file.exists()){
            return file;

        }else{
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return file;
    }

    /**
     * at the end of the game save game details
     */
    public void archive(Player player1, Player player2) {
        try {
            // add your codes in this part
            PrintStream printStream = new PrintStream(new FileOutputStream(archiveFile, true));
            printStream.println();
            printStream.println("*************************");
            String name1 = player1.getName();
            String name2 = player2.getName();
            printStream.println(player1.getName()+" = player1");
            printStream.println(player2.getName()+" = player2\n");
            if (player1.getScore() > player2.getScore()){
                printStream.println("Score of "+player1.getName() +" = "+player1.getScore()+"\nScore of "+player2.getName() +" = "+player2.getScore()+"\nWinner : "+ player1.getName());
            }if (player1.getScore() < player2.getScore()){
                printStream.println("Score of "+player1.getName() +" = "+player1.getScore()+"\nScore of "+player2.getName() +" = "+player2.getScore()+"\nWinner : "+ player2.getName());
            }if (player1.getScore() == player2.getScore()){
                printStream.println("Score of "+player1.getName() +" = "+player1.getScore()+"\nScore of "+player2.getName() +" = "+player2.getScore()+"\nIt's a Draw");
            }



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
