// A simple bot made in JDA 4 originally for HackUNT
// By Josh Durana and Kenny Ngo
// NOTE (for Eclipse users at least): MUST TERMINATE PROGRAM after running it to prevent glitches

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
		client.setOwnerId("678284229230067717");
		client.setCoOwnerIds("406643596767526933", "238529603814031360", "189941337053724672");
		
		client.setPrefix("!");
		client.addCommands(new PingCommand(), new TicTacToeCommand(), new CoinFlipCommand(), new EightBallCommand(), new DiceCommand(), new ShutDownCommand(), new ClearCommand());
		game.addEventListener(client.build());
		
		

	}
}
