package gamebot.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import gamebot.audio.GuildMusicManager;
import gamebot.audio.PlayerManager;

import net.dv8tion.jda.api.entities.GuildVoiceState;

public class SkipCommand extends Command {

    public SkipCommand() {
        this.name = "skip";
        this.help = "Skip the current song and go to the next one in the queue.";
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

        GuildVoiceState memberVoiceState = event.getMember().getVoiceState();
        if (!memberVoiceState.inVoiceChannel()) {
            event.reply("You must be in a voice channel to use this command.");
            return;
        }

        if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            event.reply("You must be in the same voice channel as me for this to work.");
            return;
        }

        GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());
        AudioPlayer audioPlayer = musicManager.getAudioPlayer();

        if(audioPlayer.getPlayingTrack() == null) {
            event.reply("There is no track playing currently.");
            return;
        }

        musicManager.getScheduler().nextTrack();
        event.reply("Skipped current track.");
    }

}
