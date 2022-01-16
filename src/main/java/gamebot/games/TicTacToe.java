package gamebot.games;

public class TicTacToe {
    private char[][] board;
    private int status;

    public static final char PLAYER_ONE = 'X';
    public static final char PLAYER_TWO = 'O';
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int ONE_WINS = 4;
    public static final int TWO_WINS = 5;
    public static final int TIE = 3;
    public static final char EMPTY = ' ';

    public TicTacToe() {
        // Creates the board and fills it with empty spots
        board = new char[3][3];

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c] = EMPTY;
            }
        }

        // Sets the initial status;
        status = ONE;
    }

    // Gets the board and status
    public char[][] getBoard() {
        return board;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    // Places a piece on the board
    public boolean placePiece(int row, int col) {
        // Create variables
        boolean placeSuccessful = false;
        char currentSpot;

        // Decreases the row and col by one
        // row--;
        // col--;

        if (row < 3 && col < 3) {
            // Goes to the location and checks if the spot is empty
            currentSpot = board[row][col];

            if (currentSpot == EMPTY) {
                // Checks the current turn and places their piece
                if (status == ONE)
                    board[row][col] = PLAYER_ONE;
                else if (status == TWO)
                    board[row][col] = PLAYER_TWO;

                placeSuccessful = true;
            }
        }
        return placeSuccessful;
    }

    // Changes turns
    public void changeTurns() {
        if (status == ONE)
            status = TWO;

        else if (status == TWO)
            status = ONE;
    }

    // Checks if there's a tie
    public void checkTie() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                // If there is still an empty spot, return
                if (board[r][c] == EMPTY) {
                    return;
                }

            }
        }

        // The board is full, but if there is already a winner, return
        if (status == ONE_WINS || status == TWO_WINS) {
            return;
        }

        // No empty space found, and no winner yet so there is a tie
        status = TIE;
    }

    // Checks if there's a winner
    public void winner(int player) {
        // Creates variables
        boolean match = false;

        // Checks for horizontal matches
        for (int r = 0; r < 3; r++) {
            match = horizontal(r, player);

            if (match)
                break;
        }

        // Checks for vertical matches
        if (!match) {
            for (int c = 0; c < 3; c++) {
                match = vertical(c, player);

                if (match)
                    break;
            }
        }

        // Checks for diagonal matches
        if (!match) {
            match = diagonal(player);
        }

        // If a match was found change status
        if (match) {
            status = player + 3;
        }
    }

    // Checks if the player won horizontally
    public boolean horizontal(int row, int player) {
        // Creates variables
        boolean match = false;
        char currentPlayer, currentLocation;
        char[][] board = getBoard();
        // row--;

        // Sets which player to check for
        if (player == 1)
            currentPlayer = PLAYER_ONE;
        else
            currentPlayer = PLAYER_TWO;

        // Loops and checks each spot in the row for the player
        for (int c = 0; c < 3; c++) {
            currentLocation = board[row][c];

            if (currentLocation == currentPlayer)
                match = true;
            else {
                match = false;
                break;
            }
        }

        return match;
    }

    public boolean vertical(int col, int player) {
        // Creates variables
        boolean match = false;
        char currentPlayer, currentLocation;
        char[][] board = getBoard();
        // col--;

        // Sets which player to check for
        if (player == 1)
            currentPlayer = PLAYER_ONE;
        else
            currentPlayer = PLAYER_TWO;

        // Loops and checks each spot in the row for the player
        for (int r = 0; r < 3; r++) {
            currentLocation = board[r][col];

            if (currentLocation == currentPlayer)
                match = true;
            else {
                match = false;
                break;
            }
        }

        return match;
    }

    // Checks for left and right diagonal
    public boolean diagonal(int player) {
        // Creates variables
        boolean match = false;
        char currentPlayer;
        char[][] board = getBoard();
        char[] leftMatch = new char[3];
        char[] rightMatch = new char[3];

        // Sets which player to check for
        if (player == 1)
            currentPlayer = PLAYER_ONE;
        else
            currentPlayer = PLAYER_TWO;

        // Left Diagonal
        leftMatch[0] = board[0][0];
        leftMatch[1] = board[1][1];
        leftMatch[2] = board[2][2];

        // Right Diagonal
        rightMatch[0] = board[0][2];
        rightMatch[1] = board[1][1];
        rightMatch[2] = board[2][0];

        // Loops through left array
        for (int i = 0; i < 3; i++) {
            if (leftMatch[i] == currentPlayer) {
                match = true;
            }
            else {
                match = false;
                break;
            }
        }

        if (!match) {
            // Loops through right array
            for (int i = 0; i < 3; i++) {
                if (rightMatch[i] == currentPlayer)
                    match = true;
                else {
                    match = false;
                    break;
                }
            }
        }

        return match;
    }

    public String printBoard() {
        String boardOutput = "";
        //String markdownFormat = "**";
        char[][] board = getBoard();
        boardOutput += "\n";
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r][c] == EMPTY) {
                    boardOutput += ":black_large_square: ";
                }
                else {
                    //				boardOutput += " " + markdownFormat + Character.toString(board[r][c]) + markdownFormat;
                    boardOutput += board[r][c] == PLAYER_ONE ? ":x: " : ":o: ";
                }
            }
            boardOutput += "\n";
        }
        //boardOutput += "-------------";

        return boardOutput;
    }
}
