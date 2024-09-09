package battleship;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[][] playField = new char[11][11];
        playField = createPlayField(playField);
        printField(playField);
        System.out.println("Enter the coordinates of the ship:");
        String shipCoord = sc.nextLine();
        String[] coOrdinate = shipCoord.split(" ");
        checkCoord(coOrdinate);
        getNumericCoord(shipCoord);
    }

    public static void checkCoord(String[] shipCoord) {
        if(shipCoord[0].charAt(0) != shipCoord[1].charAt(0) &&
        shipCoord[0].substring(1) != shipCoord[1].substring(1)) {
            System.out.println("Error!");
        }
        for(String s: shipCoord) {
            if((int) s.charAt(0) < 48 || (int) s.charAt(0) > 57) {
                System.out.println("Error!");
            }
            if(Integer.parseInt(s.substring(1)) > 10) {
                System.out.println("Error!");
            }
        }
    }
    public static void getNumericCoord(String shipCoord) {
        shipCoord = shipCoord.replace(" ","");
        int[] newCoord = new int[4];
        for(int i = 0; i < shipCoord.length(); i++) {
            int a = (int) shipCoord.charAt(i);
            if(a < 48 || a > 57) {
                newCoord[i] = a - 64;
            } else {
                newCoord[i] = a - 48;
            }
        }
    }
    public static void printField(char[][] playField){
        for(int i = 0; i < playField.length; i++){
            for (int j = 0; j < playField[0].length; j++) {
                System.out.print(playField[i][j] + " ");
            }
            System.out.println();
        }
    }
    public static char[][] createPlayField(char[][] playField){
        int alpha = 65;
        for(int i = 0; i < playField.length; i++){
            for (int j = 0; j < playField[0].length; j++){
                if(i == 0 && j == 0) {
                    playField[i][j] = ' ';
                } else if(i == 0 && j != 0) {
                    playField[i][j] = Character.forDigit(j, 10);
                } else if(j == 0 && i != 0) {
                    playField[i][j] = (char) alpha++;
                }else {
                    playField[i][j] = '~';
                }
            }
        }
        return playField;
    }
}
