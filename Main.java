

import java.lang.Math;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        player player1 = new player(1);
        player player2 = new player(2);
        battleShipBoardClass.makeBattleShipBoard(battleShipBoardClass.battleShipBoardPlayer1);
        battleShipBoardClass.makeBattleShipBoard(battleShipBoardClass.fogOfWarPlayer1);
        battleShipBoardClass.makeBattleShipBoard(battleShipBoardClass.battleShipBoardPlayer2);
        battleShipBoardClass.makeBattleShipBoard(battleShipBoardClass.fogOfWarPlayer2);
        battleShipBoardClass.printBattleShipBoard(battleShipBoardClass.battleShipBoardPlayer1);
        battleShipShips aircraftCarrierP1 = new battleShipShips("Aircraft Carrier", 5, 1);
        battleShipShips battleshipP1 = new battleShipShips("Battleship", 4, 1);
        battleShipShips submarineP1 = new battleShipShips("Submarine", 3, 1);
        battleShipShips cruiserP1 = new battleShipShips("Cruiser ", 3, 1);
        battleShipShips destroyerP1 = new battleShipShips("Destroyer", 2, 1);
        aircraftCarrierP1.placeTheShip();
        battleshipP1.placeTheShip();
        submarineP1.placeTheShip();
        cruiserP1.placeTheShip();
        destroyerP1.placeTheShip();
        //player one has now placed all of their ships
        battleShipBoardClass.changePlayersTurn(0);
        battleShipShips aircraftCarrierP2 = new battleShipShips("Aircraft Carrier", 5, 2);
        battleShipShips battleshipP2 = new battleShipShips("Battleship", 4, 2);
        battleShipShips submarineP2 = new battleShipShips("Submarine", 3, 2);
        battleShipShips cruiserP2 = new battleShipShips("Cruiser ", 3, 2);
        battleShipShips destroyerP2 = new battleShipShips("Destroyer", 2, 2);
        aircraftCarrierP2.placeTheShip();
        battleshipP2.placeTheShip();
        submarineP2.placeTheShip();
        cruiserP2.placeTheShip();
        destroyerP2.placeTheShip();
        //player two has now placed all of their ships
        battleShipBoardClass.changePlayersTurn(1);
        //Set while look to end when a player no longer has any ships so the game will end.
        while ((battleShipBoardClass.player1ShipQuantity != 0) && (battleShipBoardClass.player2ShipQuantity != 0)) {
            boolean didShotHit;
            //if the shot hit a ship then checking all the ships to see if they sunk. if a ship sinks reduces the players ship count by 1.
            didShotHit = player1.takeShot();
            if (didShotHit) {
                aircraftCarrierP2.checkIfShipIsSunk();
                battleshipP2.checkIfShipIsSunk();
                submarineP2.checkIfShipIsSunk();
                cruiserP2.checkIfShipIsSunk();
                destroyerP2.checkIfShipIsSunk();
                player1.checkIfWon();
            }
            battleShipBoardClass.changePlayersTurn(1);
            didShotHit = player2.takeShot();
            if (didShotHit) {
                aircraftCarrierP1.checkIfShipIsSunk();
                battleshipP1.checkIfShipIsSunk();
                submarineP1.checkIfShipIsSunk();
                cruiserP1.checkIfShipIsSunk();
                destroyerP1.checkIfShipIsSunk();
                player2.checkIfWon();
            }
            battleShipBoardClass.changePlayersTurn(2);
        }
    }


}

class battleShipBoardClass {
    public static char[][] battleShipBoardPlayer1 = new char[10][10];
    public static char[][] battleShipBoardPlayer2 = new char[10][10];
    public static char[][] fogOfWarPlayer1 = new char[10][10];
    public static char[][] fogOfWarPlayer2 = new char[10][10];
    public static int player1ShipQuantity;
    public static int player2ShipQuantity;


