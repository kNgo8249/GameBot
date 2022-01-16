package gamebot.commands.music;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import gamebot.audio.GuildMusicManager;
import gamebot.audio.PlayerManager;

import net.dv8tion.jda.api.EmbedBuilder;

public class QueueCommand extends Command {

    public QueueCommand() {
        this.name = "queue";
        this.help = "Check the queue of songs to be played.";
        this.category = new Category("Music");
        this.guildOnly = true;
    }

    @Override
    protected void execute(CommandEvent event) {
        GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());
        BlockingQueue<AudioTrack> queue = musicManager.getScheduler().getQueue();
        List<AudioTrack> queueAsList = new ArrayList<AudioTrack>(queue);

        int trackCount = Math.min(queue.size(), 15);

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Current Queue");
        eb.setColor(Color.CYAN);

        AudioTrack currentlyPlaying = musicManager.getAudioPlayer().getPlayingTrack();

        if (trackCount >= 0 && currentlyPlaying != null) {
            eb.appendDescription("Now Playing: `" + currentlyPlaying.getInfo().title + "` by `"
                    + currentlyPlaying.getInfo().author + "` [" + formatTime(currentlyPlaying.getDuration()) + "]\n\n");
        }

        for (int i = 0; i < trackCount; i++) {
            AudioTrack track = queueAsList.get(i);
            eb.appendDescription((i + 1) + " `" + track.getInfo().title + "` by `" + track.getInfo().author + "` ["
                    + formatTime(track.getDuration()) + "]" + "\n");
        }

        if (queueAsList.size() > trackCount) {
            eb.appendDescription("And " + (queueAsList.size() - trackCount) + " more...");
        }

        event.getChannel().sendMessageEmbeds(eb.build()).queue();
    }

    private String formatTime(long time) {
        long hours = time / TimeUnit.HOURS.toMillis(1);
        long minutes = time / TimeUnit.MINUTES.toMillis(1);
        long seconds = time % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

}