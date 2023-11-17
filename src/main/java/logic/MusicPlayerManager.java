package logic;

import domain.exceptions.MyOwnException;
import domain.queue.Queue;
import domain.queue.QueueElement;
import logic.messages.Observer;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;

public interface MusicPlayerManager {
    void addToQueue(Server server, ServerVoiceChannel voiceChannel, TextChannel textChannel, QueueElement queueElement);

    void startPlaying(ServerVoiceChannel voiceChannel, TextChannel textChannel);

    void playNextSong(ServerVoiceChannel serverVoiceChannel, TextChannel textChannel);

    void restartSong(ServerVoiceChannel voiceChannel, TextChannel textChannel);

    void playPreviousSong(ServerVoiceChannel voiceChannel, TextChannel textChannel);

    void playThisSongNext(ServerVoiceChannel voiceChannel, TextChannel textChannel, QueueElement queueElement);

    Queue getQueueByServer(Server server) throws MyOwnException;

    void stopPlaying(Server server);

    void addQueueMessage(Message message, Observer observer);
}