    public static void makeBattleShipBoard(char[][] battleShipBoard) {
        for (char[] chars : battleShipBoard) {
            Arrays.fill(chars, '~');
        }
    }

    public static void printBattleShipBoard(char[][] battleShipBoard) {
        char boardLetters = 'A';
        String boardNumbers = "  1 2 3 4 5 6 7 8 9 10";
        System.out.println(boardNumbers);
        for (char[] chars : battleShipBoard) {
            System.out.print(boardLetters + " ");
            for (char aChar : chars) {
                System.out.print(aChar + " ");
            }
            boardLetters++;
            System.out.println();
        }
    }

    public static void changePlayersTurn(int playerNumber) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Press Enter and pass the move to another player");
        //using scanner.nextLine to let the user press enter to clear the screen for the next player.
        scanner.nextLine();
        for (int i = 0; i < 90; i++) {
            System.out.println();
        }

        //using player 0 for the first turn.
        if (playerNumber == 0) {
            System.out.println("Player 2, place your ships to the game field");
            printBattleShipBoard(battleShipBoardPlayer2);
        } else if (playerNumber == 2) {
            System.out.println("Player 2 Press Enter to start your turn");
            scanner.nextLine();
            printBattleShipBoard(fogOfWarPlayer1);
            System.out.println("---------------------");
            printBattleShipBoard(battleShipBoardPlayer1);
            System.out.println("Player 1, it's your turn:");
        } else if (playerNumber == 1) {
            System.out.println("Player 1 Press Enter to start your turn");
            scanner.nextLine();
            printBattleShipBoard(fogOfWarPlayer2);
            System.out.println("---------------------");
            printBattleShipBoard(battleShipBoardPlayer2);
            System.out.println("Player 2, it's your turn:");
        }
    }

}

class battleShipShips extends battleShipBoardClass {
    int size;
    int playerNumber;
    String name;
    int[] shipCoordinates;
    boolean isAlive = true;

    public battleShipShips(String name, int size, int playerNumber) {
        this.size = size;
        this.name = name;
        this.playerNumber = playerNumber;
        if (this.playerNumber == 1) {
            battleShipBoardClass.player1ShipQuantity++;
        } else if (this.playerNumber == 2) {
            battleShipBoardClass.player2ShipQuantity++;
        }
    }

    battleShipShips() {
    }

    public void placeTheShip() {
        char[][] battleShipBoard;
        if (this.playerNumber == 1) {
            battleShipBoard = battleShipBoardPlayer1;
        } else {
            battleShipBoard = battleShipBoardPlayer2;
        }
        // If any of the conditions return true the loop repeats itself.
        boolean repeatLoop = true;
        while (repeatLoop) {
            this.getCoordinates();
            repeatLoop = this.checkIfCoordinatesWork();
            if (!repeatLoop) {
                repeatLoop = this.checkIfThereAreOtherShips();
            }
        }
        this.addToBoard();
        printBattleShipBoard(battleShipBoard);

    }

