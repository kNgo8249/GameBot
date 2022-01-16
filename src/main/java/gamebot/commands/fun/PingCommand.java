package gamebot.commands.fun;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class PingCommand extends Command
{

    public PingCommand() {
        this.name = "ping";
        this.help = "Ping Pong!";
        this.category = new Category("Fun");
        this.guildOnly = false;

    }

    @Override
    protected void execute(CommandEvent event) {
        event.reply("Pong!");
    }


}