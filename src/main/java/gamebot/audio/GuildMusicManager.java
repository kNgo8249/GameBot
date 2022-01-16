package gamebot.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

public class GuildMusicManager {

    private TrackScheduler scheduler;
    private AudioPlayer audio;

    private AudioPlayerSendHandler sendHandler;

    public GuildMusicManager(AudioPlayerManager manager) {
        this.audio = manager.createPlayer();
        this.scheduler = new TrackScheduler(this.audio);
        this.audio.addListener(this.scheduler);
        this.sendHandler = new AudioPlayerSendHandler(this.audio);
    }

    public AudioPlayerSendHandler getSendHandler() {
        return sendHandler;
    }

    public TrackScheduler getScheduler() {
        return scheduler;
    }

    public AudioPlayer getAudioPlayer() {
        return audio;
    }

}