    private void getCoordinates() {
        System.out.println("Enter the coordinates of the " + this.name + " (" + this.size + " cells):");
        Scanner scanner = new Scanner(System.in);
        String coordinates = scanner.nextLine();
        coordinates = coordinates.toUpperCase();
        int[] fullCoordinates = new int[4];
        boolean doTheCoordinatesFail = true;
        while (doTheCoordinatesFail) {
            // Checking if the User enters valid coordinates by lowering the size of the string it makes to all possible sizes.
            // Using a try catch block in case the user enters a char where an int should be which would cause an exception.
            try {
                if (coordinates.length() < 5 || coordinates.length() > 7) {
                    System.out.println("Error! Please enter in the correct format. example: J7 J10");
                    coordinates = scanner.nextLine();
                    coordinates = coordinates.toUpperCase(); //converting to uppercase because I turn char to int later on and want consistency.
                } else {
                    //splitting the String into two different strings for each coordinate.
                    String[] coordinateArray;
                    coordinateArray = coordinates.split(" ");
                    String tempCoord1 = coordinateArray[0];
                    String tempCoord2 = coordinateArray[1];
                    //splitting both of those into their x and y coordinates.
                    int coord1A = tempCoord1.charAt(0);
                    int coord1B = Integer.parseInt((tempCoord1.substring(1)));
                    int coord2A = tempCoord2.charAt(0);
                    int coord2B = Integer.parseInt((tempCoord2.substring(1)));
                    //converted from char to int A = 65 , B= 66, . . . . . . J = 74
                    //subtracting by 65 in order of A to represent index 0 on the ones converted by chars.
                    //subtracting the others by 1 in order for the user input to represent the index number.
                    fullCoordinates[0] = coord1A - 65;
                    fullCoordinates[1] = coord1B - 1;
                    fullCoordinates[2] = coord2A - 65;
                    fullCoordinates[3] = coord2B - 1;
                    doTheCoordinatesFail = false;
                    //Saving the array containing coordinates into the object in order to use later.
                    this.shipCoordinates = fullCoordinates;
                }
            } catch (Exception e) {
                //I set the coordinates = nothing, so it meets the requirements of the if then statement so the user can put another coordinate.
                coordinates = "";
            }
        }
    }

    private boolean checkIfCoordinatesWork() {
        //Checking if the user inputs a ship going either horizontal or vertical.
        if (this.shipCoordinates[0] != this.shipCoordinates[2] && this.shipCoordinates[1] != this.shipCoordinates[3]) {
            System.out.println("Error! Wrong ship location! Try again:");
            return true;
            //Checking if user places ship out of bounds.
        } else if (this.shipCoordinates[0] > 9 || this.shipCoordinates[1] > 9 || this.shipCoordinates[2] > 9 || this.shipCoordinates[3] > 9) {
            System.out.println("Error! Wrong ship location! Try again:");
            return true;
        } else if (this.shipCoordinates[0] < 0 || this.shipCoordinates[1] < 0 || this.shipCoordinates[2] < 0 || this.shipCoordinates[3] < 0) {
            System.out.println("Error! Wrong ship location! Try again:");
            return true;
        }
        int size;
        //checking if size is equal to the correct size of ship.
        if (shipCoordinates[0] == shipCoordinates[2]) {
            size = Math.abs(shipCoordinates[1] - shipCoordinates[3]) + 1;
        } else {
            size = Math.abs(shipCoordinates[0] - shipCoordinates[2]) + 1;
        }
        if (size != this.size) {
            System.out.println("Error! Wrong length of the " + this.name + " Try again:");
            return true;
        }
        return false;

    }

    private boolean checkIfThereAreOtherShips() {
        char[][] battleShipBoard;
        if (this.playerNumber == 1) {
            battleShipBoard = battleShipBoardPlayer1;
        } else {
            battleShipBoard = battleShipBoardPlayer2;
        }

        int minX = Math.min(this.shipCoordinates[0], this.shipCoordinates[2]);
        int minY = Math.min(this.shipCoordinates[1], this.shipCoordinates[3]);
        int loopVar = 0;
        while (this.size > loopVar) {
            //Checking if ship overlaps another ship.
            if (this.shipCoordinates[0] == this.shipCoordinates[2]) {
                if (battleShipBoard[minX][minY + loopVar] == 'O') {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return true;
                }
                //checking if anything to the right of the ship
                if (minY + loopVar < 9) {
                    if (battleShipBoard[minX][minY + loopVar + 1] == 'O') {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        return true;
                    }
                }
                //checking if anything below the ship
                if (minX < 9) {
                    if (battleShipBoard[minX + 1][minY + loopVar] == 'O') {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        return true;
                    }
                }
                //checking if anything to the left.
                if (minY + loopVar > 0) {
                    if (battleShipBoard[minX][minY + loopVar - 1] == 'O') {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        return true;
                    }
                }
                //checking if anything above.
                if (minX > 0) {
                    if (battleShipBoard[minX - 1][minY + loopVar] == 'O') {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        return true;
                    }
                }

            }
            if (this.shipCoordinates[1] == this.shipCoordinates[3]) {
                //Checking if ship overlaps another ship.
                if (battleShipBoard[minX + loopVar][minY] == 'O') {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return true;
                }
                //checking if anything to the right.
                if (minY < 9) {
                    if (battleShipBoard[minX + loopVar][minY + 1] == 'O') {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        return true;
                    }
                }
                //checking if anything below.
                if (minX + loopVar < 9) {
                    if (battleShipBoard[minX + loopVar + 1][minY] == 'O') {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        return true;
                    }
                }
                //checking if anything to the left.
                if (minY > 0) {
                    if (battleShipBoard[minX + loopVar][minY - 1] == 'O') {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        return true;
                    }
                }
                //checking if anything above.
                if (minX + loopVar > 0) {
                    if (battleShipBoard[minX + loopVar - 1][minY] == 'O') {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        return true;
                    }
                }
            }
            loopVar++;
        }
        return false;
    }

