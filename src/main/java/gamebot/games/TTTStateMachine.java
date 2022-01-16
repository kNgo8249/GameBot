package gamebot.games;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TTTStateMachine extends ListenerAdapter {
    private final long playerOneID, playerTwoID;
    private TicTacToe game;
    private boolean gameOn;

    public TTTStateMachine(User playerOne, User playerTwo, TicTacToe game) {
        this.playerOneID = playerOne.getIdLong();
        this.playerTwoID = playerTwo.getIdLong();
        this.game = game;
        gameOn = true;
    }

    public boolean isGameOn() {
        return gameOn;
    }

    public void setGameOn(boolean gameOn) {
        this.gameOn = gameOn;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
        String content = event.getMessage().getContentRaw();

        // Gets the user input
        if (event.getAuthor().getIdLong() == playerOneID && content.length() == 3
                && game.getStatus() == TicTacToe.ONE) {

            // Gets the row and column
            char rowC = content.charAt(0);
            char colC = content.charAt(2);

            int row;
            if(rowC == 'a') row = 0;
            else if(rowC == 'b') row = 1;
            else row = 2;

            int col = Integer.parseInt(String.valueOf(colC));
            col--;


            game.placePiece(row, col);
            game.winner(1);
            game.checkTie();

            if (game.getStatus() != TicTacToe.ONE_WINS && game.getStatus() != TicTacToe.TIE) {
                channel.sendMessage(game.printBoard()).queue();
                game.changeTurns();
                channel.sendMessage("<@" + playerTwoID + ">, it's your turn!").queue();
                channel.sendMessage("Type out the row (`a`, `b`, `c`), then column (`1`, `2`, `3`) in the format `r,c`:").queue();

            }
        }

        if (event.getAuthor().getIdLong() == playerTwoID && content.length() == 3
                && game.getStatus() == TicTacToe.TWO) {
            // Gets the row and column
            char rowC = content.charAt(0);
            char colC = content.charAt(2);

            int row;
            if(rowC == 'a') row = 0;
            else if(rowC == 'b') row = 1;
            else row = 2;

            int col = Integer.parseInt(String.valueOf(colC));
            col--;

            game.placePiece(row, col);
            game.winner(2);
            game.checkTie();

            if (game.getStatus() != TicTacToe.TWO_WINS && game.getStatus() != TicTacToe.TIE) {
                channel.sendMessage(game.printBoard()).queue();
                game.changeTurns();
                channel.sendMessage("<@" + playerOneID + ">, it's your turn!").queue();
                channel.sendMessage("\"Type out the row (`a`, `b`, `c`), then column (`1`, `2`, `3`) in the format `r,c`:").queue();

            }
        }

        // Ends the game
        if (game.getStatus() == TicTacToe.ONE_WINS || game.getStatus() == TicTacToe.TWO_WINS
                || game.getStatus() == TicTacToe.TIE) {
            channel.sendMessage(game.printBoard());
            String winner;
            if(TicTacToe.ONE_WINS == game.getStatus()) {
                winner = "<@" + playerOneID + "> wins!";
            }
            else if(TicTacToe.TWO_WINS == game.getStatus()) {
                winner = "<@" + playerTwoID + "> wins!";
            }
            else {
                winner = "It's a tie! No one wins!";
            }
            winner += "\n";
            channel.sendMessage(winner).queue();
            channel.sendMessage(game.printBoard()).queue();
            event.getJDA().removeEventListener(this);
            setGameOn(false);
        }

    }
}
