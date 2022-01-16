// Future Updates: Send a log message

// NOTE: The multi-line comments is the code for a different implementation of the clear command.
//////// The code is still here in case the purgeMessages() doesn't work out long term (purgeMessages sometimes has errors)

package gamebot.commands.moderation;

import java.util.List;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageHistory;
//import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

public class ClearCommand extends Command {

    private List<Message> messageHistory;

    public ClearCommand() {
        this.name = "clear";
        this.help = "Clear a text channel. Syntax: \"!clear #channel amount reason\"";
        this.category = new Category("Moderation");
        this.userPermissions = new Permission[] { Permission.MESSAGE_MANAGE };
    }

    @Override
    protected void execute(CommandEvent event) {
        String[] arguments = event.getMessage().getContentRaw().split(" ");

        if (arguments.length < 3) {
            event.reply("ERROR: Proper usage: `!clear #channel amount reason`");
        }
        else {
            try {
                MessageChannel channel = event.getMessage().getMentionedChannels().get(0);
                MessageHistory history = new MessageHistory(channel);
                messageHistory = history.retrievePast(Integer.parseInt(arguments[2])).complete();
                channel.purgeMessages(messageHistory);
                /*
                 * TextChannel clearedChannel =
                 * event.getMessage().getMentionedChannels().get(0); MessageHistory history =
                 * new MessageHistory(clearedChannel); messageHistory =
                 * history.retrievePast(Integer.parseInt(arguments[2])).complete();
                 * clearedChannel.deleteMessages(messageHistory).queue();
                 */
            }
            catch (IndexOutOfBoundsException e) {
                event.reply("ERROR: Channel not found!");
            }
            catch (IllegalArgumentException e) {
                /*
                 * if (e.getMessage().contains("Message Id provided was older than 2 weeks")) {
                 *
                 * for(Message m : messageHistory) { System.out.println(m); m.delete().queue();
                 * } } else { event.reply("ERROR: Must specify an amount between 1-100!"); }
                 */
                event.reply("ERROR: Must specify an amount between 1-100!");
            }
            catch (InsufficientPermissionException e) {
                event.reply("ERROR: Don't have permission!");
            }
        }
    }

}