    public void addToBoard() {
        char[][] battleShipBoard;
        if (this.playerNumber == 1) {
            battleShipBoard = battleShipBoardPlayer1;
        } else {
            battleShipBoard = battleShipBoardPlayer2;
        }
        //getting the coordinates and adding them into the board using the ship size.
        int loopVar = 0;
        int minX = Math.min(this.shipCoordinates[0], this.shipCoordinates[2]);
        int minY = Math.min(this.shipCoordinates[1], this.shipCoordinates[3]);
        while (this.size > loopVar) {
            if (this.shipCoordinates[0] == this.shipCoordinates[2]) {
                battleShipBoard[minX][minY + loopVar] = 'O';
                loopVar++;
            } else if (this.shipCoordinates[1] == this.shipCoordinates[3]) {
                battleShipBoard[minX + loopVar][minY] = 'O';
                loopVar++;
            }
        }
    }

    public void checkIfShipIsSunk() {
        char[][] battleshipBoard;
        if (this.playerNumber == 1) {
            battleshipBoard = battleShipBoardPlayer1;
        } else {
            battleshipBoard = battleShipBoardPlayer2;
        }
        int health = this.size;
        int loopVar = 0;
        int minX = Math.min(this.shipCoordinates[0], this.shipCoordinates[2]);
        int minY = Math.min(this.shipCoordinates[1], this.shipCoordinates[3]);
        //if the ship is alive will check if the ship has any Os left if they are all replaced as an X then the ship will be marked not alive
        if (this.isAlive) {
            while (this.size > loopVar) {
                if (this.shipCoordinates[0] == this.shipCoordinates[2]) {
                    if (battleshipBoard[minX][minY + loopVar] == 'X') {
                        health = health - 1;
                    }
                    loopVar++;
                } else if (this.shipCoordinates[1] == this.shipCoordinates[3]) {
                    if (battleshipBoard[minX + loopVar][minY] == 'X') {
                        health = health - 1;
                    }
                    loopVar++;
                }
                if (health == 0) {
                    //if the ship is sunk reduces the ship quantity of the player whose ship it belongs to
                    if (this.playerNumber == 1) {
                        player1ShipQuantity = player1ShipQuantity - 1;
                    } else {
                        player2ShipQuantity = player2ShipQuantity - 1;
                    }
                    this.isAlive = false;

                }
            }

        }

    }
}

class player extends battleShipShips {
    int playerNumber;
    int[] currentShot;
    int shipsNotDestroyed = 5;

