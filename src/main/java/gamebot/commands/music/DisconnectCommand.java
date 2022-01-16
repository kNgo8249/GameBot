package gamebot.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import gamebot.audio.GuildMusicManager;
import gamebot.audio.PlayerManager;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.managers.AudioManager;

public class DisconnectCommand extends Command {

    public DisconnectCommand() {
        this.name = "disconnect";
        this.aliases = new String[] { "dc", "leave" };
        this.help = "Disconnects the bot from the voice channel.";
        this.category = new Category("Music");
        this.guildOnly = true;
    }

    @Override
    protected void execute(CommandEvent event) {
        GuildVoiceState selfVoiceState = event.getSelfMember().getVoiceState();

        if (!selfVoiceState.inVoiceChannel()) {
            event.reply("I must be in a voice channel for this to work.");
            return;
        }

        GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());
        musicManager.getScheduler().getQueue().clear();
        musicManager.getAudioPlayer().stopTrack();


        AudioManager audioManager = event.getGuild().getAudioManager();
        audioManager.closeAudioConnection();
        event.reply("I have left the voice channel.");
    }

}