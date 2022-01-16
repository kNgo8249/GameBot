package gamebot.commands.fun;

import java.awt.Color;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.api.EmbedBuilder;

public class DiceCommand extends Command {

    public DiceCommand() {
        this.name = "r";
        this.help = "Roll a dice! Command Syntax: \"!r xdy + z (Ex. !r 1d20 + 3)\"";
        this.category = new Category("Fun");
        this.guildOnly = false;
        this.aliases = new String[] {"roll", "dice", "diceroll"};

    }

    @Override
    protected void execute(CommandEvent event) {
        if (event.getArgs().trim().isEmpty()) {
            event.reply("Please specify a dice to roll");
        }
        else {
            try {
                int sides;
                int modifier = 0;

                String[] args = event.getArgs().split("d");
                int diceAmount = Integer.parseInt(args[0]);

                if(args[1].contains("+")) {
                    String[] rightSide = args[1].split(" *\\+ *");
                    sides = Integer.parseInt(rightSide[0]);
                    modifier = Integer.parseInt(rightSide[1]);
                }
                else if(args[1].contains("-")) {
                    String[] rightSide = args[1].split(" *\\- *");
                    sides = Integer.parseInt(rightSide[0]);
                    modifier = -(Integer.parseInt(rightSide[1]));
                }
                else {
                    sides = Integer.parseInt(args[1]);
                }

                int totalNoMod = 0;

                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle(diceAmount + "d" + sides + " roll by " + event.getAuthor().getAsTag());
                eb.setColor(Color.RED);


                for(int i = 0; i < diceAmount; i++) {
                    int randomIndex = (int) (Math.random() * sides) + 1;
                    eb.appendDescription("Roll " + (i + 1) + ": " + randomIndex + "\n");
                    totalNoMod += randomIndex;
                }

                int total = totalNoMod + modifier;

                eb.appendDescription("\nTOTAL: " + totalNoMod + " + " + modifier + " = " + total);

                event.getChannel().sendMessageEmbeds(eb.build()).queue();
            }
            catch(Exception e) {
                event.reply("Improper syntax!");
            }
        }
    }

}

