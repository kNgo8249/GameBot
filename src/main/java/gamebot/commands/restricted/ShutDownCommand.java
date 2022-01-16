package gamebot.commands.restricted;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class ShutDownCommand extends Command {

    public ShutDownCommand() {
        this.name = "shutdown";
        this.help = "Shuts down bot.";
        this.category = new Category("Restricted");
        this.guildOnly = false;
        this.ownerCommand = true;
    }

    @Override
    protected void execute(CommandEvent event) {
        event.getJDA().shutdown();

    }

}