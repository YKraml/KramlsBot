package ui.messages;

import domain.exceptions.MyOwnException;
import domain.exceptions.messages.CouldNotGetContent;
import domain.exceptions.messages.CouldNotSendMessage;
import domain.exceptions.messages.CouldNotStartRoutine;
import domain.queue.QueueElement;
import domain.waifu.Player;
import domain.waifu.Waifu;
import domain.waifu.dungeon.Dungeon;
import domain.waifu.dungeon.Inventory;
import domain.waifu.dungeon.Team;
import logic.MessageSender;
import logic.MyMessage;
import logic.music.audio.MusicPlayerManager;
import logic.routines.RoutineRevealBuilder;
import logic.routines.RoutineRunner;
import logic.waifu.JikanFetcher;
import logic.waifu.PlayerLoader;
import logic.waifu.WaifuLoader;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import ui.messages.messages.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MessageSenderImpl implements MessageSender {


    @Override
    public Message send(MyMessage myMessage, TextChannel textChannel) throws MyOwnException {

        EmbedBuilder content;
        try {
            content = myMessage.getContent();
        } catch (MyOwnException e) {
            throw new MyOwnException(new CouldNotGetContent(), e);
        }

        if (!textChannel.canYouWrite()) {
            throw new MyOwnException(new CouldNotSendMessage(myMessage.getClass().getSimpleName()), null);
        }

        CompletableFuture<Message> sentMessageFuture = textChannel.sendMessage(content);
        Message sentMessage = sentMessageFuture.join();

        try {
            myMessage.startRoutine(sentMessage);
        } catch (MyOwnException e) {
            throw new MyOwnException(new CouldNotStartRoutine(), e);
        }

        return sentMessage;
    }

    @Override
    public void sendSafe(MyMessage myMessage, TextChannel textChannel) {

        try {
            CompletableFuture<Message> future = textChannel.sendMessage(myMessage.getContent());
            myMessage.startRoutine(future.join());
        } catch (MyOwnException ignored) {
            //Ignore
        }


    }

    @Override
    public void sendMerged(TextChannel channel, Player player, Waifu waifu) throws MyOwnException {
        send(new Merged(player, waifu), channel);
    }

    @Override
    public void sendWaifusAreDifferent(TextChannel channel, Player player) throws MyOwnException {
        send(new WaifusAreDifferent(player), channel);
    }

    @Override
    public void sendGroupList(TextChannel channel, Player player, PlayerLoader playerLoader, WaifuLoader waifuLoader, JikanFetcher jikanFetcher, MessageSender messageSender) throws MyOwnException {
        send(new GroupList(player, playerLoader, waifuLoader, jikanFetcher, messageSender), channel);
    }

    @Override
    public void sendGuessGameStarted(TextChannel textChannel, RoutineRunner routineRunner, RoutineRevealBuilder routineRevealBuilder) throws MyOwnException {
        send(new GuessGameStarted(routineRunner, routineRevealBuilder), textChannel);
    }

    @Override
    public void sendSafeNoMatchForSongMessage(TextChannel channel) {
        sendSafe(new NoMatchForSongMessage(), channel);
    }

    @Override
    public void sendSafeCouldNotLoadSongMessage(TextChannel channel) {
        sendSafe(new CouldNotLoadSongMessage(), channel);
    }

    @Override
    public void sendWaifuSpawned(TextChannel channel, Player player) throws MyOwnException {
        send(new WaifuSpawned(player), channel);
    }

    @Override
    public void sendWaifuStats(TextChannel channel, Waifu waifu, Player player, PlayerLoader playerLoader, WaifuLoader waifuLoader, JikanFetcher jikanFetcher, MessageSender messageSender) throws MyOwnException {
        send(new WaifuStats(waifu, player, playerLoader, waifuLoader, jikanFetcher, messageSender), channel);
    }

    @Override
    public void sendTeamKilled(TextChannel channel, Team team, int level, Inventory inventory) throws MyOwnException {
        send(new TeamKilled(team, level, inventory), channel);
    }

    @Override
    public void sendTeamIsLow(TextChannel textChannel, Team team) throws MyOwnException {
        send(new TeamIsLow(team), textChannel);
    }

    @Override
    public void sendDailyAlreadyUsed(TextChannel channel, Player player, String newDate) throws MyOwnException {
        send(new DailyAlreadyUsed(player, newDate), channel);
    }

    @Override
    public void sendDailyUsed(TextChannel channel, Player player) throws MyOwnException {
        send(new DailyUsed(player), channel);
    }

    @Override
    public Message sendFightRequest(TextChannel textChannel, User user, User userEnemy, long money, long stardust, long morphStones) throws MyOwnException {
        return send(new FightRequest(user, userEnemy, money, stardust, morphStones), textChannel);
    }

    @Override
    public void sendGaveWaifu(TextChannel channel, Player senderPlayer, Player receiverPlayer, Waifu waifu) throws MyOwnException {
        send(new GaveWaifu(senderPlayer, receiverPlayer, waifu), channel);
    }

    @Override
    public void sendWaifuNotFound(TextChannel channel, int waifuNumber) throws MyOwnException {
        send(new WaifuNotFound(waifuNumber), channel);
    }

    @Override
    public void sendTimeIsUpMessage(TextChannel channel) throws MyOwnException {
        send(new TimeIsUpMessage(), channel);
    }

    @Override
    public void sendWaifuToClaimWas(TextChannel textChannel, Waifu waifu) throws MyOwnException {
        send(new WaifuToClaimWas(waifu), textChannel);
    }

    @Override
    public void sendWaifuSpawn(TextChannel textChannel, Waifu newWaifu) throws MyOwnException {
        send(new WaifuSpawn(newWaifu), textChannel);
    }

    @Override
    public void sendSafeExceptionHappenedMessage(TextChannel textChannel, MyOwnException e) {
        sendSafe(new ExceptionHappenedMessage(e), textChannel);
    }

    @Override
    public void sendWaifusDeleted(TextChannel channel, Player player, int deletedWaifus, int stardust, int cookies) throws MyOwnException {
        send(new WaifusDeleted(player, deletedWaifus, stardust, cookies), channel);
    }

    @Override
    public void sendTeamRenamedMessage(TextChannel channel, Player player, String oldName, String newName) throws MyOwnException {
        send(new TeamRenamedMessage(player, oldName, newName), channel);
    }

    @Override
    public void sendGuessedRight(TextChannel channel, Player player) throws MyOwnException {
        send(new GuessedRight(player), channel);
    }

    @Override
    public void sendWonMoney(TextChannel channel, Player player, long wonMoney) throws MyOwnException {
        send(new WonMoney(player, wonMoney), channel);
    }

    @Override
    public void sendSafeDungeonMessage(TextChannel channel, Dungeon dungeon, List<Team> teams) {
        sendSafe(new DungeonMessage(dungeon, teams), channel);
    }

    @Override
    public void sendLostMoney(TextChannel channel, Player player, long bettedMoney) throws MyOwnException {
        send(new LostMoney(player, bettedMoney), channel);
    }

    @Override
    public void sendGaveMoney(TextChannel channel, Player giverPlayer, Player receiverPlayer, int money) throws MyOwnException {
        send(new GaveMoney(giverPlayer, receiverPlayer, money), channel);
    }

    @Override
    public void sendSongAdded(TextChannel channel, QueueElement queueElement, MusicPlayerManager musicPlayerManager, PlayerLoader playerLoader) throws MyOwnException {
        send(new SongAdded(queueElement, musicPlayerManager, playerLoader), channel);
    }

    @Override
    public void sendGuessedWrong(TextChannel channel, Player player) throws MyOwnException {
        send(new GuessedWrong(player), channel);
    }

    @Override
    public void sendGroupCreated(TextChannel channel, String groupName) throws MyOwnException {
        send(new GroupCreated(groupName), channel);
    }


}
