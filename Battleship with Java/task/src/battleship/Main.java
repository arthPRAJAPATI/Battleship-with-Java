package battleship;

import java.util.Scanner;
/* TODO: code review changes
    Update HORIZONTAL to be a local variable instead of a class-level variable to avoid unintended side effects between method calls.
    Initialize playField directly using the method call createPlayField.
    In the getLengthAndParts method:
    Remove commented-out code.
    Modify the checkCoord method:
    Correct the first while loop condition by using && instead of ||.
    Correct logical checks in checkCoord to avoid infinite loops and ensure errors are detected correctly:
    Change the initial while checks to if conditions as they do not need to loop continuously.
    Replace repetitive calls to getCoord with break statements after updating coordinates.
    Ensure playField is updated correctly in isOverlapping without modifying it during validation.
    In the getNumericCoord method:
    Validate input to handle potential parsing issues.
    Move coordinate orientation detection logic after parsing both coordinates.
    Ensure checkCoord properly adds ships to playField after all validations pass.
    Add missing ship placement logic inside the checkCoord method to update playField.
    In printField, ensure proper formatting and eliminate any potential index out of bounds issues.
    */
public class Main {
    public static boolean HORIZONTAL = false;
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {

        String[][] playField = new String[11][11];
        String[][] fogField = new String[11][11];
        String[] vessels = new String[]{"Aircraft Carrier", "Battleship", "Submarine", "Cruiser", "Destroyer"};
        int[] vesselLength = new int[]{5,4,3,3,2};
        StringBuilder wrondCoord = new StringBuilder();
        playField = createPlayField(playField);
        fogField = createPlayField(fogField);
        printField(playField);
        for(int i = 0; i < vessels.length;i++) {
            String[] coOrdinate = getCoord("Enter the coordinates of the "+vessels[i]+" ("+ vesselLength[i] +" cells):");
            boolean isCorrect =  checkCoord(coOrdinate, vessels[i], vesselLength[i], playField);
            if(isCorrect) {
                printField(playField);
            }
        }
        System.out.println("The game starts!");
        printField(fogField);
        takeShot(playField, fogField);
    }

    public static void takeShot(String[][] playField, String[][] fogField){
        System.out.println("Take a shot!");
        String shot = sc.nextLine();
        int x = (int) shot.charAt(0) - 64;
        int y = Character.getNumericValue(shot.charAt(1));
        while(x > 10 || x < 0 || y > 10 || y < 0 || (shot.length() > 2 && Character.valueOf(shot.charAt(shot.length() - 1)) > 0)) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            System.out.println("Take a shot!");
            shot = sc.nextLine();
            x = (int) shot.charAt(0) - 64;
            y = Character.getNumericValue(shot.charAt(1));
        }
            String value = playField[x][y];
            if ("~".equals(value)) {
                playField[(int) shot.charAt(0) - 64][Character.getNumericValue(shot.charAt(1))] = "M";
                fogField[(int) shot.charAt(0) - 64][Character.getNumericValue(shot.charAt(1))] = "M";
                printField(fogField);
                System.out.println("You missed!");
                printField(playField);
            } else if ("O".equals(value)) {
                playField[(int) shot.charAt(0) - 64][Character.getNumericValue(shot.charAt(1))] = "X";
                fogField[(int) shot.charAt(0) - 64][Character.getNumericValue(shot.charAt(1))] = "X";
                printField(fogField);
                System.out.println("You hit a ship!");
                printField(playField);
            }


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
