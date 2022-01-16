package gamebot.audio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

public class PlayerManager {

    private static PlayerManager instance;
    private Map<Long, GuildMusicManager> musicManager;
    private AudioPlayerManager audioPlayerManager;

    public PlayerManager() {
        this.musicManager = new HashMap<>();
        this.audioPlayerManager = new DefaultAudioPlayerManager();

        AudioSourceManagers.registerRemoteSources(this.audioPlayerManager);
        AudioSourceManagers.registerLocalSource(this.audioPlayerManager);

    }

    public GuildMusicManager getMusicManager(Guild guild) {

        return this.musicManager.computeIfAbsent(guild.getIdLong(), (guildId) -> {
            GuildMusicManager guildMusicManager = new GuildMusicManager(this.audioPlayerManager);
            guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());
            return guildMusicManager;
        });
    }

    public void loadAndPlay(TextChannel channel, String trackURL) {
        GuildMusicManager musicManager = this.getMusicManager(channel.getGuild());
        this.audioPlayerManager.loadItemOrdered(musicManager, trackURL, new AudioLoadResultHandler() {

            @Override
            public void trackLoaded(AudioTrack track) {
                channel.sendMessage(
                        "Adding to queue: `" + track.getInfo().title + "` by `" + track.getInfo().author + "`").queue();
                musicManager.getScheduler().queue(track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                if (playlist.isSearchResult()) {
                    AudioTrack firstTrack = playlist.getTracks().get(0);
                    channel.sendMessage("Adding to queue: `" + firstTrack.getInfo().title + "` by `"
                            + firstTrack.getInfo().author + "`").queue();
                    musicManager.getScheduler().queue(firstTrack);
                }
                else {
                    List<AudioTrack> tracks = playlist.getTracks();
                    channel.sendMessage("Adding to queue: `" + String.valueOf(tracks.size())
                            + "` tracks from playlist `" + playlist.getName() + "`").queue();
                    for (AudioTrack track : tracks) {
                        musicManager.getScheduler().queue(track);
                    }
                }
            }

            @Override
            public void noMatches() {
            }

            @Override
            public void loadFailed(FriendlyException exception) {
            }
        });
    }

    public static PlayerManager getInstance() {
        if (instance == null) {
            instance = new PlayerManager();
        }
        return instance;
    }

}