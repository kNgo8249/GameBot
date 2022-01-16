package gamebot.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import gamebot.audio.GuildMusicManager;
import gamebot.audio.PlayerManager;

import net.dv8tion.jda.api.entities.GuildVoiceState;

public class NowPlayingCommand extends Command {

    public NowPlayingCommand() {
        this.name = "nowplaying";
        this.aliases = new String[] { "np" };
        this.help = "Shows the current song that is playing.";
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
        AudioPlayer audioPlayer = musicManager.getAudioPlayer();
        AudioTrack track = audioPlayer.getPlayingTrack();

        if (track == null) {
            event.reply("There is no track playing currently.");
            return;
        }

        event.reply("Now Playing: `" + track.getInfo().title + "` by `" + track.getInfo().author + "` (Link: <"
                + track.getInfo().uri + ">)");
    }

}