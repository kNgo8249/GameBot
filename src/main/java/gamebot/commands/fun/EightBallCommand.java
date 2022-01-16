package gamebot.commands.fun;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class EightBallCommand extends Command {

    private String[] replies = { "It is certain", "It is decidedly so", "Without a doubt", "Yes - Definetly",
            "You may rely on it", "As I see it, yes", "Most Likely", "Outlook good", "Yes", "Signs point to yes",
            "Reply hazy, try again", "Ask Again later", "Better not tell you now", "Cannot predict now",
            "Concentrate and ask again", "Don't count on it", "My Reply is no", "My Sources say no",
            "Outlook not so good", "Very doubtul" };

    public EightBallCommand() {
        this.name = "8ball";
        this.help = "See what the Magic 8 Ball says!";
        this.category = new Category("Fun");
        this.guildOnly = false;
    }

    @Override
    protected void execute(CommandEvent event) {
        if (event.getArgs().trim().isEmpty()) {
            event.reply("Please ask the 8 Ball a yes/no question.");
        }

        else {
            int randomIndex = (int) (Math.random() * replies.length);
            event.reply(":8ball: " + replies[randomIndex] + " :8ball:");
        }
    }

}