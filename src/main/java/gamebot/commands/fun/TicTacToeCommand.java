package gamebot.commands.fun;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import gamebot.games.TTTStateMachine;
import gamebot.games.TicTacToe;

import net.dv8tion.jda.api.entities.User;

public class TicTacToeCommand extends Command {

    private static User player1;
    private static User player2;
    private TicTacToe game;

    public TicTacToeCommand() {
        this.name = "tictactoe";
        this.aliases = new String[] { "ttt" };
        this.help = "Play Tic Tac Toe against your friends! Command Syntax: \"!tictactoe @user\".";
        this.category = new Category("Fun");

    }

    @Override
    protected void execute(CommandEvent event) {
        game = new TicTacToe();

        player1 = event.getMessage().getAuthor();

        try {
            player2 = event.getMessage().getMentionedUsers().get(0);
        }
        catch (Exception e) {
            event.reply("ERROR: Player Not Found!");
        }

        if (player2 != null) {

            event.reply("<@" + player1.getId() + ">, it's your turn!");
            event.reply("Type out the row (`a`, `b`, `c`), then column (`1`, `2`, `3`) in the format `r,c`:");
            event.reply(game.printBoard());
            event.getJDA().addEventListener(new TTTStateMachine(player1, player2, game));

        }
    }
}