    public player(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public boolean takeShot() {
        char[][] battleshipBoard;
        char[][] fogOfWar;
        if (this.playerNumber == 1) {
            battleshipBoard = battleShipBoardPlayer2;
            fogOfWar = fogOfWarPlayer1;
        } else {
            battleshipBoard = battleShipBoardPlayer1;
            fogOfWar = fogOfWarPlayer2;
        }
        boolean didShotHit = false;
        //printBattleShipBoard(fogOfWar);
        System.out.println("Take a shot!");
        this.checkIfShotIsValid();
        boolean looper = true;
        while (looper) {
            if (battleshipBoard[this.currentShot[0]][this.currentShot[1]] == '~' || battleshipBoard[this.currentShot[0]][this.currentShot[1]] == 'M') {
                battleshipBoard[this.currentShot[0]][this.currentShot[1]] = 'M';
                fogOfWar[this.currentShot[0]][this.currentShot[1]] = 'M';
                //printBattleShipBoard(fogOfWar);
                System.out.println("You missed!");
                looper = false;
            } else if (battleshipBoard[this.currentShot[0]][this.currentShot[1]] == 'O' || battleshipBoard[this.currentShot[0]][this.currentShot[1]] == 'X') {
                battleshipBoard[this.currentShot[0]][this.currentShot[1]] = 'X';
                fogOfWar[this.currentShot[0]][this.currentShot[1]] = 'X';
                //printBattleShipBoard(fogOfWar);
                didShotHit = true;
                looper = false;
            } else {
                System.out.println("You have already shot here try again");
            }
        }
        return didShotHit;
    }

    public void checkIfShotIsValid() {
        Scanner scanner = new Scanner(System.in);
        String coordinates = scanner.nextLine();
        coordinates = coordinates.toUpperCase();
        int[] fullCoordinates = new int[2];
        boolean doTheCoordinatesFail = true;
        while (doTheCoordinatesFail) {
            // Checking if the User enters valid coordinates by lowering the size of the string it makes to all possible sizes.
            // Using a try catch block in case the user enters a char where an int should be which would cause an exception.
            try {
                if (coordinates.length() < 2 || coordinates.length() > 3) {
                    System.out.println("Error! Please enter in the correct format. example: J7");
                    coordinates = scanner.nextLine();
                    coordinates = coordinates.toUpperCase(); //converting to uppercase because I turn char to int later on and want consistency.
                } else {
                    //splitting into their x and y coordinates.
                    int coordA = coordinates.charAt(0);
                    int coordB = Integer.parseInt((coordinates.substring(1)));
                    //converted from char to int A = 65 , B= 66, . . . . . . J = 74
                    //subtracting by 65 in order of A to represent index 0 on the ones converted by chars.
                    //subtracting the others by 1 in order for the user input to represent the index number.
                    fullCoordinates[0] = coordA - 65;
                    fullCoordinates[1] = coordB - 1;
                    //Saving the array containing coordinates into the object in order to use later.
                    doTheCoordinatesFail = false;
                    if (fullCoordinates[0] > 9 || fullCoordinates[0] < 0 || fullCoordinates[1] > 9 || fullCoordinates[1] < 0) {
                        System.out.println("Error! You entered the wrong coordinates! Try again:");
                        doTheCoordinatesFail = true;
                        coordinates = scanner.nextLine();
                    }
                }
            } catch (Exception e) {
                //I set the coordinates = nothing, so it meets the requirements of the if then statement so the user can put another coordinate.
                coordinates = "";
            }
        }
        this.currentShot = fullCoordinates;
    }

    public void checkIfWon() {
        int shipQuantity = 0;
        if (this.playerNumber == 1) {
            shipQuantity = player2ShipQuantity;
        } else if (this.playerNumber == 2) {
            shipQuantity = player1ShipQuantity;
        }
        //if the ship quantity of the player was reduced when the ships were checked earlier then will send a message and changed the ships that player destroyed by 1.
        if (this.shipsNotDestroyed == shipQuantity) {
            System.out.println("You hit a ship! Try again:");
        } else {
            this.shipsNotDestroyed = this.shipsNotDestroyed - 1;
            if (this.shipsNotDestroyed == 0) {
                System.out.println("You sank the last ship. You won. Congratulations!");
            } else {
                System.out.println("You sank a ship! Specify a new target:");
            }
        }

    }
}