// A simple bot made in JDA 4 originally for HackUNT
// By Josh Durana and Kenny Ngo

package GameBot;

import com.jagrosh.jdautilities.command.CommandClientBuilder;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Bot {
	public static void main(String[] args) throws Exception {
		
		// Can't show client token online
		JDA game = new JDABuilder("").build();
		
		
		// Initialize Commands
		
		CommandClientBuilder client = new CommandClientBuilder();
		
		// MY ID
		// Discord IDs ommited for privacy reasons
		client.setOwnerId("");
		client.setCoOwnerIds("", "", "");
		
		client.setPrefix("!");
		client.addCommands(new PingCommand(), new TicTacToeCommand(), new CoinFlipCommand(), new EightBallCommand(), new DiceCommand(), new ShutDownCommand(), new ClearCommand());
		game.addEventListener(client.build());
		
		

	}
}
