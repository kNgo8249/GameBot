
package GameBot;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class CoinFlipCommand extends Command {

	public CoinFlipCommand() {
		this.name = "coinflip";
		this.help = "Flip a coin.";
		this.category = new Category("Fun");
		this.guildOnly = false;
	}

	@Override
	protected void execute(CommandEvent event) {
		if (Math.random() < 0.5) {
			event.reply("Heads");
		}
		else {
			event.reply("Tails");
		}

	}

}
