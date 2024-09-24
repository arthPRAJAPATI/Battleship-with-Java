package battleship;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Main {
    public static boolean HORIZONTAL = false;
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {

        String[][] playField1 = new String[11][11];
        String[][] fogField1 = new String[11][11];
        String[][] playField2 = new String[11][11];
        String[][] fogField2 = new String[11][11];
        String[] vessels = new String[]{"Aircraft Carrier", "Battleship", "Submarine", "Cruiser", "Destroyer"};
        int[] vesselLength = new int[]{5,4,3,3,2};
        playField1 = createPlayField(playField1);
        fogField1 = createPlayField(fogField1);
        playField2 = createPlayField(playField2);
        fogField2 = createPlayField(fogField2);
        int remainingParts1 = 17;
        int remainingParts2 = 17;
        int playerTurn = 1;
        for(int j = 0; j < 2; j++) {
            switch (playerTurn){
                case 1:
                    System.out.println("Player 1, place your ships on the game field");
                    printField(playField1);
                    for(int i = 0; i < vessels.length;i++) {
                        String[] coOrdinate = getCoord("Enter the coordinates of the "+vessels[i]+" ("+ vesselLength[i] +" cells):");
                        boolean isCorrect =  checkCoord(coOrdinate, vessels[i], vesselLength[i], playField1);
                        if(isCorrect) {
                            printField(playField1);
                        }
                    }
                    printPassMsg();
                    playerTurn = 2;
                    break;
                case 2:
                    System.out.println("Player 2, place your ships to the game field");
                    printField(playField2);
                    for(int i = 0; i < vessels.length;i++) {
                        String[] coOrdinate = getCoord("Enter the coordinates of the "+vessels[i]+" ("+ vesselLength[i] +" cells):");
                        boolean isCorrect =  checkCoord(coOrdinate, vessels[i], vesselLength[i], playField2);
                        if(isCorrect) {
                            printField(playField2);
                        }
                    }
                    printPassMsg();
                    playerTurn = 1;
                    break;
            }
        }

        while(remainingParts1 > -1 && remainingParts2 > -1) {
            switch (playerTurn) {
                case 1:
                    printPlayField(playField1, fogField2);
                    System.out.println("Player 1, it's your turn:");
                    remainingParts2 = takeShot(playField2, fogField2, remainingParts2);
                    printPassMsg();
                    playerTurn = 2;
                    break;
                case 2:
                    printPlayField(playField2, fogField1);
                    System.out.println("Player 2, it's your turn:");
                    remainingParts1 = takeShot(playField1, fogField1, remainingParts1);
                    printPassMsg();
                    playerTurn = 1;
                    break;
            }
        }









//        System.out.println("The game starts!");

    }

    private static void printPassMsg() {
        System.out.println("Press Enter and pass the move to another player");
        sc.nextLine();
    }

    public static void printPlayField(String[][] playField, String[][] fogField){
        printField(fogField);
        System.out.println("---------------------");
        printField(playField);
    }

    public static boolean isShipSunk(String[][] playField, int x, int y) {
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        for (int direction = 0; direction < 4; direction++) {
            int newX = x + dx[direction];
            int newY = y + dy[direction];

            while (newX >= 1 && newX <= 10 && newY >= 1 && newY <= 10 && playField[newX][newY].equals("X")) {
                newX += dx[direction];
                newY += dy[direction];
            }

            if (newX >= 1 && newX <= 10 && newY >= 1 && newY <= 10 && playField[newX][newY].equals("O")) {
                return false;
            }
        }

        return true;
    }


    public static int takeShot(String[][] playField, String[][] fogField, int remainingParts){
            String shot = sc.nextLine();
            int x = (int) shot.charAt(0) - 64;
            int y = Integer.parseInt(shot.substring(1));
            while (x > 10 || x < 0 || y > 10 || y < 0) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
//                System.out.println("You missed! Try again:");
                shot = sc.nextLine();
                x = (int) shot.charAt(0) - 64;
                y = Integer.parseInt(shot.substring(1));
            }
            String value = playField[x][y];
            if ("~".equals(value) || "M".equals(value)) {
                playField[(int) shot.charAt(0) - 64][Integer.parseInt(shot.substring(1))] = "M";
                fogField[(int) shot.charAt(0) - 64][Integer.parseInt(shot.substring(1))] = "M";
                System.out.println("You missed! Try again:");
            } else if ("O".equals(value) || "X".equals(value)) {
                playField[(int) shot.charAt(0) - 64][Integer.parseInt(shot.substring(1))] = "X";
                fogField[(int) shot.charAt(0) - 64][Integer.parseInt(shot.substring(1))] = "X";
                if (isShipSunk(playField, x, y)) {
                    System.out.println("You sank a ship! Specify a new target:");
                } else {
                    System.out.println("You hit a ship! Try again:");
                }
                remainingParts--;
                System.out.println(remainingParts);
            }
            if(remainingParts < 0) {
                System.out.println("You sank the last ship. You won. Congratulations!");
            }
            return remainingParts;


    }
    public static String[] getCoord(String message) {
        System.out.println(message);
        String shipCoord = sc.nextLine();
        return shipCoord.split(" ");
    }
    public static int getLengthAndParts(String[] shipCoord) {
        String end = shipCoord[1];
        StringBuilder str = new StringBuilder();
        char x = shipCoord[0].charAt(0);
        int y =  Integer.parseInt(shipCoord[0].substring(1));
        int len = 1;
        if(HORIZONTAL) {
            do {
                str.append(x).append(y).append(" ");
                if(y < Integer.parseInt(end.substring(1))){
                    y++;
                } else {
                    y--;
                }
                len++;
            }while(!end.substring(1).equals(String.valueOf(y)));
        } else {
            do{
                str.append(x).append(y).append(" ");
                if((int) x < (int) end.charAt(0)){
                    x++;
                } else {
                    x--;
                }
                len++;
            }while(x != end.charAt(0));

        }
        str.append(end);
//        System.out.println("length: " + len);


        return len;

//        System.out.println("Parts: " + str.toString());
    }

    public static boolean checkCoord(String[] shipCoord, String vessel, int vesselLength, String[][] playField) {
        while (shipCoord[0].charAt(0) != shipCoord[1].charAt(0) &&
                shipCoord[0].charAt(1) != shipCoord[1].charAt(1)) {
            shipCoord = getCoord("Error! Wrong ship location! Try again:");

        }
        if ((int) shipCoord[0].charAt(0) < 65 || (int) shipCoord[1].charAt(0) > 74) {
            System.out.println("Error!");
            return false;
        }
        for (String s : shipCoord) {
            while(Integer.parseInt(s.substring(1)) > 10 || Integer.parseInt(s.substring(1)) < 1) {
                System.out.println("Error!");
                return false;
            }
        }

        int[] numericalCoord = getNumericCoord(shipCoord);
        while(vesselLength != getLengthAndParts(shipCoord)) {
            shipCoord = getCoord("Error! Wrong length of the "+vessel+"! Try again:");
            numericalCoord = getNumericCoord(shipCoord);
        }

        while(isAdjacent(playField, numericalCoord)) {
            shipCoord = getCoord("Error! You placed it too close to another one. Try again:");
            numericalCoord = getNumericCoord(shipCoord);
        }

        while(isOverlapping(playField, numericalCoord)) {
            shipCoord = getCoord("Error! You placed it too close to another one. Try again:");
            numericalCoord = getNumericCoord(shipCoord);
        }


        return true;
    }
    public static boolean isAdjacent(String[][] playField, int[] numericalCoord) {
        int x1 = numericalCoord[0], y1 = numericalCoord[1];
        int x2 = numericalCoord[2], y2 = numericalCoord[3];

        int xStart = Math.min(x1, x2), xEnd = Math.max(x1, x2);
        int yStart = Math.min(y1, y2), yEnd = Math.max(y1, y2);

        for (int i = xStart - 1; i <= xEnd + 1; i++) {
            for (int j = yStart - 1; j <= yEnd + 1; j++) {
                if (i > 0 && i < 11 && j > 0 && j < 11) { // Make sure it's within bounds
                    if (!playField[i][j].equals("~")) {
                        return true; // Adjacent to another ship
                    }
                }
            }
        }

        return false; // No adjacent ships
    }

    public static boolean isOverlapping(String[][] playField, int[] numericalCoord) {
        int x1 = numericalCoord[0], y1 = numericalCoord[1];
        int x2 = numericalCoord[2], y2 = numericalCoord[3];

        if (x1 == x2) { // Horizontal ship
            for (int i = Math.min(y1, y2); i <= Math.max(y1, y2); i++) {
                if (!playField[x1][i].equals("~")) {
                    return true; // Overlapping
                } else {
                    playField[x1][i] = "O";
                }
            }
        } else { // Vertical ship
            for (int i = Math.min(x1, x2); i <= Math.max(x1, x2); i++) {
                if (!playField[i][y1].equals("~")) {
                    return true; // Overlapping
                } else {
                    playField[i][y1] = "O";
                }
            }
        }

        return false; // No overlapping
    }

    public static int[] getNumericCoord(String[] shipCoord) {
        int[] newCoord = new int[4];
        int index = 0;
        for(int i = 0; i < shipCoord.length; i++) {
            String x = shipCoord[i].substring(0, 1);
            int y = Integer.parseInt(shipCoord[i].substring(1));
            newCoord[index] = (int) x.charAt(0) - 64;
            newCoord[index+1] = y;
                index += 2;
        }
        if(shipCoord[0].charAt(0) == shipCoord[1].charAt(0)) {
            HORIZONTAL = true;
        } else {
            HORIZONTAL = false;
        }
        return newCoord;
    }
    public static void printField(String[][] playField){
        for(int i = 0; i < playField.length; i++){
            for (int j = 0; j < playField[0].length; j++) {
                System.out.print(playField[i][j] + " ");
            }
            System.out.println();
        }
    }
    public static String[][] createPlayField(String[][] playField){
        int alpha = 65;
        for(int i = 0; i < playField.length; i++){
            for (int j = 0; j < playField[0].length; j++){
                if(i == 0 && j == 0) {
                    playField[i][j] = " ";
                } else if(i == 0 && j != 0) {
                    playField[i][j] = String.valueOf(j);
                } else if(j == 0 && i != 0) {
                    playField[i][j] = String.valueOf((char)alpha++);
                }else {
                    playField[i][j] = "~";
                }
            }
        }
        return playField;
    }
}
