package gamebot.commands.music;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import gamebot.audio.PlayerManager;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class PlayCommand extends Command {

    public PlayCommand() {
        this.name = "play";
        this.help = "Play a song. Command Syntax: \"!play <Youtube URL>\"";
        this.category = new Category("Music");
        this.guildOnly = true;
    }

    @Override
    protected void execute(CommandEvent event) {

        GuildVoiceState selfVoiceState = event.getSelfMember().getVoiceState();

        GuildVoiceState memberVoiceState = event.getMember().getVoiceState();
        if (!memberVoiceState.inVoiceChannel()) {
            event.reply("You must be in a voice channel to use this command.");
            return;
        }

        if (!selfVoiceState.inVoiceChannel()) {
            AudioManager audio = event.getGuild().getAudioManager();
            VoiceChannel memberChannel = event.getMember().getVoiceState().getChannel();

            audio.openAudioConnection(memberChannel);
            event.reply("Connecting to :loud_sound: `" + memberChannel.getName() + "`");
        }

        if (event.getArgs().trim().isEmpty()) {
            event.reply("Please specify a youtube link.");
            return;
        }

        String link = String.join(" ", event.getArgs());
        if (!isURL(link)) {
            link = "ytsearch:" + link;
        }

        PlayerManager.getInstance().loadAndPlay(event.getTextChannel(), link);
    }

    private boolean isURL(String url) {
        try {
            new URL(url).toURI();
            return true;
        }
        catch (MalformedURLException e) {
            return false;
        }
        catch(URISyntaxException e) {
            return false;
        }
    }

}
