package battleship;

import java.util.Scanner;

public class Main {
    public static boolean HORIZONTAL = false;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[][] playField = new String[11][11];
        playField = createPlayField(playField);
        printField(playField);
        System.out.println("Enter the coordinates of the ship:");
        String shipCoord = sc.nextLine();
        String[] coOrdinate = shipCoord.split(" ");

        boolean isCorrect =  checkCoord(coOrdinate);
        if(isCorrect) {
            int[] numericalCoord = getNumericCoord(coOrdinate);
            getLengthAndParts(coOrdinate);
        }
    }

    public static void getLengthAndParts(String[] shipCoord) {
        String end = shipCoord[1];
        String cord = new String();
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
        System.out.println("length: " + len);
        System.out.println("Parts: " + str.toString());
    }

    public static boolean checkCoord(String[] shipCoord) {
        if(shipCoord[0].charAt(0) != shipCoord[1].charAt(0) &&
        shipCoord[0].charAt(1) != shipCoord[1].charAt(1)) {
            System.out.println("Error!");
            return false;
        }
            if((int) shipCoord[0].charAt(0) < 65 || (int) shipCoord[1].charAt(0) > 74) {
                System.out.println("Error!");
                return false;
            }
            for(String s: shipCoord)
            if(Integer.parseInt(s.substring(1)) > 10 || Integer.parseInt(s.substring(1)) < 1) {
                System.out.println("Error!");
                return false;
            }
        return true;
    }
    public static int[] getNumericCoord(String[] shipCoord) {
        int[] newCoord = new int[4];
        for(int i = 0; i < shipCoord.length; i++) {
            String x = shipCoord[i].substring(0, 1);
            int y = Integer.parseInt(shipCoord[i].substring(1));
            if(y < 1 || y > 10) {
                newCoord[i] = y;
            } else {
                newCoord[i] = (int) x.charAt(0);
            }
        }
        if(shipCoord[0].charAt(0) == shipCoord[1].charAt(0)) {
            HORIZONTAL = true;
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
