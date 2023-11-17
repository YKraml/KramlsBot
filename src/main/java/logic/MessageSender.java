package logic;

import domain.GuessingGame;
import domain.exceptions.MyOwnException;
import domain.queue.QueueElement;
import domain.waifu.Group;
import domain.waifu.Player;
import domain.waifu.Waifu;
import domain.waifu.dungeon.Dungeon;
import domain.waifu.dungeon.Inventory;
import domain.waifu.dungeon.Team;
import logic.music.audio.MusicPlayerManager;
import logic.routines.RoutineRevealBuilder;
import logic.routines.RoutineRunner;
import logic.waifu.JikanFetcher;
import logic.waifu.PlayerLoader;
import logic.waifu.WaifuLoader;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.util.List;

public interface MessageSender {

    Message send(MyMessage myMessage, TextChannel textChannel) throws MyOwnException;

    void sendSafe(MyMessage myMessage, TextChannel textChannel);

    void sendMerged(TextChannel channel, Player player, Waifu waifu) throws MyOwnException;

    void sendWaifusAreDifferent(TextChannel channel, Player player) throws MyOwnException;

    void sendGroupList(TextChannel channel, Player player, PlayerLoader playerLoader, WaifuLoader waifuLoader, JikanFetcher jikanFetcher, MessageSender messageSender) throws MyOwnException;

    void sendGuessGameStarted(TextChannel textChannel, RoutineRunner routineRunner, RoutineRevealBuilder routineRevealBuilder) throws MyOwnException;

    void sendSafeNoMatchForSongMessage(TextChannel channel);

    void sendSafeCouldNotLoadSongMessage(TextChannel channel);

    void sendWaifuSpawned(TextChannel channel, Player player) throws MyOwnException;

    void sendWaifuStats(TextChannel channel, Waifu waifu, Player player, PlayerLoader playerLoader, WaifuLoader waifuLoader, JikanFetcher jikanFetcher, MessageSender messageSender) throws MyOwnException;

    void sendTeamKilled(TextChannel textChannel, Team team, int level, Inventory inventory) throws MyOwnException;

    void sendTeamIsLow(TextChannel textChannel, Team team) throws MyOwnException;

    void sendDailyAlreadyUsed(TextChannel channel, Player player, String newDate) throws MyOwnException;

    void sendDailyUsed(TextChannel channel, Player player) throws MyOwnException;

    Message sendFightRequest(TextChannel textChannel, User user, User userEnemy, long money, long stardust, long morphStones) throws MyOwnException;

    void sendGaveWaifu(TextChannel channel, Player senderPlayer, Player receiverPlayer, Waifu waifu) throws MyOwnException;

    void sendWaifuNotFound(TextChannel channel, int waifuNumber) throws MyOwnException;

    void sendTimeIsUpMessage(TextChannel channel) throws MyOwnException;

    void sendWaifuToClaimWas(TextChannel textChannel, Waifu waifu) throws MyOwnException;

    void sendWaifuSpawn(TextChannel textChannel, Waifu newWaifu) throws MyOwnException;

    void sendSafeExceptionHappenedMessage(TextChannel textChannel, MyOwnException e);

    void sendWaifusDeleted(TextChannel channel, Player player, int deletedWaifus, int stardust, int cookies) throws MyOwnException;

    void sendTeamRenamedMessage(TextChannel channel, Player player, String oldName, String newName) throws MyOwnException;

    void sendGuessedRight(TextChannel channel, Player player) throws MyOwnException;

    void sendWonMoney(TextChannel channel, Player player, long wonMoney) throws MyOwnException;

    void sendSafeDungeonMessage(TextChannel channel, Dungeon dungeon, List<Team> teams);

    void sendLostMoney(TextChannel channel, Player player, long bettedMoney) throws MyOwnException;

    void sendGaveMoney(TextChannel channel, Player giverPlayer, Player receiverPlayer, int money) throws MyOwnException;

    void sendSongAdded(TextChannel channel, QueueElement queueElement, MusicPlayerManager musicPlayerManager, PlayerLoader playerLoader) throws MyOwnException;

    void sendGuessedWrong(TextChannel channel, Player player) throws MyOwnException;

    void sendGroupCreated(TextChannel channel, String groupName) throws MyOwnException;

    void sendLikedSongs(TextChannel channel, Player player, Server server, User user, PlayerLoader playerLoader, MessageSender messageSender, MusicPlayerManager musicPlayerManager) throws MyOwnException;

    void sendDungeonCreatedMessage(TextChannel channel, Dungeon dungeon) throws MyOwnException;

    void sendWaifuList(TextChannel channel, Player player, MessageSender messageSender, PlayerLoader playerLoader, WaifuLoader waifuLoader, JikanFetcher jikanFetcher) throws MyOwnException;

    void sendGuessGameEnd(TextChannel channel, GuessingGame guessingGame) throws MyOwnException;

    void sendGroupOverview(TextChannel channel, Group group, Player player, PlayerLoader playerLoader, WaifuLoader waifuLoader, JikanFetcher jikanFetcher, MessageSender messageSender) throws MyOwnException;
}
