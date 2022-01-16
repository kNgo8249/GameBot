package gamebot.commands.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import gamebot.audio.GuildMusicManager;
import gamebot.audio.PlayerManager;

import net.dv8tion.jda.api.entities.GuildVoiceState;

public class StopCommand extends Command {

    public StopCommand() {
        this.name = "stop";
        this.help = "Stops the current song and clears the queue.";
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
        AudioPlayer audioPlayer = musicManager.getScheduler().getPlayer();

        audioPlayer.stopTrack();
        musicManager.getScheduler().getQueue().clear();

        event.reply("The current song has been stopped and the queue has been cleared.");
    }

}
