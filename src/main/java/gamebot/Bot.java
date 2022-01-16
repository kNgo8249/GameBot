package gamebot;

import com.jagrosh.jdautilities.command.CommandClientBuilder;

import gamebot.commands.fun.*;
import gamebot.commands.moderation.*;
import gamebot.commands.music.*;
import gamebot.commands.restricted.*;

import io.github.cdimascio.dotenv.Dotenv;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Bot {

    public static void main(String[] args) throws Exception {

        Dotenv dotenv = Dotenv.load();

        JDA game = JDABuilder.createDefault(dotenv.get("TOKEN")).build();

        CommandClientBuilder client = new CommandClientBuilder();

        client.setOwnerId(dotenv.get("OWNER_ID"));
        client.setCoOwnerIds(dotenv.get("CO_OWNER_1"), dotenv.get("CO_OWNER_2"), dotenv.get("CO_OWNER_3"));

        client.setPrefix("!");

        client.addCommands(new PingCommand(), new TicTacToeCommand(), new CoinFlipCommand(), new EightBallCommand(),
                new DiceCommand(), new ShutDownCommand(), new ClearCommand(), new PlayCommand(),
                new QueueCommand(), new SkipCommand(), new NowPlayingCommand(), new StopCommand(), new DisconnectCommand());

        game.addEventListener(client.build());

    }

}
